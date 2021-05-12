package main.groovy.infrastructure.repositories

import main.groovy.infrastructure.model.ToleranceFinder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ToleranceFinderRepository extends JpaRepository<ToleranceFinder, Long> {}
