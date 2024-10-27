package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class InstructorServicesImpl implements IInstructorServices{

    private IInstructorRepository instructorRepository;
    private ICourseRepository courseRepository;

    @Override
    public Instructor addInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public List<Instructor> retrieveAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor retrieveInstructor(Long numInstructor) {
        return instructorRepository.findById(numInstructor).orElse(null);
    }

    @Override
    public Instructor addInstructorAndAssignToCourse(Instructor instructor, Long numCourse) {
        Course course = courseRepository.findById(numCourse).orElse(null);
        Set<Course> courseSet = new HashSet<>();
        courseSet.add(course);
        instructor.setCourses(courseSet);
        return instructorRepository.save(instructor);
    }
    public List<Instructor> retrieveInstructorsAfterRegistrationDate(LocalDate registrationDate) {
        return instructorRepository.findAllByRegistrationDateAfter(registrationDate);
    }

    @Override
    public List<Instructor> retrieveInstructorsByRegistrationDate(LocalDate date) {
        return instructorRepository.findAllByRegistrationDateAfter(date);
    }

    public Optional<Instructor> retrieveInstructorByNameAndSurname(String name, String surname) {
        return instructorRepository.findByNameAndSurname(name, surname);
    }
    public Optional<Instructor> retrieveInstructorById(Long id) {
        return instructorRepository.findById(id);
    }



}
