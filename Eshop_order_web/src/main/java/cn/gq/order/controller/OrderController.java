package cn.gq.order.controller;

import cn.gq.eshop.car.service.ICartService;
import cn.gq.eshop.order.service.IOrderService;
import cn.gq.eshop.order.service.pojo.OrderItemInfo;
import cn.gq.eshop.pojo.TbItem;
import cn.gq.eshop.pojo.TbOrderItem;
import cn.gq.eshop.pojo.TbUser;
import cn.gq.util.EShopResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    ICartService cartService ;
    @Autowired
    IOrderService orderService ;
    @RequestMapping("/order/order-cart")
    public String ShowCartList(HttpServletRequest request) {
         //这个方法执行之前请求经过拦截器 此时的用户已经登录了
        TbUser user = (TbUser) request.getAttribute("user");
        //获取用户的id
        List<TbItem> catList = cartService.getCatList(5);
        request.setAttribute("cartList",catList);
        return "order-cart" ;
    }
    @RequestMapping("/order/create")
    public String createOrder(OrderItemInfo orderInfo , HttpServletRequest request){
        //取用户的信息
        TbUser user = (TbUser) request.getAttribute("user");
        //将用户的信息添加到orderInfo当中
         orderInfo.setUserId(user.getId()) ;
        //见info交给Service处理
        EShopResult order = orderService.createOrder(orderInfo);
        //取出EshopResult
        if(order.getStatus()==200) {
            for (TbOrderItem orderItem : orderInfo.getOrderItems()) {
                //清空购物车
                 cartService.deleteCart(user.getId(),Long.parseLong(orderItem.getItemId()));
            }
        }
        //将订单号 传递
        request.setAttribute("orderId",order.getData());
        request.setAttribute("payment",orderInfo.getPayment());
        return "success";
    }
}
