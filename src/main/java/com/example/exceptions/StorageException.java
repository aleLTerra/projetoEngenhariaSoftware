package com.example.exceptions;

import java.io.Serializable;

public class StorageException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    public StorageException() {
        super();
    }
    public StorageException(String msg) {
        super(msg);
    }
    public StorageException(String msg, Exception e)  {
        super(msg, e);
    }
}
