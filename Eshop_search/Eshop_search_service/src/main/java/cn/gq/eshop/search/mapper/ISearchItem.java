package cn.gq.eshop.search.mapper;

import cn.gq.util.pojo.SearchItem;

import java.util.List;

public interface ISearchItem {
    List<SearchItem> searchItemAll();
    SearchItem getSearchItemById(long id);
}
