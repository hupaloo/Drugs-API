package main.groovy.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ItemDoesNotExistException extends RuntimeException {

    public ItemDoesNotExistException(String message) {
        super(message)
    }

}
