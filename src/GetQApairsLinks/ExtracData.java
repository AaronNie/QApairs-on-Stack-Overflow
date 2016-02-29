package GetQApairsLinks;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

import config.config_;

//读取post【index】中的数据
//对每个查询，先判断它是否是关于android的，

/* 【分析】
当时面临的一个问题，是:
	如果只遍历索引中的查询，怎么找到它对应的回答？【不选用】
	如果针对每个问题都遍历一遍索引，则会耗费较长的时间。
	因为查询本身就很多
	
	如果每个post都遍历，怎么建立问题和回答之间的联系【选用】
	那就需要在内存中建立一个索引表，来管理查询和回答之间的联系，而无法完全保存这些内容。
	不对，可以只建立一个post ID联系的表。这样的话，我只遍历一遍所有的问题就可以构建查询和回答之间的联系了。
	(查询和回答之间的联系其实是已经存在。) 有了联系之后，我就可以根据链表关系一边读一遍写了。
	
	约束：不可能把所有的信息都读入到内存中。只能一个问题一个问题进行处理。
	
	一些情况：回答中不一定有tag标签
	查询中只记录了最佳答案
	回答中记录了问题的编号
*/


/*
 * 步骤如下：
 * 1.遍历索引中的每个文档，判断是否是查询，如果是查询，
 * 
 * 2.如果是查询，同时，标签中包含"android"，就在Map中添加一个数据对象（先叫问题对象），记录该问题的编号以及标准答案的编号，同时把其它答案的编号置空。
 * 每个问题对象的格式 Map <q_id,<accept_id,<other_id1, other_id2...>>>(这个对我来说是难点)
 * 可否不使用复杂的结构？
 * 对某个查询比如 id=5，如果先读到它本身，则能看到它的accept_id=3, 如果直接写 就是 “5 & 3 & _空_” 其它回答的id未知
 * 如果先读到的是这个查询的一个回答，比如 4，则 能写成 5 & _空_ & 4. 那如果在读到查询本身，就需要再次改写这句话，把空的地方补上。
 * 很明显，这里需要动态的查询与修改，适合的数据结构是 Map.
 *，
 * 
 * 3.如果是问答，把当前Post的 id 作为对应问题对象的一个 other_id(i)
 * 
 * 4.输出问题对象到文本文件中。
 * 
 * 5.根据文本文件的内容，对每个问题对象关联的问题和回答的id进行搜索，找到对应的内容，依次写入到文本文件中。
 */


public class ExtracData {
	
	
	
	public static void getIDLinks(String index_dir) throws Exception 
	  {	
		IDs ids;
		LinkedHashMap<String, IDs> lhm_idLinks = new LinkedHashMap<String, IDs> ();
		
	    IndexReader reader_ = DirectoryReader.open(FSDirectory.open(new File(index_dir)));
	    IndexSearcher searcher_ = new IndexSearcher(reader_);
	    Document doc = null;  
	    String AcceptedAnswerId=null;
	    String Id=null;//问题的Id
    	String PostTypeId_q=null;
    	String Tags=null;
    	String ParentId=null;
    	
	    System.out.println("索引中包含的post个数"+reader_.maxDoc());
	    
        for (int i = 0; i < reader_.maxDoc(); i++) {
            doc = searcher_.doc(i);  
            PostTypeId_q=doc.get("PostTypeId");//获取它的类型:1代表提问，2代表回答
            Tags= doc.get("Tags");//当前查询所属的类别，标签
            Id= doc.get("Id");//这个是提问(post)的Id
            
            System.out.println("Doc [" + i + "] :" +" PostTypeId:"+doc.get("PostTypeId")+" ParentId:"+doc.get("ParentId")+" Id:"+doc.get("Id"));  
            
            if(PostTypeId_q.equals("1") && !Tags.toLowerCase().contains("android"))//如果它是提问,且是关于ANDROID
            	{
            		AcceptedAnswerId= doc.get("AcceptedAnswerId");//获取这个提问认可的回答(post)的Id
            		System.out.println("AcceptedAnswerId:"+AcceptedAnswerId);
        			if(AcceptedAnswerId!=null )
            		{
        				//判断map中是否包含了这个ID,应该是没有重复的，这个操作有点多余
//        				if(!lhm_idLinks.containsKey(Id)) 
        				{
        					ids = new IDs();
        					ids.accept_id=AcceptedAnswerId;   
//        					ids.other_ids.add(Id);//是查询时，这个为空
        					lhm_idLinks.put(Id,ids);
        				}
            		}
            	}
            else if (PostTypeId_q.equals("2")) // 如果读到的是回答
            	{
            		ParentId = doc.get("ParentId"); //得到回答对应的问题
            		if(!lhm_idLinks.containsKey(ParentId))
            		{
            			ids = new IDs();
//    					ids.accept_id=AcceptedAnswerId;//是回答时，这个为空
            			ids.other_ids.add(Id);
    					lhm_idLinks.put(ParentId,ids);//ParentId作为主id,accept_id为空，other_id增加一个Id
            		}
            		else //如果中间已经保存一个这个回答 对应的 问题对象，就在这个对象中添加当前回答的id
            		{
            			ids = lhm_idLinks.get(ParentId);
            			
            			System.out.println(ids.accept_id); //如何判断当前的值为空
            			if (ids.accept_id==null)
            			{
            				ids.other_ids.add(Id);
            			}
            			else if(!ids.accept_id.equals(Id))
            				{
            					ids.other_ids.add(Id);
            				}
            			
            			
            			
            			lhm_idLinks.remove(ParentId); //先去掉旧的，
            			lhm_idLinks.put(ParentId, ids);//再添加上新的
            		}
            	}
            
            System.out.println("------------------------------------------------------");
        }
        reader_.close();
        
        
        System.out.println(lhm_idLinks.size());
        
        //得到id link之后，写入到文本中。
        PrintWriter bw2=new PrintWriter(new FileWriter(new File(config_.IDsLinks_dir)));
        
        for (String key : lhm_idLinks.keySet()) {
        	IDs ids_ = lhm_idLinks.get(key);
            bw2.write("Key = " + key + ", accept_id = " + ids_.accept_id+ ", other_ids = "+ids_.other_ids+"\n");//在文本中显示当前的查询内容
        }
			
		bw2.close();
			
        for (String key : lhm_idLinks.keySet()) {
        	IDs ids_ = lhm_idLinks.get(key);
            System.out.println("Key = " + key + ", accept_id = " + ids_.accept_id+ ", other_ids = "+ids_.other_ids);
        }
        
	 }
	
	//TEST
	public static void main(String[] args) throws Exception {
		
		ExtracData.getIDLinks(config_.indexPath_posts);
	}
}



