package com.taotao.pagehelper;


import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;

public class TestPageHelper {
	@Test
	public void testPageHelper() throws Exception{
		
	//1、在mybatis配置文件中配置pagehelper插件
	
	//2、使用pagehelper静态方法声明每页的条数
	PageHelper.startPage(1, 10);
	//3、执行查询
	//初始化spring容器，扫描spring配置文件，加载进来mapper包
	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
	TbItemMapper itemMapper=applicationContext.getBean(TbItemMapper.class);
	//创建example查询对象
	TbItemExample example=new TbItemExample();
	//criteria为查询条件，如果有查询条件就创建他
	//Criteria criteria=example.createCriteria();
	//criteria.andIdEqualTo();
	List<TbItem> list=itemMapper.selectByExample(example);
	//用pageinfo取所有的分页信息
	PageInfo<TbItem> pageInfo=new PageInfo<>(list);
	System.out.println("总记录数："+pageInfo.getTotal());
	System.out.println("总页数："+pageInfo.getPages());
	System.out.println("返回的总记录数："+list.size());

	
	}

}
