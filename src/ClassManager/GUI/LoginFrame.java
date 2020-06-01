package ClassManager.GUI;

import ClassManager.main.Account;
import ClassManager.main.FontSet;
import ClassManager.main.MD5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

public class LoginFrame extends JFrame {

    private JLabel messageLabel = new JLabel("   ");
    private JTextField usernameField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);

    public LoginFrame(){

        setBounds(600,400,480,270);
        setResizable(false);

        JLabel usernameLabel = new JLabel(" 用户名");
        JLabel passwordLabel = new JLabel("密  码");
        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");
        JCheckBox plaint = new JCheckBox("明文");

        messageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordField.setEchoChar('●');

        setLayout(new GridBagLayout());
        add(messageLabel, new GBC(1, 0).setAnchor(GBC.SOUTH).setWeight(0, 1));
        add(usernameLabel, new GBC(0, 1).setAnchor(GBC.CENTER).setWeight(2, 2));
        add(usernameField, new GBC(1, 1).setAnchor(GBC.CENTER).setWeight(3, 2));
        add(passwordLabel, new GBC(0, 2).setAnchor(GBC.CENTER).setWeight(2, 2));
        add(passwordField, new GBC(1, 2).setAnchor(GBC.CENTER).setWeight(3, 2));
        add(registerButton, new GBC(0, 3).setAnchor(GBC.CENTER).setWeight(2,2));
        add(loginButton, new GBC(1, 3).setAnchor(GBC.CENTER).setWeight(2, 2));
        add(plaint, new GBC(1,3).setAnchor(GBC.EAST).setWeight(1,2));

        ActionListener plaintListener = e -> {
            if (plaint.isSelected())
                passwordField.setEchoChar('\0');
            else
                passwordField.setEchoChar('●');
        };
        plaint.addActionListener(plaintListener);

        LoginListener loginListener = new LoginListener(this);
        loginButton.addActionListener(loginListener);

        ActionListener registerListener = e -> {
            RegisterDialog registerDialog = new RegisterDialog(this);
            FontSet.initFont(registerDialog, new Font("微软雅黑", Font.PLAIN, 20));
            registerDialog.getMessageLabel().setFont(new Font("微软雅黑", Font.PLAIN, 15));
            registerDialog.setVisible(true);
        };
        registerButton.addActionListener(registerListener);
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}

class RegisterDialog extends JDialog {

    private JLabel messageLabel = new JLabel("  ");
    private JTextField usernameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);
    private JPasswordField confirmField = new JPasswordField(10);

    public RegisterDialog(LoginFrame owner){
        super(owner, "用户注册", true);

        setBounds(700,300,300,400);
        setResizable(false);

        JLabel usernameLabel = new JLabel("用户名");
        JLabel passwordLabel = new JLabel("密  码");
        JLabel confirmLabel = new JLabel("确认密码");
        JButton registerButton = new JButton("注册");
        JCheckBox plaint = new JCheckBox("明文");

        messageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordField.setEchoChar('●');
        confirmField.setEchoChar('●');

        setLayout(new GridBagLayout());

        add(messageLabel, new GBC(1, 0).setAnchor(GBC.SOUTH).setWeight(1, 2));

        add(usernameLabel, new GBC(0, 1).setAnchor(GBC.CENTER).setWeight(1, 2));
        add(usernameField, new GBC(1, 1).setAnchor(GBC.CENTER).setWeight(3, 2));

        add(passwordLabel, new GBC(0, 2).setAnchor(GBC.CENTER).setWeight(2, 2));
        add(passwordField, new GBC(1, 2).setAnchor(GBC.CENTER).setWeight(3, 2));

        add(confirmLabel, new GBC(0, 3).setAnchor(GBC.CENTER).setWeight(1,2));
        add(confirmField, new GBC(1, 3).setAnchor(GBC.CENTER).setWeight(1, 2));

        add(plaint, new GBC(0, 4).setAnchor(GBC.CENTER).setWeight(1,2));
        add(registerButton, new GBC(1, 4).setAnchor(GBC.CENTER).setWeight(1,2));

        ActionListener plaintListener = e -> {
            if (plaint.isSelected()){
                passwordField.setEchoChar('\0');
                confirmField.setEchoChar('\0');
            }
            else{
                passwordField.setEchoChar('●');
                confirmField.setEchoChar('●');
            }
        };
        plaint.addActionListener(plaintListener);

        RegisterListener registerListener = new RegisterListener(this);
        registerButton.addActionListener(registerListener);

    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmField() {
        return confirmField;
    }
}

