package main.groovy.infrastructure.model

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import java.sql.Timestamp

import static javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = 'ingredients')
class Ingredient {

    @Id
    @GeneratedValue(strategy= IDENTITY)
    Long ingredientId
    String ingredientName
    @JsonIgnore
    Timestamp created
}
