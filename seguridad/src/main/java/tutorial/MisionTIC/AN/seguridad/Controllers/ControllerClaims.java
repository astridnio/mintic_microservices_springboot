package tutorial.MisionTIC.AN.seguridad.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tutorial.MisionTIC.AN.seguridad.Exception.ResourceNotFoundException;
import tutorial.MisionTIC.AN.seguridad.Models.Claim;
import tutorial.MisionTIC.AN.seguridad.Repositories.RepositoryClaim;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/claim")
public class ControllerClaims {

    @Autowired
    private RepositoryClaim myRepositoryClaim;

    @GetMapping("")
    public List<Claim> index(){
        return this.myRepositoryClaim.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Claim create(@RequestBody Claim infoClaim){
        return this.myRepositoryClaim.save(infoClaim);
    }

    @GetMapping("{id}")
    public ResponseEntity<Claim> show(@PathVariable String id){
        Claim currentClaim = this.myRepositoryClaim
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
        return ResponseEntity.ok(currentClaim);
    }

    @PutMapping("{id}")
    public ResponseEntity<Claim> update(@PathVariable String id, @RequestBody Claim infoClaim){
        Claim currentClaim = this.myRepositoryClaim
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
        currentClaim.setUrl(infoClaim.getUrl());
        currentClaim.setMethod(infoClaim.getMethod());
        myRepositoryClaim.save(currentClaim);
        return ResponseEntity.ok(currentClaim);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id){
        Claim currentClaim = this.myRepositoryClaim
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
        myRepositoryClaim.delete(currentClaim);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
