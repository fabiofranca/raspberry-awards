package raspberry.awards.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCSVFormatException extends Exception {
    public InvalidCSVFormatException(Throwable e) {
        super("Formato do csv inválido", e);
    }
}
