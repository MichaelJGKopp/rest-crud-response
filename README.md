### Recipe Management REST Service

#### Objective:
Create a RESTful web service using Spring Boot to manage recipes.

#### Instructions:
-	Create a new Spring project.
-	Implement a Recipe POJO class with fields for recipe attributes.
-	Create a REST controller class named RecipeController with the following endpoints:
    - A GET method to retrieve a list of all available recipes.
    - A GET method to retrieve information about a single recipe by its ID, using path variable.
-	Define a base URL for the API (e.g., /api/recipes) in the request mapping annotation.
-	Test the RESTful web service using tools like Postman.
-	Ensure that recipe data is returned in JSON format.
-	Implement exception handling to handle different scenarios.
