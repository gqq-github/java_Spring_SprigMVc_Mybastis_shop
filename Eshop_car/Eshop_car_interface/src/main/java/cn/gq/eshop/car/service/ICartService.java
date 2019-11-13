package cn.gq.eshop.car.service;

import cn.gq.eshop.pojo.TbItem;
import cn.gq.util.EShopResult;

import java.util.List;

public interface ICartService {
    EShopResult addCart(long userId,long itemId,int num);
    EShopResult mergeCart(long userId, List<TbItem> list);
    List<TbItem> getCatList(long userId);
    EShopResult updateCart(long userId,long itemId,int num);
    EShopResult deleteCart(long userId,long itemId);
}
