package com.test.file.utils;

import com.test.file.utils.vo.BodyVO;
import com.test.file.utils.vo.DataVO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.UUID;

/**
 * @program: ncc
 * @description:
 * @create: 2021-01-05 13:46
 **/
public class CreatePdmUtil {
    public static int primaryKeyNum;
    public static int beginNum;
    public static void makePdmFile(DataVO dataVO,int num) {
        beginNum=num;
        UUID uuid = UUID.randomUUID();
        StringBuffer pdm=new StringBuffer();
        pdm.append("<o:Table Id=\"o"+beginNum+"\">"+ConsUtil.getLineBreak());
        setTableHeadInfo( pdm, dataVO);
        setTableBodyInfo( pdm, dataVO);
        setRef(pdm,dataVO);
        pdm.append("</o:Table>"+ConsUtil.getLineBreak());
        setTableTail(pdm);
        String newFolderPath = MakeFileUtil.createFileFolder(dataVO.getFilePath(), dataVO.getFileName());
        writeTxt(pdm.toString(), newFolderPath+"//"+dataVO.getFileName()+".pdm");
        System.out.println("生成"+dataVO.getFileName()+".pdm"+"成功！");
    }

    /**
     *
     * @param pdm
     * @param dataVO
     */
    private static void setTableHeadInfo(StringBuffer pdm,DataVO dataVO){
        UUID uuid = UUID.randomUUID();
        String tableId=uuid.toString().toUpperCase();
        String createTime=String.valueOf(System.currentTimeMillis());
        //表
        pdm.append("<a:ObjectID>"+tableId+"</a:ObjectID>"+ConsUtil.getLineBreak());
        pdm.append("<a:Name>"+dataVO.getParentVO().getTableDisplayName()+"</a:Name>"+ConsUtil.getLineBreak());
        pdm.append("<a:Code>"+dataVO.getParentVO().getTableName()+"</a:Code>"+ConsUtil.getLineBreak());
        pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Creator>Administrator</a:Creator>"+ConsUtil.getLineBreak());
        pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Modifier>msw</a:Modifier>"+ConsUtil.getLineBreak());
    }

    /**
     *
     * @param pdm
     * @param dataVO
     */
    private static void setTableBodyInfo(StringBuffer pdm,DataVO dataVO){
        String createTime=String.valueOf(System.currentTimeMillis());
        pdm.append("<c:Columns>");
        for(BodyVO vo:dataVO.getBodyVOs()){
            beginNum++;
            UUID uuid = UUID.randomUUID();
            String columnId=uuid.toString().toUpperCase();
            if(dataVO.getParentVO().getTablePk().equals(vo.getFieldCode())){
                primaryKeyNum=beginNum;
            }
            pdm.append("<o:Column Id=\"o"+beginNum+"\">"+ConsUtil.getLineBreak());
            pdm.append("<a:ObjectID>"+columnId+"</a:ObjectID>"+ConsUtil.getLineBreak());
            pdm.append("<a:Name>"+vo.getFieldName()+"</a:Name>"+ConsUtil.getLineBreak());
            pdm.append("<a:Code>"+vo.getFieldCode()+"</a:Code>"+ConsUtil.getLineBreak());
            pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>"+ConsUtil.getLineBreak());
            pdm.append("<a:Creator>Administrator</a:Creator>"+ConsUtil.getLineBreak());
            pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>"+ConsUtil.getLineBreak());
            pdm.append("<a:Modifier>Administrator</a:Modifier>"+ConsUtil.getLineBreak());
            if(dataVO.getParentVO().getTablePk().equals(vo.getFieldCode())||"modifiedtime".equals(vo.getFieldCode())||"creationtime".equals(vo.getFieldCode())){
                pdm.append("<a:DataType>char("+vo.getLength()+")</a:DataType>"+ConsUtil.getLineBreak());
            }if("Integer".equals(vo.getFieldType()) || "int".equals(vo.getFieldType())){
                pdm.append("<a:DataType>decimal(31,8)</a:DataType>"+ConsUtil.getLineBreak());
            }if("UFBoolean".equals(vo.getFieldType())){
                pdm.append("<a:DataType>char(1)</a:DataType>"+ConsUtil.getLineBreak());
            }else{
                pdm.append("<a:DataType>varchar("+vo.getLength()+")</a:DataType>"+ConsUtil.getLineBreak());
            }
            pdm.append("<a:Length>"+vo.getLength()+"</a:Length>"+ConsUtil.getLineBreak());
            if("N".equals(vo.getIsNull())) {
                pdm.append("<a:Mandatory>1</a:Mandatory>\n");//能否为空
            }
            pdm.append("</o:Column>"+ConsUtil.getLineBreak());
        }
        pdm.append("</c:Columns>"+ConsUtil.getLineBreak());
    }

