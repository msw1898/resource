package com.test.file.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @program: ncc
 * @description:
 * @author: zxb
 * @create: 2020-12-24 21:10
 **/
public class xml2 {
    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        createXml();
        System.out.println("运行时间：" + (System.currentTimeMillis() - start));
    }

    /**
     * 生成xml方法
     */
    public static void createXml() {
        try {
            // 1、创建document对象
            Document document = DocumentHelper.createDocument();
            // 2、创建根节点rss
            Element component = document.addElement("component");
            setComponentProp(component);


            // 4、生成子节点及子节点内容
            Element celllist = component.addElement("celllist");
            Element entity = celllist.addElement("entity");
            setEntityProp(entity);


            Element attributelist = entity.addElement("attributelist");
            for (int i = 0; i < 10; i++) {
                Element attribute = attributelist.addElement("attribute");
                setAttributeProp(attribute);
            }
            entity.addElement("operationlist");

            setBusiInterface(entity);
            //参照
            Element canzhaolist = entity.addElement("canzhaolist");
            Element canzhao = entity.addElement("canzhao");
            //canzhao.addAttribute("cellid", "");
            canzhao.addAttribute("isdefault", "true");
            //canzhao.addAttribute("name", "");

            Element accessor = entity.addElement("accessor");
            accessor.addAttribute("classFullname", "nc.md.model.access.javamap.NCBeanStyle");
            accessor.addAttribute("displayName", "NCVO");
            accessor.addAttribute("name", "NCVO");
            Element properties = accessor.addElement("properties");


            Element Reference = celllist.addElement("Reference");
            setReferenceProp(Reference);

            component.addElement("connectlist");
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

            Element ruler2 = rulers.addElement("ruler");
            ruler2.addAttribute("isHorizontal", "false");
            ruler2.addAttribute("unit", "0");
            ruler2.addElement("guideList");


            //title.setText("国内最新新闻");
            // 5、设置生成xml的格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置编码格式
            format.setEncoding("UTF-8");

            String filePath = "C:\\Users\\Administrator\\Desktop\\新税通补丁\\生成类文件\\payplan.xml";
            // 6、生成xml文件
            File file = new File(filePath);
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            // 设置是否转义，默认使用转义字符
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
            System.out.println("生成rss.xml成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成rss.xml失败");
        }
    }

    public static void setComponentProp(Element component) {
        // 3、属性
        component.addAttribute("conRouterType", "手动");
        component.addAttribute("createIndustry", "0");
        // component.addAttribute("createTime", "手动");
        component.addAttribute("creator", "");
        component.addAttribute("description", "");
        //component.addAttribute("displayName", "发薪计划");
        component.addAttribute("fromSourceBmf", "true");
        component.addAttribute("gencodestyle", "NC传统样式");
        component.addAttribute("help", "");
        // component.addAttribute("id", "d409d1bf-5de9-4755-8261-73b051431f34");
        component.addAttribute("industry", "0");
        component.addAttribute("industryChanged", "false");
        component.addAttribute("industryIncrease", "false");
        component.addAttribute("industryName", "基础行业");
        component.addAttribute("isSource", "false");
        component.addAttribute("isbizmodel", "false");
        //component.addAttribute("mainEntity", "e6c4dbbf-17e8-4fd4-9383-62fb17342c60");
        component.addAttribute("modifier", "");
        component.addAttribute("modifyIndustry", "0");
        //component.addAttribute("modifyTime", "");
        //component.addAttribute("name", "");
        //component.addAttribute("namespace", "hrwa");
        // component.addAttribute("ownModule", "hrwa");
        component.addAttribute("preLoad", "false");
        component.addAttribute("programcode", "");
        //component.addAttribute("resModuleName", "");
        //component.addAttribute("resid", "");
        component.addAttribute("version", "1");
        component.addAttribute("versionType", "0");
    }

    public static void setEntityProp(Element entity) {
        entity.addAttribute("accessorClassName", "nc.md.model.access.javamap.NCBeanStyle");
        entity.addAttribute("bizItfImpClassName", "SuperVO");
        //entity.addAttribute("componentID","");
        entity.addAttribute("createIndustry", "0");
        //entity.addAttribute("createTime","");
        entity.addAttribute("creator", "");
        entity.addAttribute("czlist", "");
        entity.addAttribute("description", "");
        //entity.addAttribute("displayName","");
        // entity.addAttribute("fullClassName","");
        entity.addAttribute("height", "381");
        entity.addAttribute("help", "");
        //entity.addAttribute("id","");
        entity.addAttribute("industryChanged", "false");
        entity.addAttribute("isAuthen", "true");
        entity.addAttribute("isCreateSQL", "true");
        entity.addAttribute("isExtendBean", "false");
        entity.addAttribute("isPrimary", "true");
        entity.addAttribute("isSource", "false");
        //entity.addAttribute("keyAttributeId","");
        entity.addAttribute("modInfoClassName", "");
        entity.addAttribute("modifier", "");
        entity.addAttribute("modifyIndustry", "0");
        //entity.addAttribute("modifyTime","");
        //entity.addAttribute("name","");
        //entity.addAttribute("resid","");
        entity.addAttribute("stereoType", "");
        //entity.addAttribute("tableName","");
        entity.addAttribute("userDefClassName", "");
        entity.addAttribute("versionType", "0");
        entity.addAttribute("visibility", "public");
        entity.addAttribute("width", "119");
        entity.addAttribute("x", "208");
        entity.addAttribute("y", "6");
    }

    public static void setAttributeProp(Element attribute) {
        attribute.addAttribute("accessStrategy", "");
        attribute.addAttribute("accesspower", "false");
        attribute.addAttribute("accesspowergroup", "");
        attribute.addAttribute("calculation", "false");
        //attribute.addAttribute("classID","");
        attribute.addAttribute("createIndustry", "0");
        //attribute.addAttribute("dataType","");
        attribute.addAttribute("dataTypeStyle", "SINGLE");
        //attribute.addAttribute("dbtype","char");
        attribute.addAttribute("defaultValue", "");
        attribute.addAttribute("description", "");
        //attribute.addAttribute("displayName","");
        attribute.addAttribute("dynamic", "false");
        attribute.addAttribute("dynamicTable", "");
        //attribute.addAttribute("fieldName","");
        //attribute.addAttribute("fieldType","");
        attribute.addAttribute("fixedLength", "false");
        attribute.addAttribute("forLocale", "false");
        attribute.addAttribute("help", "");
        //attribute.addAttribute("id","");
        attribute.addAttribute("industryChanged", "false");
        attribute.addAttribute("isActive", "true");
        attribute.addAttribute("isAuthorization", "true");
        attribute.addAttribute("isDefaultDimensionAttribute", "false");
        attribute.addAttribute("isDefaultMeasureAttribute", "false");
        attribute.addAttribute("isFeature", "false");
        attribute.addAttribute("isGlobalization", "false");
        attribute.addAttribute("isHide", "false");

        //attribute.addAttribute("isKey","");
        //attribute.addAttribute("isNullable","");
        attribute.addAttribute("isReadOnly", "false");
        attribute.addAttribute("isShare", "false");
        attribute.addAttribute("isSource", "true");
        //attribute.addAttribute("length","");
        attribute.addAttribute("maxValue", "");
        attribute.addAttribute("minValue", "");
        attribute.addAttribute("modifyIndustry", "0");
        //attribute.addAttribute("name","");
        attribute.addAttribute("notSerialize", "false");
        attribute.addAttribute("precise", "");
        attribute.addAttribute("refModelName", "");
        //attribute.addAttribute("resid","");
        //attribute.addAttribute("sequence","");
        //attribute.addAttribute("typeDisplayName","");
        attribute.addAttribute("typeDisplayName", "");
        attribute.addAttribute("typeName", "");
        attribute.addAttribute("versionType", "0");
        attribute.addAttribute("visibility", "public");
    }

    public static void setBusiInterface(Element entity) {
        Element busiitfs = entity.addElement("busiitfs");
        Element itfid = busiitfs.addElement("itfid");
        itfid.setText("6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        Element busimaps = entity.addElement("busimaps");
        Element busimap = entity.addElement("busimap");
        //busimap.addAttribute("attrid","b8831abc-09e2-4c55-bf16-d18dcfec6fdd");
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "5dd3c721-22ad-42b1-9c10-4351c236bc77");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        //busimap.addAttribute("cellid","e6c4dbbf-17e8-4fd4-9383-62fb17342c60");

        busimap = entity.addElement("busimap");
        busimap.addAttribute("attrid", "");
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "c8334364-7ab9-4266-8d4b-e74537935e4");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        //busimap.addAttribute("cellid","e6c4dbbf-17e8-4fd4-9383-62fb17342c60");

        busimap = entity.addElement("busimap");
        //busimap.addAttribute("attrid","b8831abc-09e2-4c55-bf16-d18dcfec6fdd");
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "ecf1b76a-6e44-42e2-a55e-87596504775b");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        //busimap.addAttribute("cellid","e6c4dbbf-17e8-4fd4-9383-62fb17342c60");


        busimap = entity.addElement("busimap");
        busimap.addAttribute("attrid", "");
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "d32cc17b-f415-415a-923f-0764443eb102");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        //busimap.addAttribute("cellid","e6c4dbbf-17e8-4fd4-9383-62fb17342c60");

        busimap = entity.addElement("busimap");
        busimap.addAttribute("attrid", "");
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "89578a97-42fe-439b-827c-8eabd9e3604c");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        //busimap.addAttribute("cellid","e6c4dbbf-17e8-4fd4-9383-62fb17342c60");
        busimap = entity.addElement("busimap");
        //busimap.addAttribute("attrid","b8831abc-09e2-4c55-bf16-d18dcfec6fdd");
        busimap.addAttribute("attrpath", "");
        busimap.addAttribute("attrpathid", "");
        busimap.addAttribute("busiitfattrid", "a47e6cda-09e4-480b-923f-ec6f41e3e06c");
        busimap.addAttribute("busiitfid", "6c8722b9-911a-489b-8d0d-18bd3734fcf6");
        //busimap.addAttribute("cellid","e6c4dbbf-17e8-4fd4-9383-62fb17342c60");
    }

    public static void setReferenceProp(Element Reference) {
        //Reference.addAttribute("componentID", "");
        Reference.addAttribute("createIndustry", "0");
        //Reference.addAttribute("createTime", "");
        Reference.addAttribute("creator", "");
        Reference.addAttribute("description", "");
        Reference.addAttribute("displayName", "displayName");
        Reference.addAttribute("fullClassName", "nc.vohrwa.pay.reference");
        Reference.addAttribute("height", "167");
        Reference.addAttribute("help", "");
        //Reference.addAttribute("id", "");
        Reference.addAttribute("industryChanged", "false");
        Reference.addAttribute("isSource", "false");
        Reference.addAttribute("mdFilePath", "\\meta\\general_interface.bmf");
        Reference.addAttribute("modifier", "");
        Reference.addAttribute("modifyIndustry", "0");

        Reference.addAttribute("modifyTime", "");
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
}
