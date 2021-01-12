package com.cat.bc;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetXML {

    public static String LoadContentByPath(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder buffer = new StringBuilder();
        String line = "";
        while ((line = in.readLine()) != null){
            buffer.append(line);
        }
        return buffer.toString();

    }

    public static List<String> getFieldListByRegex(String xml, String label) {
        //正则表达式
        String regex = "<" + label + ">(.*?)</" + label + ">";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(xml);
        //匹配的有多个
        List<String> fieldList = new ArrayList<>();
        while (m.find()) {
            if (StringUtils.isNotEmpty(m.group(1).trim())) {
                fieldList.add(m.group(1).trim());
            }
        }
        return fieldList;
    }

}