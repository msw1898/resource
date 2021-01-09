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
        pdm.append("<o:Table Id=\"o"+beginNum+"\">\r\n");
        setTableHeadInfo( pdm, dataVO);
        setTableBodyInfo( pdm, dataVO);
        setRef(pdm,dataVO);
        pdm.append("</o:Table>\r\n");
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
        pdm.append("<a:ObjectID>"+tableId+"</a:ObjectID>\r\n");
        pdm.append("<a:Name>"+dataVO.getParentVO().getTableDisplayName()+"</a:Name>\r\n");
        pdm.append("<a:Code>"+dataVO.getParentVO().getTableName()+"</a:Code>\r\n");
        pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>\r\n");
        pdm.append("<a:Creator>Administrator</a:Creator>\r\n");
        pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>\r\n");
        pdm.append("<a:Modifier>msw</a:Modifier>\r\n");
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
            pdm.append("<o:Column Id=\"o"+beginNum+"\">\r\n");
            pdm.append("<a:ObjectID>"+columnId+"</a:ObjectID>\r\n");
            pdm.append("<a:Name>"+vo.getFieldName()+"</a:Name>\r\n");
            pdm.append("<a:Code>"+vo.getFieldCode()+"</a:Code>\r\n");
            pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>\r\n");
            pdm.append("<a:Creator>Administrator</a:Creator>\r\n");
            pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>\r\n");
            pdm.append("<a:Modifier>Administrator</a:Modifier>\r\n");
            if(dataVO.getParentVO().getTablePk().equals(vo.getFieldCode())||"modifiedtime".equals(vo.getFieldCode())||"creationtime".equals(vo.getFieldCode())){
                pdm.append("<a:DataType>char("+vo.getLength()+")</a:DataType>\r\n");
            }if("Integer".equals(vo.getFieldType())){
                pdm.append("<a:DataType>smallint</a:DataType>\r\n");
            }if("UFBoolean".equals(vo.getFieldType())){
                pdm.append("<a:DataType>char(1)</a:DataType>\r\n");
            }else{
                pdm.append("<a:DataType>nvarchar("+vo.getLength()+")</a:DataType>\r\n");
            }
            pdm.append("<a:Length>"+vo.getLength()+"</a:Length>\r\n");
            if("N".equals(vo.getIsNull())) {
                pdm.append("<a:Mandatory>1</a:Mandatory>\n");//能否为空
            }
            pdm.append("</o:Column>\r\n");
        }
        pdm.append("</c:Columns>\r\n");
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
        pdm.append("<o:Key Id=\"o"+refNum+"\">\r\n");
        pdm.append("<a:ObjectID>"+keyId+"</a:ObjectID>\r\n");
        pdm.append("<a:Name>"+dataVO.getParentVO().getTablePk()+"</a:Name>\r\n");
        pdm.append("<a:Code>"+dataVO.getParentVO().getTablePk()+"</a:Code>\r\n");
        pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>\r\n");
        pdm.append("<a:Creator>msw</a:Creator>\r\n");
        pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>\r\n");
        pdm.append("<a:Modifier>msw</a:Modifier>\r\n");
        pdm.append("<a:ConstraintName>"+dataVO.getParentVO().getTablePk()+"</a:ConstraintName>\r\n");
        pdm.append("<c:Key.Columns>\r\n");
        pdm.append("<o:Column Ref=\"o"+primaryKeyNum+"\"/>\r\n");
        pdm.append("</c:Key.Columns>\r\n");
        pdm.append("</o:Key>\r\n");

        //主键
        uuid = UUID.randomUUID();
        keyId=uuid.toString().toUpperCase();
        pdm.append("<o:Key Id=\"o"+(++beginNum)+"\">\r\n");
        pdm.append("<a:ObjectID>"+keyId+"</a:ObjectID>\r\n");
        pdm.append("<a:Name>Key_2</a:Name>\r\n");
        pdm.append("<a:Code>Key_2</a:Code>\r\n");
        pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>\r\n");
        pdm.append("<a:Creator>Administrator</a:Creator>\r\n");
        pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>\r\n");
        pdm.append("<a:Modifier>Administrator</a:Modifier>\r\n");
        pdm.append("<c:Key.Columns>\r\n");
        pdm.append("<o:Column Ref=\"o"+primaryKeyNum+"\"/>\r\n");
        pdm.append("</c:Key.Columns>\r\n");
        pdm.append("</o:Key>\r\n");

        pdm.append("</c:Keys>\r\n");
        pdm.append("<c:Indexes>\r\n");
        pdm.append("<o:Index Id=\"o"+(++beginNum)+"\">\r\n");

        uuid = UUID.randomUUID();
        keyId=uuid.toString().toUpperCase();
        pdm.append("<a:ObjectID>"+keyId+"</a:ObjectID>\r\n");
        pdm.append("<a:Name>i_"+dataVO.getParentVO().getTableName()+"</a:Name>\r\n");
        pdm.append("<a:Code>i_"+dataVO.getParentVO().getTableName()+"</a:Code>\r\n");
        pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>\r\n");
        pdm.append("<a:Creator>msw</a:Creator>\r\n");
        pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>\r\n");
        pdm.append("<a:Modifier>msw</a:Modifier>\r\n");
        pdm.append("<c:IndexColumns>\r\n");
        pdm.append("<o:IndexColumn Id=\"o"+(++beginNum)+"\">\r\n");

        uuid = UUID.randomUUID();
        keyId=uuid.toString().toUpperCase();
        pdm.append("<a:ObjectID>"+keyId+"</a:ObjectID>\r\n");
        pdm.append("<a:CreationDate>"+createTime+"</a:CreationDate>\r\n");
        pdm.append("<a:Creator>Administrator</a:Creator>\r\n");
        pdm.append("<a:ModificationDate>"+createTime+"</a:ModificationDate>\r\n");
        pdm.append("<a:Modifier>Administrator</a:Modifier>\r\n");
        pdm.append("<c:Column>\r\n");
        pdm.append("<o:Column Ref=\"o"+primaryKeyNum+"\"/>\r\n");
        pdm.append("</c:Column>\r\n");
        pdm.append("</o:IndexColumn>\r\n");
        pdm.append("</c:IndexColumns>\r\n");
        pdm.append("</o:Index>\r\n");
        pdm.append("</c:Indexes>\r\n");
        pdm.append("<c:PrimaryKey>\r\n");
        pdm.append("<o:Key Ref=\"o"+refNum+"\"/>\r\n");
        pdm.append("</c:PrimaryKey>\r\n");
        pdm.append("<c:ClusterObject>\r\n");
        pdm.append("<o:Key Ref=\"o"+refNum+"\"/>\r\n");
        pdm.append("</c:ClusterObject>\r\n");

    }

    /**
     *
     * @param pdm
     */
    private static void setTableTail(StringBuffer pdm){
        pdm.append(" </c:Tables>\r\n");
        pdm.append("<c:DefaultGroups>\r\n");
        pdm.append("<o:Group Id=\"o"+(++beginNum)+"\">\r\n");
        pdm.append("<a:ObjectID>D875EA43-02AB-4865-B78D-C55DBDEAAAAB</a:ObjectID>\r\n");
        pdm.append("<a:Name>PUBLIC</a:Name>\r\n");
        pdm.append("<a:Code>PUBLIC</a:Code>\r\n");
        pdm.append("<a:CreationDate>1198473279</a:CreationDate>\r\n");
        pdm.append("<a:Creator>xuanlt</a:Creator>\r\n");
        pdm.append("<a:ModificationDate>1198473279</a:ModificationDate>\r\n");
        pdm.append("<a:Modifier>xuanlt</a:Modifier>\r\n");
        pdm.append("</o:Group>\r\n");
        pdm.append("</c:DefaultGroups>\r\n");
        pdm.append("<c:Defaults>\r\n");
        pdm.append("<o:PhysicalDefault Id=\"o"+(++beginNum)+"\">\r\n");
        pdm.append("<a:ObjectID>AEC2C06F-C000-4715-95FA-6F0E8AA07511</a:ObjectID>\r\n");
        pdm.append("<a:Name>D_UFBoolean</a:Name>\r\n");
        pdm.append("<a:Code>D_UFBoolean</a:Code>\r\n");
        pdm.append("<a:CreationDate>1260343128</a:CreationDate>\r\n");
        pdm.append("<a:Creator>zhoucx</a:Creator>\r\n");
        pdm.append("<a:ModificationDate>1264556878</a:ModificationDate>\r\n");
        pdm.append("<a:Modifier>zhangg</a:Modifier>\r\n");
        pdm.append("<a:PhysicalDefault.Value>&#39;N&#39;</a:PhysicalDefault.Value>\r\n");
        pdm.append("</o:PhysicalDefault>\r\n");
        pdm.append("</c:Defaults>\r\n");
        pdm.append("<c:TargetModels>\r\n");
        pdm.append("<o:TargetModel Id=\"o"+(++beginNum)+"\">\r\n");
        pdm.append("<a:ObjectID>0B5DF864-6D76-46D0-AD3C-341770E8489C</a:ObjectID>\r\n");
        pdm.append("<a:Name>薪资管理</a:Name>\r\n");
        pdm.append("<a:Code>CDM_WA</a:Code>\r\n");
        pdm.append("<a:CreationDate>0</a:CreationDate>\r\n");
        pdm.append("<a:Creator/>\r\n");
        pdm.append("<a:ModificationDate>0</a:ModificationDate>\r\n");
        pdm.append("<a:Modifier/>\r\n");
        pdm.append("<a:TargetModelURL>file:///E|/Product group/李惠萍/NC_SD_WA_CDM.cdm</a:TargetModelURL>\r\n");
        pdm.append("<a:TargetModelID>886C3AD4-2798-11D5-A63D-52544C197D3C</a:TargetModelID>\r\n");
        pdm.append("<a:TargetModelClassID>1E597170-9350-11D1-AB3C-0020AF71E433</a:TargetModelClassID>\r\n");
        pdm.append("<c:SessionShortcuts>\r\n");
        pdm.append("<o:Shortcut Ref=\"o3\"/>\r\n");
        pdm.append("</c:SessionShortcuts>\r\n");
        pdm.append("</o:TargetModel>\r\n");
        pdm.append("<o:TargetModel Id=\"o"+(++beginNum)+"\">\r\n");
        pdm.append("<a:ObjectID>BFF62C20-B83C-42E4-A97E-143DA43704C8</a:ObjectID>\r\n");
        pdm.append("<a:Name>Microsoft SQL Server 2005</a:Name>\r\n");
        pdm.append("<a:Code>MSSQLSRV2005</a:Code>\r\n");
        pdm.append("<a:CreationDate>1260343128</a:CreationDate>\r\n");
        pdm.append("<a:Creator>zhoucx</a:Creator>\r\n");
        pdm.append("<a:ModificationDate>1260343128</a:ModificationDate>\r\n");
        pdm.append("<a:Modifier>zhoucx</a:Modifier>\r\n");
        pdm.append("<a:TargetModelURL>file:///%_DBMS%/sqlsv2k5.xdb</a:TargetModelURL>\r\n");
        pdm.append("<a:TargetModelID>030105E8-1DFA-4990-B2C8-DEB36D9D8D09</a:TargetModelID>\r\n");
        pdm.append("<a:TargetModelClassID>4BA9F647-DAB1-11D1-9944-006097355D9B</a:TargetModelClassID>\r\n");
        pdm.append("<c:SessionShortcuts>\r\n");
        pdm.append("<o:Shortcut Ref=\"o4\"/>\r\n");
        pdm.append("</c:SessionShortcuts>\r\n");
        pdm.append("</o:TargetModel>\r\n");
        pdm.append("</c:TargetModels>\r\n");
        pdm.append("</o:Model>\r\n");
        pdm.append("</c:Children>\r\n");
        pdm.append("</o:RootObject>\r\n");
        pdm.append("</Model>\r\n");
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
