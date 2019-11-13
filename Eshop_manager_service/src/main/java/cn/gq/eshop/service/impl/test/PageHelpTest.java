package cn.gq.eshop.service.impl.test;

import cn.gq.eshop.mapper.TbItemMapper;
import cn.gq.eshop.pojo.TbItem;
import cn.gq.eshop.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class PageHelpTest {
 //湖区Spring容器
 public static void main(String[] args) {


//
//        ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
//        TbItemMapper bean = app.getBean(TbItemMapper.class);
//        //设置第一个参数表示当前的页码
//        //第二个参数表示当前的页面的大小
//        PageHelper.startPage(1,10);
//        TbItemExample example = new TbItemExample();
//        List<TbItem> tbItems = bean.selectByExample(example);
//        PageInfo<TbItem> info = new PageInfo<>(tbItems);
//        System.out.println("第一页"+info.getFirstPage());
//        System.out.println("总条数"+info.getTotal());
//        System.out.println("总页数"+info.getPages());
//        System.out.println("页面的大小"+info.getPageSize());
     ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
     //获得Mapper的代理对象
     TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
     //设置分页信息
     PageHelper.startPage(1, 30);
     //执行查询
     TbItemExample example = new TbItemExample();
     List<TbItem> list = itemMapper.selectByExample(example);
     //取分页信息
     PageInfo<TbItem> pageInfo = new PageInfo<>(list);
     System.out.println(pageInfo.getTotal());
     System.out.println(pageInfo.getPages());
     System.out.println(pageInfo.getPageNum());
     System.out.println(pageInfo.getPageSize());
    }
}
