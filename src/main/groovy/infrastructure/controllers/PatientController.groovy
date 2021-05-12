package main.groovy.infrastructure.controllers

import main.groovy.infrastructure.model.Ingredient
import main.groovy.infrastructure.model.Patient
import main.groovy.infrastructure.services.PatientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("patients")
class PatientController {

    @Autowired
    PatientService patientService

    @GetMapping()
    List<Patient> findAll() {
        patientService.findAll()
    }

    @PostMapping()
    void addPatient(@RequestBody Patient patient) {
        patientService.create(patient)
    }

    @GetMapping("{patientId}")
    Patient findById(@PathVariable(name = "patientId") Long id) {
        patientService.findById(id)
    }

    @PostMapping("{patientId}/ingredients")
    void addIngredient(@PathVariable(name = "patientId") Long id, @RequestBody List<Ingredient> ingredients) {
        patientService.addIngredients(id, ingredients)
    }

    @DeleteMapping("{patientId}/ingredients/{ingredientName}")
    void deleteIngredient(@PathVariable(name = "patientId") Long id, @PathVariable(name = "ingredientName") String ingredient) {
        patientService.deleteIngredient(id, ingredient)
    }

    @DeleteMapping("{patientId}")
    void deleteById(@PathVariable(name = "patientId") Long id) {
        patientService.deleteById(id)
    }

}
