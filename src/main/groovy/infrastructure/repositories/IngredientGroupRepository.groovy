package main.groovy.infrastructure.repositories

import main.groovy.infrastructure.model.IngredientGroup
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientGroupRepository extends JpaRepository<IngredientGroup, Long> {}
