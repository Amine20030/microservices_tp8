package ma.emsi.tp8;

import ma.emsi.tp8.entities.Compte;
import ma.emsi.tp8.entities.TypeCompte;
import ma.emsi.tp8.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class MicroserviceTp8Application {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceTp8Application.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(CompteRepository compteRepository) {
        return args -> {
            compteRepository.save(new Compte(null, 4500.0, new Date(), TypeCompte.COURANT));
            compteRepository.save(new Compte(null, 15000.0, new Date(), TypeCompte.EPARGNE));
            compteRepository.save(new Compte(null, 8200.0, new Date(), TypeCompte.COURANT));

            System.out.println("================================================================================");
            System.out.println("  TP 8 : SPRING MVC @RestController (JSON & XML SUPPORT)");
            System.out.println("================================================================================");
            compteRepository.findAll().forEach(compte -> {
                System.out.println("-> Compte ID " + compte.getId() + " | Solde: " + String.format("%.2f", compte.getSolde()) + " DH | Type: " + compte.getType());
            });
            System.out.println("================================================================================");
        };
    }
}
