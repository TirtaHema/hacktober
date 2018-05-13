package advprog.example.bot.photos.nearby;

public class Photo {
    private String url, title;

    public Photo(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() { return title; }
}


