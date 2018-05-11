package yerlandinata.youtubeinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoutubeInfoChatResponder {

    private final YoutubeInfoFetcher youtubeInfoFetcher;

    @Autowired
    public YoutubeInfoChatResponder(YoutubeInfoFetcher youtubeInfoFetcher) {
        this.youtubeInfoFetcher = youtubeInfoFetcher;
    }

}
