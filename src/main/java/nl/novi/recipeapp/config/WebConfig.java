package nl.novi.recipeapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final Path uploadDirectory =
            Path.of("uploads", "recipes").toAbsolutePath().normalize();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/recipes/**")
                .addResourceLocations(
                        uploadDirectory.toUri().toString()
                );
    }

//    Hierdoor kunnen afbeeldingen die lokaal zijn opgeslagen worden opgevraagd
//    via bijvoorbeeld http://localhost:8080/uploads/recipes/voorbeeld.jpg.

}
