package com.test.file.utils.vo;

import java.util.List;
import java.util.Map;

/**
 * @program: ncc
 * @description:
 * @author: zxb
 * @create: 2020-12-25 09:38
 **/
public class DataVO {
    private com.test.file.utils.vo.MainVO parentVO;
    private List<BodyVO> bodyVOs;
    private Map<String, String> residFileMap;
    private Map<String, String> appendResidFileMap;
    private String fileName;
    private String filePath;
    private String createTime;
    private String residAllPath;
    private String residFileName;
    private String componentID;
    private String moduleName;
    private String residRule;
    private String referenceId;
    private Map<String, EmumVO> emumMap;

    public String getComponentID() {
        return componentID;
    }

    public void setComponentID(String componentID) {
        this.componentID = componentID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public com.test.file.utils.vo.MainVO getParentVO() {
        return parentVO;
    }

    public void setParentVO(com.test.file.utils.vo.MainVO parentVO) {
        this.parentVO = parentVO;
    }

    public List<BodyVO> getBodyVOs() {
        return bodyVOs;
    }

    public void setBodyVOs(List<BodyVO> bodyVOs) {
        this.bodyVOs = bodyVOs;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getResidAllPath() {
        return residAllPath;
    }

    public void setResidAllPath(String residAllPath) {
        this.residAllPath = residAllPath;
    }

    public Map<String, String> getResidFileMap() {
        return residFileMap;
    }

    public void setResidFileMap(Map<String, String> residFileMap) {
        this.residFileMap = residFileMap;
    }

    public String getResidFileName() {
        return residFileName;
    }

    public void setResidFileName(String residFileName) {
        this.residFileName = residFileName;
    }

    public String getResidRule() {
        return residRule;
    }

    public void setResidRule(String residRule) {
        this.residRule = residRule;
    }

    public Map<String, String> getAppendResidFileMap() {
        return appendResidFileMap;
    }

    public void setAppendResidFileMap(Map<String, String> appendResidFileMap) {
        this.appendResidFileMap = appendResidFileMap;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Map<String, EmumVO> getEmumMap() {
        return emumMap;
    }

    public void setEmumMap(Map<String, EmumVO> emumMap) {
        this.emumMap = emumMap;
    }
}
