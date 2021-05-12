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

    List<Drug> findAll() {
        def drugs = drugRepository.findAll()
        if (!drugs) {
            throw new NoContentException("There is no any drug")
        }
        drugs
    }

    Drug findByName(String name) {
        def drugs = findAll()
        def drug = drugs.find { it.name.toLowerCase() == name.toLowerCase() }
        if (!drug) {
            throw new ItemDoesNotExistException("There is no drug with name '$name'")
        }
        drug
    }

    Drug updateDrug(Drug drug) {
        def drugs = findAll()
        def existingDrug = drugs.find { it.drugId == drug.drugId }
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
        drugRepository.save(drug)
    }

    void deleteDrug(String drugName) {
        def drug = findAll().find {
            it.name == drugName
        }
        if (!drug) {
            throw new ItemDoesNotExistException("There is no drug with name '$drugName'")
        }
        drugRepository.delete(drug)
    }


    void validateRequiredDrugFields(Drug drug) {
        if (drug.name.isEmpty()) {
            throw new BadRequestException("Name is required")
        } else if (drug.atcCode.isEmpty()) {
            throw new BadRequestException("ATC code is required")
        } else if (drug.ingredients.isEmpty()) {
            throw new BadRequestException("At least one ingredient is required")
        }
    }

    void validateDrugUniqueness(Drug drug) {
        def drugs = findAll()
        if (drugs.find { it.drugId == drug.drugId }) {
            throw new ItemAlreadyExistException("Drug with id '$drug.drugId' already exists")
        }
        if (drugs.find { it.name == drug.name }) {
            throw new ItemAlreadyExistException("Drug with name '$drug.name' already exists")
        }
    }

    void setDefaultTimestamp(Drug drug) {
        drug.created = new Timestamp(System.currentTimeMillis())
        drug.updated = new Timestamp(System.currentTimeMillis())
    }

    void updateTimestamp(Drug drug) {
        drug.updated = new Timestamp(System.currentTimeMillis())
    }


}
