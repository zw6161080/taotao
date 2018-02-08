package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		//树形结构，根据父节点id查询子节点列表
		TbItemCatExample example=new TbItemCatExample();
		//设置查询条件
		Criteria criteria=example.createCriteria();
		//设置parentid
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//因为要返回EasyUITreeNode列表，所以要转换成EasyUITreeNode列表
		List<EasyUITreeNode> resultList=new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node=new EasyUITreeNode();
			//EasyUITreeNode的属性
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			//如果父节点下有子节点则close，没有贼open
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			//添加到节点列表
			resultList.add(node);
		}
		
		
		return resultList;
	}

}
