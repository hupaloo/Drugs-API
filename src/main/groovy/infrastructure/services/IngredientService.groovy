package main.groovy.infrastructure.services

import main.groovy.infrastructure.exception.ItemAlreadyExistException
import main.groovy.infrastructure.exception.ItemDoesNotExistException
import main.groovy.infrastructure.exception.NoContentException
import main.groovy.infrastructure.model.Ingredient
import main.groovy.infrastructure.repositories.IngredientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.persistence.EntityNotFoundException

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
            it.ingredientName == ingredientName
        }
        if (ingredient == null) {
            throw new ItemDoesNotExistException("There is no ingredient with name '$ingredientName'")
        }
        ingredientRepository.delete(ingredient)
    }

    void create(Ingredient ingredient) {
        def isIngredientExists = findAll().find {
            it.ingredientName == ingredient.ingredientName
        }
        if (isIngredientExists) {
            throw new ItemAlreadyExistException("Ingredient with name '$ingredient.ingredientName' already exists")
        }
        ingredientRepository.save(ingredient)
    }

    Ingredient findById(Long ingredientId) {
        ingredientRepository.findById(ingredientId).orElseThrow{
            new EntityNotFoundException()
        }
    }

}
