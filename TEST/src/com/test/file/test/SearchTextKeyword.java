package com.test.file.test;

import com.test.file.utils.ConsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SearchTextKeyword {
    /**
     * 计算文件数量
     */
    private static int count = 0;
    private static String keyword=ConsUtil.KEYWORD;
    private static String charset =ConsUtil.CHARSET;
    //关键字
    public static String keyWords;

    static {
        try {
            keyWords = new String(keyword.getBytes(charset),charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //搜索后查询到的文件路径汇总文件地址
    public static String searchedFilePath = ConsUtil.FILEFOLDERPATH+ ConsUtil.SEPARATOR+"test"+ConsUtil.FILETYPE;
    public static String path = ConsUtil.FILEFOLDERPATH + ConsUtil.SEPARATOR+ConsUtil.FLAG;

    public static File searchedFile = new File(searchedFilePath);

    public static FileOutputStream fos = null;

    /**
     * 递归搜索文件
     * @param files
     */
    public static void getFiles(File[] files) {
        FileInputStream fis = null;
        try {
            for (File file : files) {
                count++;
                if (file.isDirectory()) {
                    getFiles(file.listFiles());
                } else {
                    StringBuilder sb = new StringBuilder();
                    byte[] bytes = new byte[1024];
                    fis = new FileInputStream(file);
                    int len = 0;
                    while ((len = fis.read(bytes)) != -1) {
                        sb.append(new String(bytes, 0, len));
                    }
                    fis.close();
                    if (sb.indexOf(keyWords) >= 0) {
                        System.out.println(file.getAbsolutePath());
                        fos.write((file.getAbsolutePath() + System.lineSeparator()).getBytes());
                        fos.flush();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(keyWords);
        try {
            fos = new FileOutputStream(searchedFile);
            if (!searchedFile.exists()) {
                searchedFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(path);
        File[] files = file.listFiles();
        getFiles(files);
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }
}
