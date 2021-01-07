package com.test.file.utils.vo;

import java.util.List;

/**
 * @program: ncc
 * @description:
 * @create: 2021-01-07 09:56
 **/
public class EmumVO {
    String name;
    String code;
    String value;
    List<EmumVO> emumList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<EmumVO> getEmumList() {
        return emumList;
    }

    public void setEmumList(List<EmumVO> emumList) {
        this.emumList = emumList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
