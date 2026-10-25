package ma.emsi.tp8;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.emsi.tp8.entities.Compte;
import ma.emsi.tp8.entities.TypeCompte;
import ma.emsi.tp8.repositories.CompteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long sampleId;

    @BeforeEach
    void setUp() {
        compteRepository.deleteAll();
        Compte c1 = compteRepository.save(new Compte(null, 5000.0, new Date(), TypeCompte.COURANT));
        compteRepository.save(new Compte(null, 12000.0, new Date(), TypeCompte.EPARGNE));
        sampleId = c1.getId();
    }

    @Test
    @DisplayName("1. Récupération de tous les comptes au format JSON (GET /banque/comptes)")
    void testGetAllComptesJson() throws Exception {
        mockMvc.perform(get("/banque/comptes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].solde", is(5000.0)))
                .andExpect(jsonPath("$[0].type", is("COURANT")));
    }

    @Test
    @DisplayName("2. Récupération de tous les comptes au format XML (GET /banque/comptes)")
    void testGetAllComptesXml() throws Exception {
        mockMvc.perform(get("/banque/comptes")
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
                .andExpect(xpath("/List/item").nodeCount(2));
    }

    @Test
    @DisplayName("3. Consultation d'un compte par ID (GET /banque/comptes/{id})")
    void testGetCompteById() throws Exception {
        mockMvc.perform(get("/banque/comptes/{id}", sampleId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(sampleId.intValue())))
                .andExpect(jsonPath("$.solde", is(5000.0)))
                .andExpect(jsonPath("$.type", is("COURANT")));
    }

    @Test
    @DisplayName("4. Création d'un nouveau compte bancaire (POST /banque/comptes)")
    void testCreateCompte() throws Exception {
        Compte nouveau = new Compte(null, 9500.0, new Date(), TypeCompte.EPARGNE);

        mockMvc.perform(post("/banque/comptes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nouveau)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.solde", is(9500.0)))
                .andExpect(jsonPath("$.type", is("EPARGNE")));
    }

    @Test
    @DisplayName("5. Suppression d'un compte bancaire (DELETE /banque/comptes/{id})")
    void testDeleteCompte() throws Exception {
        mockMvc.perform(delete("/banque/comptes/{id}", sampleId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/banque/comptes/{id}", sampleId))
                .andExpect(status().isNotFound());
    }
}
