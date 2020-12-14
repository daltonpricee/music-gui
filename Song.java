package Music;

public class Song {
    private String album;
    private String title;
    private String artist;

    public Song(String album, String title, String artist) {
        this.album = album;
        this.title = title;
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String toString() {
        return album + "\n"
                + artist + "\n"
                + title + "\n";
    }
}
