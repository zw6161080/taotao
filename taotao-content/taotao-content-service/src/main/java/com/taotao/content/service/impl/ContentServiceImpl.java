package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;

	@Override
	public EasyUIDataGridResult getContentList(Long categoryId, int page, int rows) {
		
			
			PageHelper.startPage(page, rows);
			TbContentExample example=new TbContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);
			List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
			PageInfo<TbContent> pageInfo=new PageInfo<>(list);
			EasyUIDataGridResult result=new EasyUIDataGridResult();
			result.setRows(list);
			result.setTotal(pageInfo.getTotal());
			return result;
		}

	@Override
	public TaotaoResult addContent(TbContent content) {
		content.setCreated(new Date());
		content.setCreated(new Date());
		contentMapper.insert(content);
		//缓存同步，返回结果之前先删除缓存中的信息
		jedisClient.hdel(INDEX_CONTENT,content.getCategoryId().toString());
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult editContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKey(content);
		//缓存同步，返回结果之前先删除缓存中的信息
		jedisClient.hdel(INDEX_CONTENT,content.getCategoryId().toString());
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContent(String ids) {
		String[] idList=ids.split(",");
		for (String id : idList) {
			contentMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		
		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentByCid(Long cid) {
		//先从缓存中查询
		try {
			String json = jedisClient.hget(INDEX_CONTENT,cid+"");
			if(StringUtils.isNotBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExample(example );
		//将查询结果添加到缓存
		try {
			jedisClient.hset(INDEX_CONTENT, cid+"", JsonUtils.objectToJson(list));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}


