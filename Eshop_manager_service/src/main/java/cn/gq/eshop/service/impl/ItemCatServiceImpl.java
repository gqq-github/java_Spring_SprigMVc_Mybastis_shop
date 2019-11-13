package cn.gq.eshop.service.impl;

import cn.gq.eshop.mapper.TbItemCatMapper;
import cn.gq.eshop.pojo.TbItemCat;
import cn.gq.eshop.pojo.TbItemCatExample;
import cn.gq.eshop.service.IitemCatService;
import cn.gq.util.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
@Service
//注意这个Service不要导报错误
public class ItemCatServiceImpl implements IitemCatService {
    @Autowired
    TbItemCatMapper itemCatMapper;
    public List<EasyUITreeNode> getEasyUITreeNodeByParentId(long pareantid) {
        //根据pareantId来获取对象
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(pareantid);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        List<EasyUITreeNode> list = new ArrayList<>();
        for(TbItemCat tcat : tbItemCats){
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tcat.getId());
            easyUITreeNode.setText(tcat.getName());
            easyUITreeNode.setState(tcat.getIsParent()?"closed":"open");
           list.add(easyUITreeNode) ;
        }
     return list ;
    }
}
