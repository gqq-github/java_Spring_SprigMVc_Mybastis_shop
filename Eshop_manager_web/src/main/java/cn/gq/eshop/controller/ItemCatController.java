package cn.gq.eshop.controller;

import cn.gq.eshop.service.IitemCatService;
import cn.gq.util.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
//显示树形
//EasyUlTreeNode的控制层

public class ItemCatController {
    @Autowired
    IitemCatService service ;
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getEasyTreeCatList(@RequestParam(name = "id" ,defaultValue = "0") long parentid){
     return service.getEasyUITreeNodeByParentId(parentid);
  }
}
