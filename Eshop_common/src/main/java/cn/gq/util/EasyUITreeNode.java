package cn.gq.util;

import java.io.Serializable;
/*
* 将后台取出的数据进行封装
* 封装的格式为easyUltree的格式
* 这个格式:
*  id : long
*  text : String 为上商品的名称
*  state : "close" || "open"
*  这个state的这个参数能够设置这个树形的几点是否能够展开
*  在节点展开的时候又能够进行异步的加载
*
*  最后记得将这个项目部署到本地
* */
public class EasyUITreeNode implements Serializable {
     private Long id ;
     private  String text ;
     private  String state ;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
