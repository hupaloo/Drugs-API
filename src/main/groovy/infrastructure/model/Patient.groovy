package main.groovy.infrastructure.model

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
@Table(name = 'patients')
class Patient {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long patientId
    Timestamp created
    Timestamp updated
    @ManyToMany
    @JoinTable(
            name = "patients_ingredients",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    Set<Ingredient> ingredients
}
