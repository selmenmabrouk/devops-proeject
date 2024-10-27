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
        Optional<Instructor> existingInstructor = instructorRepository.findByNameAndSurname(instructor.getFirstName(), instructor.getLastName());


        if (existingInstructor.isPresent()) {
            throw new RuntimeException("Instructor already exists");
        }
        return instructorRepository.save(instructor);
    }

    @Override
    public List<Instructor> retrieveAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor updateInstructor(Instructor updatedInstructor) {
        Optional<Instructor> existingInstructorOpt = instructorRepository.findById(updatedInstructor.getNumInstructor());
        if (existingInstructorOpt.isPresent()) {
            Instructor existingInstructor = existingInstructorOpt.get();
            existingInstructor.setFirstName(updatedInstructor.getFirstName());
            existingInstructor.setLastName(updatedInstructor.getLastName());
            // Update other fields as necessary
            return instructorRepository.save(existingInstructor);
        } else {
            throw new RuntimeException("Instructor not found");
        }
    }


    @Override
    public Instructor retrieveInstructor(Long numInstructor) {
        return instructorRepository.findById(numInstructor).orElse(null);
    }

    @Override
    public Instructor addInstructorAndAssignToCourse(Instructor instructor, Long numCourse) {
        Course course = courseRepository.findById(numCourse)
                .orElseThrow(() -> new RuntimeException("Course not found"));
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
        return Optional.ofNullable(instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found")));
    }




}
