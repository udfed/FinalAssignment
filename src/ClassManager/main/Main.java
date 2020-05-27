package ClassManager.main;

import ClassManager.GUI.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

//        ArrayList<Student> stu = new ArrayList<>();
//        Student.fileRead(stu);
//
//        Student.arrayPrint(stu);

        Font font = new Font("微软雅黑", Font.PLAIN, 20);
        FontSet.GlobalFontSetting(font);
//        UIManager.put("Label.font", new Font("微软雅黑", Font.PLAIN, 20));
//        UIManager.put("TextField.font", new Font("Consolas", Font.PLAIN, 20));

        EventQueue.invokeLater(() -> {

            LoginFrame frame = new LoginFrame();
            frame.setTitle("班级信息管理系统");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        });



//        Class myclass = new Class(stu);

//        Scanner in = new Scanner(System.in);
//        String name = new String(in.next());
//        String number = new String(in.next());
//        Student x = null;

//
//        System.out.printf("\nClass.search() Test:\n");
//        x = myclass.search(name, number);
//        if (x != null) {
//            x.outPut();
//        }
//        else {
//            System.out.println("未找到该学生信息");
//        }
//
//        System.out.printf("\nClass.getAllStudents() Test:\n");
//        myclass.getAllStudents();
//
//        System.out.printf("\nClass.getFiveStudents() Test:\n");
//        myclass.getFiveStudents();

    }
}
