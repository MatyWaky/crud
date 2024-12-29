package io.github.matywaky.crud.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when invalid input data is provided.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {

    private static final Logger LOG = LoggerFactory.getLogger(InvalidInputException.class);
    /**
     * Constructor for creating an {@link InvalidInputException}.
     *
     * @param input the invalid input data that caused the exception.
     */
    public InvalidInputException(String input) {
        super(String.format("Invalid input data {%s}", input));
        LOG.error("Invalid input data {{}}", input);
    }
}
