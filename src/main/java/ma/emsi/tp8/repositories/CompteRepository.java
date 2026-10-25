package ma.emsi.tp8.repositories;

import ma.emsi.tp8.entities.Compte;
import ma.emsi.tp8.entities.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    List<Compte> findByType(TypeCompte type);
}
