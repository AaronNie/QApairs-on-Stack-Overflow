/**
 * 用SAX解析XML的Handler
 */
package com.xml.sax;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.dom4j.DocumentException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import config.config_;



public class Myhandler extends DefaultHandler {
    //存储正在解析的元素的数据
    private posts post=null;
    
    //开始解析的元素
    String nodeName=null;
    String indexPath=config_.indexPath_posts;
    IndexWriter writer =null;
    
    String Id=null;
	String PostTypeId=null;
	String ParentId=null;
	String AcceptedAnswerId=null;
	String Score=null;
	String ViewCount=null;
	String Body=null;
	String OwnerUserId=null;
	String Title=null;
	String Tags=null;
	String AnswerCount=null;
	String CommentCount=null;
	String FavoriteCount=null;
	String LastEditorUserId=null;
    

    public Myhandler(String nodeName) {
        this.nodeName=nodeName;
    }

    //开始解析文档，即开始解析XML根元素时调用该方法
    @Override
    public void startDocument() throws SAXException {
    	try {
    	  Directory dir = FSDirectory.open(new File(indexPath));//保存索引文件的目录打开
	      Analyzer analyzer = new StandardAnalyzer();//构建一个标准分析器
	      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);//参数管理器
	      iwc.setRAMBufferSizeMB(1024.0);//增加缓存容量
	       writer = new IndexWriter(dir, iwc);
    	 } catch (IOException e) {
		      System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		    }
    	
    }
    
    //开始解析每个元素时都会调用该方法
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        //判断正在解析的元素是不是开始解析的元素
//        if(qName.equals(nodeName)){
//        	 post=new posts();
//        }
        
        
        if(qName.equals(nodeName)&&attributes!=null){
        	Id=attributes.getValue("Id");
        	System.out.println(Id);
        	PostTypeId=attributes.getValue("PostTypeId");
        	ParentId=attributes.getValue("ParentId");
        	AcceptedAnswerId=attributes.getValue("AcceptedAnswerId");
        	Score=attributes.getValue("Score");
        	ViewCount=attributes.getValue("ViewCount");
        	Body=attributes.getValue("Body");
        	OwnerUserId=attributes.getValue("OwnerUserId");
        	LastEditorUserId=attributes.getValue("LastEditorUserId");
        	Title=attributes.getValue("Title");
        	Tags=attributes.getValue("Tags");
        	AnswerCount=attributes.getValue("AnswerCount");
        	CommentCount=attributes.getValue("CommentCount");
        	FavoriteCount=attributes.getValue("FavoriteCount");
        }
        
    }
    
    //解析到每个元素的内容时会调用此方法
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }
    
    //每个元素结束的时候都会调用该方法
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
    	
    	
        //判断是否为一个节点结束的元素标签
        if(qName.equals(nodeName)){
            //把这个写入到索引中，表示每一行的Post
        	Document doc = new Document();
	        //索引，StringField表示不处理，保存
	        doc.add(new StringField("Id", Id, Field.Store.YES));
	        if(null!=PostTypeId)
	        doc.add(new StringField("PostTypeId", PostTypeId, Field.Store.YES));
	        if(null!=ParentId)
	        	doc.add(new StringField("ParentId", ParentId, Field.Store.YES));
//	        else
//	        	doc.add(new StringField("ParentId", "null", Field.Store.YES));
	        if(null!=AcceptedAnswerId)
	        	doc.add(new StringField("AcceptedAnswerId", AcceptedAnswerId, Field.Store.YES));
//	        else
//	        	doc.add(new StringField("AcceptedAnswerId", "null", Field.Store.YES));
	        if(null!=Score)
	        doc.add(new StringField("Score", Score, Field.Store.YES));
	        
	        if(null!=ViewCount)
	        doc.add(new StringField("ViewCount", ViewCount, Field.Store.YES));
	        if(null!=OwnerUserId)
	        doc.add(new StringField("OwnerUserId", OwnerUserId, Field.Store.YES));
	        if(null!=AnswerCount)
	        doc.add(new StringField("AnswerCount", AnswerCount, Field.Store.YES));
	        if(null!=CommentCount)
	        doc.add(new StringField("CommentCount", CommentCount, Field.Store.YES));
	        if(null!=FavoriteCount)
	        doc.add(new StringField("FavoriteCount", FavoriteCount, Field.Store.YES));
	        if(null!=LastEditorUserId)
	        doc.add(new StringField("LastEditorUserId", LastEditorUserId, Field.Store.YES));
	        
	        //索引，TextField表示处理（分词之类的），保存
	        if(null!=Tags)
	        doc.add(new TextField("Tags", Tags, Field.Store.YES));
	        if(null!=Title)
	        doc.add(new TextField("Title", Title, Field.Store.YES));
	        if(null!=Body)
	        doc.add(new TextField("Body", Body, Field.Store.YES));
			
	        try {
				writer.addDocument(doc);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
	        
        	
//            try {
//				IndexPosts_SO.Indexposts(post,config_.indexPath_posts);
//			} catch (DocumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
            
            post=null;
        }
    }
    
    //结束解析文档，即解析根元素结束标签时调用该方法
    @Override
    public void endDocument() throws SAXException {
    	 try {
			writer.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        super.endDocument();
    }
}