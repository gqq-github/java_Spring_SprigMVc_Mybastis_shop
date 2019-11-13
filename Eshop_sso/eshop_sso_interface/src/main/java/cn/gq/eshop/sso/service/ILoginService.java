package cn.gq.eshop.sso.service;

import cn.gq.util.EShopResult;

public interface ILoginService {
    /**
     * 实现用户的登录功能
     * param String username String password
     * // 1.判断输入的密码和用户名是否合法  不合法返回登录 的页面
     * // 2. 利用用户名来查询数据库
     *  3.有数据向下进行,没有则返回登录页面提示用户名或密码错误
     *  4 .利用UUID生成token
     *  5 .将token保存到redis当中,key为SESSION:token value 为用户的信息
     *  设置过期的时间
     *  6 .将token返回到控制层,将token保存到cooker当中用于服务端的验证
     *  7 .利用Eshopresult来保存token
     */
    EShopResult UserLogin(String name,String password);
}