class RegisterListener implements ActionListener{

    private RegisterDialog registerDialog;

    public RegisterListener(RegisterDialog registerDialog){
        this.registerDialog = registerDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        registerDialog.getMessageLabel().setFont(new Font("微软雅黑", Font.PLAIN, 15));
        registerDialog.getMessageLabel().setForeground(Color.red);

        try {
            FileInputStream is = new FileInputStream("src\\account.properties");
            Properties properties = new Properties();
            properties.load(is);

            String pwd = properties.getProperty(registerDialog.getUsernameField().getText());

            if (pwd != null){
                registerDialog.getMessageLabel().setText("用户名已存在");
                return;
            }


        }catch (IOException e1){
            e1.printStackTrace();
        }

        if (registerDialog.getUsernameField().getText().isEmpty()){
            registerDialog.getMessageLabel().setText("用户名不能为空");
            return;
        }
        if (new String(registerDialog.getPasswordField().getPassword()).isEmpty()){
            registerDialog.getMessageLabel().setText("密码不能为空");
            return;
        }
        if (new String(registerDialog.getConfirmField().getPassword()).isEmpty()){
            registerDialog.getMessageLabel().setText("请确认密码");
            return;
        }



        if (new String(registerDialog.getPasswordField().getPassword())
                .equals(new String(registerDialog.getConfirmField().getPassword()))){

            Account account = new Account(registerDialog.getUsernameField().getText(),
                    MD5.getMD5(new String(registerDialog.getPasswordField().getPassword())));

            try {
                FileOutputStream os = new FileOutputStream("src\\account.properties", true);

                Properties properties = new Properties();
                properties.setProperty(registerDialog.getUsernameField().getText(),
                        MD5.getMD5(new String(registerDialog.getPasswordField().getPassword())));
                properties.store(os, "");

                os.close();
                registerDialog.getMessageLabel().setText("注册成功！");

            } catch (IOException e1){
                e1.printStackTrace();
            }
        }
        else
            registerDialog.getMessageLabel().setText("两次输入的密码不一致");
    }
}
class LoginListener implements ActionListener {

    private LoginFrame loginFrame;

    public LoginListener(LoginFrame loginFrame){
        this.loginFrame = loginFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = loginFrame.getUsernameField().getText();
        String password = new String(loginFrame.getPasswordField().getPassword());

        loginFrame.getMessageLabel().setFont(new Font("微软雅黑", Font.PLAIN, 15));
        loginFrame.getMessageLabel().setForeground(Color.red);

        if (username.isEmpty()){
            loginFrame.getMessageLabel().setText("请输入用户名");
            return ;
        }
        if (password.isEmpty()){
            loginFrame.getMessageLabel().setText("请输入密码");
            return ;
        }

        Account currentAccount = new Account(username, MD5.getMD5(password));

        try {
            FileInputStream is = new FileInputStream("src\\account.properties");

            Properties properties = new Properties();
            properties.load(is);

            String pwd = properties.getProperty(loginFrame.getUsernameField().getText());

            if (pwd != null && pwd.equals(MD5.getMD5(new String(loginFrame.getPasswordField().getPassword())))) {

                MainFrame mainFrame = new MainFrame();
                mainFrame.setTitle("班级信息管理系统");
                mainFrame.getUsernameLabel().setText("当前用户: " + username);
                mainFrame.setUsername(username);

                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setVisible(true);
                loginFrame.dispose();
            }
            else {
                loginFrame.getMessageLabel().setText("用户名或密码错误");
            }

            is.close();
        } catch (IOException e1){
            e1.printStackTrace();
        }
    }

}

