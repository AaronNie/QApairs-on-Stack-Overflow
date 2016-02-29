package config;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xml.sax.posts;

public class tools {
		
	public static LinkedList<String> splitCamelCaseString2(String s) {
		LinkedList<String> resultlist = new LinkedList<String>();
		if(s.isEmpty())
		{
			return resultlist;
		}
		StringBuilder word = new StringBuilder();
		char[] buf = s.toCharArray();
		boolean prevIsupper=false;
		for(int i=0;i<buf.length;i++)
		{
			char ch= buf[i];
			if(Character.isUpperCase(ch))
			{
				if(i==0)
				{
					word.append(ch);
				}else if(!prevIsupper)
				{
					resultlist.add(word.toString());
					word=new StringBuilder();
					word.append(ch);
				}else
				{
					word.append(ch);
				}
				prevIsupper=true;
			}else
			{
				word.append(ch);
				prevIsupper=false;
			}
		}
		if(word!=null&&word.length()>0)
		{
			resultlist.add(word.toString());
		}
		
		
		return resultlist;
	}
		
	public static String ReadTextFile(String Snippetpath)
	  {
	  	File JavaFilenamepath_ = new File(Snippetpath);
	  	String javafile_content="";
	  	if(JavaFilenamepath_.canRead())
	  	{
		    	byte[] input = null;
				try {
				    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(Snippetpath));
				    input = new byte[bufferedInputStream.available()];
			          bufferedInputStream.read(input);
			          bufferedInputStream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				javafile_content=new String(input);
	  }
		return javafile_content;
		
	  }
		
	//直接读取一个文本文件
	public static String ReadText(String text_dir) throws IOException
	  {
		String ss = null;
	  	File text_file = new File(text_dir);
	  	BufferedReader br=new BufferedReader(new InputStreamReader(new  FileInputStream(text_file),"UTF-8")); 
	  	String line="";
		while((line=br.readLine())!=null)
		{
			if(line.startsWith("  <row Id="))
				{
					System.out.println(line);
					ss=line;
					break;
				}
		}
		br.close();
	  	
		return ss;
	  }


	
		
	
	//按照树的方式来读text文件比较慢
	public static String read_textInfo_from_XML(File xmlfile) throws IOException, DocumentException
	{
//		HashMap<String,String> name_value=new HashMap<String,String>();		
		String Id_text="";
		SAXReader reader = new SAXReader();
		org.dom4j.Document document = reader.read(xmlfile);
		Element rootElement=document.getRootElement();
		
		Iterator<Element> itemIterator = rootElement.elements("string").iterator();

		 while (itemIterator.hasNext()) 
			 {  
				Element itemElement = (Element) itemIterator.next();	
//				String name_text=itemElement.attributeValue("name");
				String value_text=itemElement.getText();
//				name_value.put(name_text,value_text);
				Id_text=Id_text+" "+value_text;
			 }

		 return Id_text;			 
	}
	
