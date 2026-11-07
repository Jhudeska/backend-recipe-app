package nl.novi.recipeapp.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleResourceNotFound(
            ResourceNotFoundException exception) {

        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return errors;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGeneralException(
            Exception exception) {

        return Map.of(
                "error", "Er is een interne fout opgetreden."
        );
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleResourceAlreadyExists(
            ResourceAlreadyExistsException exception) {

        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDataIntegrityViolation(
            DataIntegrityViolationException exception) {

        String databaseMessage =
                exception.getMostSpecificCause().getMessage();

        if (databaseMessage != null) {

            // Ingrediënt bestaat al in een recept
            if (databaseMessage.contains("uk_recipe_ingredient")) {
                return Map.of(
                        "error",
                        "Dit ingrediënt is al toegevoegd aan dit recept."
                );
            }

            // Ingrediënt is nog gekoppeld aan recepten
            if (databaseMessage.contains("fkgukrw6na9f61kb8djkkuvyxy8")) {
                return Map.of(
                        "error",
                        "Dit ingrediënt kan niet worden verwijderd omdat het nog aan een of meerdere recepten gekoppeld is."
                );
            }
        }

        return Map.of(
                "error",
                "Deze gegevens kunnen niet worden verwerkt omdat ze nog ergens in gebruik zijn."
        );
    }

    @ExceptionHandler(ResourceInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleResourceInUse(
            ResourceInUseException exception) {

        return Map.of("error", exception.getMessage());
    }
}
