package ClassManager.GUI;

import ClassManager.main.FontSet;
import ClassManager.main.Student;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class MainFrame extends JFrame {

    private String username;
    private JLabel usernameLabel = new JLabel("当前用户: ");
    private DefaultTableModel tableModel = new DefaultTableModel();
    private DefaultMutableTreeNode root  = new DefaultMutableTreeNode("班级管理");
    private JTree classTree = new JTree(root);
    private JTable table = new JTable(tableModel);

    public MainFrame() {
        //获取屏幕大小
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        //设置框架大小
        final int WIDTH = 1024;
        final int HEIGHT = 576;
        setSize(WIDTH, HEIGHT);

        //设置框架位置
        setLocation(screenWidth / 2 - WIDTH / 2, screenHeight / 2 - HEIGHT / 2);

        //setLocationByPlatform(true);

        JPanel eastPanel = new JPanel();
        JPanel operationPanel = new JPanel();
        String[] tableTitle = new String[]{
                "姓名", "学号", "性别", "年龄", "身高", "体重", "语文", "数学",
                "英语", "物理", "化学", "生物", "政治", "历史", "地理", "技术"};
        tableModel.setColumnIdentifiers(tableTitle);

        JScrollPane tableScrollPane = new JScrollPane(table);
        FontSet.initFont(tableScrollPane, new Font("微软雅黑", Font.PLAIN, 12));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getColumn("姓名").setPreferredWidth(90);
        table.getColumn("学号").setPreferredWidth(110);
        table.getColumn("性别").setPreferredWidth(50);
        table.setRowHeight(25);
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();// 设置table内容居中
        renderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, renderer);

        //test
        ArrayList<Student> students = new ArrayList<>();
        Student.fileRead("src\\data.txt", students);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("class1");
        root.add(node);
        classTree.expandRow(0);
        for (Student e1 : students){
            node.add(new DefaultMutableTreeNode(new StudentNode(e1)));
            tableModel.addRow(e1.getData());
        }
        setTitle("班级信息管理系统 [" + "class1" + "]");

        //树状结构
        JScrollPane treeScrollPane = new JScrollPane();
        treeScrollPane.setViewportView(classTree);

        //菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("文件");
        menuBar.add(fileMenu);
        JMenuItem importItem = new JMenuItem("导入");
        fileMenu.add(importItem);
        FontSet.initFont(menuBar, new Font("宋体", Font.PLAIN, 12));

        importItem.addActionListener(e -> {
            ImportDialog importDialog = new ImportDialog(this);
            importDialog.setVisible(true);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        });

        //用户面板
        JPanel userPanel = new JPanel();
        Font userPanelFont = new Font("微软雅黑", Font.PLAIN, 17);

        JButton logoutButton = new JButton(" 登出  ");
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);

        userPanel.setBorder(BorderFactory.createEtchedBorder());
        userPanel.setLayout(new GridBagLayout());
        userPanel.add(usernameLabel, new GBC(0, 0, 2, 1).setWeight(1, 1));
        userPanel.add(logoutButton, new GBC(1, 1).setAnchor(GBC.EAST).setWeight(1, 1));

        FontSet.initFont(userPanel, userPanelFont);
        HashMap<TextAttribute, Object> hm = new HashMap<TextAttribute, Object>();
        hm.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON); // 定义是否有下划线
        hm.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);// 定义斜体
        hm.put(TextAttribute.SIZE, 13); // 定义字号
        hm.put(TextAttribute.FAMILY, "微软雅黑"); // 定义字体名
        logoutButton.setFont(new Font(hm));
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //右侧面板
        eastPanel.setLayout(new GridBagLayout());
        eastPanel.add(userPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1));
        eastPanel.add(treeScrollPane, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 6));

        //操作面板
        operationPanel.setBorder(BorderFactory.createEtchedBorder());

        setLayout(new GridBagLayout());
        add(tableScrollPane, new GBC(0, 0).setFill(GBC.BOTH).setWeight(7,17));
        add(eastPanel, new GBC(1,0, 1, 2).setFill(GBC.BOTH).setWeight(1, 1));
        add(operationPanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(7, 1));



    }


    public void setUsername(String username) {
        this.username = username;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public JTree getClassTree() {
        return classTree;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }


    //    public void updateSample() {
//        String fontFace = (String) face.getSelectedItem();
//
//        int fontSize = size.getItemAt(size.getSelectedIndex());
//        Font font = new Font(fontFace, 0, fontSize);
//
//    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
        }
        MainFrame mainFrame = new MainFrame();
        mainFrame.setTitle("班级信息管理系统");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}


