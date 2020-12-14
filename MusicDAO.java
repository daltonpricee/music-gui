package Music;
import java.sql.*;
import java.util.*;

public class MusicDAO {
    private Connection myConn;

    public MusicDAO() throws Exception {
        //Establish DB connection 
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicdb", "student", "student");
    }



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

    public List<Song> searchSongs(String title) throws Exception {
        List<Song> list = new ArrayList<>();
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            title += "%";
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


    public Song convertRowToSong(ResultSet myRs) throws SQLException {

        String album = myRs.getString("artist");
        String title = myRs.getString("track_name");
        String artist = myRs.getString("Artist");

        Song tempSong = new Song(album, title, artist);
        return tempSong;
    }

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

