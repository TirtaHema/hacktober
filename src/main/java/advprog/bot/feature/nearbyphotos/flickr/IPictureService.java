package advprog.bot.feature.nearbyphotos.flickr;

import java.util.List;

public interface IPictureService {
    public List<Photo> get5Photos(Location location);
    public String formatTitleForCarouselImages(String title);
}
