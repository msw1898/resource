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
public class CreateXml {
    public static void main(String[]args){
        Long start = System.currentTimeMillis();
        createXml();
        System.out.println("����ʱ�䣺"+ (System.currentTimeMillis() - start));
    }
    /**
     * ����xml����
     */
    public static void createXml(){
        try {
            // 1������document����
            Document document = DocumentHelper.createDocument();
            // 2���������ڵ�rss
            Element rss = document.addElement("rss");
            // 3����rss�ڵ����version����
            rss.addAttribute("version", "2.0");
            // 4�������ӽڵ㼰�ӽڵ�����
            Element channel = rss.addElement("channel");
            Element title = channel.addElement("title");
            title.setText("������������");
            // 5����������xml�ĸ�ʽ
            OutputFormat format = OutputFormat.createPrettyPrint();
            // ���ñ����ʽ
            format.setEncoding("UTF-8");

            String filePath = "C:\\Users\\Administrator\\Desktop\\��˰ͨ����\\�������ļ�\\payplan.xml";
            // 6������xml�ļ�
            File file = new File(filePath);
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            // �����Ƿ�ת�壬Ĭ��ʹ��ת���ַ�
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
            System.out.println("����rss.xml�ɹ�");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("����rss.xmlʧ��");
        }
    }
}
