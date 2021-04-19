package models;

public class FixedValuesTransactionDTO {

    private User user;

    private Float value;

    public FixedValuesTransactionDTO(User user, Float value) {
        this.user = user;
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
