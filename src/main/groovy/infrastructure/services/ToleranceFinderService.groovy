package main.groovy.infrastructure.services

import main.groovy.infrastructure.exception.BadRequestException
import main.groovy.infrastructure.exception.ItemDoesNotExistException
import main.groovy.infrastructure.model.Drug
import main.groovy.infrastructure.model.ToleranceFinder
import main.groovy.infrastructure.repositories.ToleranceFinderRepository
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ToleranceFinderService {

    @Autowired
    ToleranceFinderRepository toleranceFinderRepository
    @Autowired
    PatientService patientService
    @Autowired
    IngredientService ingredientService
    @Autowired
    DrugService drugService

    ToleranceFinder findById(Long id) {
        toleranceFinderRepository.findById(id).orElseThrow {
            new ItemDoesNotExistException("There is no runId '$id'")
        }
    }

    Set<Drug> runTolerance(ToleranceFinder toleranceFinder) {
        validateToleranceObject(toleranceFinder)
        def ingredientsSet = collectIngredientsFromObject(toleranceFinder)
        def tolerantDrugs = findTolerantDrugs(toleranceFinder.atcCodes, ingredientsSet)
        tolerantDrugs
    }

    Set<Drug> findTolerantDrugs(List<String> atcCodes, Set<String> intolerantIngredients) {
        Set<Drug> tolerantDrugsSet = new HashSet<Drug>()

        drugService.findAll().forEach { drug ->
            atcCodes.forEach { atcCode ->
                if (drug.atcCode.startsWith(atcCode) || drug.atcCode == atcCode) {
                    tolerantDrugsSet.add(drug)
                }
            }
        }

        Set<Drug> intolerantDrugs = new HashSet<Drug>()
        if (!intolerantIngredients.isEmpty()) {
            tolerantDrugsSet.forEach { drug ->
                def drugIngredientNames = drug.ingredients.collect { it.ingredientName }
                intolerantIngredients.forEach { ingredient ->
                    if (ingredient in drugIngredientNames) {
                        intolerantDrugs.add(drug)
                    }
                }
            }
        }

        tolerantDrugsSet.removeAll(intolerantDrugs)

        tolerantDrugsSet
    }

    void validateToleranceObject(ToleranceFinder toleranceFinder) {
        if (!toleranceFinder.ingredientNames && toleranceFinder.patientId == null) {
            throw new BadRequestException("Whether Ingredient names or patient id should be added, but wasn't")
        }
        toleranceFinder.atcCodes.forEach {
            // given atc code should start with one of ATC code prefix
            if (!StringUtils.startsWithAny(it.toLowerCase(),
                    "a", "b", "c", "d", "g", "h", "j", "l", "m", "n", "p", "r", "s", "v")) {
                throw new BadRequestException("Bad ATC code given '$it'")
            }
        }
        if (toleranceFinder.patientId) {
            patientService.findById(toleranceFinder.patientId)
        }
        if (toleranceFinder.ingredientNames) {
            def ingredientNames = ingredientService.findAll().collect { it.ingredientName }
            toleranceFinder.ingredientNames.forEach {
                if (!ingredientNames.contains(it)) {
                    throw new BadRequestException("Bad ingredient name given '$it'")
                }
            }
        }
    }

    HashSet<String> collectIngredientsFromObject(ToleranceFinder toleranceFinder) {
        def ingredientsSet = new HashSet()
        if (toleranceFinder.patientId) {
            def patientIngredients = patientService.findById(toleranceFinder.patientId).ingredients.collect { it.ingredientName }
            ingredientsSet.addAll(patientIngredients)
        }
        if (toleranceFinder.ingredientNames) {
            ingredientsSet.addAll(toleranceFinder.ingredientNames)
        }
        ingredientsSet
    }
}
