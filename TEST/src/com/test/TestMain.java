package com.test;

import java.io.UnsupportedEncodingException;

public class TestMain {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String name="中原银行股份有限公司商丘开发区支行";
        int lenght=getStrLength(name);
        System.out.println(name+":   "+lenght);
        boolean flag=validateStrByLength(lenght, 50);
        System.out.println(name+":   "+flag);
        System.out.println("UTF-8编码长度:"+name.getBytes("UTF-8").length);
        System.out.println("GBK编码长度:"+name.getBytes("GBK").length);
        System.out.println("GB2312编码长度:"+name.getBytes("GB2312").length);
    }
    private static int getStrLength(String strParameter){
        int temp_int = 0;
        byte[] b = strParameter.getBytes();

        for (int i = 0; i < b.length; i++) {
            if (b[i] >= 0) {
                temp_int = temp_int + 1;
            } else {
                temp_int = temp_int + 2;
                i++;
            }
        }
        return temp_int;
    }

    private static boolean validateStrByLength(int temp_int, int limitLength) {
        if (temp_int < limitLength) {
            return false;
        } else {
            return true;
        }
    }
}
