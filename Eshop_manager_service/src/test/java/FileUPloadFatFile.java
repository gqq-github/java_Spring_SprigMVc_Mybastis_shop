import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;
public class FileUPloadFatFile {
    @Test
    public static void main(String[] args) throws IOException, MyException {

      /*
      * 架加载配置文件配置文件的内容就是tracker服务的
      * 地址
      *
      */
       //1.加载配置的文件
        ClientGlobal.init("F:\\宜立方商城\\Eshop_manager_service\\src\\main\\resources\\conf\\cilne.conf");
        //创建一个trackerClinet的对象
        TrackerClient trackerClient = new TrackerClient();
        // use tracker get TrackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        // make storageServer = null
          StorageServer storageServer = null ;
         //creat StorageCline
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        //use it to uploads
        //第一参数文件的完整路径
        // 文件的扩展名
        // 文件的元数据
        String[] jpgs = storageClient.upload_file("C:\\Users\\DELL\\Desktop\\IMG_9472.jpg", "jpg", null);
        //得到文件所在的组和文件的名字
        for(String str:jpgs) {
            System.out.println(str);
        }

    }
}
