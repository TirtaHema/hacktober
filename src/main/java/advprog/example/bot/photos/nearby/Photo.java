package advprog.example.bot.photos.nearby;

public class Photo {
    private Integer id;
    private String name, url, data, fileType;

    public Photo(Integer id, String name, String url, String data, String fileType) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.data = data;
        this.fileType = fileType;
    }

    Integer getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getUrl() {
        return url;
    }

    String getData() {
        return data;
    }

    String getFileType() {
        return fileType;
    }
}


