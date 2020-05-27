package ClassManager.GUI;

import ClassManager.main.Account;
import ClassManager.main.MD5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginFrame extends JFrame {

    private JLabel messageLabel = new JLabel("    ");
    private JTextField usernameField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);

    public LoginFrame(){

        setBounds(600,400,480,270);

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

class LoginListener implements ActionListener {

    private LoginFrame loginFrame;

    public LoginListener(LoginFrame loginFrame){
        this.loginFrame = loginFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        loginFrame.getMessageLabel().setFont(new Font("微软雅黑", Font.PLAIN, 15));
        loginFrame.getMessageLabel().setForeground(Color.red);

        if (loginFrame.getUsernameField().getText().isEmpty()){
            loginFrame.getMessageLabel().setText("请输入用户名");
            return ;
        }
        if (new String(loginFrame.getPasswordField().getPassword()).isEmpty()){
            loginFrame.getMessageLabel().setText("请输入密码");
            return ;
        }

        Account currentAccount = new Account(loginFrame.getUsernameField().getText(),
                MD5.getMD5(new String(loginFrame.getPasswordField().getPassword())));

        try {
            FileInputStream is = new FileInputStream("src\\account.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = null;

            boolean flag = true;
            while((str = br.readLine()) != null && flag){
                Account account = new Account(str);

                if (currentAccount.equals(account)){
                    MainFrame frame = new MainFrame();
                    frame.setTitle("班级信息管理系统");
                    frame.setUsername(loginFrame.getUsernameField().getText());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);

                    loginFrame.dispose();

                    flag = false;
                }
            }

            if (flag){
                loginFrame.getMessageLabel().setText("用户名或密码错误");
            }

            is.close();
            br.close();

        } catch (IOException e1){
            e1.printStackTrace();
        }
    }

}

