package cn.gq.eshop.content.service.service.impl;

import cn.gq.eshop.content.service.IContentCategoryService;
import cn.gq.eshop.mapper.TbContentCategoryMapper;
import cn.gq.eshop.mapper.TbContentMapper;
import cn.gq.eshop.pojo.TbContentCategory;
import cn.gq.eshop.pojo.TbContentCategoryExample;
import cn.gq.eshop.pojo.TbContentExample;
import cn.gq.util.EShopResult;
import cn.gq.util.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements IContentCategoryService {
    @Autowired
    TbContentCategoryMapper tbContentCategoryDao ;
    public List<EasyUITreeNode> getEasyUiTreeNodeByParentId(long parnentid) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parnentid);
        List<TbContentCategory> ls = tbContentCategoryDao.selectByExample(example);
         List<EasyUITreeNode> list = new ArrayList<>();
        for (TbContentCategory tcc:ls) {
            EasyUITreeNode treeNode = new EasyUITreeNode() ;
            treeNode.setText(tcc.getName());
            treeNode.setId(tcc.getId());
            treeNode.setState(tcc.getIsParent()?"closed":"open");
            list.add(treeNode) ;
        }
        return list;
    }

    @Override
    public EShopResult insertContentCategoryByParentId(long parentid, String name) {
        //插入的第一步,创建一个对象用来保存数据
        TbContentCategory category = new TbContentCategory();
        category.setParentId(parentid);
        category.setName(name);
        category.setSortOrder(1);
        // 1 表示正常 0 反之
        category.setStatus(1);
        category.setCreated(new Date());
        category.setUpdated(new Date());
         // 返回的父节点保存到当前对象当中
         tbContentCategoryDao.insertSelective(category);
         //根据这个父节点查询到当前的节点是否为父节点
        TbContentCategory tbContentCategory = tbContentCategoryDao.selectByPrimaryKey(parentid);
          if(!tbContentCategory.getIsParent()){
              //将父节点更改为true
              tbContentCategory.setIsParent(true);
              tbContentCategoryDao.updateByPrimaryKey(tbContentCategory);
          }
          // 返回新产生对象的ID
        return EShopResult.ok(category);
    }

    @Override
    public EShopResult updateNameConteCategoryByParentId(long id, String name) {
        TbContentCategory vo = new TbContentCategory();
         vo.setName(name);
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        tbContentCategoryDao.updateByExampleSelective(vo,example);
        return EShopResult.ok();
    }

    @Override
    public EShopResult deleteNameConteCategoryById(long id) {
          // 获取当前的节点
         TbContentCategory vo = tbContentCategoryDao.selectByPrimaryKey(id);

         if(!vo.getIsParent()){
             //是叶子节点则直接的删除
             tbContentCategoryDao.deleteByPrimaryKey(id);
             return EShopResult.ok();
         }
         else {
             // 该分支判断为父节点
             TbContentCategoryExample example = new TbContentCategoryExample();
             TbContentCategoryExample.Criteria criteria = example.createCriteria();
             criteria.andParentIdEqualTo(id);
             //查找父节点为当前ID的所有子节点
             List<TbContentCategory> leafs = tbContentCategoryDao.selectByExample(example);
             for(int i =0 ; i<leafs.size() ; i++){
                 TbContentCategory leaf = leafs.get(i);
                // 删除子节点
                 tbContentCategoryDao.deleteByPrimaryKey(leaf.getId());
             }
              // 删除当前节点
              tbContentCategoryDao.deleteByPrimaryKey(id);
             // 得到当前节点的父节点,然后查询他的子节点的个数
             // 如果-1为0 的话说明当前的节点只有他一个节点那么那就该变为子节点
             TbContentCategory tbcpa = tbContentCategoryDao.selectByPrimaryKey(vo.getParentId());
               // 得到的父节点的字节点是否只有一个节点
             TbContentCategoryExample example1 = new TbContentCategoryExample();
             TbContentCategoryExample.Criteria criteria1= example.createCriteria();
             criteria1.andParentIdEqualTo(vo.getParentId());
             List<TbContentCategory> leaf2 = tbContentCategoryDao.selectByExample(example);
             if(leaf2.size()-1==0){
                 TbContentCategory category = new TbContentCategory();
                 category.setIsParent(false);
                 TbContentCategoryExample example2 = new TbContentCategoryExample();
                 example.createCriteria().andIdEqualTo(vo.getParentId());
                 tbContentCategoryDao.updateByExampleSelective(category,example2);
             }
             return  EShopResult.ok() ;
         }

    }
}
