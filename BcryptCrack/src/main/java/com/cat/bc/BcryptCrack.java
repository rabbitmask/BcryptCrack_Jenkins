package com.cat.bc;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BcryptCrack {
    public static void main(String[] args) {

        Scanner h = new Scanner(System.in);
        System.out.print("请输入您的密文：");
        String hashed = h.nextLine();

        Scanner p = new Scanner(System.in);
        System.out.print("请提供您的字典：");
        String Pass_Path = p.nextLine();

        List<String> pass = readTxt(Pass_Path);

        getPass(hashed,pass);

    }


    public static void getPass(String hashed, List<String> pass) {
        assert pass != null;
        for (Object o : pass) {
            if (BCrypt.checkpw((String) o, hashed)) {
                System.out.println("破解成功:" + o);
                break;
            }
        }
    }

    //读取TXT返回列表形式
    public static List<String> readTxt(String filePath) {
        List<String> fileList = new ArrayList<>();
        try {
            fileList = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

}


