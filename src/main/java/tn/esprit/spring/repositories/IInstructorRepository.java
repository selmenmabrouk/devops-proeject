package tn.esprit.spring.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.Instructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface IInstructorRepository extends JpaRepository<Instructor, Long> {
    //methode de recherche par nom et prenom
    Optional<Instructor> findByNameAndSurname(String name, String surname);
    List<Instructor> findAllByRegistrationDateAfter(LocalDate registrationDate);
    Optional<Instructor> findById(Long id);
}
