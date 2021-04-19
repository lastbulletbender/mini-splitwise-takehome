package models;

import java.util.HashMap;

public class User {

    private Long id;

    private String name;

    private HashMap<String, Group> groups;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
        this.groups = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Group> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, Group> groups) {
        this.groups = groups;
    }

}
