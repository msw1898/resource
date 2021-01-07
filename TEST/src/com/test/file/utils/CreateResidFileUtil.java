package com.test.file.utils;

import com.test.file.utils.vo.BodyVO;
import com.test.file.utils.vo.DataVO;
import com.test.file.utils.vo.EmumVO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: ncc
 * @description:
 * @author: zxb
 * @create: 2020-12-25 14:08
 **/
public class CreateResidFileUtil {
    /**
     * @param dataVO
     * @throws IOException
     */
    public static void makeResidFile(DataVO dataVO) throws IOException {
        List<String> keyList = setAppendRedisMap(dataVO);
        writeRedisFile(dataVO, keyList);
        System.out.println("生成多语文件成功！");
    }

    /**
     * @param dataVO
     */
    public static List<String> setAppendRedisMap(DataVO dataVO) {
        Map<String, String> redisMap = dataVO.getResidFileMap();
        Map<String, String> appendResidMap = dataVO.getAppendResidFileMap();
        List<String> keyList = new ArrayList<>();
        if (appendResidMap == null) {
            appendResidMap = new HashMap<String, String>();
        }
        if (!redisMap.containsValue(dataVO.getParentVO().getTableDisplayName())) {
            if (!redisMap.containsKey(dataVO.getParentVO().getTableDisplayName())) {
                appendResidMap.put(dataVO.getParentVO().getTableDisplayName(), dataVO.getResidRule());
                keyList.add(dataVO.getParentVO().getTableDisplayName());
            }
        }
        String redisFlag = dataVO.getResidRule().substring(0, dataVO.getResidRule().length() - 3);
        String num = dataVO.getResidRule().substring(dataVO.getResidRule().length() - 3, dataVO.getResidRule().length());
        int seq = Integer.parseInt(num);
        for (BodyVO vo : dataVO.getBodyVOs()) {
            if (!redisMap.containsKey(vo.getFieldName())) {
                seq++;
                if (seq < 10) {
                    num = "00" + seq;
                } else if (seq < 100) {
                    num = "0" + seq;
                } else {
                    num = seq + "";
                }
                appendResidMap.put(vo.getFieldName(), redisFlag + num);
                keyList.add(vo.getFieldName());
            }
        }
        if (dataVO.getEmumMap() != null) {
            for (String str : dataVO.getEmumMap().keySet()) {
                for (EmumVO emum : dataVO.getEmumMap().get(str).getEmumList()) {
                    if (!redisMap.containsKey(emum.getName())) {
                        seq++;
                        if (seq < 10) {
                            num = "00" + seq;
                        } else if (seq < 100) {
                            num = "0" + seq;
                        } else {
                            num = seq + "";
                        }
                        appendResidMap.put(emum.getName(), redisFlag + num);
                        keyList.add(emum.getName());
                    }
                }
            }
        }
        dataVO.setAppendResidFileMap(appendResidMap);
        return keyList;
    }

    /**
     * @param dataVO
     * @throws IOException
     */
    public static void writeRedisFile(DataVO dataVO, List<String> keyList) throws IOException {
        File file = new File(dataVO.getResidAllPath());
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter fileWritter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataVO.getResidAllPath(), true), ConsUtil.UTF_16));
        for (String str : keyList) {
            String content = dataVO.getAppendResidFileMap().get(str) + "=" + str + "\r\n";
            fileWritter.write(content);
        }
        fileWritter.close();
    }
}
