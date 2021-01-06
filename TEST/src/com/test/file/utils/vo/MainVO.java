package com.test.file.utils.vo;

/**
 * @program: ncc
 * @description:
 * @author: zxb
 * @create: 2020-12-25 09:35
 **/
public class MainVO {
    private String tableName;
    private String tableDisplayName;
    private String tablePk;
    private String packagePath;
    private String className;
    private String voResid;
    private String tableNameResid;
    private String residFileName;
    private String classID;
    private String keyAttributeId;
    private String isReference;
    private String referenceName;

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getKeyAttributeId() {
        return keyAttributeId;
    }

    public void setKeyAttributeId(String keyAttributeId) {
        this.keyAttributeId = keyAttributeId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTablePk() {
        return tablePk;
    }

    public void setTablePk(String tablePk) {
        this.tablePk = tablePk;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getVoResid() {
        return voResid;
    }

    public void setVoResid(String voResid) {
        this.voResid = voResid;
    }

    public String getTableNameResid() {
        return tableNameResid;
    }

    public void setTableNameResid(String tableNameResid) {
        this.tableNameResid = tableNameResid;
    }

    public String getTableDisplayName() {
        return tableDisplayName;
    }

    public void setTableDisplayName(String tableDisplayName) {
        this.tableDisplayName = tableDisplayName;
    }

    public String getResidFileName() {
        return residFileName;
    }

    public void setResidFileName(String residFileName) {
        this.residFileName = residFileName;
    }

    public String getIsReference() {
        return isReference;
    }

    public void setIsReference(String isReference) {
        this.isReference = isReference;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

}
