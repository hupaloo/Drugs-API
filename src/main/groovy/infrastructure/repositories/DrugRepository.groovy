package main.groovy.infrastructure.repositories

import main.groovy.infrastructure.model.Drug
import org.springframework.data.jpa.repository.JpaRepository

interface DrugRepository extends JpaRepository<Drug, Long> {}