	public static posts read_textInfo_from_StringXML(File xmlfile) throws DocumentException {
		
		posts post=new posts();
		
		SAXReader reader = new SAXReader();
		org.dom4j.Document document = reader.read(xmlfile);
		Element rootElement=document.getRootElement();
		Iterator<Element> itemIterator = rootElement.elements("row").iterator();
		
		
		 while (itemIterator.hasNext()) 
			 {  
			 	
				Element itemElement = (Element) itemIterator.next();
				
				String Id=itemElement.attributeValue("Id");
//				System.out.println(Id);
				post.setId(Id);
				String PostTypeId=itemElement.attributeValue("PostTypeId");
				System.out.println(PostTypeId);
				post.setPostTypeId(PostTypeId);
				String ParentId=itemElement.attributeValue("ParentId");
//				System.out.println(ParentId);
				if(null!=ParentId)
				{
					post.setParentId(ParentId);
				}
				String AcceptedAnswerId=itemElement.attributeValue("AcceptedAnswerId");
				System.out.println(AcceptedAnswerId);
				post.setAcceptedAnswerId(AcceptedAnswerId);
				String Score=itemElement.attributeValue("Score");
//				System.out.println(Score);
				post.setScore(Score);
				String ViewCount=itemElement.attributeValue("ViewCount");
//				System.out.println(ViewCount);
				post.setViewCount(ViewCount);
				String Body=itemElement.attributeValue("Body");
//				System.out.println(Body);
				post.setBody(Body);
				String OwnerUserId=itemElement.attributeValue("OwnerUserId");
//				System.out.println(OwnerUserId);
				post.setOwnerUserId(OwnerUserId);
				String Title=itemElement.attributeValue("Title");
//				System.out.println(Title);
				post.setTitle(Title);
				String Tags=itemElement.attributeValue("Tags");
//				System.out.println(Tags);
				post.setTags(Tags);
				String AnswerCount=itemElement.attributeValue("AnswerCount");
//				System.out.println(AnswerCount);
				post.setAnswerCount(AnswerCount);
				String CommentCount=itemElement.attributeValue("CommentCount");
//				System.out.println(CommentCount);
				post.setCommentCount(CommentCount);
				String FavoriteCount=itemElement.attributeValue("FavoriteCount");
//				System.out.println(FavoriteCount);
				post.setFavoriteCount(FavoriteCount);
				String LastEditorUserId=itemElement.attributeValue("LastEditorUserId");
//				System.out.println(LastEditorUserId);
				post.setLastEditorUserId(LastEditorUserId);
				break;
			 }

		 return post;
	}
	
	
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% evaluate 结果的评估参数
	public static Float caculate_rankBest_5(ArrayList<Integer> al)
			{
				Integer best= 0;
				for(int i=0;i<5;i++)
					{
					 if(al.get(i)>=3)
					 {
						 best=i+1;
						 break;
					 }
					}
				if(best==0)
					return (float) 0;
				else
					return (float) (1/(float)best);
			}
	public static Integer caculate_rankBest_10(ArrayList<Integer> al)
			{
				Integer best= 0;
				for(int i=0;i<al.size();i++)
					{
					 if(al.get(i)>=3)
					 {
						 best=i+1;
						 break;
					 }
					}
//				System.out.println(best);
				
//				if(best==0)
//					return (float) 0;
//				else
//				return (float) (1/(float)best);
				
				return best;
			}
			//计算ndcg_10 	//计算ndcg，输入是一个linkedHashSet,得到一个值
	public static Float caculate_NDCG_10(ArrayList<Integer> al)
				{
					Float dcg=(float)al.get(0);
					for(int i=1;i<al.size();i++)
						{
							dcg=(float) (dcg+(float)al.get(i)*Math.log(2)/Math.log(i+1));
						}
//					System.out.println(dcg);
					
					Collections.sort(al);
					Collections.reverse(al);
					
					Float idcg=(float)al.get(0);
					for(int i=1;i<al.size();i++)
					{
						idcg=(float) (idcg+(float)al.get(i)*Math.log(2)/Math.log(i+1));
					}
//					System.out.println(idcg);
					
					if(idcg!=0.0)
						return (float) (dcg/idcg);
					else 
						return (float) 0;
				}
				
	public static Float caculate_NDCG_5(ArrayList<Integer> al)
				{
					ArrayList<Integer> a2 = new ArrayList<Integer>();
					Float dcg=(float)al.get(0);
					a2.add(al.get(0));
					for(int i=1;i<5;i++)
						{
							dcg=(float) (dcg+(float)al.get(i)*Math.log(2)/Math.log(i+1));
							a2.add(al.get(i));
						}
//					System.out.println(a2.size());
					
					Collections.sort(a2);
					Collections.reverse(a2);
					
					Float idcg=(float)a2.get(0);
					for(int i=1;i<a2.size();i++)
					{
						idcg=(float) (idcg+(float)a2.get(i)*Math.log(2)/Math.log(i+1));
					}
//					System.out.println(idcg);
					
					if(idcg!=0.0)
						return (float) (dcg/idcg);
					else 
						return (float) 0;
				}
				
	
	
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public static void doPagingSearchforSnippets(IndexSearcher searcher_s, Query query, boolean raw, Integer num, Filter filter,LinkedHashMap<String,Float> LHM_snippets) throws IOException {
			 
		    // Collect enough docs to show 5 pages 获取足够填充5个页面的文档，每个页面显示多少个
		    TopDocs results = searcher_s.search(query,filter,num);//已经获取结果，下面只是对获取的结果进行分页处理。可以按照自己的意愿来进行相关内容的调整
		    ScoreDoc[] hits = results.scoreDocs; //命中的文档集合

		    int numTotalHits = results.totalHits;//命中的文档的个数
//		    System.out.println(numTotalHits + " total matching snippets");
		    
		    int start = 0;
		    int end = Math.min(numTotalHits, num);
		    HashSet<String> hs_appname=new HashSet<String>();
		    
		    abc: while (true) {
		      for (int i = start; i < end; i++) {
		        if (raw) {                              // output raw format 输出未经处理的格式
		          System.out.println("doc="+hits[i].doc+" score="+hits[i].score);//输出文档的编号，以及该文档对应得分
		          continue;
		        }
		        
		        
		      //以第i个答案的文档内容作为输入，找到其文档的其它信息
		        Document doc = searcher_s.doc(hits[i].doc); //根据文档的编号得到文档的对象。
		        String methodName = doc.get("methodName");//获取检索到的文件的名字
		        if (methodName != null) {
////		          System.out.println((i+1) + ". " + methodName);
//		          //输出文档所在的应用
//		          System.out.println("   packagename: " + doc.get("packageName"));
//		          
//		          //输出文档的代码部分
////		          String code = doc.get("Code");//获取检索到的代码，但是丢失了原来的格式，需要再次读取原来的文件
//		          String snippet=ReadTextFile(method_CodeSnippets+methodName);//读取代码的所有内容
//		          if (snippet != null) {
////		            System.out.println("   Code: \n" + snippet);
//		          }
//		          
//		          //输出文档的得分
////		          System.out.println("   score: " + hits[i].score);
//		          
//		        	if(!LHM_snippets.containsValue(doc.get("packageName")))
//		        	{
//		        		LHM_snippets.put(methodName, doc.get("packageName")); //得到top10的代码段的名字 及所在的应用名
//		        		if(LHM_snippets.size()==10)
//		        			break abc;
//		        	}
		        	
		        	
//		        	if(!hs_appname.contains(doc.get("packageName")))//保证来自同一个应用的代码段只有一个
//		        	{
//		        		hs_appname.add(doc.get("packageName"));
		        		LHM_snippets.put(methodName, hits[i].score); //得到top10的代码段的名字 及代码段的相似度得分【没有 去掉重复项目】
//		        		if(LHM_snippets.size()==10)
//		        			break abc;
//		        	}
		        	
		          
		        } else {
		          System.out.println((i+1) + ". " + "No path for this document");
		        }
		                  
		      }
		        break;
		    }
		  }
		  
