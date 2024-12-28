package io.github.matywaky.crud.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an entity is not found in the database.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private static final Logger LOG = LoggerFactory.getLogger(EntityNotFoundException.class);

    /**
     * Constructor for creating an {@link EntityNotFoundException}.
     *
     * @param entityName the name of the entity that could not be found.
     * @param id the ID of the entity that could not be found.
     */
    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("{%s} entity with id {%s} not found.", entityName, id));
        LOG.error("{{}} entity with id {{}} not found.", entityName, id);
    }
}
