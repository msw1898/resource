package com.test.file.utils;

import com.test.file.utils.vo.BodyVO;
import com.test.file.utils.vo.DataVO;
import nccloud.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class CreateClassUtil {
    /**
     *
     * @param dataVO
     */
    public static void createClass(DataVO dataVO) {
        if (dataVO.getBodyVOs() == null) {
            return;
        }
        StringBuffer content = new StringBuffer();
        content.append("package "+dataVO.getParentVO().getPackagePath()+";\r\n");
        content.append("public class "+dataVO.getParentVO().getClassName()+" extends PsnTaxDeclareVO {\r\n");
        content.append("private static final long serialVersionUID = 1L;\r\n");
        content.append("private static final String _TABLE_NAME = \"" + dataVO.getParentVO().getTableName() + "\";\r\n");
        for (BodyVO fileInfo : dataVO.getBodyVOs()) {
            if("N".equals(fileInfo.getIsCreateField())){
                continue;
            }
            content.append(" /** " + fileInfo.getFieldName() + " */\r\n");
            content.append("private " + fileInfo.getFieldType() + " " + fileInfo.getFieldCode() + ";\r\n");
        }
        for (BodyVO fileInfo : dataVO.getBodyVOs()) {
            if("N".equals(fileInfo.getIsCreateField())){
                continue;
            }
            content.append(" /** " + fileInfo.getFieldName() + " */\r\n");
            content.append(" public static final String " + fileInfo.getFieldCode().toUpperCase() + "=\"" + fileInfo.getFieldCode() + "\";\r\n");
        }
        for (BodyVO fileInfo : dataVO.getBodyVOs()) {
            if("N".equals(fileInfo.getIsCreateField())||StringUtils.isBlank(fileInfo.getFieldName())){
                continue;
            }
            createFieldsetSetMethod(content, fileInfo);
            createFieldsetGetMethod(content, fileInfo);
        }
        setDefaultTableName(content);
        setTableName(content);
        setPKFieldName(content, dataVO.getParentVO().getTablePk());
        content.append("} \r\n");
        String newFolderPath = MakeFileUtil.createFileFolder(dataVO.getFilePath(), dataVO.getFileName());
        writeTxt(content.toString(), newFolderPath+"//"+dataVO.getFileName()+".java");
        System.out.println("生成"+dataVO.getFileName()+".java"+"成功！");
    }

    /**
     * 将生成的字段属性写入txt
     *
     * @param content
     * @param filePath
     * @return
     */
    public static boolean writeTxt(String content, String filePath) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        InputStreamReader reader = null;
        boolean flag = true;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferedReader = new BufferedReader(new StringReader(content));
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            char buffer[] = new char[1024];
            int len;
            while ((len = bufferedReader.read(buffer)) != -1) {
                bufferedWriter.write(buffer, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                }
            }
        }
        return flag;
    }

    /**
     * @param content
     * @param fileInfo
     * @param method
     */
    public static void createFieldsetSetMethod(StringBuffer content, BodyVO fileInfo) {
        String method = getMethod(fileInfo);
        content.append("public void set" + method + "(" + fileInfo.getFieldType()+ " " + fileInfo.getFieldCode() + "){\r\n");
        content.append("\t this." + fileInfo.getFieldCode() + "=" + fileInfo.getFieldCode() + ";\r\n");
        content.append("}\r\n");
    }

    /**
     * @param content
     * @param fileInfo
     * @param method
     */
    public static void createFieldsetGetMethod(StringBuffer content, BodyVO fileInfo) {
        String method = getMethod(fileInfo);
        content.append("public " + fileInfo.getFieldType() + " get" + method + "(){\r\n");
        content.append("\t return " + fileInfo.getFieldCode() + ";\r\n");
        content.append("}\r\n");
    }

    /**
     * @param fileInfo
     * @return
     */
    public static String getMethod(BodyVO fileInfo) {
        String method = fileInfo.getFieldCode().substring(0, 1).toUpperCase() + fileInfo.getFieldCode().substring(1, fileInfo.getFieldCode().length());
        return method;
    }

    /**
     * @param content
     */
    public static void setDefaultTableName(StringBuffer content) {
        content.append("public String getDefaultTableName(){\r\n");
        content.append("\t return _TABLE_NAME;\r\n");
        content.append("}\r\n");
    }

    /**
     * @param content
     */
    public static void setTableName(StringBuffer content) {
        content.append("@Override \r\n");
        content.append("public String getTableName(){\r\n");
        content.append("\t return _TABLE_NAME;\r\n");
        content.append("}\r\n");
    }

    /**
     * @param content
     * @param pk
     */
    public static void setPKFieldName(StringBuffer content, String pk) {
        content.append("@Override \r\n");
        content.append("public String getPKFieldName(){\r\n");
        content.append("\t return \"" + pk + "\";\r\n");
        content.append("}\r\n");
    }
}