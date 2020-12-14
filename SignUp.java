package Music;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.*;
import java.sql.*;

public class SignUp {
    private JPanel Register;
    private JTextField userField;
    private JPasswordField passField;
    private JButton submitButton;
    private JLabel continueLabel;
    private JButton backButton;
    private PreparedStatement myStmt = null;
    private Connection myConn = null;
    private ResultSet myRs = null;

    public SignUp() {
        JFrame signUp = new JFrame("Sign Up!");
        signUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signUp.setSize(600,500);
        signUp.setVisible(true);
        signUp.add(Register);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp.dispose();
                new RapCorner();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //call method to add to db
                try {
                    myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicdb", "student", "student");

                    String sql = "INSERT INTO users (username, password)  VALUES (?,?)";
                    String username = userField.getText();
                    String password = passField.getText();
                    myStmt = myConn.prepareStatement(sql);
                    myStmt.setString(1, username);
                    myStmt.setString(2,password);
                    myStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Successfully registered!");
                    signUp.dispose();
                    new RapCorner();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