    /**
     *
     * @param pdm
     * @param dataVO
     */
    private static void setRef(StringBuffer pdm,DataVO dataVO){
        String createTime=String.valueOf(System.currentTimeMillis());
        int refNum=++beginNum;
        UUID uuid = UUID.randomUUID();
        String keyId=uuid.toString().toUpperCase();
        pdm.append("<c:Keys>");
        pdm.append("<o:Key Id=\"o"+refNum+"\">"+ConsUtil.getLineBreak());
        pdm.append("<a:ObjectID>"+keyId+"</a:ObjectID>"+ConsUtil.getLineBreak());
        pdm.append("<a:Name>"+dataVO.getParentVO().getTablePk()+"</a:Name>"+ConsUtil.getLineBreak());
        pdm.append("<a:Code>"+dataVO.getParentVO().getTablePk()+"</a:Code>"+ConsUtil.getLineBreak());
        pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Creator>Administrator</a:Creator>"+ConsUtil.getLineBreak());
        pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Modifier>Administrator</a:Modifier>"+ConsUtil.getLineBreak());
        pdm.append("<a:ConstraintName>"+dataVO.getParentVO().getTablePk()+"</a:ConstraintName>"+ConsUtil.getLineBreak());
        pdm.append("<c:Key.Columns>"+ConsUtil.getLineBreak());
        pdm.append("<o:Column Ref=\"o"+primaryKeyNum+"\"/>"+ConsUtil.getLineBreak());
        pdm.append("</c:Key.Columns>"+ConsUtil.getLineBreak());
        pdm.append("</o:Key>"+ConsUtil.getLineBreak());

        //主键
        uuid = UUID.randomUUID();
        keyId=uuid.toString().toUpperCase();
        pdm.append("<o:Key Id=\"o"+(++beginNum)+"\">"+ConsUtil.getLineBreak());
        pdm.append("<a:ObjectID>"+keyId+"</a:ObjectID>"+ConsUtil.getLineBreak());
        pdm.append("<a:Name>Key_2</a:Name>"+ConsUtil.getLineBreak());
        pdm.append("<a:Code>Key_2</a:Code>"+ConsUtil.getLineBreak());
        pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Creator>Administrator</a:Creator>"+ConsUtil.getLineBreak());
        pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Modifier>Administrator</a:Modifier>"+ConsUtil.getLineBreak());
        pdm.append("<c:Key.Columns>"+ConsUtil.getLineBreak());
        pdm.append("<o:Column Ref=\"o"+primaryKeyNum+"\"/>"+ConsUtil.getLineBreak());
        pdm.append("</c:Key.Columns>"+ConsUtil.getLineBreak());
        pdm.append("</o:Key>"+ConsUtil.getLineBreak());

        pdm.append("</c:Keys>"+ConsUtil.getLineBreak());
        pdm.append("<c:Indexes>"+ConsUtil.getLineBreak());
        pdm.append("<o:Index Id=\"o"+(++beginNum)+"\">"+ConsUtil.getLineBreak());

        uuid = UUID.randomUUID();
        keyId=uuid.toString().toUpperCase();
        pdm.append("<a:ObjectID>"+keyId+"</a:ObjectID>"+ConsUtil.getLineBreak());
        pdm.append("<a:Name>i_"+dataVO.getParentVO().getTableName()+"</a:Name>"+ConsUtil.getLineBreak());
        pdm.append("<a:Code>i_"+dataVO.getParentVO().getTableName()+"</a:Code>"+ConsUtil.getLineBreak());
        pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Creator>msw</a:Creator>"+ConsUtil.getLineBreak());
        pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Modifier>msw</a:Modifier>"+ConsUtil.getLineBreak());
        pdm.append("<c:IndexColumns>"+ConsUtil.getLineBreak());
        pdm.append("<o:IndexColumn Id=\"o"+(++beginNum)+"\">"+ConsUtil.getLineBreak());

        uuid = UUID.randomUUID();
        keyId=uuid.toString().toUpperCase();
        pdm.append("<a:ObjectID>"+keyId+"</a:ObjectID>"+ConsUtil.getLineBreak());
        pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Creator>Administrator</a:Creator>"+ConsUtil.getLineBreak());
        pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Modifier>Administrator</a:Modifier>"+ConsUtil.getLineBreak());
        pdm.append("<c:Column>"+ConsUtil.getLineBreak());
        pdm.append("<o:Column Ref=\"o"+primaryKeyNum+"\"/>"+ConsUtil.getLineBreak());
        pdm.append("</c:Column>"+ConsUtil.getLineBreak());
        pdm.append("</o:IndexColumn>"+ConsUtil.getLineBreak());
        pdm.append("</c:IndexColumns>"+ConsUtil.getLineBreak());
        pdm.append("</o:Index>"+ConsUtil.getLineBreak());
        pdm.append("</c:Indexes>"+ConsUtil.getLineBreak());
        pdm.append("<c:PrimaryKey>"+ConsUtil.getLineBreak());
        pdm.append("<o:Key Ref=\"o"+refNum+"\"/>"+ConsUtil.getLineBreak());
        pdm.append("</c:PrimaryKey>"+ConsUtil.getLineBreak());
        pdm.append("<c:ClusterObject>"+ConsUtil.getLineBreak());
        pdm.append("<o:Key Ref=\"o"+refNum+"\"/>"+ConsUtil.getLineBreak());
        pdm.append("</c:ClusterObject>"+ConsUtil.getLineBreak());

    }

