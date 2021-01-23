package com.test;

import com.test.file.utils.ConsUtil;
import com.test.file.utils.MakeFileUtil;

import java.io.IOException;

/**
 * @program: ncc
 * @description:
 * @create: 2021-01-06 09:47
 **/
public class CreateFileToNCMain {
    static String FILEPATH = "C:\\Users\\Administrator\\Desktop\\新税通补丁\\生成类文件\\";
    static String RESIDALLPATH = FILEPATH + "\\601313psntax.properties";

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ConsUtil.setFilepath(FILEPATH);
        ConsUtil.setResidallpath(RESIDALLPATH);
        ConsUtil.setLineBreak("\r\n");
        System.out.println("================================开始！");
        CreateFile();
        System.out.println("=================================结束！");
       /* System.out.println("================================开始！");
        String filePath="E:\\yonyouResource\\Version\\develop1.0\\新建文件夹\\HR_HRWA";
        String newFilePath="E:\\yonyouResource\\Version\\develop1.0\\新建文件夹\\test";
        DB2File.createDB2File(filePath, newFilePath);
        System.out.println("=================================结束！");*/
    }

    /**
     *
     * @throws IOException
     */
    public static void CreateFile() throws IOException {
        //GetDataFromExcelFile.readExcelToJson("C:\\Users\\Administrator\\Desktop\\新税通补丁\\生成类文件\\1.xls");
        //CreatePsnTaxSettingVO();
        //CreatePsnTaxClassVO();
        //CreatePsnReportingVO();
        //CreatePsnTaxDeclarationVO();
        //CreateSpecialItemVO();
        //CreateSpecialItemVO();
        //CreateCompIncomeDecVO();
        //CreateReduTaxVO();
        //CreateReduDetailVO();
        CreateTaxProcessVO();
        //CreateReduItemVO();
        //CreateTaxItemSettingVO();
        //CreateTaxPaymentVO();
        //CreateTripeAgreementVO();
    }

    public static void CreateTripeAgreementVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("TripeAgreementVO", "0601313tripeagreement000"), 326);
    }

    public static void CreateTaxPaymentVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("TaxPaymentVO", "0601313taxpayment000"), 293);
    }

    public static void CreateTaxItemSettingVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("TaxItemSettingVO", "0601313taxsettingitem000"), 262);
    }

    public static void CreateReduItemVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("ReduItemVO", "0601313reduceitem000"), 244);
    }

    public static void CreateTaxProcessVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("TaxProcessVO", "0601313declprocess000"), 227);
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

    public static void CreatePsnTaxSettingVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("PsnTaxSettingVO", "1000000test000"), 0);
    }

    public static void CreatePsnTaxClassVO() throws IOException {
        MakeFileUtil.makeFile(MakeFileUtil.ensureParam("PsnTaxClassVO", "0000000test000"), 0);
    }
}
