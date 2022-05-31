package main.groovy.infrastructure.controllers

import main.groovy.infrastructure.model.Ingredient
import main.groovy.infrastructure.services.IngredientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("ingredients")
class IngredientController {

    @Autowired
    IngredientService ingredientService

    @GetMapping
    List<Ingredient> findAll() {
        ingredientService.findAll().collect {
            it.tap { it.ingredientName = ingredientName.capitalize() }
        }
    }

    @DeleteMapping("{ingredientName}")
    void deleteByName(@PathVariable("ingredientName") String ingredientName) {
        ingredientService.deleteByName(ingredientName)
    }

    @PostMapping
    void addIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.create(ingredient)
    }
}
