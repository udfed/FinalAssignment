package ClassManager.GUI;

import ClassManager.main.Account;
import ClassManager.main.MD5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LoginFrame extends JFrame {

    private JLabel messageLabel = new JLabel("    ");
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

        try {
            FileInputStream is = new FileInputStream("src\\account.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = null;

            while ((str = br.readLine()) != null){
                if (registerDialog.getUsernameField().getText().equals(str.substring(0, str.indexOf(' ')))){
                    registerDialog.getMessageLabel().setText("用户名已存在");

                    br.close();
                    is.close();

                    return;
                }
            }
        }catch (IOException e1){
            e1.printStackTrace();
        }
        if (new String(registerDialog.getPasswordField().getPassword())
                .equals(new String(registerDialog.getConfirmField().getPassword()))){

            Account account = new Account(registerDialog.getUsernameField().getText(),
                    MD5.getMD5(new String(registerDialog.getPasswordField().getPassword())));

            try {
                FileOutputStream os = new FileOutputStream("src\\account.txt", true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

                bw.write(account.toString());
                bw.newLine();

                bw.close();
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
                    frame.getUsernameLabel().setText("当前用户: " + loginFrame.getUsernameField().getText());
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

