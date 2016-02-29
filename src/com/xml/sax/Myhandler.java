/**
 * ��SAX����XML��Handler
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
    //�洢���ڽ�����Ԫ�ص�����
    private posts post=null;
    
    //��ʼ������Ԫ��
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

    //��ʼ�����ĵ�������ʼ����XML��Ԫ��ʱ���ø÷���
    @Override
    public void startDocument() throws SAXException {
    	try {
    	  Directory dir = FSDirectory.open(new File(indexPath));//���������ļ���Ŀ¼��
	      Analyzer analyzer = new StandardAnalyzer();//����һ����׼������
	      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);//����������
	      iwc.setRAMBufferSizeMB(1024.0);//���ӻ�������
	       writer = new IndexWriter(dir, iwc);
    	 } catch (IOException e) {
		      System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		    }
    	
    }
    
    //��ʼ����ÿ��Ԫ��ʱ������ø÷���
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        //�ж����ڽ�����Ԫ���ǲ��ǿ�ʼ������Ԫ��
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
    
    //������ÿ��Ԫ�ص�����ʱ����ô˷���
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }
    
    //ÿ��Ԫ�ؽ�����ʱ�򶼻���ø÷���
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
    	
    	
        //�ж��Ƿ�Ϊһ���ڵ������Ԫ�ر�ǩ
        if(qName.equals(nodeName)){
            //�����д�뵽�����У���ʾÿһ�е�Post
        	Document doc = new Document();
	        //������StringField��ʾ����������
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
	        
	        //������TextField��ʾ�����ִ�֮��ģ�������
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
    
    //���������ĵ�����������Ԫ�ؽ�����ǩʱ���ø÷���
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