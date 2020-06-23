package ClassManager.GUI;

import ClassManager.main.Class;
import ClassManager.main.FontSet;
import ClassManager.main.Student;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class MainFrame extends JFrame {

    private String username;
    private JLabel usernameLabel = new JLabel("当前用户: ");
    private DefaultMutableTreeNode root  = new DefaultMutableTreeNode("班级管理");
    private DefaultTreeModel treeModel = new DefaultTreeModel(root);
    private JTree classTree = new JTree(treeModel);
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JTable table = new JTable();
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


        JPanel eastPanel = new JPanel();

        JScrollPane tableScrollPane = new JScrollPane(table);
        FontSet.initFont(tableScrollPane, new Font("微软雅黑", Font.PLAIN, 12));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();// 设置table内容居中
        renderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, renderer);
        table.addMouseListener(tableMouseListener());

        //test
//        ArrayList<Student> students = new ArrayList<>();
//        Student.fileRead("src\\data.txt", students);
//
//        ClassNode classNode = new ClassNode("class1");
//        DefaultMutableTreeNode aClassNode = new DefaultMutableTreeNode(classNode);
//        DefaultTableModel aTableModel = classNode.getTableModel();
//
//        classNode.setPath("src\\data.txt");
//
//        treeModel.insertNodeInto(aClassNode, root, root.getChildCount());
//        classTree.expandRow(0);
//        for (Student e1 : students){
////            DefaultMutableTreeNode studentNode = new DefaultMutableTreeNode(new StudentNode(e1));
////            treeModel.insertNodeInto(studentNode, aClassNode, aClassNode.getChildCount());
////            StudentNode aStudentNode = (StudentNode) studentNode.getUserObject();
//            aTableModel.addRow(e1.getData());
////            classNode.addEditables(false);
//        }
//
//        table.setModel(aTableModel);
//        table.getColumn("姓名").setPreferredWidth(90);
//        table.getColumn("学号").setPreferredWidth(110);
//        table.getColumn("性别").setPreferredWidth(50);
//        table.setRowHeight(25);
//        setTitle("班级信息管理系统 [" + "class1" + "]");


        //树状结构
        JScrollPane treeScrollPane = new JScrollPane();
        treeScrollPane.setViewportView(classTree);
        classTree.setEditable(true);
        classTree.getCellEditor().addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                CellEditor editor = (CellEditor) e.getSource();
                TreePath path = classTree.getSelectionPath();
                if (path == null) return;
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object aClassNode = selectedNode.getUserObject();
                ClassNode classNode = (ClassNode)aClassNode;
                String str = (String) editor.getCellEditorValue();
                classNode.setName(str);
                setTitle("班级信息管理系统 [" + classNode.toString() + "]");
            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });
        classTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = classTree.getSelectionPath();
                if (path == null) return;
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                ClassNode classNode = (ClassNode) selectedNode.getUserObject();
                table.setModel(classNode.getTableModel());
                table.getColumn("姓名").setPreferredWidth(90);
                table.getColumn("学号").setPreferredWidth(110);
                table.getColumn("性别").setPreferredWidth(50);
                table.setRowHeight(25);
                setTitle("班级信息管理系统 [" + classNode.toString() + "]");
            }
        });
        classTree.addMouseListener(treeMouseListener());

        //菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("文件");
        menuBar.add(fileMenu);
        JMenuItem importItem = new JMenuItem("导入");
        JMenuItem saveItem = new JMenuItem("保存");
        fileMenu.add(importItem);
        fileMenu.add(saveItem);

        FontSet.initFont(menuBar, new Font("宋体", Font.PLAIN, 12));

        importItem.addActionListener(e -> {
            ImportDialog importDialog = new ImportDialog(this);
            importDialog.setVisible(true);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        });

        saveItem.addActionListener(e -> {
            TreePath path = classTree.getSelectionPath();
            if (path == null) return;
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            Object userObject = selectedNode.getUserObject();
            ClassNode _classNode = (ClassNode) userObject;
            try{
                File file = new File("src\\tmp\\tmp.txt");
                if (!file.exists()){
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                int n = table.getRowCount();
                boolean flag = false;
                for (int i = 0; i < n; ++i){
                    for (int j = 0; j < 16; ++j){
                        if (j <= 2){
                            String str = (String) table.getValueAt(i, j);
                            if (str.isEmpty()){
                                table.setRowSelectionInterval(i, i);
                                JOptionPane.showMessageDialog(table, table.getModel().getColumnName(j) + "不可为空");
                                flag = true;
                                break;
                            }
                            else {
                                bw.write(str + " ");
                            }
                        }
                        else if (j == 3){
                            int age = (int) table.getValueAt(i, j);
                            if (age == 0){
                                table.setRowSelectionInterval(i, i);
                                JOptionPane.showMessageDialog(table, table.getModel().getColumnName(j) + "不合法");
                                flag = true;
                                break;
                            }
                            else {
                                bw.write(Integer.toString(age) + " ");
                            }
                        }
                        else {
                            bw.write(table.getValueAt(i, j).toString() + " ");
                        }
                    }
                    if (flag) break;
                    bw.newLine();
                }

                bw.close();
                fw.close();

                File dest = new File("src\\account\\" + username + "\\" + _classNode.toString() + ".txt");
                dest.delete();
                Files.copy(file.toPath(), dest.toPath());
                file.delete();


            }catch (Exception e1){
                e1.printStackTrace();
            }
        });

        //用户面板
        JPanel userPanel = new JPanel();
        Font userPanelFont = new Font("微软雅黑", Font.PLAIN, 17);

        JButton logoutButton = new JButton(" 登出  ");
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);
        logoutButton.addActionListener(e -> {
            this.dispose();
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            }catch (Exception e1){
                e1.printStackTrace();
            }

            Font font = new Font("微软雅黑", Font.PLAIN, 20);

            EventQueue.invokeLater(() -> {

                LoginFrame frame = new LoginFrame();
                frame.setTitle("班级信息管理系统");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                FontSet.initFont(frame, font);
                frame.getMessageLabel().setFont(new Font("微软雅黑", Font.PLAIN, 15));
            });
        });

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


        setLayout(new GridBagLayout());
        add(tableScrollPane, new GBC(0, 0).setFill(GBC.BOTH).setWeight(7,17));
        add(eastPanel, new GBC(1,0, 1, 2).setFill(GBC.BOTH).setWeight(1, 1));



    }

    private MouseListener tableMouseListener(){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3){
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem insert = new JMenuItem("插入");
                    JMenuItem delete = new JMenuItem("删除");
                    popupMenu.add(insert);
                    popupMenu.add(delete);

                    int row = table.rowAtPoint(e.getPoint());
                    int[] rows = table.getSelectedRows();
                    if (rows.length <= 1)   table.setRowSelectionInterval(row, row);


                    insert.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Student student = new Student();
//                            DefaultTableModel tableModel1 = tableModel;
//                            tableModel1.insertRow(row >=0 ? row : 0, student.getData());
//                            table.setModel(tableModel1);
                            DefaultTableModel aTableModel = ((DefaultTableModel)table.getModel());
                            aTableModel.insertRow(row, student.getData());
                        }
                    });

                    delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DefaultTableModel aTableModel = ((DefaultTableModel)table.getModel());
                            for (int i = 0; i < rows.length; ++i){
                                aTableModel.removeRow(rows[0]);
                            }
                        }
                    });

                    popupMenu.show(table, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    private MouseListener treeMouseListener(){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem delete = new JMenuItem("删除");
                    popupMenu.add(delete);

                    TreePath path = classTree.getPathForLocation(e.getX(), e.getY());
                    classTree.setSelectionPath(path);
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    Object obj = node.getUserObject();
                    ClassNode classNode = (ClassNode) obj;

                    delete.addActionListener(e1 -> {
                        root.remove(node);
                        File file = new File("src\\account\\" + username + "\\" + classNode.toString() + ".txt");
                        file.delete();
                        classTree.updateUI();
                    });

                    popupMenu.show(classTree, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public static DefaultTableModel importData(MainFrame frame, String className, String path){
        DefaultTreeModel treeModel = frame.getTreeModel();
        DefaultMutableTreeNode root = frame.getRoot();

        ClassNode classNode = new ClassNode(className);
        DefaultMutableTreeNode aClassNode = new DefaultMutableTreeNode(classNode);
        treeModel.insertNodeInto(aClassNode, root, root.getChildCount());
        frame.getClassTree().setSelectionPath(new TreePath(aClassNode.getPath()));

        ArrayList<Student> students = new ArrayList<>();
        Student.fileRead(path, students);

        for (Student e1 : students){
//                    DefaultMutableTreeNode studentNode = new DefaultMutableTreeNode(new StudentNode(e1));
//                    treeModel.insertNodeInto(studentNode, aClassNode, aClassNode.getChildCount());
            classNode.getTableModel().addRow(e1.getData());
//                    classNode.addEditables(false);
        }

        return classNode.getTableModel();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public JTree getClassTree() {
        return classTree;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
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
            int flag = 0;
            DefaultMutableTreeNode root = owner.getRoot();
            Enumeration<TreeNode> en = root.children();
            while (en.hasMoreElements()){
                TreeNode node = en.nextElement();
                if (node.toString().equals(classnameField.getText())){
                    flag = 1;
                }
            }

            if (classnameField.getText().isEmpty()) {
                messageLabel.setText("请输入班级名");
            }
            else if (pathField.getText().isEmpty()) {
                messageLabel.setText("请选择文件");
            }
            else if (flag == 1){
                messageLabel.setText("班级名已存在");
            }
            else {
                //导入实现
//                DefaultTreeModel treeModel = owner.getTreeModel();
//
//                ClassNode classNode = new ClassNode(classnameField.getText());
//                DefaultMutableTreeNode aClassNode = new DefaultMutableTreeNode(classNode);
//                treeModel.insertNodeInto(aClassNode, root, root.getChildCount());
//                owner.getClassTree().setSelectionPath(new TreePath(aClassNode.getPath()));
//
//                ArrayList<Student> students = new ArrayList<>();
//                Student.fileRead(pathField.getText(), students);
//
//                classNode.setPath(pathField.getText());
//
//                for (Student e1 : students){
////                    DefaultMutableTreeNode studentNode = new DefaultMutableTreeNode(new StudentNode(e1));
////                    treeModel.insertNodeInto(studentNode, aClassNode, aClassNode.getChildCount());
//                    classNode.getTableModel().addRow(e1.getData());
////                    classNode.addEditables(false);
//                }
                DefaultTableModel tableModel = MainFrame.importData(owner, classnameField.getText(), pathField.getText());
                owner.getTable().setModel(tableModel);
//                owner.getTable().getColumn("姓名").setPreferredWidth(90);
//                owner.getTable().getColumn("学号").setPreferredWidth(110);
//                owner.getTable().getColumn("性别").setPreferredWidth(50);
//                owner.getTable().setRowHeight(25);

                owner.setTitle("班级信息管理系统 [" + classnameField.getText() + "]");

                try {
                    Files.copy(new File(pathField.getText()).toPath(),
                            new File("src\\account\\" + owner.getUsername() + "\\" + classnameField.getText() + ".txt").toPath());
                }catch (Exception e1){
                    e1.printStackTrace();
                }

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
    //private ArrayList<Boolean> editables = new ArrayList<>();
    private String path;
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

//    public void addEditables(boolean editable) {
//        editables.add(editable);
//    }
//
//    public void setEditables(int row, boolean editable) {
//        editables.set(row, editable);
//    }


    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
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

    public Student getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return student.getName();
    }
}
class CellEditorAction implements CellEditorListener {
    public void editingCanceled(ChangeEvent e) {
        System.out.println("编辑取消");
    }

    public void editingStopped(ChangeEvent e) {
        System.out.println("编辑结束");
    }
}
