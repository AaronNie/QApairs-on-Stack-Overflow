/**
 * 程序入口
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
    	
//        	SaxService.ReadXML(config_.posts_dir,"posts");//建索引
        	
    		tools.readindex_SO(config_.indexPath_posts);//读索引
    		
//    		tools.Count_readindex_SO(config_.indexPath_posts);//计数 查看Post中包含了多少Android的posts
    }
    

}