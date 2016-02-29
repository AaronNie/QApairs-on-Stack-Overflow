/**
 * ��װ����ҵ����
 */
package com.xml.sax;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class SaxService {
    
    public static void ReadXML(String uri,String NodeName){
        try {
            //����һ������XML�Ĺ�������
            SAXParserFactory parserFactory=SAXParserFactory.newInstance();
            //����һ������XML�Ķ���
            SAXParser parser=parserFactory.newSAXParser();
            //����һ������������
            Myhandler myhandler=new Myhandler("row"); //��ǿ�ʼ�Ľڵ�
            parser.parse(uri, myhandler);
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            
        }
        
        
    }
}