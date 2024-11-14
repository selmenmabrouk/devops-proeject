//package com.example.gestionstationskii;
//
//import com.example.gestionstationskii.entities.*;
//import com.example.gestionstationskii.repositories.*;
//import com.example.gestionstationskii.services.RegistrationServicesImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class RegistrationTest {
//
//    @Autowired
//    private IRegistrationRepository registrationRepository;
//
//    @Autowired
//    private ISkierRepository skierRepository;
//
//    @Autowired
//    private ICourseRepository courseRepository;
//
//    @Autowired
//    private RegistrationServicesImpl registrationServices;
//
//    private Skier skier;
//    private Course course;
//    private Registration registration;
//
//    @BeforeEach
//    void setup() {
//        registrationRepository.deleteAll();
//        skierRepository.deleteAll();
//        courseRepository.deleteAll();
//        skier = new Skier();
//        skier.setNumSkier(1L);
//        skier.setDateOfBirth(LocalDate.of(2000, 1, 1));
//        skier = skierRepository.save(skier);
//
//        course = new Course();
//        course.setNumCourse(1L);
//        course.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);
//        course = courseRepository.save(course);
//
//        registration = new Registration();
//        registration.setNumWeek(1);
//    }
//
//    @Order(1)
//    @Test
//    void testCreateRegistration_Success() {
//        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, skier.getNumSkier());
//        assertNotNull(result);
//        assertEquals(1, registrationRepository.count());
//    }
//
//    @Test
//    void testReadRegistration_Success() {
//        Registration savedRegistration = registrationServices.addRegistrationAndAssignToSkier(registration, skier.getNumSkier());
//        Registration found = registrationRepository.findById(savedRegistration.getNumRegistration()).orElse(null);
//        assertNotNull(found);
//        assertEquals(savedRegistration.getNumRegistration(), found.getNumRegistration());
//    }
//
//    @Test
//    void testUpdateRegistration_Success() {
//        Registration savedRegistration = registrationServices.addRegistrationAndAssignToSkier(registration, skier.getNumSkier());
//        savedRegistration.setNumWeek(2);
//        Registration updatedRegistration = registrationRepository.save(savedRegistration);
//        assertNotNull(updatedRegistration);
//        assertEquals(2, updatedRegistration.getNumWeek());
//    }
//
//    @Test
//    void testDeleteRegistration_Success() {
//        Registration savedRegistration = registrationServices.addRegistrationAndAssignToSkier(registration, skier.getNumSkier());
//        registrationRepository.deleteById(savedRegistration.getNumRegistration());
//        assertFalse(registrationRepository.existsById(savedRegistration.getNumRegistration()));
//    }
//
//    @Test
//    void testAddRegistrationAndAssignToSkier_Success() {
//        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, skier.getNumSkier());
//        assertNotNull(result);
//        assertEquals(skier.getNumSkier(), result.getSkier().getNumSkier());
//        assertNotNull(result.getNumRegistration());
//    }
//
//    @Test
//    void testAssignRegistrationToCourse_Success() {
//        registration = registrationServices.addRegistrationAndAssignToSkier(registration, skier.getNumSkier());
//        Registration result = registrationServices.assignRegistrationToCourse(
//                registration.getNumRegistration(), course.getNumCourse());
//        assertNotNull(result);
//        assertEquals(course.getNumCourse(), result.getCourse().getNumCourse());
//    }
//
//    @Test
//    void testAddRegistrationAndAssignToSkierAndCourse_Success() {
//        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(
//                registration, skier.getNumSkier(), course.getNumCourse());
//        assertNotNull(result);
//        assertEquals(skier.getNumSkier(), result.getSkier().getNumSkier());
//        assertEquals(course.getNumCourse(), result.getCourse().getNumCourse());
//    }
//
//    @Test
//    void testCleanUp() {
//        registrationRepository.deleteAll();
//        skierRepository.deleteAll();
//        courseRepository.deleteAll();
//        assertFalse(registrationRepository.findAll().iterator().hasNext());
//        assertFalse(skierRepository.findAll().iterator().hasNext());
//        assertFalse(courseRepository.findAll().iterator().hasNext());
//    }
//}