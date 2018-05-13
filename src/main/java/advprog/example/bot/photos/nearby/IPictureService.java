package advprog.example.bot.photos.nearby;

import java.util.List;

public interface IPictureService {
    public List<Photo> get5Photos(Location location);
}
