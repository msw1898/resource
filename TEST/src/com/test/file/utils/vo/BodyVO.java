package com.test.file.utils.vo;

/**
 * @program: ncc
 * @description:
 * @author: zxb
 * @create: 2020-12-25 09:35
 **/
public class BodyVO {
    private String fieldName;
    private String fieldCode;
    private String fieldType;
    private String resid;
    private String length;
    private String isNull;
    private String isCreateField;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getIsCreateField() {
        return isCreateField;
    }

    public void setIsCreateField(String isCreateField) {
        this.isCreateField = isCreateField;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }
}