	public static void doPagingSearchforApps (IndexSearcher searcher, Query query, 
	             Integer num,LinkedHashMap<String,Float> LHM_apps_score) throws IOException {

				TopDocs results = searcher.search(query, num);//已经获取结果，下面只是对获取的结果进行分页处理。可以按照自己的意愿来进行相关内容的调整
				ScoreDoc[] hits = results.scoreDocs; //命中的文档集合
				
				//output
				int numTotalHits = results.totalHits;//命中的文档的个数
				System.out.println(numTotalHits + " total matching apps");
				
				
				int start = 0;
				int end = Math.min(numTotalHits, num);
				while (true) {
				for (int i = start; i < end; i++) {
				//以第i个答案的文档内容作为输入，找到其文档的其它信息
				Document doc = searcher.doc(hits[i].doc);
				String packagename_versionCode = doc.get("packagename_versionCode");//获取该文档的路径信息
				if (packagename_versionCode != null) {
				
				////System.out.println((i+1) + ". " + packagename_versionCode);
				//
				//String packagename = doc.get("packagename");//获取该文档的标题
				//if (packagename != null) {
				////System.out.println("   packagename: " + doc.get("packagename"));
				//}
				//
				//String description = doc.get("description");//获取该文档的标题
				//if (description != null) {
				////System.out.println("   description: " + doc.get("description"));
				//}
				//
				//String textInfo = doc.get("textInfo");//获取该文档的标题
				//if (textInfo != null) {
				////System.out.println("   textInfo: " + doc.get("textInfo"));
				//}
				//
//				System.out.println("   score: " +hits[i].score); 
				//
				//把得到的应用列表放到一个Set中
					LHM_apps_score.put(packagename_versionCode, hits[i].score);//应用的名字以及应用匹配信息与查询的相似度
//				LHS_apps.add(packagename_versionCode);
				
				} else {
				System.out.println((i+1) + ". " + "No path for this document");
				}
				
				}
				
				break;
				
				}
				
				}

	
	
	
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//遍历索引中的每个文档
	public static void readindex_SO(String index_dir) throws Exception 
	  {
		    IndexReader reader_ = DirectoryReader.open(FSDirectory.open(new File(index_dir)));
		    IndexSearcher searcher_ = new IndexSearcher(reader_);
		    Document doc = null;  
		    System.out.println("索引中包含的post个数"+reader_.maxDoc());
	        for (int i = 0; i < 10; i++) {  //只输出前10个
	            doc = searcher_.doc(i);  
	            System.out.println("Doc [" + i + "] : Body: " + doc.get("Body")+" PostTypeId:"+doc.get("PostTypeId")+" ParentId:"+doc.get("ParentId")+" Id:"+doc.get("Id"));  
	            System.out.println("------------------------------------------------------");
	        }
	        reader_.close();
	    }
	
