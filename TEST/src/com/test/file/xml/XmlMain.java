package com.test.file.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * @program: ncc
 * @description:
 * @author: zxb
 * @create: 2020-12-24 20:57
 **/
public class XmlMain {
        public static void main(String[] args) throws Exception {
            String filePath = "C:\\Users\\Administrator\\Desktop\\��˰ͨ����\\�������ļ�\\payplan.bmf";
            //1.����Reader����
            SAXReader reader = new SAXReader();
            //2.����xml
            Document document = reader.read(new File(filePath));
            //3.��ȡ���ڵ�
            Element rootElement = document.getRootElement();
            Iterator iterator = rootElement.elementIterator();
            while (iterator.hasNext()){
                Element stu = (Element) iterator.next();
                List<Attribute> attributes = stu.attributes();
                System.out.println("======��ȡ����ֵ======");
                for (Attribute attribute : attributes) {
                    System.out.println(attribute.getValue());
                }
                System.out.println("======�����ӽڵ�======");
                Iterator iterator1 = stu.elementIterator();
                while (iterator1.hasNext()){
                    Element stuChild = (Element) iterator1.next();
                    System.out.println("�ڵ�����"+stuChild.getName()+"---�ڵ�ֵ��"+stuChild.getStringValue());
                }
            }
        }
}
