package cn.gq.eshop.search.service;


import cn.gq.util.pojo.SearchResult;

// 提供商品的查询功能
public interface ISearchService {
  SearchResult search(String keyword, Integer page , Integer rows) throws Exception;
}