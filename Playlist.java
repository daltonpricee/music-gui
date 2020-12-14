package Music;

public class Playlist {
    private String songName;
    private String artist;
    private String album;

    public Playlist(String songName, String artist, String album) {
        this.songName = songName;
        this.artist = artist;
        this.album = album;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
