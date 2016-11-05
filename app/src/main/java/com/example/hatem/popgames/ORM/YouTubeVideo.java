
package com.example.hatem.popgames.ORM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YouTubeVideo {

    @SerializedName("youtube_id")
    @Expose
    private String youtubeId;

    /**
     * 
     * @return
     *     The youtubeId
     */
    public String getYoutubeId() {
        return youtubeId;
    }

    /**
     * 
     * @param youtubeId
     *     The youtube_id
     */
    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }


}
