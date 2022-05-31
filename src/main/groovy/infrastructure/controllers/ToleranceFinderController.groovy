package main.groovy.infrastructure.controllers

import main.groovy.infrastructure.model.Drug
import main.groovy.infrastructure.model.ToleranceFinder
import main.groovy.infrastructure.services.ToleranceFinderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("run-tolerance")
class ToleranceFinderController {

    @Autowired
    ToleranceFinderService toleranceFinderService

    @PostMapping
    Set<Drug> runTolerance(@RequestBody ToleranceFinder toleranceFinder) {
        toleranceFinderService.runTolerance(toleranceFinder)
    }

    @GetMapping("{runId}")
    ToleranceFinder findById(@PathVariable(name = "runId") Long id) {
        toleranceFinderService.findById(id)
    }
}
