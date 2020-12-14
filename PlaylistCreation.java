package Music;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class PlaylistCreation {
    
    private JPanel creationPanel;
    private JTable songsToAdd;
    private JPanel songList;
    private JTable newSongs;
    private JPanel addedSongs;
    private JScrollPane addSongs;
    private JButton createPlaylistButton;
    private JLabel confirmLabel;
    private Connection myConn = null;
    private Statement myStmt = null;
    private PreparedStatement st = null;
    private PreparedStatement pts;
    private ResultSet myRs = null;
    private DefaultTableModel model = null;
    private DefaultTableModel model2 = null;
    private DefaultListModel mod = new DefaultListModel();
    HomePage homePage = new HomePage();

    public PlaylistCreation() throws SQLException {
        JFrame create = new JFrame("Create your playlist here!");
        create.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        create.setSize(700,600);
        create.setVisible(true);
        create.add(creationPanel);
        createTable();

        createPlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String song;
                String album;
                String artist;
                String sql;
                try {
                    myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicdb", "student", "student");
                    TableModel songModel = songsToAdd.getModel();

                    int[] indexs = songsToAdd.getSelectedRows();
                    Object[] row = new Object[3];
                    DefaultTableModel m = (DefaultTableModel)newSongs.getModel();

                    sql = "INSERT INTO playlist_songs(album_name, song_name, artist_name) VALUES(?, ?, ?)";
                    st = myConn.prepareStatement(sql);

                    for (int i = 0; i < indexs.length; i++) {
                        row[0]= songModel.getValueAt(indexs[i], 0);
                        row[1]= songModel.getValueAt(indexs[i], 1);
                        row[2]= songModel.getValueAt(indexs[i], 2);
                        m.addRow(row);

                        album = songModel.getValueAt(indexs[i], 0).toString();
                        song = songModel.getValueAt(indexs[i], 1).toString();
                        artist = songModel.getValueAt(indexs[i], 2).toString();

                        st.setString(1, album);
                        st.setString(2, song);
                        st.setString(3, artist);
                        st.executeUpdate();

                    }
                    
                    JOptionPane.showMessageDialog(null, "Playlist created!");
                    create.dispose();


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void createTable() {
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicdb", "student", "student");
            model = new DefaultTableModel(new String[]{"Album", "Song Title", "Artist"}, 0);
            model2 = new DefaultTableModel(new String[]{"Album", "Song Title", "Artist"}, 0);

            String query = "SELECT * FROM songs";
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(query);

            while (myRs.next()) {
                String d = myRs.getString("idalbum");
                String e = myRs.getString("track_name");
                String f = myRs.getString("Artist");
                model.addRow(new Object[]{d, e, f});
            }
            songsToAdd.setModel(model);
            newSongs.setModel(model2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
