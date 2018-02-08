package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	@Value("${ORDER_ID_BEGEN_KEY}")
	private String ORDER_ID_BEGEN_KEY;
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;

	@Override
	public TaotaoResult createOrder(OrderInfo orderInfo) {
		//生成订单号之前先判断是否存在，如果不存在先设置一个初始值
		if(!jedisClient.exists(ORDER_ID_GEN_KEY)){
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGEN_KEY);
		}
		//生成订单号，用redis的incr自增方法生成
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		//向订单表中插入数据，需要补全pojo属性
		orderInfo.setOrderId(orderId);
		//免邮费
		orderInfo.setPostFee("0");
		//设置订单状态，1未付款，2已付款，3未发货，4已发货，5交易成功，6交易关闭
		orderInfo.setStatus(1);
		//订单创建时间与更新时间
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		tbOrderMapper.insert(orderInfo);
		//向订单明细表中添加数据
		//获得订单明细
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			//获得明细主键
			String oid = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
			//设置主键与商品订单id
			tbOrderItem.setId(oid);
			tbOrderItem.setOrderId(orderId);
			tbOrderItemMapper.insert(tbOrderItem);
		}
		//向订单物流表插入数据
		TbOrderShipping tbOrderShipping = orderInfo.getOrderShipping();
		//补全属性
		tbOrderShipping.setOrderId(orderId);
		tbOrderShipping.setCreated(new Date());
		tbOrderShipping.setUpdated(new Date());
		tbOrderShippingMapper.insert(tbOrderShipping);
		//返回订单号，要返回订单id
		return TaotaoResult.ok(orderId);
	}

}
