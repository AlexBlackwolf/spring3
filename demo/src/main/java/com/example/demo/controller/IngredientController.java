package com.example.demo.controller;

import com.example.demo.entity.Ingredient;
import com.example.demo.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable("id") Long id) {
        Optional<Ingredient> ingredient = ingredientService.getIngredientById(id);
        return ingredient.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        Ingredient createdIngredient = ingredientService.createIngredient(ingredient);
        return new ResponseEntity<>(createdIngredient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(
            @PathVariable("id") Long id, @RequestBody Ingredient ingredient) {
        Optional<Ingredient> existingIngredient = ingredientService.getIngredientById(id);
        if (((Optional<?>) existingIngredient).isPresent()) {
            ingredient.setId(id);
            Ingredient updatedIngredient = ingredientService.updateIngredient(ingredient);
            return new ResponseEntity<>(updatedIngredient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable("id") Long id) {
        ingredientService.deleteIngredient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
