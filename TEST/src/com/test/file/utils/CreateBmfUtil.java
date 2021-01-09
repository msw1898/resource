package com.test.file.utils;

import com.test.file.utils.vo.BodyVO;
import com.test.file.utils.vo.DataVO;
import com.test.file.utils.vo.EmumVO;
import com.yonyou.cloud.utils.StringUtils;
import nccloud.commons.collections.MapUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @program: ncc
 * @description:
 * @create: 2020-12-24 21:10
 **/
public class CreateBmfUtil {
    public static void makeBfmFile(DataVO dataVO) {
        UUID uuid = UUID.randomUUID();
        dataVO.setComponentID(uuid.toString());
        dataVO.setModuleName("hrwa");
        uuid = UUID.randomUUID();
        dataVO.getParentVO().setClassID(uuid.toString());
        uuid = UUID.randomUUID();
        dataVO.getParentVO().setKeyAttributeId(uuid.toString());
        uuid = UUID.randomUUID();
        dataVO.setReferenceId(uuid.toString());

        // 1、创建document对象
        Document document = DocumentHelper.createDocument();
        setBfmFileContent(document, dataVO);
        writeBmfFile(document, dataVO);
    }

    /**
     * @param document
     * @param dataVO
     */
    public static void setBfmFileContent(Document document, DataVO dataVO) {
        // 2、创建根节点
        Element component = document.addElement("component");
        setComponentProp(component, dataVO);

        // 4、生成子节点及子节点内容
        Element celllist = component.addElement("celllist");
        Element entity = celllist.addElement("entity");
        setEntityProp(entity, dataVO);
        //设置元数据具体属性值
        Element attributelist = entity.addElement("attributelist");
        for (int i = 0; i < dataVO.getBodyVOs().size(); i++) {
            Element attribute = attributelist.addElement("attribute");
            setAttributeProp(attribute, dataVO.getBodyVOs().get(i), i, dataVO);
        }
        entity.addElement("operationlist");
        setBusiInterface(entity, dataVO);
        //参照
        Element canzhaolist = entity.addElement("canzhaolist");
        if ("Y".equals(dataVO.getParentVO().getIsReference())) {
            Element canzhao = canzhaolist.addElement("canzhao");
            canzhao.addAttribute("cellid", dataVO.getParentVO().getClassID());
            canzhao.addAttribute("isdefault", "true");
            canzhao.addAttribute("name", dataVO.getParentVO().getReferenceName());
        }
        Element accessor = entity.addElement("accessor");
        accessor.addAttribute("classFullname", "nc.md.model.access.javamap.NCBeanStyle");
        accessor.addAttribute("displayName", "NCVO");
        accessor.addAttribute("name", "NCVO");
        Element properties = accessor.addElement("properties");
        // 引用
        Element Reference = celllist.addElement("Reference");
        setReferenceProp(Reference, dataVO);
        // 枚举
        if (!MapUtils.isEmpty(dataVO.getEmumMap())) {
            setEnumerates(celllist, dataVO, document);
        }
        Element connectlist = component.addElement("connectlist");
        Element busiitfconnection = connectlist.addElement("busiitfconnection");
        setbusiitfconnection(busiitfconnection, dataVO);
        busiitfconnection.addElement("points");
        // 引用
        Element refdepends = component.addElement("refdepends");
        Element dependfile = refdepends.addElement("dependfile");
        dependfile.addAttribute("entityid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");

        component.addElement("refdependLoseIDs");
        component.addElement("cellRemoveLog");
        Element rulers = component.addElement("rulers");
        Element ruler = rulers.addElement("ruler");
        ruler.addAttribute("isHorizontal", "true");
        ruler.addAttribute("unit", "0");
        ruler.addElement("guideList");

        ruler = rulers.addElement("ruler");
        ruler.addAttribute("isHorizontal", "false");
        ruler.addAttribute("unit", "0");
        ruler.addElement("guideList");
    }

