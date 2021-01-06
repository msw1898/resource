package com.test;

import com.test.file.utils.MakeFileUtil;

import java.io.IOException;

/**
 * @program: ncc
 * @description:
 * @create: 2021-01-06 09:47
 **/
public class CreateFileToNCMain {
    public static void main(String[] args) throws IOException {
        System.out.println("================================开始！");
        //GetDataFromExcelFile.readExcelToJson("C:\\Users\\Administrator\\Desktop\\新税通补丁\\生成类文件\\1.xls");
        //CreatePsnReportingVO();
        //CreatePsnTaxDeclarationVO();
        //CreateSpecialItemVO();
        CreateCompIncomeDecVO();
        System.out.println("=================================结束！");
    }

    public static void CreatePsnReportingVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("PsnReportingVO", "0000000psntax000"), 0);
    }

    public static void CreatePsnTaxDeclarationVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("PsnTaxDeclarationVO", "0000000psntax000"), 0);
    }

    public static void CreateSpecialItemVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("SpecialItemVO", "0601313specialitem000"), 115);
    }

    public static void CreateCompIncomeDecVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("CompIncomeDecVO", "0601313compincomedec000"), 143);
    }

}
