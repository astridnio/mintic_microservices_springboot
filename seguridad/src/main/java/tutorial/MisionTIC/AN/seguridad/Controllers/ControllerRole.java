package tutorial.MisionTIC.AN.seguridad.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tutorial.MisionTIC.AN.seguridad.Exception.ResourceNotFoundException;
import tutorial.MisionTIC.AN.seguridad.Models.Role;
import tutorial.MisionTIC.AN.seguridad.Models.User;
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
    public ResponseEntity<Role> show(@PathVariable String id){
        Role currentRole = this.myRepositoryRole
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
        return ResponseEntity.ok(currentRole);
    }

    @PutMapping("{id}")
    public ResponseEntity<Role> update(@PathVariable String id, @RequestBody Role infoRole){
        Role currentRole = this.myRepositoryRole
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
            currentRole.setName(infoRole.getName());
            currentRole.setDescription(infoRole.getDescription());
            myRepositoryRole.save(currentRole);
            return ResponseEntity.ok(currentRole);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id){
        Role currentRole = this.myRepositoryRole
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
        myRepositoryRole.delete(currentRole);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
