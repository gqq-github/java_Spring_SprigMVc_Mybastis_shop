package cn.gq.eshop.controller;

import cn.gq.util.FastDFSClient;
import cn.gq.util.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PictureUpController {
    @Value("${TRACKER_SERVER}")
    //这个配置里面配置的是http://+主机名
    private String host ;
    // 设置回应的数据类型
    @RequestMapping(value ="/pic/upload",produces =MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
   @ResponseBody
    public String UpPicture(MultipartFile uploadFile) {
        //uploadFile这个参数相当于数组
        try {
             //通过工具类来获取当前的文件上传对象
             FastDFSClient fsc = new FastDFSClient("classpath:conf/cilne.conf");
             //获取原本的文件名
            String filename = uploadFile.getOriginalFilename();
            String extName = filename.substring(filename.lastIndexOf(".")+1);
            //第一参数有个byte的数组, 文件的拓展名
            //返回的字符串是当当前图片保存的位置
            String filepath = fsc.uploadFile(uploadFile.getBytes(), extName);
            //但是返回的结构需要完整的地址
            String url = host+ filepath ;
            Map map = new HashMap<>();
            map.put("error",0);
            map.put("url",url);

            return JsonUtils.objectToJson(map);
         }catch (Exception e) {
             e.printStackTrace();
            Map map = new HashMap<>();
            map.put("error",1);
            map.put("message","上传失败");
            return JsonUtils.objectToJson(map);
         }
    }
}
