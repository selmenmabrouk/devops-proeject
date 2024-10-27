package tn.esprit.spring.services;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.entities.Support;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IInstructorServices {

    Instructor addInstructor(Instructor instructor);

    List<Instructor> retrieveAllInstructors();

    Instructor updateInstructor(Instructor instructor);

    Instructor retrieveInstructor(Long numInstructor);

    Instructor addInstructorAndAssignToCourse(Instructor instructor, Long numCourse);
    public Optional<Instructor> retrieveInstructorByNameAndSurname(String name, String surname) ;

 List<Instructor> retrieveInstructorsAfterRegistrationDate(LocalDate registrationDate) ;
    public List<Instructor> retrieveInstructorsByRegistrationDate(LocalDate date) ;
    public Optional<Instructor> retrieveInstructorById(Long id) ;
}
