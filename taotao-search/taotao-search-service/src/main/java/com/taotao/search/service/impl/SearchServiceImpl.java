package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception{
		
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(queryString);
		if(page<1){
			page=1;
		}
		solrQuery.setStart((page - 1)*rows);
		if(rows<1){
			rows=10;
		}
		solrQuery.setRows(rows);
		
		solrQuery.set("df", "item_title");
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<span style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</span>");
		
		SearchResult searchResult = searchDao.search(solrQuery);
		
		long recordCount = searchResult.getRecordCount();
		long pageCount = recordCount/rows;
		if(recordCount%rows>0){
			pageCount++;
		}
		
		searchResult.setTotalPages(pageCount);
		return searchResult;
	}

}
