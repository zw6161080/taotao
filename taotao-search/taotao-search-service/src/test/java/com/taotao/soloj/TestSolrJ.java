//package com.taotao.soloj;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServer;
//import org.apache.solr.client.solrj.impl.HttpSolrServer;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.SolrInputDocument;
//import org.junit.Test;
//
//public class TestSolrJ {
//	
//	@Test
//	public void addDocument() throws Exception {
//		// 第一步：把solrJ的jar包添加到工程中。
//		// 第二步：创建一个SolrServer，使用HttpSolrServer创建对象。
//		SolrServer solrServer = new HttpSolrServer("http://172.27.90.226:8080/solr/collection1");
//		
//		// 第三步：创建一个文档对象SolrInputDocument对象。
//		SolrInputDocument document = new SolrInputDocument();
//		// 第四步：向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
//		document.addField("id", "test001");
//		document.addField("item_title", "测试商品");
//		document.addField("item_price", "199");
//		// 第五步：把文档添加到索引库中。
//		solrServer.add(document);
//		// 第六步：提交。
//		solrServer.commit();
//	}
//	
//	@Test
//	public void deleteDocumentByQuery() throws Exception {
//		SolrServer solrServer = new HttpSolrServer("http://172.27.90.226:8080/solr");
//		solrServer.deleteByQuery("id:test001");
//		solrServer.commit();
//	}
//	
//	@Test
//	public void deleteDocument() throws Exception {
//		SolrServer solrServer = new HttpSolrServer("http://172.27.90.226:8080/solr");
//		solrServer.deleteByQuery("*:*");
//		solrServer.commit();
//	}
//
//
//	@Test
//	public void searchDocument() throws Exception {
//		SolrServer solrServer = new HttpSolrServer("http://172.27.90.226:8080/solr");
//		SolrQuery solrQuery=new SolrQuery();
//		
//		solrQuery.setQuery("手机");
//		solrQuery.set("df", "item_keywords");
//		solrQuery.setStart(0);
//		solrQuery.setRows(10);
//		solrQuery.setHighlight(true);
//		solrQuery.addHighlightField("item_title");
//		solrQuery.setHighlightSimplePre("<div>");
//		solrQuery.setHighlightSimplePost("</div>");
//		
//		QueryResponse queryResponse = solrServer.query(solrQuery);
//		SolrDocumentList solrDocumentList = queryResponse.getResults();
//		
//		System.out.println("查询结果总记录数："+ solrDocumentList.getNumFound());
//		
//		for (SolrDocument solrDocument : solrDocumentList) {
//			
//			System.out.println(solrDocument.get("id"));
//			
//			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
//			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
//			String itemTitle=null;
//			if(list != null && list.size()>0){
//				itemTitle=list.get(0);
//			} else {
//				itemTitle=(String) solrDocument.get("item_title");
//			}
//			
//			System.out.println(itemTitle);
//			System.out.println(solrDocument.get("item_price"));
//			System.out.println(solrDocument.get("item_category_name"));
//			System.out.println("=============================================");
//		}
//	
//	
//	
//	}
//
//
//}
