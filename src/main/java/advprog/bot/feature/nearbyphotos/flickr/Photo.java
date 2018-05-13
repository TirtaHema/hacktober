package advprog.bot.feature.nearbyphotos.flickr;

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


