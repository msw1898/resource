package com.test;

import java.io.UnsupportedEncodingException;

public class TestMain {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String name="��ԭ���йɷ����޹�˾���𿪷���֧��";
        int lenght=getStrLength(name);
        System.out.println(name+":   "+lenght);
        boolean flag=validateStrByLength(lenght, 50);
        System.out.println(name+":   "+flag);
        System.out.println("UTF-8���볤��:"+name.getBytes("UTF-8").length);
        System.out.println("GBK���볤��:"+name.getBytes("GBK").length);
        System.out.println("GB2312���볤��:"+name.getBytes("GB2312").length);
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
