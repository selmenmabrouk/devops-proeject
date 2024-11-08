package com.example.gestionstationskii;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.gestionstationskii.controllers.PisteRestController;
import com.example.gestionstationskii.entities.Color;
import com.example.gestionstationskii.entities.Piste;
import com.example.gestionstationskii.services.PisteServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PisteRestController.class)
public class PisteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PisteServicesImpl pisteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddPiste() throws Exception {
        Piste piste = new Piste(1L, "Piste Verte", Color.GREEN, 400, 25, null);

        when(pisteService.addPiste(any(Piste.class))).thenReturn(piste);

        mockMvc.perform(post("/piste/add") // Changer l'URL ici
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"namePiste\": \"Piste Verte\", \"color\": \"GREEN\", \"length\": 400, \"slope\": 25}"))
                .andExpect(status().isOk()) // Vérifie que le statut de la réponse est 200 OK
                .andExpect(jsonPath("$.namePiste").value("Piste Verte")); // Vérifie que le nom de la piste est correct

        verify(pisteService, times(1)).addPiste(any(Piste.class)); // Vérifie que la méthode addPiste a été appelée une fois
    }


    @Test
    public void testDeletePiste() throws Exception {
        // Identifiant de la piste à supprimer
        Long idPiste = 1L;

        // Préparer le mock pour vérifier que le service removePiste est appelé
        doNothing().when(pisteService).removePiste(idPiste);

        // Exécuter la requête DELETE
        mockMvc.perform(delete("/piste/delete/{id-piste}", idPiste)) // L'URL doit correspondre à votre configuration
                .andExpect(status().isOk()); // S'attendre à un statut 200 OK ou 204 No Content

        // Vérifiez que la méthode de service a été appelée une fois
        verify(pisteService, times(1)).removePiste(idPiste);
    }


    @Test
    public void testRetrievePiste() throws Exception {
        Piste piste = new Piste(1L, "Piste Bleue", Color.BLUE, 300, 20, null);

        when(pisteService.retrievePiste(1L)).thenReturn(piste);

        mockMvc.perform(get("/piste/get/1")) // Vérifiez l'URL
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.namePiste").value("Piste Bleue"));

        verify(pisteService, times(1)).retrievePiste(1L);
    }

}

