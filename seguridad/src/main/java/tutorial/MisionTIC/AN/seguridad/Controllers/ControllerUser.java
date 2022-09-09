package tutorial.MisionTIC.AN.seguridad.Controllers;

import org.springframework.http.ResponseEntity;
import tutorial.MisionTIC.AN.seguridad.Exception.ResourceNotFoundException;
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
    public ResponseEntity<User> show(@PathVariable String id){
        User currentUser = this.myRepositoryUser
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
        return ResponseEntity.ok(currentUser);
    }
    /**
     * Update user by Id
     **/
    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody User infoUser){
        User currentUser = this.myRepositoryUser
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
            currentUser.setUsername(infoUser.getUsername());
            currentUser.setEmail(infoUser.getEmail());
            currentUser.setPassword(convertSHA256(infoUser.getPassword()));
            myRepositoryUser.save(currentUser);
            return ResponseEntity.ok(currentUser);
    }

    /**
     * Delete User by Id
     **/
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id){
        User currentUser = this.myRepositoryUser
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
        myRepositoryUser.delete(currentUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /**
     * Assigne Role to an User
     **/
    @PutMapping("{id}/role/{id_role}")
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
