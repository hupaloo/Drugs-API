package main.groovy.infrastructure.model

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table
import java.sql.Timestamp

import static javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = 'drugs')
class Drug {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long drugId
    String name
    String applicantDetails
    String producerDetails
    String registrationCertificateNumber
    String atcCode
    String compendiumUrl
    String expirationDate
    String internationalName
    @JsonIgnore
    Timestamp created
    @JsonIgnore
    Timestamp updated
    @ManyToMany
    @JoinTable(
            name = "drugs_ingredients",
            joinColumns = @JoinColumn(name = "drug_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    List<Ingredient> ingredients
}
