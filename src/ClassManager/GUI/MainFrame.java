package ClassManager.GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static final int TABLE_ROWS = 10;
    public static final int TABLE_COLUMNS = 17;

    private String username;
    private JTable table;

    public MainFrame() {
        //获取屏幕大小
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        //设置框架位置
        setLocation(screenWidth / 4, screenHeight / 4);


        //设置框架大小
        setSize(screenWidth / 2, screenHeight / 2);
        //setLocationByPlatform(true);

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JLabel usernameLabel = new JLabel("当前用户: " + username);
        table= new JTable(0, 16);

        setLayout(new GridBagLayout());

        add(table, new GBC(0, 0).setFill(GBC.BOTH).setWeight(7,17));
        add(jp1, new GBC(1,0, 1, 2).setFill(GBC.BOTH).setWeight(1, 1));
        add(jp2, new GBC(0, 1).setFill(GBC.BOTH).setWeight(7, 1));



        jp1.setBorder(BorderFactory.createEtchedBorder());
        jp1.setLayout(new GridBagLayout());
        jp1.add(usernameLabel, new GBC(0, 0).setWeight(1, 4));

        jp2.setBorder(BorderFactory.createEtchedBorder());

    }

    public void setUsername(String username) {
        this.username = username;
    }

    //    public void updateSample() {
//        String fontFace = (String) face.getSelectedItem();
//
//        int fontSize = size.getItemAt(size.getSelectedIndex());
//        Font font = new Font(fontFace, 0, fontSize);
//
//    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setTitle("班级信息管理系统");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
