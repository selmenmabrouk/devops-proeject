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
    @Test
    public void testAddInstructorAndAssignToCourse() {

        Instructor instructor = new Instructor(1L, "John", "Doe", LocalDate.of(2020, 1, 15), new HashSet<>());
        Course course = new Course(1L, 1, TypeCourse.COLLECTIVE_ADULT, Support.SNOWBOARD, 100.0f, 60, null);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);


        Instructor assignedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);


        assertNotNull(assignedInstructor);
        assertEquals(instructor.getNumInstructor(), assignedInstructor.getNumInstructor());
        assertTrue(assignedInstructor.getCourses().contains(course)); // Assuming you have a method to get courses
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, times(1)).save(instructor);
    }
    //Tests avancés
    @Test
    public void testAddInstructor_DuplicateValidation() {
        // Arrange
        Instructor instructor = new Instructor(1L, "John", "Doe", LocalDate.of(2020, 1, 15), new HashSet<>());
        when(instructorRepository.findByNameAndSurname("John", "Doe")).thenReturn(Optional.of(instructor));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> instructorServices.addInstructor(instructor));
        assertEquals("Instructor already exists", exception.getMessage());
        verify(instructorRepository, times(0)).save(instructor);
    }
    @Test
    public void testAssignInstructorToNonExistingCourse() {
        // Arrange
        Instructor instructor = new Instructor(1L, "John", "Doe", LocalDate.of(2020, 1, 15), new HashSet<>());
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> instructorServices.addInstructorAndAssignToCourse(instructor, 99L));
        assertEquals("Course not found", exception.getMessage());
        verify(courseRepository, times(1)).findById(99L);
        verify(instructorRepository, times(0)).save(instructor);
    }
    @Test
    public void testUpdateInstructor() {
        // Arrange
        Instructor existingInstructor = new Instructor(1L, "John", "Doe", LocalDate.of(2020, 1, 15), null);
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(existingInstructor));

        // Create the updated instructor object
        Instructor updatedInstructor = new Instructor(1L, "John", "Smith", LocalDate.of(2020, 1, 15), null);

        // Mock save method to return the updated instructor
        when(instructorRepository.save(any(Instructor.class))).thenReturn(updatedInstructor);

        // Act
        Instructor result = instructorServices.updateInstructor(updatedInstructor);

        // Assert
        assertNotNull(result); // Check that the result is not null
        assertEquals("John", result.getFirstName()); // Verify first name is unchanged
        assertEquals("Smith", result.getLastName()); // Verify last name is updated

        // Verify that save was called with an Instructor object that has the expected properties
        verify(instructorRepository).save(argThat(instructor ->
                "John".equals(instructor.getFirstName()) &&
                        "Smith".equals(instructor.getLastName()) &&
                        updatedInstructor.getNumInstructor().equals(instructor.getNumInstructor())
        ));
    }
    @Test
    public void testRetrieveInstructorsByRegistrationDate() {
        // Arrange
        Instructor instructor1 = new Instructor(1L, "John", "Doe", LocalDate.of(2020, 1, 15), new HashSet<>());
        Instructor instructor2 = new Instructor(2L, "Jane", "Smith", LocalDate.of(2021, 2, 20), new HashSet<>());
        List<Instructor> instructors = Arrays.asList(instructor1, instructor2);
        when(instructorRepository.findAllByRegistrationDateAfter(LocalDate.of(2020, 1, 1))).thenReturn(instructors);

        // Act
        List<Instructor> result = instructorServices.retrieveInstructorsByRegistrationDate(LocalDate.of(2020, 1, 1));

        // Assert
        assertEquals(2, result.size());
        verify(instructorRepository, times(1)).findAllByRegistrationDateAfter(LocalDate.of(2020, 1, 1));
    }
    @Test
    public void testRetrieveInstructorById_NotFound() {
        // Arrange
        when(instructorRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> instructorServices.retrieveInstructorById(1L));

        // Check the exception message
        assertEquals("Instructor not found", exception.getMessage());
    }



}
