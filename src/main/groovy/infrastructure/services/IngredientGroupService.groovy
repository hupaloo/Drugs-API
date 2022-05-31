package main.groovy.infrastructure.services

import main.groovy.infrastructure.exception.ItemAlreadyExistException
import main.groovy.infrastructure.exception.ItemDoesNotExistException
import main.groovy.infrastructure.exception.NoContentException
import main.groovy.infrastructure.model.Ingredient
import main.groovy.infrastructure.model.IngredientGroup
import main.groovy.infrastructure.repositories.IngredientGroupRepository
import main.groovy.infrastructure.repositories.IngredientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.sql.Timestamp

@Service
class IngredientGroupService {

    @Autowired
    IngredientGroupRepository ingredientGroupRepository
    @Autowired
    IngredientRepository ingredientRepository

    List<IngredientGroup> findAll() {
        def ingredientGroups = ingredientGroupRepository.findAll()
        if (!ingredientGroups) {
            throw new NoContentException("There is no any ingredient group")
        }
        ingredientGroups
    }

    IngredientGroup findByName(String name) {
        def group = findAll().find { it.groupName.toLowerCase() == name.toLowerCase() }
        if (!group) {
            throw new ItemDoesNotExistException("There is no group with name '$name'")
        }
        group
    }

    IngredientGroup createGroup(IngredientGroup ingredientGroup) {
        if (findAll().find { it.groupName.toLowerCase() == ingredientGroup.groupName.toLowerCase() }) {
            throw new ItemAlreadyExistException("Group with name '$ingredientGroup.groupName' already exists")
        }
        setDefaultTimestamp(ingredientGroup)
        ingredientGroupRepository.save(ingredientGroup)
    }

    void deleteGroup(String name) {
        ingredientGroupRepository.delete(findByName(name))
    }

    void deleteIngredientFromGroup(String name, String ingredientName) {
        def group = findByName(name)
        def ingredientToDelete = group.ingredients.find { it.ingredientName == ingredientName }
        if (!ingredientToDelete) {
            throw new ItemDoesNotExistException("There is no ingredient '$ingredientName' in group '$name'")
        }
        group.ingredients.remove(ingredientToDelete)
        ingredientGroupRepository.save(group)
    }

    IngredientGroup addIngredientToGroup(String groupName, List<Ingredient> ingredients) {
        def group = findByName(groupName)
        def dbIngredients = ingredientRepository.findAll()
        ingredients.forEach { ingr ->
            def ingredient = group.ingredients.find { it.ingredientName == ingr.ingredientName }
            if (ingredient) {
                throw new ItemAlreadyExistException("Ingredient with name '$ingr.ingredientName' already exists in group '$groupName'")
            }
            ingr.ingredientId = dbIngredients.find { it.ingredientName == ingr.ingredientName }.ingredientId
        }
        group.ingredients.addAll(ingredients)
        updateTimestamp(group)
        ingredientGroupRepository.save(group)
        group
    }

    static void setDefaultTimestamp(IngredientGroup ingredientGroup) {
        ingredientGroup.created = new Timestamp(System.currentTimeMillis())
        ingredientGroup.updated = new Timestamp(System.currentTimeMillis())
    }

    static void updateTimestamp(IngredientGroup ingredientGroup) {
        ingredientGroup.updated = new Timestamp(System.currentTimeMillis())
    }
}
