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

    @Test
    public void testRetrieveAllInstructors() {
        // Arrange
        Instructor instructor1 = new Instructor(1L, "John", "Doe", LocalDate.of(2020, 1, 15), null);
        Instructor instructor2 = new Instructor(2L, "Jane", "Smith", LocalDate.of(2021, 2, 20), null);
        List<Instructor> instructors = Arrays.asList(instructor1, instructor2);
        when(instructorRepository.findAll()).thenReturn(instructors);

        // Act
        List<Instructor> result = instructorServices.retrieveAllInstructors();

        // Assert
        assertEquals(2, result.size());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    public void testAddInstructor() {
        // Arrange
        Instructor instructor = new Instructor(1L, "John", "Doe", LocalDate.of(2020, 1, 15), null);
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        // Act
        Instructor result = instructorServices.addInstructor(instructor);

        // Assert
        assertNotNull(result);
        assertEquals(instructor.getNumInstructor(), result.getNumInstructor());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    public void testAddInstructorAndAssignToCourse() {
        // Arrange
        Instructor instructor = new Instructor(1L, "John", "Doe", LocalDate.of(2020, 1, 15), new HashSet<>());
        Course course = new Course(1L, 1, TypeCourse.COLLECTIVE_ADULT, Support.SNOWBOARD, 100.0f, 60, null);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act
        Instructor assignedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);

        // Assert
        assertNotNull(assignedInstructor);
        assertEquals(instructor.getNumInstructor(), assignedInstructor.getNumInstructor());
        assertTrue(assignedInstructor.getCourses().contains(course)); // Assuming you have a method to get courses
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, times(1)).save(instructor);
    }
}
