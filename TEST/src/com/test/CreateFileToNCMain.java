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
        //CreateSpecialItemVO();
        //CreateCompIncomeDecVO();
        //CreateReduTaxVO();
        //CreateReduDetailVO();
        //CreateDeclProcessVO();
        CreateReduItemVO();
        System.out.println("=================================结束！");
    }
    public static void CreateReduItemVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("ReduItemVO", "0601313reduceitem000"), 244);
    }
    public static void CreateDeclProcessVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("DeclProcessVO", "0601313declprocess000"), 227);
    }
    public static void CreateReduDetailVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("ReduDetailVO", "0601313reducedetail000"), 211);
    }
    public static void CreateReduTaxVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("ReduTaxVO", "0601313reducetax000"), 188);
    }

    public static void CreateCompIncomeDecVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("CompIncomeDecVO", "0601313compincomedec000"), 143);
    }

    public static void CreateSpecialItemVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("SpecialItemVO", "0601313specialitem000"), 115);
    }

    public static void CreatePsnReportingVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("PsnReportingVO", "0000000psntax000"), 0);
    }

    public static void CreatePsnTaxDeclarationVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("PsnTaxDeclarationVO", "0000000psntax000"), 0);
    }


}
