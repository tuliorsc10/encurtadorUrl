package tulio.costa.encurtadorUrl.exception;

import org.springframework.http.HttpStatus;
public class AppError extends RuntimeException{

    public HttpStatus statusCode;

    public AppError(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {return this.statusCode;}
}
