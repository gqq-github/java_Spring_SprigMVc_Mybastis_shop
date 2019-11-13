package cn.gq.eshop.service;
        import cn.gq.eshop.pojo.TbItem;
        import cn.gq.eshop.pojo.TbItemDesc;
        import cn.gq.util.DateGridResultPageBean;
        import cn.gq.util.EShopResult;

public interface IitemService {
    DateGridResultPageBean getPageResult(int currentpage,int pagesize) ;
    TbItem getItemById(long id) ;
    EShopResult addItem(TbItem item ,String desc) ;
    TbItemDesc getItemDescById(long id) ;
}
