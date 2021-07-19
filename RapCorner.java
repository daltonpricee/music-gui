package Music;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
*Class RapCorner, main class with instance variables
*/
public class RapCorner extends JFrame {
    private PreparedStatement myStmt = null;
    private Connection myConn = null;
    private ResultSet myRs = null;
    private JButton forgotPasswordButton;
    private JPanel panelMain;
    private JPanel passwordPanel;
    private JLabel logoLabel;
    private JPanel optionPanel;
    private JPasswordField passwordTextField;
    private JTextField usernameTextField;
    private JButton logInButton;
    private JLabel noAccountLabel;
    private JButton signUpButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private String user;

    /*
    *Consturctor for RapCorner.
    */
    public RapCorner() {
            JFrame frame = new JFrame("The Rap Corner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600,600);
            frame.setVisible(true);
            frame.add(panelMain);
        
        /*
        *Action listener for logInButton.
        *Allow user to log in.
        */
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicdb", "student", "student");
                    
                    //SQL query
                    String queryCheck = "SELECT * from users WHERE username = ? AND password = ?";
                    user = usernameTextField.getText();
                    String password = passwordTextField.getText();

                    myStmt = myConn.prepareStatement(queryCheck);
                    myStmt.setString(1, user);
                    myStmt.setString(2,password);
                    myRs = myStmt.executeQuery();
                    
                    //if user is null/empty
                    if (user.isEmpty()) {
                        JOptionPane.showMessageDialog(null,"Enter your info to log in.");
                        usernameTextField.setText("");
                        passwordTextField.setText("");
                    }
                    else if (myRs.next()) {
                        dispose();
                        passwordTextField.setText("");
                        usernameTextField.setText("");
                        HomePage homePage = new HomePage();
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Invalid Credentials.");
                        usernameTextField.setText("");
                        passwordTextField.setText("");
                    }
                    } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        /**
        *Action listener for signUpButton.
        */
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SignUp();
            }
        });
    }
    
}
