package com.com.clone_spotify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SongDto {
    private String mediaId;
    private String albumImg;
    private String songTitle;
    private String artistName;
    private String songUrl;
}