    /**
     * @param busiitfconnection
     * @param dataVO
     */
    public static void setbusiitfconnection(Element busiitfconnection, DataVO dataVO) {
        busiitfconnection.addAttribute("bizItfImpClassName", "");
        busiitfconnection.addAttribute("componentID", dataVO.getComponentID());
        busiitfconnection.addAttribute("createIndustry", "0");
        busiitfconnection.addAttribute("createTime", dataVO.getCreateTime());
        busiitfconnection.addAttribute("creator", "");
        busiitfconnection.addAttribute("description", "");
        busiitfconnection.addAttribute("displayName", "");
        busiitfconnection.addAttribute("help", "");
        busiitfconnection.addAttribute("id", UUID.randomUUID().toString());
        busiitfconnection.addAttribute("industryChanged", "false");
        busiitfconnection.addAttribute("isSource", "false");
        busiitfconnection.addAttribute("modifier", "");
        busiitfconnection.addAttribute("modifyIndustry", "0");
        busiitfconnection.addAttribute("modifyTime", "");
        busiitfconnection.addAttribute("name", "busiItf connection");
        busiitfconnection.addAttribute("realsource", dataVO.getParentVO().getClassID());
        busiitfconnection.addAttribute("realtarget", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        busiitfconnection.addAttribute("source", dataVO.getParentVO().getClassID());
        busiitfconnection.addAttribute("target", dataVO.getReferenceId());
        busiitfconnection.addAttribute("versionType", "0");
    }

    /**
     * @param component
     * @param dataVO
     */
    public static void setComponentProp(Element component, DataVO dataVO) {
        // 3、属性
        component.addAttribute("conRouterType", "手动");
        component.addAttribute("createIndustry", "0");
        component.addAttribute("createTime", dataVO.getCreateTime());
        component.addAttribute("creator", "");
        component.addAttribute("description", "");
        component.addAttribute("displayName", dataVO.getParentVO().getTableDisplayName());
        component.addAttribute("fromSourceBmf", "true");
        component.addAttribute("gencodestyle", "NC传统样式");
        component.addAttribute("help", "");
        component.addAttribute("id", dataVO.getComponentID());
        component.addAttribute("industry", "0");
        component.addAttribute("industryChanged", "false");
        component.addAttribute("industryIncrease", "false");
        component.addAttribute("industryName", "基础行业");
        component.addAttribute("isSource", "false");
        component.addAttribute("isbizmodel", "false");
        component.addAttribute("mainEntity", dataVO.getParentVO().getClassID());
        component.addAttribute("modifier", "");
        component.addAttribute("modifyIndustry", "0");
        component.addAttribute("modifyTime", dataVO.getCreateTime());
        component.addAttribute("name", dataVO.getParentVO().getTableName());
        component.addAttribute("namespace", dataVO.getModuleName());
        component.addAttribute("ownModule", dataVO.getModuleName());
        component.addAttribute("preLoad", "false");
        component.addAttribute("programcode", "");
        component.addAttribute("resModuleName", dataVO.getResidFileName());
        component.addAttribute("resid", getResidNameByResid(dataVO.getParentVO().getTableDisplayName(), dataVO));
        component.addAttribute("version", "1");
        component.addAttribute("versionType", "0");
    }

    /**
     * @param entity
     * @param dataVO
     */
    public static void setEntityProp(Element entity, DataVO dataVO) {
        entity.addAttribute("accessorClassName", "nc.md.model.access.javamap.NCBeanStyle");
        entity.addAttribute("bizItfImpClassName", "SuperVO");
        entity.addAttribute("componentID", dataVO.getComponentID());
        entity.addAttribute("createIndustry", "0");
        entity.addAttribute("createTime", dataVO.getCreateTime());
        entity.addAttribute("creator", "");
        if ("Y".equals(dataVO.getParentVO().getIsReference())) {
            entity.addAttribute("czlist", dataVO.getParentVO().getReferenceName());
        } else {
            entity.addAttribute("czlist", "");
        }
        entity.addAttribute("description", "");
        entity.addAttribute("displayName", dataVO.getParentVO().getTableDisplayName());
        entity.addAttribute("fullClassName", dataVO.getParentVO().getPackagePath() + "." + dataVO.getParentVO().getClassName());
        entity.addAttribute("height", "381");
        entity.addAttribute("help", "");
        entity.addAttribute("id", dataVO.getParentVO().getClassID());
        entity.addAttribute("industryChanged", "false");
        entity.addAttribute("isAuthen", "true");
        entity.addAttribute("isCreateSQL", "true");
        entity.addAttribute("isExtendBean", "false");
        entity.addAttribute("isPrimary", "true");
        entity.addAttribute("isSource", "false");
        entity.addAttribute("keyAttributeId", dataVO.getParentVO().getKeyAttributeId());
        entity.addAttribute("modInfoClassName", "");
        entity.addAttribute("modifier", "");
        entity.addAttribute("modifyIndustry", "0");
        entity.addAttribute("modifyTime", dataVO.getCreateTime());
        entity.addAttribute("name", dataVO.getParentVO().getTableName());
        entity.addAttribute("resid", getResidNameByResid(dataVO.getParentVO().getTableDisplayName(), dataVO));
        entity.addAttribute("stereoType", "");
        entity.addAttribute("tableName", dataVO.getParentVO().getTableName());
        entity.addAttribute("userDefClassName", "");
        entity.addAttribute("versionType", "0");
        entity.addAttribute("visibility", "public");
        entity.addAttribute("width", "119");
        entity.addAttribute("x", "208");
        entity.addAttribute("y", "6");
    }

    /**
     * @param attribute
     * @param bodyVO
     * @param sequence
     * @param dataVO
     */
    public static void setAttributeProp(Element attribute, BodyVO bodyVO, int sequence, DataVO dataVO) {
        setCommonAttributeProp(attribute, bodyVO, sequence, dataVO);
        setSpecialAttributeProp(attribute, bodyVO, sequence, dataVO);
    }

    /**
     * @param attribute
     * @param bodyVO
     * @param sequence
     * @param dataVO
     */
    public static void setSpecialAttributeProp(Element attribute, BodyVO bodyVO, int sequence, DataVO dataVO) {
        if (dataVO.getParentVO().getTablePk().equals(bodyVO.getFieldCode())) {
            attribute.addAttribute("id", dataVO.getParentVO().getKeyAttributeId());
            attribute.addAttribute("isKey", "true");
            attribute.addAttribute("isNullable", "false");
            attribute.addAttribute("typeDisplayName", "UFID");
            attribute.addAttribute("typeName", "UFID");
            attribute.addAttribute("dataType", "BS000010000100001051");
            attribute.addAttribute("dbtype", "char");
            attribute.addAttribute("fieldType", "char");
            attribute.addAttribute("displayName", bodyVO.getFieldName());
        } else {
            attribute.addAttribute("id", UUID.randomUUID().toString());
            if ("pk_group".equals(bodyVO.getFieldCode())) {
                attribute.addAttribute("dataType", "3b6dd171-2900-47f3-bfbe-41e4483a2a65");
                attribute.addAttribute("dataTypeStyle", "REF");
                attribute.addAttribute("displayName", "所属集团");
                attribute.addAttribute("refModelName", "集团");
                attribute.addAttribute("typeDisplayName", "集团");
                attribute.addAttribute("typeName", "group");
            } else if ("pk_org".equals(bodyVO.getFieldCode())) {
                attribute.addAttribute("dataType", "985be8a4-3a36-4778-8afe-2d8ed3902659");
                attribute.addAttribute("dataTypeStyle", "REF");
                attribute.addAttribute("displayName", "所属组织");
                attribute.addAttribute("refModelName", "业务单元");
                attribute.addAttribute("typeDisplayName", "组织");
                attribute.addAttribute("typeName", "org");
            } else if ("pk_tax_org".equals(bodyVO.getFieldCode())) {
                attribute.addAttribute("dataType", "985be8a4-3a36-4778-8afe-2d8ed3902659");
                attribute.addAttribute("dataTypeStyle", "REF");
                attribute.addAttribute("displayName", "纳税申报组织");
                attribute.addAttribute("refModelName", "业务单元");
                attribute.addAttribute("typeDisplayName", "组织");
                attribute.addAttribute("typeName", "org");
            } else if ("pk_tax_class".equals(bodyVO.getFieldCode())) {
                attribute.addAttribute("dataType", "4ca7caca-736b-407c-95ed-beca3d0d8858");
                attribute.addAttribute("dataTypeStyle", "REF");
                attribute.addAttribute("displayName", "报税方案主键");
                attribute.addAttribute("refModelName", "");
                attribute.addAttribute("typeDisplayName", "个人所得税申报设置子表");
                attribute.addAttribute("typeName", "wa_tax_class");
            } else if ("pk_psndoc".equals(bodyVO.getFieldCode())) {
                attribute.addAttribute("dataType", "218971f0-e5dc-408b-9a32-56529dddd4db");
                attribute.addAttribute("dataTypeStyle", "REF");
                attribute.addAttribute("displayName", "人员主键");
                attribute.addAttribute("refModelName", "HR人员(集团)");
                attribute.addAttribute("typeDisplayName", "人员基本信息");
                attribute.addAttribute("typeName", "bd_psndoc");
            } else if ("pk_psnjob".equals(bodyVO.getFieldCode())) {
                attribute.addAttribute("dataType", "7156d223-4531-4337-b192-492ab40098f1");
                attribute.addAttribute("dataTypeStyle", "REF");
                attribute.addAttribute("displayName", "任职记录");
                attribute.addAttribute("refModelName", "人员工作记录");
                attribute.addAttribute("typeDisplayName", "工作记录");
                attribute.addAttribute("typeName", "hi_psnjob");
            } else if ("creator".equals(bodyVO.getFieldCode())) {
                attribute.addAttribute("dataType", "f6f9a473-56c0-432f-8bc7-fbf8fde54fee");
                attribute.addAttribute("dataTypeStyle", "REF");
                attribute.addAttribute("displayName", "创建人");
                attribute.addAttribute("refModelName", "用户");
                attribute.addAttribute("typeDisplayName", "用户");
                attribute.addAttribute("typeName", "user");
            } else if ("creationtime".equals(bodyVO.getFieldCode())) {
                attribute.addAttribute("dataType", "BS000010000100001034");
                attribute.addAttribute("dataTypeStyle", "SINGLE");
                attribute.addAttribute("displayName", "创建时间");
                attribute.addAttribute("typeDisplayName", "UFDateTime");
                attribute.addAttribute("typeName", "UFDateTime");
                attribute.addAttribute("dbtype", "char");
                attribute.addAttribute("fieldType", "char");
            } else if ("modifier".equals(bodyVO.getFieldCode())) {
                attribute.addAttribute("dataType", "f6f9a473-56c0-432f-8bc7-fbf8fde54fee");
                attribute.addAttribute("dataTypeStyle", "REF");
                attribute.addAttribute("displayName", "修改人");
                attribute.addAttribute("refModelName", "用户");
                attribute.addAttribute("typeDisplayName", "用户");
                attribute.addAttribute("typeName", "user");
            } else if ("modifiedtime".equals(bodyVO.getFieldCode())) {
                attribute.addAttribute("dataType", "BS000010000100001034");
                attribute.addAttribute("dataTypeStyle", "SINGLE");
                attribute.addAttribute("displayName", "修改时间");
                attribute.addAttribute("typeDisplayName", "UFDateTime");
                attribute.addAttribute("typeName", "UFDateTime");
                attribute.addAttribute("dbtype", "char");
                attribute.addAttribute("fieldType", "char");
            }
        }
    }

    /**
     * @param attribute
     * @param bodyVO
     * @param sequence
     * @param dataVO
     */
    public static void setCommonAttributeProp(Element attribute, BodyVO bodyVO, int sequence, DataVO dataVO) {
        String isNull = bodyVO.getIsNull() == null ? "Y" : bodyVO.getIsNull();
        attribute.addAttribute("isKey", "false");
        attribute.addAttribute("isNullable", "Y".equals(bodyVO.getIsNull()) ? "true" : "false");
        attribute.addAttribute("refModelName", "");
        attribute.addAttribute("accessStrategy", "");
        attribute.addAttribute("accesspower", "false");
        attribute.addAttribute("accesspowergroup", "");
        attribute.addAttribute("calculation", "false");
        attribute.addAttribute("classID", dataVO.getParentVO().getClassID());
        attribute.addAttribute("createIndustry", "0");
        if ("UFBoolean".equals(bodyVO.getFieldType())) {
            attribute.addAttribute("dataType", "BS000010000100001001");
            attribute.addAttribute("fieldType", "char");
            attribute.addAttribute("dbtype", "char");
            attribute.addAttribute("typeDisplayName", "UFBoolean");
            attribute.addAttribute("typeName", "UFBoolean");
        }else if ("Integer".equals(bodyVO.getFieldType())) {
            attribute.addAttribute("dataType", "BS000010000100001004");
            attribute.addAttribute("fieldType", "int");
            attribute.addAttribute("dbtype", "int");
            attribute.addAttribute("typeDisplayName", "Integer");
            attribute.addAttribute("typeName", "Integer");
        }else {
            attribute.addAttribute("dataType", "BS000010000100001032");
            attribute.addAttribute("fieldType", "varchar");
            attribute.addAttribute("dbtype", "varchar");
            attribute.addAttribute("typeDisplayName", "String");
            attribute.addAttribute("typeName", "String");
        }
        attribute.addAttribute("dataTypeStyle", "SINGLE");
        attribute.addAttribute("displayName", bodyVO.getFieldName());
        attribute.addAttribute("defaultValue", "");
        attribute.addAttribute("description", "");
        attribute.addAttribute("refModelName", "");
        attribute.addAttribute("dynamic", "false");
        attribute.addAttribute("dynamicTable", "");
        attribute.addAttribute("fieldName", bodyVO.getFieldCode());
        attribute.addAttribute("fixedLength", "false");
        attribute.addAttribute("forLocale", "false");
        attribute.addAttribute("help", "");
        attribute.addAttribute("industryChanged", "false");
        attribute.addAttribute("isActive", "true");
        attribute.addAttribute("isAuthorization", "true");
        attribute.addAttribute("isDefaultDimensionAttribute", "false");
        attribute.addAttribute("isDefaultMeasureAttribute", "false");
        attribute.addAttribute("isFeature", "false");
        attribute.addAttribute("isGlobalization", "false");
        attribute.addAttribute("isHide", "false");
        attribute.addAttribute("isReadOnly", "false");
        attribute.addAttribute("isShare", "false");
        attribute.addAttribute("isSource", "true");
        attribute.addAttribute("length", StringUtils.isBlank(bodyVO.getLength()) ? (20 + "") : bodyVO.getLength());
        attribute.addAttribute("maxValue", "");
        attribute.addAttribute("minValue", "");
        attribute.addAttribute("modifyIndustry", "0");
        attribute.addAttribute("name", bodyVO.getFieldCode());
        attribute.addAttribute("notSerialize", "false");
        attribute.addAttribute("precise", "");
        attribute.addAttribute("resid", getResidNameByResid(bodyVO.getFieldName(), dataVO));
        attribute.addAttribute("sequence", String.valueOf(sequence));
        attribute.addAttribute("versionType", "0");
        attribute.addAttribute("visibility", "public");
    }

    /**
     * @param entity
     * @param dataVO
     */
    public static void setBusiInterface(Element entity, DataVO dataVO) {
        Element busiitfs = entity.addElement("busiitfs");
        Element itfid = busiitfs.addElement("itfid");
        itfid.setText("6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        Element busimaps = entity.addElement("busimaps");
        Element busimap = busimaps.addElement("busimap");
        busimap.addAttribute("attrid", dataVO.getParentVO().getKeyAttributeId());
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "5dd3c721-22ad-42b1-9c10-4351c236bc77");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        busimap.addAttribute("cellid", dataVO.getParentVO().getClassID());

        busimap = busimaps.addElement("busimap");
        busimap.addAttribute("attrid", "");
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "c8334364-7ab9-4266-8d4b-e74537935e46");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        busimap.addAttribute("cellid", dataVO.getParentVO().getClassID());

        busimap = busimaps.addElement("busimap");
        busimap.addAttribute("attrid","");
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "ecf1b76a-6e44-42e2-a55e-87596504775b");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        busimap.addAttribute("cellid", dataVO.getParentVO().getClassID());


