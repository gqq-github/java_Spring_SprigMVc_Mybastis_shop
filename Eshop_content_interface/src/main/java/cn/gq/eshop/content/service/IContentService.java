package cn.gq.eshop.content.service;

import cn.gq.eshop.pojo.TbContent;
import cn.gq.util.DateGridResultPageBean;
import cn.gq.util.EShopResult;

import java.sql.DatabaseMetaData;
import java.util.List;

public interface IContentService {
    DateGridResultPageBean getContentList(long categoryId,int page , int rows);
    EShopResult insertContent (TbContent content) ;
    List<TbContent> getContentListByCategoryId(Long categoryid);
}
