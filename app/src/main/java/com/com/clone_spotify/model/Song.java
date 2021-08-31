package com.com.clone_spotify.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Song {

    private String mediaId;
    private String title;
    private String subTitle;
    private String songUrl;
    private String imageUrl;
    private String userId;
}
