package main.groovy.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class ItemAlreadyExistException extends RuntimeException {

    ItemAlreadyExistException(String message) {
        super(message)
    }
}
