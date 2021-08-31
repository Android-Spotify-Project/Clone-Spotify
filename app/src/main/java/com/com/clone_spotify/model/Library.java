package com.com.clone_spotify.model;

import android.widget.Button;

import lombok.Data;

@Data
public class Library {
    private String albumImg;
    private String songTitle;
    private String artistName;
    //private Button like;

    public String getAlbumImg() {
        return albumImg;
    }

    public void setAlbumImg(String albumImg) {
        this.albumImg = albumImg;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

//    public Button getLike() {
//        return like;
//    }
//
//    public void setLike(Button like) {
//        this.like = like;
//    }
}
