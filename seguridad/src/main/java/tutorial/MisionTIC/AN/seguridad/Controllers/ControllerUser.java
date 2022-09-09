package tutorial.MisionTIC.AN.seguridad.Controllers;

import tutorial.MisionTIC.AN.seguridad.Models.Role;
import tutorial.MisionTIC.AN.seguridad.Models.User;
import tutorial.MisionTIC.AN.seguridad.Repositories.RepositoryRole;
import tutorial.MisionTIC.AN.seguridad.Repositories.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")

public class ControllerUser {
    @Autowired
    private RepositoryUser myRepositoryUser;

    @Autowired
    private RepositoryRole myRepositoryRole;

    /**
     * List all Users
     * ***/
    @GetMapping("")
    public List<User> index(){
        return this.myRepositoryUser.findAll();
    }

    /**
     * User Creation Method
     **/

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody User infoUser){
        infoUser.setPassword(convertSHA256(infoUser.getPassword()));
        return this.myRepositoryUser.save(infoUser);
    }

    /**
     * Get user by Id
     **/
    @GetMapping("{id}")
    public User show(@PathVariable String id){
        User currentUser = this.myRepositoryUser
                .findById(id)
                .orElse( null);
        return currentUser;
    }
    /**
     * Update user by Id
     **/
    @PutMapping("{id}")
    public User update(@PathVariable String id, @RequestBody User infoUser){
        User currentUser = this.myRepositoryUser
                .findById(id)
                .orElse( null );
        if (currentUser != null) {
            currentUser.setUsername(infoUser.getUsername());
            currentUser.setEmail(infoUser.getEmail());
            currentUser.setPassword(convertSHA256(infoUser.getPassword()));
            return this.myRepositoryUser.save(currentUser);
        }else {
            return null;
        }
    }

    /**
     * Delete User by Id
     **/
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        User currentUser = this.myRepositoryUser
                .findById(id)
                .orElse( null );
        if(currentUser != null){
            this.myRepositoryUser.delete(currentUser);
        }
    }
    /**
     * Assigne Role to an User
     **/
    public User assingRoletoUser(@PathVariable String id, @PathVariable String id_role){
        User currentUser=this.myRepositoryUser.findById(id).orElseThrow(RuntimeException::new);
        Role currentRole = this.myRepositoryRole.findById(id_role).orElseThrow(RuntimeException::new);
        currentUser.setRole(currentRole);
        return  this.myRepositoryUser.save(currentUser);
    }

    /**
     * encrypt function
     **/
    public String convertSHA256(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (byte b : hash){
            sb.append(String.format("%02x" , b));
        }
        return sb.toString();
    }
}
