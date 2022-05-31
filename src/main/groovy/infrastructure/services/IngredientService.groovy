package main.groovy.infrastructure.services

import main.groovy.infrastructure.exception.BadRequestException
import main.groovy.infrastructure.exception.ItemAlreadyExistException
import main.groovy.infrastructure.exception.ItemDoesNotExistException
import main.groovy.infrastructure.exception.NoContentException
import main.groovy.infrastructure.model.Ingredient
import main.groovy.infrastructure.repositories.IngredientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository

    List<Ingredient> findAll() {
        def ingredients = ingredientRepository.findAll()
        if (!ingredients) {
            throw new NoContentException("There is no any ingredient")
        }
        ingredients
    }

    void deleteByName(String ingredientName) {
        def ingredient = findAll().find {
            it.ingredientName.toLowerCase() == ingredientName.toLowerCase()
        }
        if (ingredient == null) {
            throw new ItemDoesNotExistException("There is no ingredient with name '$ingredientName'")
        }
        ingredientRepository.delete(ingredient)
    }

    void create(Ingredient ingredient) {
        validateIngredient(ingredient)
        def isIngredientExists = this.findAll().find {
            it.ingredientName.toLowerCase() == ingredient.ingredientName.toLowerCase()
        }
        if (isIngredientExists) {
            throw new ItemAlreadyExistException("Ingredient with name '$ingredient.ingredientName' already exists")
        }
        ingredientRepository.save(ingredient)
    }

    static void validateIngredient(Ingredient ingredient) {
        if (!ingredient || ingredient.ingredientName.isEmpty()) {
            throw new BadRequestException("Назва компоненту є обов'язковою")
        }
    }
}
