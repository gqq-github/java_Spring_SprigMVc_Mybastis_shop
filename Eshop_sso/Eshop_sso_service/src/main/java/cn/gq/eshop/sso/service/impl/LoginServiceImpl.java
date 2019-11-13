package cn.gq.eshop.sso.service.impl;

import cn.gq.eshop.mapper.TbUserMapper;
import cn.gq.eshop.pojo.TbUser;
import cn.gq.eshop.pojo.TbUserExample;
import cn.gq.eshop.sso.service.ILoginService;
import cn.gq.util.EShopResult;
import cn.gq.util.JsonUtils;
import cn.gq.util.jides.JedisClientPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

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
 *  注册服务
 */
@Service
public class LoginServiceImpl implements ILoginService {
    @Value("${SESSION_TIME}")
    private Integer session_time;
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    JedisClientPool jedisClientPool ;
    @Override
    public EShopResult UserLogin(String name, String password) {
        if(StringUtils.isBlank(name)||StringUtils.isBlank(password)){
            return  EShopResult.build(400,"请检查用户名或密码");
        }
        TbUserExample example = new TbUserExample() ;
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(name);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if(tbUsers==null||tbUsers.size()==0){
            return EShopResult.build(400,"用户名或密码错误");
        }
        TbUser user = tbUsers.get(0);
         if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
             return EShopResult.build(400,"用户名或密码错误");
         }
         String token =UUID.randomUUID().toString();
         //将用户的密码设置为null防止暴露到服务端
         user.setPassword(null);
         jedisClientPool.set("SESSION:"+token,JsonUtils.objectToJson(user));
         jedisClientPool.expire("SESSION:"+token,session_time);
        return EShopResult.ok(token);
    }
}