class ImportDialog extends JDialog {


    public ImportDialog(MainFrame owner) {
        super(owner, "导入班级", true);

        setBounds(owner.getX() + owner.getWidth() / 4, owner.getY() + owner.getHeight() / 4,
                owner.getWidth() / 2, owner.getHeight() / 3);
        setResizable(false);

        JLabel messageLabel = new JLabel("  ");

        JLabel classnameLabel = new JLabel("班级名：");
        JTextField classnameField = new JTextField(7);

        JLabel pathLabel = new JLabel("文件路径：");
        JTextField pathField = new JTextField();
        pathField.setEditable(false);
        JButton browseButton = new JButton("···");

        JButton confirmButton = new JButton("确认");
        JButton cancelButton = new JButton("取消");

        setLayout(new GridBagLayout());
        add(messageLabel, new GBC(1, 0).setWeight(1, 1));
        add(classnameLabel, new GBC(0, 1).setWeight(1, 1));
        add(classnameField, new GBC(1, 1).setFill(GBC.HORIZONTAL).setAnchor(GBC.WEST).setWeight(4, 1));
        add(pathLabel, new GBC(0, 2).setWeight(1, 1));
        add(pathField, new GBC(1, 2).setFill(GBC.HORIZONTAL).setAnchor(GBC.WEST).setWeight(4, 1));
        add(browseButton, new GBC(2, 2).setWeight(0, 1));
        add(confirmButton, new GBC(0, 3, 2, 1)
                .setAnchor(GBC.EAST).setWeight(1, 1));
        add(cancelButton, new GBC(2, 3).setWeight(0, 1));

        FontSet.initFont(this, new Font("微软雅黑", Font.PLAIN, 15));
        messageLabel.setForeground(Color.RED);

        JFileChooser chooser = new JFileChooser();
        browseButton.addActionListener(e -> {
            chooser.setCurrentDirectory(new File("."));
            chooser.setFileFilter(new FileNameExtensionFilter("文本文件(*.txt)", "txt"));
            chooser.setAcceptAllFileFilterUsed(false);

            int result = chooser.showDialog(this, "导入");
            if (result == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getPath();
                pathField.setText(path);
            }
        });

        confirmButton.addActionListener(e -> {
            if (classnameField.getText().isEmpty()) {
                messageLabel.setText("请输入班级名");
            } else if (pathField.getText().isEmpty()) {
                messageLabel.setText("请选择文件");
            } else {
                //导入实现
                ClassNode classNode = new ClassNode(classnameField.getText());
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(classNode);
                owner.getRoot().add(node);

//                owner.getClassTree().expandRow(0);
//                owner.getClassTree().expandRow(1);
                //System.out.println(owner.getRoot().getChildCount());
                ArrayList<Student> students = new ArrayList<>();
                Student.fileRead(pathField.getText(), students);
                for (Student e1 : students){
                    node.add(new DefaultMutableTreeNode(new StudentNode(e1)));
                    classNode.getTableModel().addRow(e1.getData());
                }


                owner.getTable().setModel(classNode.getTableModel());
                owner.getTable().getColumn("姓名").setPreferredWidth(90);
                owner.getTable().getColumn("学号").setPreferredWidth(110);
                owner.getTable().getColumn("性别").setPreferredWidth(50);
                owner.getTable().setRowHeight(25);

                owner.setTitle("班级信息管理系统 [" + classnameField.getText() + "]");
                dispose();
            }
        });

        cancelButton.addActionListener(e -> {
            dispose();
        });

    }
}

class ClassNode{
    private String name;
    private DefaultTableModel tableModel = new DefaultTableModel();

    protected ClassNode(String name){
        this.name = name;
        String[] tableTitle = new String[]{
                "姓名", "学号", "性别", "年龄", "身高", "体重", "语文", "数学",
                "英语", "物理", "化学", "生物", "政治", "历史", "地理", "技术"};
        tableModel.setColumnIdentifiers(tableTitle);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    @Override
    public String toString() {
        return name;
    }
}
class StudentNode {
    private Student student;

    public StudentNode(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return student.getName();
    }
}
