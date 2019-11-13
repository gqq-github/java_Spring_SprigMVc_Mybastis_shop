package cn.gq.eshop.controller;

import cn.gq.eshop.content.service.IContentCategoryService;
import cn.gq.util.EShopResult;
import cn.gq.util.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCategoryController { 
    @Autowired
    IContentCategoryService categoryService;
    @RequestMapping(value = "/content/category/list")
    @ResponseBody
    // 生成树节点
    public List<EasyUITreeNode> getEasyTreeCategoryList(@RequestParam(name = "id",defaultValue = "0") long parentid){
        return categoryService.getEasyUiTreeNodeByParentId(parentid);
    }
    // 添加分类的节点
    @RequestMapping("/content/category/create")
    @ResponseBody
    public EShopResult getCreatNodeCategory(Long parentId,String name) {
        EShopResult eShopResult = categoryService.insertContentCategoryByParentId(parentId, name);
        return eShopResult ;
    }
     @RequestMapping("/content/category/update")
    @ResponseBody
    public EShopResult UpContentCategory(long id ,String name) {
        EShopResult eShopResult = categoryService.updateNameConteCategoryByParentId(id, name);
        return eShopResult ;
    }
    @RequestMapping("/content/category/delete")
    @ResponseBody
    public EShopResult DeleteContentCategory(long id) {
        EShopResult eShopResult = categoryService.deleteNameConteCategoryById(id);
        return eShopResult ;
    }
}
