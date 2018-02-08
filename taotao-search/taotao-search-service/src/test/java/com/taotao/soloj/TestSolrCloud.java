///*package com.taotao.soloj;
//
//import org.apache.solr.client.solrj.impl.CloudSolrServer;
//import org.apache.solr.common.SolrInputDocument;
//import org.junit.Test;
//
//public class TestSolrCloud {
//	
//	@Test
//	public void testSolrCloudAddDocument() throws Exception{
//		
//		CloudSolrServer cloudSolrServer=new CloudSolrServer("172.27.90.226:2182,172.27.90.226:2183,172.27.90.226:2184");
//		cloudSolrServer.setDefaultCollection("collection1");
//		SolrInputDocument document=new SolrInputDocument();
//		document.addField("id", "test001");
//		document.addField("item_title", "测试商品");
//		document.addField("item_price", 100);
//		cloudSolrServer.add(document);
//		cloudSolrServer.commit();
//	}
//
//}
