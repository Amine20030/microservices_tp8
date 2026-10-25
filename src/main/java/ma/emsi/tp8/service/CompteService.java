package ma.emsi.tp8.service;

import ma.emsi.tp8.entities.Compte;
import ma.emsi.tp8.entities.TypeCompte;
import ma.emsi.tp8.repositories.CompteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompteService {

    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Transactional(readOnly = true)
    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Compte> getCompteById(Long id) {
        return compteRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Compte> getComptesByType(TypeCompte type) {
        return compteRepository.findByType(type);
    }

    public Compte createCompte(Compte compte) {
        if (compte.getDateCreation() == null) {
            compte.setDateCreation(new Date());
        }
        return compteRepository.save(compte);
    }

    public Optional<Compte> updateCompte(Long id, Compte compte) {
        return compteRepository.findById(id).map(existing -> {
            existing.setSolde(compte.getSolde());
            if (compte.getType() != null) {
                existing.setType(compte.getType());
            }
            if (compte.getDateCreation() != null) {
                existing.setDateCreation(compte.getDateCreation());
            }
            return compteRepository.save(existing);
        });
    }

    public boolean deleteCompte(Long id) {
        if (compteRepository.existsById(id)) {
            compteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
