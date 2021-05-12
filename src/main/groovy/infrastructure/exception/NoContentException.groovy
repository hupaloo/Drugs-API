package main.groovy.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NO_CONTENT)
class NoContentException extends RuntimeException {

    public NoContentException(String message) {
        super(message)
    }

}
