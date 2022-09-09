package tutorial.MisionTIC.AN.seguridad.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tutorial.MisionTIC.AN.seguridad.Models.Role;
import tutorial.MisionTIC.AN.seguridad.Repositories.RepositoryRole;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/role")
public class ControllerRole {
    @Autowired
    private RepositoryRole myRepositoryRole;

    @GetMapping("")
    public List<Role> index(){
        return this.myRepositoryRole.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Role create(@RequestBody Role infoRole){
        return this.myRepositoryRole.save(infoRole);
    }

    @GetMapping("{id}")
    public Role show(@PathVariable String id){
        Role currentRole = this.myRepositoryRole
                .findById(id)
                .orElse(null);
        return currentRole;
    }

    @PutMapping("{id}")
    public Role update(@PathVariable String id, @RequestBody Role infoRole){
        Role currentRole = this.myRepositoryRole
                .findById(id)
                .orElse(null);
        if (currentRole!=null){
            currentRole.setName(infoRole.getName());
            currentRole.setDescription(infoRole.getDescription());
            return this.myRepositoryRole.save(currentRole);
        }else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Role currentRole = this.myRepositoryRole
                .findById(id)
                .orElse(null);
        if(currentRole!=null){
            this.myRepositoryRole.delete(currentRole);
        }
    }
}
