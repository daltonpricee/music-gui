package Music;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.*;

/**
*Class header for HomePage.
*Extends JFrame class.
*/
public class HomePage extends JFrame {
    private Statement myStmt = null;
    private PreparedStatement st = null;
    private Connection myConn = null;
    private ResultSet myRs = null;
    private DefaultTableModel model = null;
    private DefaultTableModel model2 = null;
    private JPanel homePanel;
    private JTabbedPane tabbedPane1;
    private JTabbedPane artistTab;
    private JTextField searchBar;
    private JButton searchButton;
    private JTable musicTable;
    private JScrollPane panel3;
    private JButton makeNewPlaylistButton;
    private JButton logOutButton;
    private JLabel welcomeLabel;
    private JCheckBox checkBox;
    private JLabel thankYouLabel;
    private JLabel mom;
    private JLabel dad;
    private JCheckBox searchByAlbumCheckBox;
    private JTable addSongs;
    private JScrollPane songsDisplay;
    private JScrollPane createPlaylist;
    private DefaultListModel mod = new DefaultListModel();
    private DefaultTableModel model3;
    private JList<String> playlistList;
    private JLabel playlistLabel;
    private JTable artistTable;
    private JTable yourSongs;
    private String playlistName;

    /**
    *HomePage constructor.
    *@throws SQLException.
    */
    public HomePage() throws SQLException {
        JFrame homeFrame = new JFrame("Homepage");
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setSize(700, 600);
        homeFrame.setVisible(true);
        homeFrame.add(homePanel);
        createTable();
        
        //action listener for log out button.
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeFrame.dispose();
                new RapCorner();
            }
        });
        
        /**
        *Action listener for searchButton, allows user to search.
        */
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicdb", "student", "student");
                    model = new DefaultTableModel(new String[]{"Album", "Song Title", "Artist"}, 0);
                    String q = "SELECT * FROM songs WHERE track_name LIKE ?";
                    String search = searchBar.getText();

                    st = myConn.prepareStatement(q);

                    st.setString(1, "%" + search + "%");
                    myRs = st.executeQuery();

                    while (myRs.next()) {
                        String d = myRs.getString("idalbum");
                        String g = myRs.getString("track_name");
                        String f = myRs.getString("Artist");
                        model.addRow(new Object[]{d, g, f});
                    }
                    musicTable.setModel(model);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        /**
        *Action listener for makeNewPlaylist button. Lets user make new playlist on button click.
        */
        makeNewPlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    playlistName = JOptionPane.showInputDialog("What's the name of your playlist: ");
                mod.addElement(playlistName);
                    playlistList.setModel(mod);

                try {
                    new PlaylistCreation();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            };
        });
        
        /**
        *Action Listener for addListSeletion.
        *Adds to list selection list.
        */
        playlistList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
               String value = playlistList.getSelectedValue();
               
               String sql = "SELECT * FROM playlist_songs WHERE playlist_title = " + value;
                try {
                    myStmt = myConn.createStatement();
                    myRs = myStmt.executeQuery(sql);
                    
                    while (myRs.next()) {
                       // addSongs.
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    /**
    *Method to create table using MNySQL DB info.
    */
    public void createTable() {
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicdb", "student", "student");
            model = new DefaultTableModel(new String[]{"Album", "Song Title", "Artist"}, 0);
            model2 = new DefaultTableModel(new String[]{"Your Artists"}, 0);
           
            String query = "SELECT * FROM songs";
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
                String d = myRs.getString("idalbum");
                String e = myRs.getString("track_name");
                String f = myRs.getString("Artist");
                model.addRow(new Object[]{d, e, f});
                model2.addRow(new Object[]{f});
            }

            musicTable.setModel(model);
            artistTable.setModel(model2);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //to-do
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

