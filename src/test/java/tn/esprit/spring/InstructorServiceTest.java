package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstructorServiceTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    // tests simples CRUD
    @Test
    public void testRetrieveAllInstructors() {

        Instructor instructor1 = new Instructor(1L, "John", "Doe", LocalDate.of(2020, 1, 15), null);
        Instructor instructor2 = new Instructor(2L, "Jane", "Smith", LocalDate.of(2021, 2, 20), null);
        List<Instructor> instructors = Arrays.asList(instructor1, instructor2);
        when(instructorRepository.findAll()).thenReturn(instructors);


        List<Instructor> result = instructorServices.retrieveAllInstructors();


        assertEquals(2, result.size());
        verify(instructorRepository, times(1)).findAll();
    }
    @Test
    public void testAddInstructor() {

        Instructor instructor = new Instructor(1L, "John", "Doe", LocalDate.of(2020, 1, 15), null);
        when(instructorRepository.save(instructor)).thenReturn(instructor);


        Instructor result = instructorServices.addInstructor(instructor);


        assertNotNull(result);
        assertEquals(instructor.getNumInstructor(), result.getNumInstructor());
        verify(instructorRepository, times(1)).save(instructor);
    }

}
