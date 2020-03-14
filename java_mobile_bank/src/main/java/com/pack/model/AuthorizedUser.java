package com.pack.model;

import java.io.Serializable;
import java.util.UUID;

public class AuthorizedUser implements Serializable {

    private UUID token;
    private long id;

    public AuthorizedUser(UUID token, long id) {
        this.token = token;
        this.id = id;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
