/**
 * 封装解析业务类
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
            //创建一个解析XML的工厂对象
            SAXParserFactory parserFactory=SAXParserFactory.newInstance();
            //创建一个解析XML的对象
            SAXParser parser=parserFactory.newSAXParser();
            //创建一个解析助手类
            Myhandler myhandler=new Myhandler("row"); //标记开始的节点
            parser.parse(uri, myhandler);
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            
        }
        
        
    }
}