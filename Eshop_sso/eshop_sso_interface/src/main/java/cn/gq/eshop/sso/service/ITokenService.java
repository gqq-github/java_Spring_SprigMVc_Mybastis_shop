package cn.gq.eshop.sso.service;

import cn.gq.util.EShopResult;

/*
* 结合ajax请求来实现跨域
* 这个方法用来在redis中获取token中保存的用户数据
* */
public interface ITokenService {
    EShopResult UserToken (String token) ;
}
