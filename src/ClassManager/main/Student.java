package ClassManager.main;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Student{
    private final int items = 16;
    private static final String title = String.format(
            "%-4s\t%-8s\t%-4s\t%-4s\t%-4s\t%-4s\t%-4s\t%-4s\t%-4s\t%-4s\t%-4s\t%-4s\t%-4s\t%-4s\t%-4s\t%-4s\t",
            "姓名", "学号", "性别", "年龄", "身高", "体重", "语文", "数学",
            "英语", "物理", "化学", "生物", "政治", "历史", "地理", "技术");
    private String name;
    private String number;
    private String sex;
    private int age;
    private double height;
    private double weight;
    private Score score = new Score();

    Student(){
        name = "";
        number = "";
        sex = "";
        age = 0;
        height = 0;
        weight = 0;
    }

    Student(String name, String number, String sex, int age, double height, double weight, Score score){
        this.name = name;
        this.number = number;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.score = score;
    }

    Student(String str){
        int[] pos = new int[items];
        pos[0] = 0;
        int i = 1;
        while (i < items){
            pos[i] = str.indexOf(' ', pos[i - 1]) + 1;
            i++;
        }
        this.name = str.substring(pos[0], pos[1] - 1);
        this.number = str.substring(pos[1], pos[2] - 1);
        this.sex = str.substring(pos[2], pos[3] - 1);
        this.age = Integer.valueOf(str.substring(pos[3], pos[4] - 1));
        this.height = Double.parseDouble(str.substring(pos[4], pos[5] - 1));
        this.weight = Double.parseDouble(str.substring(pos[5], pos[6] - 1));
        this.score = new Score(
                Double.parseDouble(str.substring(pos[6], pos[7] - 1)),
                Double.parseDouble(str.substring(pos[7], pos[8] - 1)),
                Double.parseDouble(str.substring(pos[8], pos[9] - 1)),
                Double.parseDouble(str.substring(pos[9], pos[10] - 1)),
                Double.parseDouble(str.substring(pos[10], pos[11] - 1)),
                Double.parseDouble(str.substring(pos[11], pos[12] - 1)),
                Double.parseDouble(str.substring(pos[12], pos[13] - 1)),
                Double.parseDouble(str.substring(pos[13], pos[14] - 1)),
                Double.parseDouble(str.substring(pos[14], pos[15] - 1)),
                Double.parseDouble(str.substring(pos[15], str.length()))
        );
    }

    public static void fileRead(ArrayList<Student> stu){
        try {
            FileInputStream is = new FileInputStream("src\\data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = null;
            while((str = br.readLine()) != null)
            {
                stu.add(new Student(str));
            }

            //close
            is.close();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void arrayPrint(ArrayList<Student> stu){
        System.out.println(title);
        if (!stu.isEmpty()){
            for (Student e : stu)
                e.outPut();
        }
    }
    public void outPut(){
        System.out.println(this.toString() + score.toString());
    }

    public DefaultTableModel getTable(){
        return null;
    }

    @Override
    public String toString() {
        return String.format("%-4s\t%-8s\t%-4s\t%-4d\t%-4.2f\t%-4.1f\t", name, number, sex, age, height, weight);
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}