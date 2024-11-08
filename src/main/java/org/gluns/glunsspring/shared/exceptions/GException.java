package org.gluns.glunsspring.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * GException
 * Used to define the GException class.
 * 
 * @version 1.0
 */
@Getter
public class GException extends RuntimeException {
    
    final HttpStatus httpStatus;
    
    final String apiMessage;
    
    public GException(final String message, 
                       final Throwable cause) {
        super(message, cause);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        apiMessage = "Server error";
    }
    
    public GException(final String message, 
                       final Throwable cause,
                       final HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
        // TODO: Implement a dict of standard messages
        apiMessage = message;
    }
    
}
