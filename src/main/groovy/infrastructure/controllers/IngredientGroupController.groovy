package main.groovy.infrastructure.controllers

import main.groovy.infrastructure.model.Ingredient
import main.groovy.infrastructure.model.IngredientGroup
import main.groovy.infrastructure.services.IngredientGroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("ingredient-groups")
class IngredientGroupController {

    @Autowired
    IngredientGroupService ingredientGroupService

    @GetMapping
    List<IngredientGroup> findAll() {
        ingredientGroupService.findAll()
    }

    @GetMapping("{groupName}")
    IngredientGroup findByName(@PathVariable(name = "groupName") String groupName) {
        ingredientGroupService.findByName(groupName)
    }

    @PostMapping
    IngredientGroup createGroup(@RequestBody IngredientGroup ingredientGroup) {
        ingredientGroupService.createGroup(ingredientGroup)
    }

    @DeleteMapping("{groupName}")
    void deleteGroup(@PathVariable(name = "groupName") String groupName) {
        ingredientGroupService.deleteGroup(groupName)
    }

    @DeleteMapping("{groupName}/ingredients/{ingredientName}")
    void deleteIngredient(@PathVariable(name = "groupName") String groupName,
                          @PathVariable(name = "ingredientName") String ingredientName) {
        ingredientGroupService.deleteIngredientFromGroup(groupName, ingredientName)
    }

    @PostMapping("{groupName}/ingredients")
    IngredientGroup addIngredient(@PathVariable(name = "groupName") String groupName,
                                  @RequestBody List<Ingredient> ingredients) {
        ingredientGroupService.addIngredientToGroup(groupName, ingredients)
    }
}
