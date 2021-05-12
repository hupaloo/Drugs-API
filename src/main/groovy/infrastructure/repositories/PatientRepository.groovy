package main.groovy.infrastructure.repositories

import main.groovy.infrastructure.model.Patient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PatientRepository extends JpaRepository<Patient, Long> {}
