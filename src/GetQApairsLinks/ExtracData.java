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

//��ȡpost��index���е�����
//��ÿ����ѯ�����ж����Ƿ��ǹ���android�ģ�

/* ��������
��ʱ���ٵ�һ�����⣬��:
	���ֻ���������еĲ�ѯ����ô�ҵ�����Ӧ�Ļش𣿡���ѡ�á�
	������ÿ�����ⶼ����һ�����������ķѽϳ���ʱ�䡣
	��Ϊ��ѯ����ͺܶ�
	
	���ÿ��post����������ô��������ͻش�֮�����ϵ��ѡ�á�
	�Ǿ���Ҫ���ڴ��н���һ���������������ѯ�ͻش�֮�����ϵ�����޷���ȫ������Щ���ݡ�
	���ԣ�����ֻ����һ��post ID��ϵ�ı������Ļ�����ֻ����һ�����е�����Ϳ��Թ�����ѯ�ͻش�֮�����ϵ�ˡ�
	(��ѯ�ͻش�֮�����ϵ��ʵ���Ѿ����ڡ�) ������ϵ֮���ҾͿ��Ը��������ϵһ�߶�һ��д�ˡ�
	
	Լ���������ܰ����е���Ϣ�����뵽�ڴ��С�ֻ��һ������һ��������д���
	
	һЩ������ش��в�һ����tag��ǩ
	��ѯ��ֻ��¼����Ѵ�
	�ش��м�¼������ı��
*/


/*
 * �������£�
 * 1.���������е�ÿ���ĵ����ж��Ƿ��ǲ�ѯ������ǲ�ѯ��
 * 
 * 2.����ǲ�ѯ��ͬʱ����ǩ�а���"android"������Map�����һ�����ݶ����Ƚ�������󣩣���¼������ı���Լ���׼�𰸵ı�ţ�ͬʱ�������𰸵ı���ÿա�
 * ÿ���������ĸ�ʽ Map <q_id,<accept_id,<other_id1, other_id2...>>>(���������˵���ѵ�)
 * �ɷ�ʹ�ø��ӵĽṹ��
 * ��ĳ����ѯ���� id=5������ȶ������������ܿ�������accept_id=3, ���ֱ��д ���� ��5 & 3 & _��_�� �����ش��idδ֪
 * ����ȶ������������ѯ��һ���ش𣬱��� 4���� ��д�� 5 & _��_ & 4. ������ڶ�����ѯ��������Ҫ�ٴθ�д��仰���ѿյĵط����ϡ�
 * �����ԣ�������Ҫ��̬�Ĳ�ѯ���޸ģ��ʺϵ����ݽṹ�� Map.
 *��
 * 
 * 3.������ʴ𣬰ѵ�ǰPost�� id ��Ϊ��Ӧ��������һ�� other_id(i)
 * 
 * 4.�����������ı��ļ��С�
 * 
 * 5.�����ı��ļ������ݣ���ÿ������������������ͻش��id�����������ҵ���Ӧ�����ݣ�����д�뵽�ı��ļ��С�
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
	    String Id=null;//�����Id
    	String PostTypeId_q=null;
    	String Tags=null;
    	String ParentId=null;
    	
	    System.out.println("�����а�����post����"+reader_.maxDoc());
	    
        for (int i = 0; i < reader_.maxDoc(); i++) {
            doc = searcher_.doc(i);  
            PostTypeId_q=doc.get("PostTypeId");//��ȡ��������:1�������ʣ�2����ش�
            Tags= doc.get("Tags");//��ǰ��ѯ��������𣬱�ǩ
            Id= doc.get("Id");//���������(post)��Id
            
            System.out.println("Doc [" + i + "] :" +" PostTypeId:"+doc.get("PostTypeId")+" ParentId:"+doc.get("ParentId")+" Id:"+doc.get("Id"));  
            
            if(PostTypeId_q.equals("1") && !Tags.toLowerCase().contains("android"))//�����������,���ǹ���ANDROID
            	{
            		AcceptedAnswerId= doc.get("AcceptedAnswerId");//��ȡ��������ϿɵĻش�(post)��Id
            		System.out.println("AcceptedAnswerId:"+AcceptedAnswerId);
        			if(AcceptedAnswerId!=null )
            		{
        				//�ж�map���Ƿ���������ID,Ӧ����û���ظ��ģ���������е����
//        				if(!lhm_idLinks.containsKey(Id)) 
        				{
        					ids = new IDs();
        					ids.accept_id=AcceptedAnswerId;   
//        					ids.other_ids.add(Id);//�ǲ�ѯʱ�����Ϊ��
        					lhm_idLinks.put(Id,ids);
        				}
            		}
            	}
            else if (PostTypeId_q.equals("2")) // ����������ǻش�
            	{
            		ParentId = doc.get("ParentId"); //�õ��ش��Ӧ������
            		if(!lhm_idLinks.containsKey(ParentId))
            		{
            			ids = new IDs();
//    					ids.accept_id=AcceptedAnswerId;//�ǻش�ʱ�����Ϊ��
            			ids.other_ids.add(Id);
    					lhm_idLinks.put(ParentId,ids);//ParentId��Ϊ��id,accept_idΪ�գ�other_id����һ��Id
            		}
            		else //����м��Ѿ�����һ������ش� ��Ӧ�� ������󣬾��������������ӵ�ǰ�ش��id
            		{
            			ids = lhm_idLinks.get(ParentId);
            			
            			System.out.println(ids.accept_id); //����жϵ�ǰ��ֵΪ��
            			if (ids.accept_id==null)
            			{
            				ids.other_ids.add(Id);
            			}
            			else if(!ids.accept_id.equals(Id))
            				{
            					ids.other_ids.add(Id);
            				}
            			
            			
            			
            			lhm_idLinks.remove(ParentId); //��ȥ���ɵģ�
            			lhm_idLinks.put(ParentId, ids);//��������µ�
            		}
            	}
            
            System.out.println("------------------------------------------------------");
        }
        reader_.close();
        
        
        System.out.println(lhm_idLinks.size());
        
        //�õ�id link֮��д�뵽�ı��С�
        PrintWriter bw2=new PrintWriter(new FileWriter(new File(config_.IDsLinks_dir)));
        
        for (String key : lhm_idLinks.keySet()) {
        	IDs ids_ = lhm_idLinks.get(key);
            bw2.write("Key = " + key + ", accept_id = " + ids_.accept_id+ ", other_ids = "+ids_.other_ids+"\n");//���ı�����ʾ��ǰ�Ĳ�ѯ����
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



