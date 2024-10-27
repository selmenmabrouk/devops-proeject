package tn.esprit.spring.ServiceTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PisteServiceTest {

    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllPistes() {
        List<Piste> pistes = new ArrayList<>();
        pistes.add(new Piste(1L, "Piste Rouge", Color.RED, 500, 30, null));
        pistes.add(new Piste(2L, "Piste Bleue", Color.BLUE, 600, 35, null));

        when(pisteRepository.findAll()).thenReturn(pistes);

        List<Piste> result = pisteService.retrieveAllPistes();
        assertEquals(2, result.size());
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    public void testAddPiste() {
        Piste piste = new Piste(1L, "Piste Verte", Color.GREEN, 400, 25, null);

        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        Piste result = pisteService.addPiste(piste);
        assertNotNull(result);
        assertEquals("Piste Verte", result.getNamePiste());
        verify(pisteRepository, times(1)).save(piste);
    }

    @Test
    public void testRemovePiste() {
        Long idToRemove = 1L; // ID de la Piste à supprimer

        // Appeler la méthode de suppression
        pisteService.removePiste(idToRemove);

        // Vérifier que deleteById a été appelé avec l'identifiant correct
        verify(pisteRepository, times(1)).deleteById(idToRemove);
    }

    @Test
    public void testRetrievePiste() {
        // Création d'une piste fictive pour le test
        Piste piste = new Piste(1L, "Piste Noire", Color.BLACK, 700, 40, null);

        // Simulation du comportement du mock
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));

        // Appel de la méthode à tester
        Piste result = pisteService.retrievePiste(1L);

        // Assertions pour vérifier le résultat
        assertNotNull(result);
        assertEquals("Piste Noire", result.getNamePiste());

        // Vérification que la méthode findById a été appelée une seule fois
        verify(pisteRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrievePisteNotFound() {
        when(pisteRepository.findById(1L)).thenReturn(Optional.empty());

        Piste result = pisteService.retrievePiste(1L);
        assertNull(result);
        verify(pisteRepository, times(1)).findById(1L);
    }
}