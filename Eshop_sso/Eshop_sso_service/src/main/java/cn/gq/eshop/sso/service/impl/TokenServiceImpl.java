package cn.gq.eshop.sso.service.impl;

import cn.gq.eshop.pojo.TbUser;
import cn.gq.eshop.sso.service.ITokenService;
import cn.gq.util.EShopResult;
import cn.gq.util.JsonUtils;
import cn.gq.util.jides.JedisClientPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements ITokenService {
        @Value("${SESSION_TIME}")
        private Integer session_time;
     //获取JedisClinePOOL
     @Autowired
     JedisClientPool jedisClientPool ;
    @Override
    public EShopResult UserToken(String token) {
         //获取redis中的数据
        String json = jedisClientPool.get("SESSION:" + token);
         //为空表示session已经过期,提示重新登录
        if(StringUtils.isBlank(json)){
           return EShopResult.build(400,"用户登录已经过期,请重新登录");
        }
        //不为空更新这个key
         jedisClientPool.expire("SESSION:" + token,session_time);
         TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return EShopResult.ok(user);
    }
}
