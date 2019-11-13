package cn.gq.eshop.content.service;

import cn.gq.util.EShopResult;
import cn.gq.util.EasyUITreeNode;

import java.util.List;

public interface IContentCategoryService {
    List<EasyUITreeNode> getEasyUiTreeNodeByParentId(long parentid);
    EShopResult  insertContentCategoryByParentId(long parentid ,String name) ;
    //这个方法的名字存在在问题
    EShopResult  updateNameConteCategoryByParentId(long id ,String name);
    EShopResult  deleteNameConteCategoryById(long parentid);
}
