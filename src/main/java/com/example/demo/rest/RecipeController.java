package com.example.demo.rest;

import com.example.demo.entity.Recipe;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.RecipeResponse;
import com.example.demo.service.RecipeService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing recipe resources.
 * Provides API endpoints for retrieving and managing recipes.
 */
@RestController
@RequestMapping("${api.base-path}/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostConstruct
    public void init() {
        List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Recipe recipe = new Recipe(null, "Recipe No." + (i + 1), "Description No." + (i + 1));
            recipes.add(recipe);
        }
        recipes.forEach(recipeService::saveRecipe);
    }

    @GetMapping("")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return new ResponseEntity<>(recipeService.getAllRecipes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        return new ResponseEntity<>(recipeService.getRecipeById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Recipe> addRecipe(@Valid @RequestBody Recipe recipe, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());
        }
        return new ResponseEntity<>(recipeService.saveRecipe(recipe), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable Long id) {
        return new ResponseEntity<>(recipeService.updateRecipe(id, recipe), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecipeResponse> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        RecipeResponse recipeResponse = new RecipeResponse(
                HttpStatus.OK.value(),
                "Recipe ID deleted - " + id,
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(recipeResponse, HttpStatus.OK);
    }
}
