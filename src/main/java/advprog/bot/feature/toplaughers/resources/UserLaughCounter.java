package advprog.bot.feature.toplaughers.resources;

public class UserLaughCounter {

    private String userId;
    private Integer counter;

    public UserLaughCounter(String userId) {
        this.userId = userId;
        this.counter = 1;
    }

    public void increment() {
        counter++;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getCounter() {
        return counter;
    }
}