        busimap = busimaps.addElement("busimap");
        busimap.addAttribute("attrid", "");
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "d32cc17b-f415-415a-923f-0764443eb102");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        busimap.addAttribute("cellid", dataVO.getParentVO().getClassID());

        busimap = busimaps.addElement("busimap");
        busimap.addAttribute("attrid", "");
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "89578a97-42fe-439b-827c-8eabd9e3604c");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        busimap.addAttribute("cellid", dataVO.getParentVO().getClassID());

        busimap = busimaps.addElement("busimap");
        busimap.addAttribute("attrid","");
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "a47e6cda-09e4-480b-923f-ec6f41e3e06c");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        busimap.addAttribute("cellid", dataVO.getParentVO().getClassID());
    }

    /**
     * @param Reference
     * @param dataVO
     */
    public static void setReferenceProp(Element Reference, DataVO dataVO) {
        Reference.addAttribute("componentID", dataVO.getComponentID());
        Reference.addAttribute("createIndustry", "0");
        Reference.addAttribute("createTime", dataVO.getCreateTime());
        Reference.addAttribute("creator", "");
        Reference.addAttribute("description", "");
        Reference.addAttribute("displayName", "displayName");
        Reference.addAttribute("fullClassName", "nc.vohrwa.pay.reference");
        Reference.addAttribute("height", "167");
        Reference.addAttribute("help", "");
        Reference.addAttribute("id", dataVO.getReferenceId());
        Reference.addAttribute("industryChanged", "false");
        Reference.addAttribute("isSource", "false");
        Reference.addAttribute("mdFilePath", "\\meta\\general_interface.bmf");
        Reference.addAttribute("modifier", "");
        Reference.addAttribute("modifyIndustry", "0");

        Reference.addAttribute("modifyTime", dataVO.getCreateTime());
        Reference.addAttribute("moduleName", "uapbs");
        Reference.addAttribute("name", "reference");
        Reference.addAttribute("refId", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        Reference.addAttribute("resid", "");
        Reference.addAttribute("versionType", "0");
        Reference.addAttribute("visibility", "public");
        Reference.addAttribute("width", "100");
        Reference.addAttribute("x", "55");
        Reference.addAttribute("y", "37");
    }

    /**
     * 设置枚举
     *
     * @param celllist
     * @param dataVO
     */
    public static void setEnumerates(Element celllist, DataVO dataVO, Document document) {
        int position=0;
        for (String str : dataVO.getEmumMap().keySet()) {
            position++;
            Element Enumerate = celllist.addElement("Enumerate");
            EmumVO emumVO = dataVO.getEmumMap().get(str);
            String enumPId = UUID.randomUUID().toString();
            //回写枚举
            backWriteEnumAttr(document, emumVO, enumPId);
            // 枚举主体
            setEnumerate(Enumerate, enumPId, dataVO, str,position);
            Element enumitemlist = Enumerate.addElement("enumitemlist");
            for (int i = 0; i < emumVO.getEmumList().size(); i++) {
                Element enumitem = enumitemlist.addElement("enumitem");
                // 枚举具体值
                setEnumitem(enumitem, emumVO, enumPId, i, dataVO);
            }
        }
    }

    /**
     *
     * @param document
     * @param emumVO
     * @param enumPId
     */
    public static void backWriteEnumAttr(Document document, EmumVO emumVO, String enumPId) {
        List attributeList = document.selectNodes("//attribute");
        Iterator it = attributeList.iterator();
        while (it.hasNext()) {
            Element elt = (Element) it.next();
            Attribute attr = elt.attribute("fieldName");
            if (attr.getValue().equals(emumVO.getCode())) {
                elt.attribute("typeDisplayName").setValue(emumVO.getName());
                elt.attribute("typeName").setValue(emumVO.getName());
                elt.attribute("dataType").setValue(enumPId);
            }
        }
    }

    /**
     *
     * @param Enumerate
     * @param enumPId
     * @param dataVO
     * @param str
     */
    public static void setEnumerate(Element Enumerate, String enumPId, DataVO dataVO, String str,int position) {
        Enumerate.addAttribute("componentID", dataVO.getComponentID());
        Enumerate.addAttribute("createIndustry", "0");
        Enumerate.addAttribute("createTime", "37");
        Enumerate.addAttribute("creator", "");
        Enumerate.addAttribute("dataType", "BS000010000100001001");
        Enumerate.addAttribute("dbtype", "varchar");
        Enumerate.addAttribute("description", "");
        Enumerate.addAttribute("displayName", str);
        Enumerate.addAttribute("fullClassName", "");
        Enumerate.addAttribute("height", "100");
        Enumerate.addAttribute("help", "");
        Enumerate.addAttribute("id", enumPId);
        Enumerate.addAttribute("industryChanged", "false");
        Enumerate.addAttribute("isSource", "false");
        Enumerate.addAttribute("modInfoClassName", "");
        Enumerate.addAttribute("modifier", "");
        Enumerate.addAttribute("modifyIndustry", "0");
        Enumerate.addAttribute("modifyTime", "");
        Enumerate.addAttribute("name", str);
        Enumerate.addAttribute("resid", getResidNameByResid(str, dataVO));
        Enumerate.addAttribute("typeDisplayName", "String");
        Enumerate.addAttribute("typeName", "String");
        Enumerate.addAttribute("versionType", "0");
        Enumerate.addAttribute("visibility", "public");
        Enumerate.addAttribute("width", "80");
        Enumerate.addAttribute("x", "530");
        Enumerate.addAttribute("y", String.valueOf(50+position*120));
    }

    /**
     *
     * @param enumitem
     * @param emumVO
     * @param enumPId
     * @param i
     * @param dataVO
     */
    public static void setEnumitem(Element enumitem, EmumVO emumVO, String enumPId, int i, DataVO dataVO) {
        enumitem.addAttribute("createIndustry", "0");
        enumitem.addAttribute("description", "");
        enumitem.addAttribute("enumDisplay", emumVO.getEmumList().get(i).getName());
        enumitem.addAttribute("enumID", UUID.randomUUID().toString());
        enumitem.addAttribute("enumValue", emumVO.getEmumList().get(i).getValue());
        enumitem.addAttribute("hidden", "false");
        enumitem.addAttribute("id", enumPId);
        enumitem.addAttribute("industryChanged", "fasle");
        enumitem.addAttribute("isSource", "fasle");
        enumitem.addAttribute("modifyIndustry", "0");
        enumitem.addAttribute("resid", getResidNameByResid(emumVO.getEmumList().get(i).getName(), dataVO));
        enumitem.addAttribute("sequence", String.valueOf(i));
        enumitem.addAttribute("versionType", "0");
    }

    /**
     * @param document
     * @param dataVO
     */
    public static void writeBmfFile(Document document, DataVO dataVO) {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置编码格式
            format.setEncoding("UTF-8");
            // 6、生成xml文件
            String newFolderPath = MakeFileUtil.createFileFolder(dataVO.getFilePath(), dataVO.getFileName());
            File file = new File(newFolderPath + "//" + dataVO.getFileName() + ".bmf");
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            // 设置是否转义，默认使用转义字符
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
            System.out.println("生成" + dataVO.getFileName() + ".bmf成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成" + dataVO.getFileName() + ".bmf失败！");
        }
    }

    /**
     * @param key
     * @param dataVO
     * @return
     */
    public static String getResidNameByResid(String key, DataVO dataVO) {
        if (dataVO.getResidFileMap().get(key) != null) {
            return dataVO.getResidFileMap().get(key);
        }
        if (dataVO.getAppendResidFileMap() == null) {
            return "";
        }
        return dataVO.getAppendResidFileMap().get(key);
    }
}
