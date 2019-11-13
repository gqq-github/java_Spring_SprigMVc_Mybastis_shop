package cn.gq.util.pojo;

import java.io.Serializable;
//这个类用于商品基础查询的封装类
public class SearchItem implements Serializable {
    private String id;
    private String title;
    private  long price;
    private String sell_point;
    private String image;
    private String category_name;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSell_point() {
        return sell_point;
    }
    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
    public String[] getImages () {
        if(image!=null&&!"".equals(image)){
            String[] split =image.split(",");
             return split ;
        }
        return null;
    }
}
