/**
 * �������
 */
package com.xml.sax;


import config.config_;
import config.tools;


public class XmlSax_Test {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	
//        	SaxService.ReadXML(config_.posts_dir,"posts");//������
        	
    		tools.readindex_SO(config_.indexPath_posts);//������
    		
//    		tools.Count_readindex_SO(config_.indexPath_posts);//���� �鿴Post�а����˶���Android��posts
    }
    

}