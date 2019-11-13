package cn.gq.eshop.sso.service.impl;

import cn.gq.eshop.mapper.TbUserMapper;
import cn.gq.eshop.pojo.TbUser;
import cn.gq.eshop.pojo.TbUserExample;
import cn.gq.eshop.sso.service.IRegisterService;
import cn.gq.util.EShopResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/*
 *验证手机号用户名邮箱是否重复
 * 1==应户名
 * 2==手机号
 * 3==邮箱
 * 其他的数字表示数据无效
 * ..发布服务
 */
@Service
public class RegisterServiceImpl implements IRegisterService {
    @Autowired
    TbUserMapper tbUserMapper ;
    @Override
    public EShopResult findDateType(String parm, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if(type==1){
             criteria.andUsernameEqualTo(parm);
         } else if(type==2){
            criteria.andPhoneEqualTo(parm);
         }else if(type==3){
            criteria.andEmailEqualTo(parm);
        } else {
            return EShopResult.build(400,"数据可是错误");
        }
         // 查询的结果如果为null或者size==0 返回ture
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
          if(tbUsers!=null&&tbUsers.size()>0){
              return  EShopResult.ok(false);
          }
        return EShopResult.ok(true);
    }
    /*
     * 用户的注册功能的实现
     */
    @Override
    public EShopResult createUser(TbUser user) {
         //需要判断数据的完整性
        if(StringUtils.isBlank(user.getPassword())||
                StringUtils.isBlank(user.getPhone())||
                StringUtils.isBlank(user.getUsername())){
            return  EShopResult.build(400,"数据不完整,注册失败");
        }
        EShopResult dateType = findDateType(user.getUsername(), 1);
        if(!(boolean)dateType.getData()){
            return  EShopResult.build(400,"用户名重复,注册失败");
        }
        EShopResult dateType1 = findDateType(user.getPhone(), 2);
        if(!(boolean)dateType1.getData()){
            return  EShopResult.build(400,"手机号重复,注册失败");
        }
        user.setCreated(new Date());
        user.setUpdated(new Date());
        String md5pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5pass);
        tbUserMapper.insert(user);
        return EShopResult.ok();
    }
}
