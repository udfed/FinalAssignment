package ClassManager.main;

import ClassManager.GUI.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
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


//        ArrayList<Student> stu = new ArrayList<>();
//        Student.fileRead(stu);
//
//        Student.arrayPrint(stu);

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
