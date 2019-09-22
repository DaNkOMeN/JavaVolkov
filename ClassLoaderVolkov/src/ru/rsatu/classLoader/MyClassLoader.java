package ru.rsatu.classLoader;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MyClassLoader extends ClassLoader {
    private String pathToBin;


    public MyClassLoader(String pathToBin, ClassLoader parent){
        super(parent);
        this.pathToBin = pathToBin;
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException{
        try {
            byte myClass[] = getClassFromFileSystem(pathToBin +"/"+ className + ".class");
            return defineClass(className, myClass, 0, myClass.length);
        } catch (FileNotFoundException e) {
            return super.findClass(className);
        } catch (IOException e){
            return super.findClass(className);

        }
    }


    public byte[] getClassFromFileSystem(String pathToFile) throws IOException {
        InputStream is = new FileInputStream(new File(pathToFile));

        long lengthOfFile = new File(pathToFile).length();

        if (lengthOfFile > Integer.MAX_VALUE){
            throw new IOException("LengthOfFile > MAX Integer VALUE");
        }

        byte[] bytes = new byte[(int)lengthOfFile];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length){
            throw new IOException("Could not completely read file "+pathToFile);
        }

        is.close();
        return  bytes;
    }

}
