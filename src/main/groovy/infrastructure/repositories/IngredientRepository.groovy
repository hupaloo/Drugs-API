package main.groovy.infrastructure.repositories

import main.groovy.infrastructure.model.Ingredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IngredientRepository extends JpaRepository<Ingredient, Long> {}
