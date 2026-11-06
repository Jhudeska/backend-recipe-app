package nl.novi.recipeapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "RecipeApp API",
                version = "1.0",
                description = """
                        Welkom bij de RecipeApp API.

                        Met deze API kunnen gebruikers recepten bekijken,
                        zoeken en beheren.

                        De API biedt onder andere functionaliteiten voor:
                        - Recepten beheren
                        - Ingrediënten beheren
                        - Categorieën beheren
                        - Receptafbeeldingen uploaden
                        - Favorieten beheren
                        - Reviews en beoordelingen beheren
                        - Gebruikersprofielen beheren

                        Voor het toevoegen, wijzigen en verwijderen van gegevens
                        zijn afhankelijk van de functionaliteit bepaalde gebruikersrollen vereist.
                        """
        )
)
public class OpenApiConfig {
}