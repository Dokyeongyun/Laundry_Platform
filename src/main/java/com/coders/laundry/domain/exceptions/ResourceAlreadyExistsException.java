package com.coders.laundry.domain.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String resourceType) {
        super(String.format("[%s] is Already exists.", resourceType));
    }

}
