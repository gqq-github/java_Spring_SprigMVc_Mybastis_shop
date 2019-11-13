package cn.gq.eshop.order.service.pojo;

import cn.gq.eshop.pojo.TbOrder;
import cn.gq.eshop.pojo.TbOrderItem;
import cn.gq.eshop.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

public class OrderItemInfo extends TbOrder implements Serializable {
    private List<TbOrderItem> orderItems ;
    private TbOrderShipping orderShipping ;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
