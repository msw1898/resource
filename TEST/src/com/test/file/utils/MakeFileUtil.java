package com.test.file.utils;

import com.test.file.utils.vo.DataVO;
import com.test.file.utils.vo.MainVO;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MakeFileUtil {
    public static void makeFile(DataVO dataVO,int beginNum) throws IOException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //系统当前时间
        String strsystime = sf.format(System.currentTimeMillis());
        dataVO.setCreateTime(strsystime);
        MainVO mainVO = new MainVO();
        dataVO.setParentVO(mainVO);
        Map<String,String> residMap=new HashMap<String,String>();
        dataVO.setResidFileMap(residMap);
        dataVO = GetDataFromExcelFile.readExcel(dataVO);
        dataVO = GetDataFromExcelFile.readExcelToEmum(dataVO);
        dataVO = GetDataFromTxtFile.readTxtFile(dataVO);
        CreateResidFileUtil.makeResidFile(dataVO);
        CreateClassUtil.createClass(dataVO);
        CreateBmfUtil.makeBfmFile(dataVO);
        CreatePdmUtil.makePdmFile(dataVO, beginNum);
    }

    public static DataVO ensureParam(String fileName, String residRule) throws IOException {
        DataVO dataVO = new DataVO();
        dataVO.setFilePath(ConsUtil.getFilepath());
        dataVO.setResidAllPath(ConsUtil.getResidallpath());
        dataVO.setResidRule(residRule);
        dataVO.setFileName(fileName);
        return dataVO;
    }

    public static String createFileFolder(String path, String fileFolerName) {
        String fileFolderPath = path + "//" + fileFolerName;
        File file = new File(fileFolderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return fileFolderPath;
    }
}
