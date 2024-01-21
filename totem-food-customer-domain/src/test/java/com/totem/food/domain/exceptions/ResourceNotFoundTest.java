package com.totem.food.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceNotFoundTest {

    @Test
    public void resourceNotFoundTest() {
        Class<?> resource = String.class;
        String message = "Resource not found";

        Exception thrownException = assertThrows(ResourceNotFound.class,
                () -> {
                    throw new ResourceNotFound(resource, message);
                });

        assertEquals(message, thrownException.getMessage());
    }
}