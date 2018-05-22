package advprog.bot.feature.fakenews;

public class News {
    private Category category;
    private String note;

    public News(Category category, String note) {
        this.category = category;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isSafe() {
        return category.equals(Category.SAFE);
    }
}
