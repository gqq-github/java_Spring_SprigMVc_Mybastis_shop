package cn.gq.eshop.car.controller;

import cn.gq.eshop.car.service.ICartService;
import cn.gq.eshop.pojo.TbItem;
import cn.gq.eshop.pojo.TbUser;
import cn.gq.eshop.service.IitemService;
import cn.gq.util.CookieUtils;
import cn.gq.util.EShopResult;
import cn.gq.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
/**
 * 通过cookie来实现购物车的功能
 * 实现的步骤
 * ---首先当客户进行添加购物的请求的时候需要交由后台处理
 * --判断当前的cookie中是否包含当前的物品包含则将里面的商品加1
 * --没有则在后台获取商品的信息加到购物车里面面
 * --存在的不足因为cookie所保存的数据是有些的
 * --2.cookie中的数据没有办法区分到底是谁的购物车(TODO暂时这么说)
 */
@Controller
public class ShopCarController{
    @Autowired
    ICartService cartService ;
    @Autowired
    IitemService itemService ;
    @Value("${COOKIE_CAR_NAME}")
    private String COOKIE_CAR_NAME ;
    @Value("${COOKIE_CAR_TIME}")
    private Integer COOKIE_CAR_TIME;
    @RequestMapping("/cart/add/{itemid}")
    //返回视图
    //添加商品
    public String addItemToShopCar(@PathVariable Long itemid , @RequestParam(defaultValue ="1") int num, HttpServletRequest request, HttpServletResponse response){
        TbUser user = (TbUser) request.getAttribute("user");
         if(user!=null){
             cartService.addCart(user.getId(),itemid,num);
             return "cartSuccess" ;
         }
        //首先得到cookie中的商品信息
        List<TbItem> tbItems = GetCookeItemList(request);
        boolean flag = false ;
        for (TbItem item : tbItems){
            if(item.getId()==itemid.longValue()){
                item.setNum(item.getNum()+num);
                flag = true;
                break;
            }
        }
        if(!flag){
            TbItem itemById = itemService.getItemById(itemid);
            String image = itemById.getImage();
            if(StringUtils.isNotBlank(image)){
                itemById.setImage(itemById.getImage().split(",")[0]);
            }
            tbItems.add(itemById);
        }
        CookieUtils.setCookie(request,response,COOKIE_CAR_NAME,JsonUtils.objectToJson(tbItems));
        return  "cartSuccess";
    }
    /**
     * 展示商品的列表
     * */
    @RequestMapping("cart/cart")
    public String ShowShopCatList(HttpServletRequest request,HttpServletResponse response){
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        List<TbItem> items = GetCookeItemList(request);
        //如果用户进行登录则在cookie当中的数据和服务端的数据据进行整合
        if(user!=null){
            cartService.mergeCart(user.getId(),items);
            //然后把cookie中的购物车删除
            CookieUtils.deleteCookie(request,response,COOKIE_CAR_NAME);
            items = cartService.getCatList(user.getId());
        }
        //从服务端取购物车列表
        request.setAttribute("cartList",items);
        return "cart";
    }
    /**
     * 更新购物车的信息
     *
     * */
    @RequestMapping("/cart/update/num/{itemid}/{num}")
    @ResponseBody
    public EShopResult UpdateShopCart(@PathVariable Long itemid , @PathVariable int num , HttpServletRequest request, HttpServletResponse response){
        TbUser user = (TbUser) request.getAttribute("user");
        if(user!=null){
            EShopResult result = cartService.updateCart(user.getId(), itemid, num);
            return  result ;
        }
        List<TbItem> tbItems = GetCookeItemList(request);
        for (TbItem item:tbItems){
            if(item.getId()==itemid.longValue()){
                item.setNum(num);
                break;
            }
        }
        CookieUtils.setCookie(request,response,COOKIE_CAR_NAME,JsonUtils.objectToJson(tbItems));
      return EShopResult.ok();
    }
    /**
     * 购物车商品的删除
     * */
    @RequestMapping("/cart/delete/{itemid}")
    public String DeleteShopCart(@PathVariable long itemid,HttpServletRequest request,HttpServletResponse response) {
        TbUser user = (TbUser) request.getAttribute("user");
        if(user!=null){
           cartService.deleteCart(user.getId(),itemid);
        }else {
            List<TbItem> tbItems = GetCookeItemList(request);
            for (TbItem item : tbItems) {
                if (item.getId() == itemid) {
                    tbItems.remove(item);
                    break;
                }
            }
            CookieUtils.setCookie(request, response, COOKIE_CAR_NAME, JsonUtils.objectToJson(tbItems));

        } //路径的跳转要加/
        return "redirect:/cart/cart.html";

    }

    /**
     * 获取购物车的list的工具类
     * */
    public List<TbItem> GetCookeItemList(HttpServletRequest request) {
        //得到的是json中保存的数据
        String json = CookieUtils.getCookieValue(request, COOKIE_CAR_NAME, true);
        if(StringUtils.isBlank(json)){
            return   new ArrayList<>();
        }
        List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
        return itemList ;
    }
}
