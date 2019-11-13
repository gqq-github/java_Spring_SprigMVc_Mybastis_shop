package cn.gq.eshop.order.service;

import cn.gq.eshop.order.service.pojo.OrderItemInfo;
import cn.gq.util.EShopResult;

public interface IOrderService {
    EShopResult createOrder(OrderItemInfo info);
}
