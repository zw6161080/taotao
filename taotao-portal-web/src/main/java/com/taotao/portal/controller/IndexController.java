package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.rpc.protocol.dubbo.telnet.CountTelnetHandler;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AD1Node;

@Controller
public class IndexController {
	
	//从properties配置文件中取出特定的值
	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_WIDTH_B;
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index")
	public String showIndex(Model model){
		//根据cid查询首页大广告轮播图列表
		List<TbContent> contentList = contentService.getContentByCid(AD1_CATEGORY_ID);
		//将查询到的列表准换成AD1Node列表
		List<AD1Node> ad1Nodes=new ArrayList<>();
		for (TbContent tbContent : contentList) {
			AD1Node node=new AD1Node();
			//将每一个tbcontent元素都换成adnode元素
			node.setAlt(tbContent.getTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setHref(tbContent.getUrl());
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			//将元素添加到列表
			ad1Nodes.add(node);
		}
		//将列表转换成josn数据
		String ad1Json = JsonUtils.objectToJson(ad1Nodes);
		//将json结果传递到页面，使用model
		model.addAttribute("ad1", ad1Json);
		
		return "index";
	}
	

}
