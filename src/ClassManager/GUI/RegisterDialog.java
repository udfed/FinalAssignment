package ClassManager.GUI;

import ClassManager.main.Account;
import ClassManager.main.MD5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RegisterDialog extends JDialog {

    private JLabel messageLabel = new JLabel("  ");
    private JTextField usernameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);
    private JPasswordField confirmField = new JPasswordField(10);

    public RegisterDialog(LoginFrame owner){
        super(owner, "用户注册", true);

        setBounds(700,300,300,400);

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

                os.write(String.format("%s\n", account.toString()).getBytes());

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