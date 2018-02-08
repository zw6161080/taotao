package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;

import net.sf.jsqlparser.expression.operators.relational.ItemsList;

@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery solrQuery) throws SolrServerException{
		
		QueryResponse queryResponse = solrServer.query(solrQuery);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		long numFound = solrDocumentList.getNumFound();
		List<SearchItem> itemsLists=new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem searchItem=new SearchItem();
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			
			String image=(String) solrDocument.get("item_image");
			if(StringUtils.isNoneBlank(image)){
				image=image.split(",")[0];
			}
			searchItem.setImage(image);
			searchItem.setPrice((long) solrDocument.get("item_price"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle="";
			if(list!=null && list.size()>0){
				
				itemTitle=list.get(0);
				
			}else{
				itemTitle=(String) solrDocument.get("item_title");
			}
			
			searchItem.setTitle(itemTitle);
			
			itemsLists.add(searchItem);
		}
		
		SearchResult searchResult=new SearchResult();
		searchResult.setItemList(itemsLists);
		searchResult.setRecordCount(numFound);
		
		return searchResult;
	}

}
