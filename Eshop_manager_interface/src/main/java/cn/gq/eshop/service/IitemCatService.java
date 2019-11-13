package cn.gq.eshop.service;

import cn.gq.util.EasyUITreeNode;

import java.util.List;

public interface IitemCatService {
  List<EasyUITreeNode> getEasyUITreeNodeByParentId(long pareantid);
}
