package cn.gq.eshop.order.service.impl;

import cn.gq.eshop.mapper.TbOrderItemMapper;
import cn.gq.eshop.mapper.TbOrderMapper;
import cn.gq.eshop.mapper.TbOrderShippingMapper;
import cn.gq.eshop.order.service.IOrderService;
import cn.gq.eshop.order.service.pojo.OrderItemInfo;
import cn.gq.eshop.pojo.TbOrderItem;
import cn.gq.eshop.pojo.TbOrderShipping;
import cn.gq.util.EShopResult;
import cn.gq.util.jides.JedisClientPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Value("${ORDER_ID_GEN_KEY}")
    private  String ORDER_ID_GEN;
    @Value("${ORDER_ID_DATE}")
    private String ORDER_ID_DATE;
    @Value("${ORDER_DETAILS_KEY}")
    private String ORDER_DETAILS_KEY ;
    @Autowired
    JedisClientPool jedisClientPool ;
    @Autowired
    TbOrderMapper orderMapper ;
    @Autowired
    TbOrderShippingMapper orderShippingMapper ;
    @Autowired
    TbOrderItemMapper orderItemMapper ;
    @Override
    public EShopResult createOrder(OrderItemInfo info) {
        //生成订单号 利用redis来生成
        Boolean exists = jedisClientPool.exists(ORDER_ID_GEN);
        if(!exists){
            jedisClientPool.set(ORDER_ID_GEN,ORDER_ID_DATE);
        }
        String orderID = jedisClientPool.incr(ORDER_ID_GEN).toString();
         info.setOrderId(orderID);
         //'状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',
         info.setStatus(1);
         info.setCreateTime(new Date());
         info.setUpdateTime(new Date());
         //将数据插入到数据库当中
         orderMapper.insert(info) ;
         //向订单的详情表中插入数据
         //订单白哦啊记录的是一个商品的详细信息
        List<TbOrderItem> items = info.getOrderItems();
        if(items!=null||items.size()>0) {
            for (TbOrderItem orderItem : items) {
                String incr = jedisClientPool.incr(ORDER_DETAILS_KEY).toString();
                orderItem.setId(incr);
                orderItem.setOrderId(orderID);
                orderItemMapper.insert(orderItem);
            }
        }
        TbOrderShipping orderShipping = info.getOrderShipping();
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShipping.setOrderId(orderID);
        return EShopResult.ok(orderID);
    }
}
