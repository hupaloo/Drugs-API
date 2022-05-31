package main.groovy.infrastructure.controllers

import main.groovy.infrastructure.model.Drug
import main.groovy.infrastructure.services.DrugService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("drugs")
class DrugController {

    @Autowired
    DrugService drugService

    @GetMapping
    List<Drug> findAll() {
        drugService.findAll()
    }

    @GetMapping("{drugName}")
    Drug findByName(@PathVariable(name = "drugName") String drugName) {
        drugService.findByName(drugName)
    }

    @PutMapping
    Drug update(@RequestBody Drug drug) {
        drugService.updateDrug(drug)
    }

    @PostMapping
    Drug create(@RequestBody Drug drug) {
        drugService.createDrug(drug)
    }

    @DeleteMapping("{drugName}")
    void remove(@PathVariable(name = "drugName") String drugName) {
        drugService.deleteDrug(drugName)
    }
}