    /**
     *
     * @param pdm
     */
    private static void setTableTail(StringBuffer pdm){
        pdm.append(" </c:Tables>"+ConsUtil.getLineBreak());
        pdm.append("<c:DefaultGroups>"+ConsUtil.getLineBreak());
        pdm.append("<o:Group Id=\"o"+(++beginNum)+"\">"+ConsUtil.getLineBreak());
        pdm.append("<a:ObjectID>D875EA43-02AB-4865-B78D-C55DBDEAAAAB</a:ObjectID>"+ConsUtil.getLineBreak());
        pdm.append("<a:Name>PUBLIC</a:Name>"+ConsUtil.getLineBreak());
        pdm.append("<a:Code>PUBLIC</a:Code>"+ConsUtil.getLineBreak());
        pdm.append("<a:CreationDate>1198473279</a:CreationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Creator>xuanlt</a:Creator>"+ConsUtil.getLineBreak());
        pdm.append("<a:ModificationDate>1198473279</a:ModificationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Modifier>xuanlt</a:Modifier>"+ConsUtil.getLineBreak());
        pdm.append("</o:Group>"+ConsUtil.getLineBreak());
        pdm.append("</c:DefaultGroups>"+ConsUtil.getLineBreak());
        pdm.append("<c:Defaults>"+ConsUtil.getLineBreak());
        pdm.append("<o:PhysicalDefault Id=\"o"+(++beginNum)+"\">"+ConsUtil.getLineBreak());
        pdm.append("<a:ObjectID>AEC2C06F-C000-4715-95FA-6F0E8AA07511</a:ObjectID>"+ConsUtil.getLineBreak());
        pdm.append("<a:Name>D_UFBoolean</a:Name>"+ConsUtil.getLineBreak());
        pdm.append("<a:Code>D_UFBoolean</a:Code>"+ConsUtil.getLineBreak());
        pdm.append("<a:CreationDate>1260343128</a:CreationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Creator>zhoucx</a:Creator>"+ConsUtil.getLineBreak());
        pdm.append("<a:ModificationDate>1264556878</a:ModificationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Modifier>zhangg</a:Modifier>"+ConsUtil.getLineBreak());
        pdm.append("<a:PhysicalDefault.Value>&#39;N&#39;</a:PhysicalDefault.Value>"+ConsUtil.getLineBreak());
        pdm.append("</o:PhysicalDefault>"+ConsUtil.getLineBreak());
        pdm.append("</c:Defaults>"+ConsUtil.getLineBreak());
        pdm.append("<c:TargetModels>"+ConsUtil.getLineBreak());
        pdm.append("<o:TargetModel Id=\"o"+(++beginNum)+"\">"+ConsUtil.getLineBreak());
        pdm.append("<a:ObjectID>0B5DF864-6D76-46D0-AD3C-341770E8489C</a:ObjectID>"+ConsUtil.getLineBreak());
        pdm.append("<a:Name>薪资管理</a:Name>"+ConsUtil.getLineBreak());
        pdm.append("<a:Code>CDM_WA</a:Code>"+ConsUtil.getLineBreak());
        pdm.append("<a:CreationDate>0</a:CreationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Creator/>"+ConsUtil.getLineBreak());
        pdm.append("<a:ModificationDate>0</a:ModificationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Modifier/>"+ConsUtil.getLineBreak());
        pdm.append("<a:TargetModelURL>file:///E|/Product group/李惠萍/NC_SD_WA_CDM.cdm</a:TargetModelURL>"+ConsUtil.getLineBreak());
        pdm.append("<a:TargetModelID>886C3AD4-2798-11D5-A63D-52544C197D3C</a:TargetModelID>"+ConsUtil.getLineBreak());
        pdm.append("<a:TargetModelClassID>1E597170-9350-11D1-AB3C-0020AF71E433</a:TargetModelClassID>"+ConsUtil.getLineBreak());
        pdm.append("<c:SessionShortcuts>"+ConsUtil.getLineBreak());
        pdm.append("<o:Shortcut Ref=\"o3\"/>"+ConsUtil.getLineBreak());
        pdm.append("</c:SessionShortcuts>"+ConsUtil.getLineBreak());
        pdm.append("</o:TargetModel>"+ConsUtil.getLineBreak());
        pdm.append("<o:TargetModel Id=\"o"+(++beginNum)+"\">"+ConsUtil.getLineBreak());
        pdm.append("<a:ObjectID>BFF62C20-B83C-42E4-A97E-143DA43704C8</a:ObjectID>"+ConsUtil.getLineBreak());
        pdm.append("<a:Name>Microsoft SQL Server 2005</a:Name>"+ConsUtil.getLineBreak());
        pdm.append("<a:Code>MSSQLSRV2005</a:Code>"+ConsUtil.getLineBreak());
        pdm.append("<a:CreationDate>1260343128</a:CreationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Creator>zhoucx</a:Creator>"+ConsUtil.getLineBreak());
        pdm.append("<a:ModificationDate>1260343128</a:ModificationDate>"+ConsUtil.getLineBreak());
        pdm.append("<a:Modifier>zhoucx</a:Modifier>"+ConsUtil.getLineBreak());
        pdm.append("<a:TargetModelURL>file:///%_DBMS%/sqlsv2k5.xdb</a:TargetModelURL>"+ConsUtil.getLineBreak());
        pdm.append("<a:TargetModelID>030105E8-1DFA-4990-B2C8-DEB36D9D8D09</a:TargetModelID>"+ConsUtil.getLineBreak());
        pdm.append("<a:TargetModelClassID>4BA9F647-DAB1-11D1-9944-006097355D9B</a:TargetModelClassID>"+ConsUtil.getLineBreak());
        pdm.append("<c:SessionShortcuts>"+ConsUtil.getLineBreak());
        pdm.append("<o:Shortcut Ref=\"o4\"/>"+ConsUtil.getLineBreak());
        pdm.append("</c:SessionShortcuts>"+ConsUtil.getLineBreak());
        pdm.append("</o:TargetModel>"+ConsUtil.getLineBreak());
        pdm.append("</c:TargetModels>"+ConsUtil.getLineBreak());
        pdm.append("</o:Model>"+ConsUtil.getLineBreak());
        pdm.append("</c:Children>"+ConsUtil.getLineBreak());
        pdm.append("</o:RootObject>"+ConsUtil.getLineBreak());
        pdm.append("</Model>"+ConsUtil.getLineBreak());
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
}
