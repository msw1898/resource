package com.test.file.utils;

import com.test.file.utils.vo.BodyVO;
import com.test.file.utils.vo.DataVO;
import com.test.file.utils.vo.EmumVO;
import com.test.file.utils.vo.MainVO;
import nccloud.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: ncc
 * @description:
 * @author: zxb
 * @create: 2020-12-25 10:20
 **/
public class GetDataFromExcelFile {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * @param dataVO
     * @return
     */
    public static DataVO readExcel(DataVO dataVO) {
        String filePath = dataVO.getFilePath() + "//" + dataVO.getFileName() + ".xls";
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<BodyVO> list = null;
        String cellData = null;
        wb = createWorkbook(filePath);
        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<BodyVO>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            dataVO.setParentVO(getMainVOData(sheet));
            dataVO.setBodyVOs(getBodyVODatas(sheet, rownum));
        }
        return dataVO;
    }

    /**
     * @param filePath
     * @return
     */
    public static Workbook createWorkbook(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * 获取excel单元格的值
     *
     * @param cell
     * @return
     */
    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            cellValue = getCellValue(cell);
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    /**
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType type = cell.getCellType();
        String value = "";
        switch (type) {
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    value = sdf.format(cell.getDateCellValue());
                    break;
                }
                value = String.valueOf(cell.getNumericCellValue());
                if (value.indexOf("E") > 0) {
                    value = parseDouble(value);
                }
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
        }
        return value.trim();
    }

    /**
     * @param strDigt
     * @return
     */
    private static String parseDouble(String strDigt) {
        int pos = strDigt.indexOf("E");
        int dotPos = strDigt.indexOf(".");
        String betweenValue = strDigt.substring(dotPos + 1, pos);
        String temp = "";
        int power = Integer.parseInt(strDigt.substring(pos + 1));
        if (power > 0) {
            StringBuilder sbd = new StringBuilder();
            sbd.append(strDigt.substring(0, dotPos));
            for (int index = 0; index < power; index++) {
                if (index < betweenValue.length()) {
                    sbd.append(betweenValue.charAt(index));
                } else {
                    sbd.append("0");
                }
            }

            if (power < betweenValue.length()) {
                temp = betweenValue.substring(power);
                sbd.append((new StringBuilder()).append(".").append(temp).toString());
            }
            return sbd.toString();
        } else {
            return strDigt;
        }
    }

    /**
     * @param sheet
     * @return
     */
    public static MainVO getMainVOData(Sheet sheet) {
        MainVO mainVO = new MainVO();
        Row row = sheet.getRow(0);
        mainVO.setTableName((String) getCellFormatValue(row.getCell(1)));
        mainVO.setTablePk((String) getCellFormatValue(row.getCell(3)));
        mainVO.setTableDisplayName((String) getCellFormatValue(row.getCell(5)));

        row = sheet.getRow(1);
        mainVO.setPackagePath((String) getCellFormatValue(row.getCell(1)));
        mainVO.setClassName((String) getCellFormatValue(row.getCell(3)));
        mainVO.setIsReference((String) getCellFormatValue(row.getCell(5)));

        row = sheet.getRow(2);
        mainVO.setReferenceName((String) getCellFormatValue(row.getCell(1)));
        mainVO.setResidFileName((String) getCellFormatValue(row.getCell(3)));

        return mainVO;
    }

    /**
     * @param sheet
     * @param rownum
     * @return
     */
    public static List<BodyVO> getBodyVODatas(Sheet sheet, int rownum) {
        Row row;
        BodyVO fieldInfo;
        List list = new ArrayList<BodyVO>();
        for (int i = 5; i < rownum; i++) {
            Map<String, String> map = new LinkedHashMap<String, String>();
            row = sheet.getRow(i);
            if (row != null) {
                String fieldType = (String) getCellFormatValue(row.getCell(0));
                String fieldCode = (String) getCellFormatValue(row.getCell(1));
                String fieldName = (String) getCellFormatValue(row.getCell(2));
                String isNull = (String) getCellFormatValue(row.getCell(3));
                String length = (String) getCellFormatValue(row.getCell(4));
                String isCreateVOField = (String) getCellFormatValue(row.getCell(5));
                if (StringUtils.isNotBlank(fieldType) || StringUtils.isNotBlank(fieldCode) || StringUtils.isNotBlank(fieldName) || StringUtils.isNotBlank(length)) {
                    fieldInfo = new BodyVO();
                    fieldInfo.setFieldType(fieldType);
                    fieldInfo.setFieldCode(fieldCode);
                    fieldInfo.setFieldName(fieldName);
                    fieldInfo.setLength(length.replace(".0", ""));
                    fieldInfo.setIsNull(isNull == null ? "Y" : isNull);
                    if (StringUtils.isNotBlank(isCreateVOField) && "N".equals(isCreateVOField)) {
                        fieldInfo.setIsCreateField("N");
                    }
                    list.add(fieldInfo);
                }
            }
        }
        return list;
    }

    /**
     * @param filePath
     */
    public static void readExcelToJson(String filePath) {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        wb = createWorkbook(filePath);
        if (wb != null) {
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            getColumnValToJson(sheet, rownum);
        }
    }

    public static void getColumnValToJson(Sheet sheet, int rownum) {
        Row row;
        BodyVO fieldInfo;
        List list = new ArrayList<BodyVO>();
        for (int i = 1; i < rownum; i++) {
            Map<String, String> map = new LinkedHashMap<String, String>();
            row = sheet.getRow(i);
            if (row != null) {
                System.out.println("json.put(\"" + row.getCell(0) + "\",row.getCell(\"\").getDisplay()); // " + row.getCell(1));
            }
        }
    }

    public static DataVO readExcelToEmum(DataVO dataVO) {
        String filePath = dataVO.getFilePath() + "//" + dataVO.getFileName() + ".xls";
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<BodyVO> list = null;
        String cellData = null;
        wb = createWorkbook(filePath);
        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<BodyVO>();
            //获取第一个sheet
            sheet = wb.getSheetAt(1);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            if (rownum == 0) {
                return dataVO;
            }
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            dataVO.setEmumMap(getEmumMap(sheet, rownum));
        }
        return dataVO;
    }

    /**
     * @param sheet
     * @param rownum
     * @return
     */
    public static Map<String, EmumVO> getEmumMap(Sheet sheet, int rownum) {
        Row row;
        BodyVO fieldInfo;
        Map<String, EmumVO> map = new HashMap<String, EmumVO>();
        EmumVO emumVOP;
        List<EmumVO> bodyList;
        String lastPName = "";
        for (int i = 0; i < rownum; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                String emumPCode = (String) getCellFormatValue(row.getCell(0));
                String emumPName = (String) getCellFormatValue(row.getCell(1));
                String emumBCode = (String) getCellFormatValue(row.getCell(2));
                String emumBName = (String) getCellFormatValue(row.getCell(3));
                String emumBValue = (String) getCellFormatValue(row.getCell(4));
                if (StringUtils.isNotBlank(emumPName)) {
                    lastPName = emumPName;
                }
                emumVOP = map.get(lastPName);
                if (emumVOP == null) {
                    emumVOP = new EmumVO();
                    emumVOP.setCode(emumPCode);
                    emumVOP.setName(emumPName);
                    map.put(emumPName, emumVOP);
                }
                bodyList = emumVOP.getEmumList();
                if (bodyList == null) {
                    bodyList = new ArrayList<>();
                    emumVOP.setEmumList(bodyList);
                }
                EmumVO emumVOB = new EmumVO();
                emumVOB.setName(emumBName);
                emumVOB.setCode(emumBCode);
                emumVOB.setValue(emumBValue.replace(".0", ""));
                bodyList.add(emumVOB);
            }
        }
        return map;
    }
}
