package com.kenzie.appserver;

public class CollectionNotFoundException extends RuntimeException {

    // TODO: Determine if this should be an Exception or RuntimeException
    // TODO: How to generate serialVersionUID
    public CollectionNotFoundException() {}

    public CollectionNotFoundException(String collectionId) {
        super("Could not find Collection: " + collectionId);
    }

    public CollectionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionNotFoundException(Throwable cause) {
        super(cause);
    }

    //    public CollectionNotFoundException(String message) {
//        super(message);
//    }
}
