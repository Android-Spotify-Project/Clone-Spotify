package com.com.clone_spotify.model;

import android.media.Image;
import android.widget.ImageView;

import lombok.Data;

@Data
public class Home {
    private String songTitle;
    private String albumImg1;
    private String albumImg2;
    private String albumImg3;
    private String albumImg4;
    private String albumImg5;
    private String albumImg6;
    private String artistName1;
    private String artistName2;
    private String artistName3;
    private String artistName4;
    private String artistName5;
    private String artistName6;

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getAlbumImg1() {
        return albumImg1;
    }

    public void setAlbumImg1(String albumImg1) {
        this.albumImg1 = albumImg1;
    }

    public String getAlbumImg2() {
        return albumImg2;
    }

    public void setAlbumImg2(String albumImg2) {
        this.albumImg2 = albumImg2;
    }

    public String getAlbumImg3() {
        return albumImg3;
    }

    public void setAlbumImg3(String albumImg3) {
        this.albumImg3 = albumImg3;
    }

    public String getAlbumImg4() {
        return albumImg4;
    }

    public void setAlbumImg4(String albumImg4) {
        this.albumImg4 = albumImg4;
    }

    public String getAlbumImg5() {
        return albumImg5;
    }

    public void setAlbumImg5(String albumImg5) {
        this.albumImg5 = albumImg5;
    }

    public String getAlbumImg6() {
        return albumImg6;
    }

    public void setAlbumImg6(String albumImg6) {
        this.albumImg6 = albumImg6;
    }

    public String getArtistName1() {
        return artistName1;
    }

    public void setArtistName1(String artistName1) {
        this.artistName1 = artistName1;
    }

    public String getArtistName2() {
        return artistName2;
    }

    public void setArtistName2(String artistName2) {
        this.artistName2 = artistName2;
    }

    public String getArtistName3() {
        return artistName3;
    }

    public void setArtistName3(String artistName3) {
        this.artistName3 = artistName3;
    }

    public String getArtistName4() {
        return artistName4;
    }

    public void setArtistName4(String artistName4) {
        this.artistName4 = artistName4;
    }

    public String getArtistName5() {
        return artistName5;
    }

    public void setArtistName5(String artistName5) {
        this.artistName5 = artistName5;
    }

    public String getArtistName6() {
        return artistName6;
    }

    public void setArtistName6(String artistName6) {
        this.artistName6 = artistName6;
    }
}