	//遍历索引中的每个文档
	public static void Count_readindex_SO(String index_dir) throws Exception 
	  {
		    int count=0;
		    IndexReader reader_ = DirectoryReader.open(FSDirectory.open(new File(index_dir)));
		    IndexSearcher searcher_ = new IndexSearcher(reader_);
		    Document doc = null;  
		    System.out.println("索引中包含的post个数"+reader_.maxDoc());
	        for (int i = 0; i < reader_.maxDoc(); i++) {  //只输出前10个
	            doc = searcher_.doc(i);
	            
	            if((doc.get("Body")!=null)&&(doc.get("Body").contains("Android")))//包含了Android的
	            	System.out.println(count);
	            	count=count+1;
	        }
	        reader_.close();
	        System.out.println(count);
	    }
	
	  public static String Searchindex(String query_, String index_) throws Exception 
	  {
		  String content="";
		  String field="Id";
		  IndexReader reader_ = DirectoryReader.open(FSDirectory.open(new File(index_)));
		  IndexSearcher searcher_ = new IndexSearcher(reader_);
		  Analyzer analyzer = new StandardAnalyzer();
		  //在单个域上进行查询
		  QueryParser parser = new QueryParser(field, analyzer);
		  Query q = parser.parse(query_);
		  TopDocs results = searcher_.search(q,1);
		  ScoreDoc[] hits = results.scoreDocs;
		  Document doc = searcher_.doc(hits[0].doc);
		  
		  String Id = doc.get("Id");//获取该文档的路径信息
	      String Body= doc.get("Body");
	      String Title= doc.get("Title");
	      String Tags= doc.get("Tags");
	      
//		  System.out.println(" Id:"+ Id);
		  content=content+" "+Body+" "+Title+" "+Tags;
//		  System.out.println(" content:"+ content);
	    return content;
	  }
	  
	  //用于结果的输出及查看 
	  //输入是根据查询推荐的问答对：问题标号 答案标号 - 问题%%%答案
	  public static void Searchindex_pair(LinkedHashMap<String,Float> lhm_orderBySimAndScore, String index_) throws Exception 
	  {
		  String field="lable";
		  IndexReader reader_ = DirectoryReader.open(FSDirectory.open(new File(index_)));
		  IndexSearcher searcher_ = new IndexSearcher(reader_);
		  Analyzer analyzer = new StandardAnalyzer();
		  
		  int k=0;
		  for (Entry<String, Float> entry: lhm_orderBySimAndScore.entrySet()) {
				String lable = entry.getKey();
//				System.out.println(" lable:"+ lable);
				
				QueryParser parser = new QueryParser(field, analyzer);
				Query q = parser.parse(lable);
				TopDocs results = searcher_.search(q,1);
				ScoreDoc[] hits = results.scoreDocs;
//				System.out.println(hits.length);
				Document doc = searcher_.doc(hits[0].doc);
				String content = doc.get("content");//获取该文档的路径信息
				System.out.println(" lable:"+ lable+" content:"+ content);
				
				k=k+1;
				if(k==10)
					break;
			}
	  }
//找score的最大最小值
	  public static String findMinMax(String file_dir) throws Exception{
		  Float min=(float) 10000;
		  Float max=(float) -10000;
		SAXReader reader = new SAXReader();
		File xmlfile =new File(file_dir);
		org.dom4j.Document document = reader.read(xmlfile);
		Element rootElement=document.getRootElement();
		Iterator<Element> itemIterator = rootElement.elements("row").iterator();
		//每一行产生一个document 写入一次数据库
		 while (itemIterator.hasNext()) 
		 {
			 	//get the content of each line
			 	Element itemElement = (Element) itemIterator.next();
				Float Score=Float.valueOf(itemElement.attributeValue("Score"));
//				System.out.println(Score);
				if(Score>max)
				{
					max=Score;
				}
				else if(Score<min)
				{
					min=Score;
				}
				
		 }
		 String minMax=min.toString()+" "+max.toString();
		 System.out.println(minMax);
		 return minMax;
	  }

//遍历indexPairs
		public static void readindexPairs(String index_dir) throws Exception 
		  {
			    IndexReader reader_ = DirectoryReader.open(FSDirectory.open(new File(index_dir)));
			    IndexSearcher searcher_ = new IndexSearcher(reader_);
			    Document doc = null;  
		        for (int i = 0; i < reader_.maxDoc(); i++) {  
		            doc = searcher_.doc(i);  
		            System.out.println("Doc [" + i + "] : lable: " + doc.get("lable") + ", content: " + doc.get("content"));  
		        }
		        reader_.close();
		    }

}
