package com.test.file.utils;

import java.io.File;

public class ConsUtil {
    public static String FILEFOLDERPATH = "E:/test/";
    public static String FILENAME = "ncchr";
    public static String FILETYPE = ".log";
    public static String FLAG = "splitFile";
    public static String SEPARATOR = File.separator;
    public static String KEYWORD = "“Ï≥£¥ÌŒÛ";
    public static String CHARSET = "UTF-8";
    public static String UTF_16 = "utf-16";
    /***/
    private static String filepath;
    /***/
    private static String residallpath;

    public static String getFilepath() {
        return filepath;
    }

    public static void setFilepath(String filepath) {
        ConsUtil.filepath = filepath;
    }

    public static String getResidallpath() {
        return residallpath;
    }

    public static void setResidallpath(String residallpath) {
        ConsUtil.residallpath = residallpath;
    }
}
