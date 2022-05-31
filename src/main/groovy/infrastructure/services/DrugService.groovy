package main.groovy.infrastructure.services

import main.groovy.infrastructure.exception.BadRequestException
import main.groovy.infrastructure.exception.ItemAlreadyExistException
import main.groovy.infrastructure.exception.ItemDoesNotExistException
import main.groovy.infrastructure.exception.NoContentException
import main.groovy.infrastructure.model.Drug
import main.groovy.infrastructure.repositories.DrugRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.sql.Timestamp

@Service
class DrugService {

    @Autowired
    DrugRepository drugRepository
    @Autowired
    IngredientService ingredientService

    List<Drug> findAll() {
        def drugs = drugRepository.findAll()
        if (!drugs) {
            throw new NoContentException("There is no any drug")
        }
        drugs
    }

    Drug findByName(String name) {
        def drug = this.findAll().find { it.name.equalsIgnoreCase(name) }
        if (!drug) {
            throw new ItemDoesNotExistException("There is no drug with name '$name'")
        }
        drug
    }

    Drug updateDrug(Drug drug) {
        def existingDrug = this.findAll().find { it.name.equalsIgnoreCase(drug.name) }
        drug.drugId = existingDrug.drugId
        if (!existingDrug) {
            throw new ItemDoesNotExistException("There is no drug with name '$drug.name'")
        }
        validateRequiredDrugFields(drug)
        updateTimestamp(drug)
        drugRepository.save(drug)
    }

    Drug createDrug(Drug drug) {
        validateDrugUniqueness(drug)
        validateRequiredDrugFields(drug)
        setDefaultTimestamp(drug)
        setDrugIngredientIds(drug)
        drugRepository.save(drug)
    }

    void deleteDrug(String drugName) {
        def drug = this.findAll().find {
            it.name.toLowerCase() == drugName.toLowerCase()
        }
        if (!drug) {
            throw new ItemDoesNotExistException("There is no drug with name '$drugName'")
        }
        drugRepository.delete(drug)
    }


    void setDrugIngredientIds(Drug drug) {
        def ingredients = ingredientService.findAll()
        def ingredientNames = ingredients.collect { it.ingredientName.toLowerCase() }
        drug.ingredients.forEach { drugIngredient ->
            if (drugIngredient.ingredientName.toLowerCase() in ingredientNames) {
                drugIngredient.ingredientId = ingredients.find {
                    it.ingredientName.toLowerCase() == drugIngredient.ingredientName.toLowerCase()
                }.ingredientId
            }
        }
    }

    static void validateRequiredDrugFields(Drug drug) {
        if (drug.name.isEmpty()) {
            throw new BadRequestException("Назва медикаменту є обов'язковою")
        } else if (drug.atcCode.isEmpty()) {
            throw new BadRequestException("ATC code is required")
        } else if (drug.ingredients.isEmpty()) {
            throw new BadRequestException("At least one ingredient is required")
        }
    }

    void validateDrugUniqueness(Drug drug) {
        def drugs = this.findAll()
        if (drugs.find { it.drugId == drug.drugId }) {
            throw new ItemAlreadyExistException("Drug with id '$drug.drugId' already exists")
        }
        if (drugs.find { it.name == drug.name }) {
            throw new ItemAlreadyExistException("Drug with name '$drug.name' already exists")
        }
    }

    static void setDefaultTimestamp(Drug drug) {
        drug.created = new Timestamp(System.currentTimeMillis())
        drug.updated = new Timestamp(System.currentTimeMillis())
    }

    static void updateTimestamp(Drug drug) {
        drug.updated = new Timestamp(System.currentTimeMillis())
    }
}
