package Music;
import java.sql.*;
import java.util.*;

//MusicDAO class.
public class MusicDAO {
    private Connection myConn;

    //MusicDAO constructor
    public MusicDAO() throws Exception {
        //Establish DB connection 
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicdb", "student", "student");
    }
    
    /**
    *getAllSongs method to get all songs
    */
    public List<Song> getAllSongs() throws Exception {
        List<Song> list = new ArrayList<>();
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM songs");
            while (myRs.next()) {
                Song tempSong = convertRowToSong(myRs);
                list.add(tempSong);
            }
            return list;
        } finally {
            close(myStmt, myRs);
        }
    }

    //Search songs method, searches songs.
    public List<Song> searchSongs(String title) throws Exception {
        List<Song> list = new ArrayList<>();
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            title += "%";
            //Search query
            myStmt = myConn.prepareStatement("SELECT * FROM songs WHERE track_name LIKE ?");
            myStmt.setString(1, title);
            myRs = myStmt.executeQuery();
            while (myRs.next()) {
                Song tempSong = convertRowToSong(myRs);
                list.add(tempSong);
            }
            return list;
        } finally {
            close(myStmt, myRs);

        }
    }

    /**
    *return song type, convertRowToString method
    */
    public Song convertRowToSong(ResultSet myRs) throws SQLException {

        String album = myRs.getString("artist");
        String title = myRs.getString("track_name");
        String artist = myRs.getString("Artist");

        Song tempSong = new Song(album, title, artist);
        return tempSong;
    }
    
    /**
    *Close the connection
    */
    private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
            throws SQLException {

        if (myRs != null) {
            myRs.close();
        }
        if (myStmt != null) {

        }
        if (myConn != null) {
            myConn.close();
        }
    }

    private void close(Statement myStmt, ResultSet myRs) throws SQLException {
        close(null, myStmt, myRs);
    }
    
}

