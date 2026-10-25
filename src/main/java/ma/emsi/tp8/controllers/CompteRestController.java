package ma.emsi.tp8.controllers;

import ma.emsi.tp8.entities.Compte;
import ma.emsi.tp8.entities.TypeCompte;
import ma.emsi.tp8.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banque")
public class CompteRestController {

    private final CompteService compteService;

    public CompteRestController(CompteService compteService) {
        this.compteService = compteService;
    }

    @GetMapping(value = "/comptes", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Compte>> getAllComptes() {
        List<Compte> comptes = compteService.getAllComptes();
        return ResponseEntity.ok(comptes);
    }

    @GetMapping(value = "/comptes/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Compte> getCompteById(@PathVariable("id") Long id) {
        return compteService.getCompteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/comptes/type/{type}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Compte>> getComptesByType(@PathVariable("type") TypeCompte type) {
        return ResponseEntity.ok(compteService.getComptesByType(type));
    }

    @PostMapping(value = "/comptes", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Compte> createCompte(@RequestBody Compte compte) {
        Compte created = compteService.createCompte(compte);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping(value = "/comptes/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Compte> updateCompte(@PathVariable("id") Long id, @RequestBody Compte compte) {
        return compteService.updateCompte(id, compte)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable("id") Long id) {
        if (compteService.deleteCompte(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
