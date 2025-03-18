package com.example.demo.service;

import com.example.demo.dao.RecipeRepository;
import com.example.demo.entity.Recipe;
import com.example.demo.exception.RecipeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException("Recipe ID not found - " + id));
    }

    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Transactional
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException("Recipe ID not found - " + id);
        }
        recipeRepository.deleteById(id);
    }

    @Transactional
    public Recipe updateRecipe(Long id, Recipe recipe) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException("Recipe ID not found - " + id);
        }
        recipe.setId(id);
        return recipeRepository.save(recipe);
    }

//    public List<Recipe> getRecipesByCategory(String category) {
//        return recipeRepository.findByCategory(category);
//    }
//
//    public List<Recipe> getRecipesByIngredient(String ingredient) {
//        return recipeRepository.findByIngredientsContaining(ingredient);
//    }
}
