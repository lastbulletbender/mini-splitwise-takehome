package models;

public class PercentageBasedSplitTransactionDTO {

    private User user;

    private Float percentage;

    public PercentageBasedSplitTransactionDTO(User user, Float percentage) {
        this.user = user;
        this.percentage = percentage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }
}
