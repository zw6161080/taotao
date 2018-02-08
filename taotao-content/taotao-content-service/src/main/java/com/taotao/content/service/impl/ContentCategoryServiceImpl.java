package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;


@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList=new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			//添加到结果列表
			resultList.add(node);
		}
		
		return resultList;
	}
	@Override
	public TaotaoResult addContentCategory(Long parentId, String name) {
		
		//创建一个pojo对象
		TbContentCategory contentCategory=new TbContentCategory();
		//补全对象属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		contentCategory.setUpdated(new Date());
		
		//将对象添加到数据库
		contentCategoryMapper.insert(contentCategory);
		//判断新增加的节点是否为父节点
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			//如果不是父节点，将他设置为父节点
			parent.setIsParent(true);
			//将新增加的父节点更新到mapper中
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		//返回结果
		return TaotaoResult.ok(contentCategory);
	}
	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult deleteContentCategory(Long id) {
		deleteContentCategoryAndChileNode(id);
		return TaotaoResult.ok();
	}
	public void deleteContentCategoryAndChileNode(Long id) {
		
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(id);
		System.out.println("p");
		if(parent.getIsParent()){
			List<TbContentCategory> list = getChildNodeList(id);
			for (TbContentCategory tbContentCategory : list) {
				deleteContentCategoryAndChileNode(tbContentCategory.getId());
			}
		}
		if(getChildNodeList(parent.getParentId()).size()==1){
			TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(parent.getParentId());
			contentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(contentCategory);
		}
		contentCategoryMapper.deleteByPrimaryKey(id);
	}
	private List<TbContentCategory> getChildNodeList(Long id) {
		
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		return list;


		
	}
	
	

}
