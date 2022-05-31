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
@Table(name = 'ingredient_groups')
class IngredientGroup {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long groupId
    String groupName
    @JsonIgnore
    Timestamp created
    @JsonIgnore
    Timestamp updated
    @ManyToMany
    @JoinTable(
            name = "ingredients_group_belonging",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    Set<Ingredient> ingredients
}
