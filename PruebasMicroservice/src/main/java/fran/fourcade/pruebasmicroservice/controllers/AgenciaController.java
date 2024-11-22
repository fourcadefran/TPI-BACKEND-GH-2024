package fran.fourcade.pruebasmicroservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fran.fourcade.pruebasmicroservice.models.Agencia;
import fran.fourcade.pruebasmicroservice.repositories.AgenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/agencias")
public class AgenciaController {

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/sync")
    public ResponseEntity<String> sincronizarAgencia() {
        String endpointUrl = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(endpointUrl, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Agencia agencia = objectMapper.readValue(response.getBody(), Agencia.class);


                agenciaRepository.save(agencia);

                return ResponseEntity.ok("Agencia sincronizada exitosamente.");
            } else {
                return ResponseEntity.status(response.getStatusCode())
                        .body("Error al obtener la respuesta del servidor externo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al sincronizar la agencia: " + e.getMessage());
        }
    }
}

