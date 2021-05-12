package main.groovy.infrastructure.model

import main.groovy.infrastructure.utils.StringListConverter

import javax.persistence.Convert
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import java.sql.Timestamp

import static javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = 'tolerance_finder')
class ToleranceFinder {

    @Id
    @GeneratedValue(strategy= IDENTITY)
    Long runId

    @Convert(converter = StringListConverter)
    List<String> atcCodes
//    @OneToMany(targetEntity = Ingredient)
    @Convert(converter = StringListConverter)
    List<String> ingredientNames
    Long patientId
    String runStatus
    Timestamp created
    Timestamp updated

}
