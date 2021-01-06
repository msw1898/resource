package com.test.file.utils;

import com.test.file.utils.vo.DataVO;
import nccloud.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @program: ncc
 * @description:
 * @author: zxb
 * @create: 2020-12-25 13:36
 **/
public class GetDataFromTxtFile {
    public static DataVO readTxtFile(DataVO dataVO) {
        try {
            File file = new File(dataVO.getResidAllPath());
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), ConsUtil.UTF_16);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    setResidMap(lineTxt,dataVO);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return dataVO;
    }

    /**
     *
     * @param lineTxt
     * @param dataVO
     */
    public static void setResidMap(String lineTxt,DataVO dataVO){
        if(StringUtils.isAllBlank(lineTxt)){
            return;
        }
        String[] resids=lineTxt.split("=");
        dataVO.getResidFileMap().put(resids[1],resids[0]);
    }
}
