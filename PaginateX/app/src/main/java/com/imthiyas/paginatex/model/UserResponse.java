package com.imthiyas.paginatex.model;

import java.util.List;

public class UserResponse {
    private List<User> users;
    private int nextPage;

    public List<User> getUsers() {
        return users;
    }

    public int getNextPage() {
        return nextPage;
    }
}
