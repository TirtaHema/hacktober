package yerlandinata.youtubeinfo;

public final class YoutubeVideo {
    private final String title;
    private final String channelName;
    private final int viewCount;
    private final int likeCount;
    private final int dislikeCount;

    public YoutubeVideo(String title, String channelName, int viewsCount,
                        int likesCount, int dislikesCount) {
        this.title = title;
        this.channelName = channelName;
        this.viewCount = viewsCount;
        this.likeCount = likesCount;
        this.dislikeCount = dislikesCount;
    }

    public String getTitle() {
        return title;
    }

    public String getChannelName() {
        return channelName;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof YoutubeVideo)) {
            return false;
        }
        YoutubeVideo other = (YoutubeVideo) o;
        return title.equals(other.title)
                && channelName.equals(other.channelName)
                && viewCount == other.viewCount
                && likeCount == other.likeCount
                && dislikeCount == other.dislikeCount;
    }

    @Override
    public String toString() {
        return String.format("[Youtube: title=(%s), channel=(%s), views=(%d), likes=(%d), "
                        + "dislikes=(%d)]",
                        title, channelName, viewCount, likeCount, dislikeCount);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + title.hashCode();
        result = 37 * result + channelName.hashCode();
        result = 37 * result + viewCount;
        result = 37 * result + likeCount;
        result = 37 * result + dislikeCount;
        return result;
    }

}
