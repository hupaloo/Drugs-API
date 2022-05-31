package main.groovy.infrastructure.services

import main.groovy.infrastructure.exception.ItemAlreadyExistException
import main.groovy.infrastructure.exception.ItemDoesNotExistException
import main.groovy.infrastructure.model.Ingredient
import main.groovy.infrastructure.model.Patient
import main.groovy.infrastructure.repositories.IngredientRepository
import main.groovy.infrastructure.repositories.PatientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.sql.Timestamp

@Service
class PatientService {

    @Autowired
    PatientRepository patientRepository
    @Autowired
    IngredientRepository ingredientRepository

    List<Patient> findAll() {
        patientRepository.findAll()
    }

    Patient findById(Long id) {
        patientRepository.findById(id).orElseThrow {
            new ItemDoesNotExistException("There is no patient with such id")
        }
    }

    void create(Patient patient) {
        def patientIds = findAll().collect { it.patientId }
        if (patient.patientId in patientIds) {
            throw new ItemAlreadyExistException("Patient with id '$patient.patientId' already exists")
        }
        patient.created = new Timestamp(System.currentTimeMillis())
        patient.updated = new Timestamp(System.currentTimeMillis())
        patientRepository.save(patient)
    }

    void addIngredients(Long id, List<Ingredient> addedIngredients) {
        def patient = findById(id)
        def dbIngredients = ingredientRepository.findAll()
        def dbIngredientNames = dbIngredients.collect { it.ingredientName }
        def patientIngredientNames = patient.ingredients.collect { it.ingredientName }
        def addedIngredientNames = addedIngredients.collect { it.ingredientName }

        addedIngredientNames.forEach {
            if (it in patientIngredientNames) {
                throw new ItemAlreadyExistException("Patient with id '$patient.patientId' already has ingredient '$it'")
            }
        }
        addedIngredientNames.forEach {
            if (!dbIngredientNames.contains(it)) {
                throw new ItemDoesNotExistException("There is no ingredient with '$it' name")
            }
        }

        def ingredientToAdd = dbIngredients.findAll { it.ingredientName in addedIngredientNames }
        patient.ingredients.addAll(ingredientToAdd)
        patient.updated = new Timestamp(System.currentTimeMillis())
        patientRepository.save(patient)
    }

    void deleteIngredient(Long id, String ingredientName) {
        def patient = findById(id)
        def patientIngredients = patient.ingredients
        def patientIngredientNames = patientIngredients.collect { it.ingredientName }

        if (!patientIngredientNames.contains(ingredientName)) {
            throw new ItemDoesNotExistException("There is no ingredient with '$ingredientName' name")
        }

        def ingredientToRemove = patientIngredients.find { it.ingredientName == ingredientName }
        patient.ingredients.remove(ingredientToRemove)
        patientRepository.save(patient)
    }

    void deleteById(Long id) {
        patientRepository.delete(findById(id))
    }
}
