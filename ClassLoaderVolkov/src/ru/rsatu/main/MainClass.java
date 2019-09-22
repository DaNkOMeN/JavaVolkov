package ru.rsatu.main;

import ru.rsatu.classLoader.MyClassLoader;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("Enter binPath");
        Scanner in = new Scanner(System.in);
        String binPath = in.next();

        MyClassLoader loader = new MyClassLoader(binPath,ClassLoader.getSystemClassLoader());

        File dir = new File(binPath);
        String[] classes = dir.list();
        for (int i =0; i < classes.length; i++){
            System.out.println(classes[i]);
        }


        for (String classs: classes){
            try {
                if (classs.contains(".class")) {
                    Class clazz = loader.findClass(classs.split(".class")[0]);
                    Field[] fields = clazz.getDeclaredFields();
                    Method[] methods = clazz.getMethods();
                    for (Field field : fields) {
                        System.out.println(field.toString());
                    }
                    for (Method method: methods){
                        System.out.println(method.toString());
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
