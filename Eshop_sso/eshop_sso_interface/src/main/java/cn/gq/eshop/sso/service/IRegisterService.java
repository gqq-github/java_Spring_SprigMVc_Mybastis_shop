package cn.gq.eshop.sso.service;

import cn.gq.eshop.pojo.TbUser;
import cn.gq.util.EShopResult;

public interface IRegisterService {
   EShopResult findDateType(String parm ,int type);
   EShopResult createUser(TbUser user) ;
}
