package ClassManager.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Class{
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<String> title = (ArrayList<String>) Arrays.asList("姓名", "学号", "性别", "年龄", "身高", "体重");
    private int size;

    public Class(){
        size = 0;
    }

    public Class(Student[] stu){
        int i = 0;
        while (i < stu.length){
            if (stu[i] != null) students.add(stu[i]);
            i++;
        }
        size = students.size();
    }

    public Class(ArrayList<Student> students){
        this.students = new ArrayList<>(students);
        size = students.size();
    }

    public boolean isEmpty(){
        return size == 0 ? true : false;
    }

    public Student search(String name, String number) {
        if (!students.isEmpty()){
            for (Student e : students){
                if (e.getName().compareTo(name) == 0 &&
                        e.getNumber().compareTo(number) == 0){
                    return e;
                }
            }
            return null;
        }
        return null;
    }

    public void getAllStudents(){
        if (isEmpty())
            System.out.println("该班级为空");
        else{

            System.out.println("姓名   学号      性别  年龄   身高  体重");
            for (Student e : students)
                e.outPut();
        }
    }

    public void getFiveStudents(){
        if (size < 5)
            System.out.println("该班级少于5人");
        else {
            System.out.println("姓名   学号      性别  年龄   身高  体重");
            int i = 5;
            Random r = new Random();
            while (i-- > 0) {
                int n = r.nextInt(size);
                students.get(n).outPut();
            }
        }
    }
}