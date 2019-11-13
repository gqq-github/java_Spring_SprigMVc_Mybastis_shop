package cn.gq.util;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {
    public static void main(String[] args) {
        //利用java来连接单机班的redis
        tetsCline();
        testPoll();
        testCluster();
    }

    public static void tetsCline() {
        // public Jedis(String host, int port) {
        //        super(host, port);
        //    }
        //第一个参数表示服务器地址
        //其实当你不设定端口号的时候默认的是6379
        // 获取jedis对象
        Jedis jedis = new Jedis("192.168.25.129");
        jedis.set("test", "1234");
        System.out.println("cline"+jedis.get("test"));
        //查询完之后要关闭
        jedis.close();
    }
    //通过连接池对量来创建连接
    public static  void testPoll () {
        JedisPool jedisPool = new JedisPool("192.168.25.129",6379);
        Jedis jedis = jedisPool.getResource();
        System.out.println("poll"+jedis.get("test"));
         jedis.close();
         jedisPool.close();
     }
    public static void testCluster() {
        Set<HostAndPort> set = new HashSet<>();
        set.add(new HostAndPort("192.168.25.129",7001));
        set.add(new HostAndPort("192.168.25.129",7002));
        set.add(new HostAndPort("192.168.25.129",7003));
        set.add(new HostAndPort("192.168.25.129",7004));
        set.add(new HostAndPort("192.168.25.129",7005));
        set.add(new HostAndPort("192.168.25.129",7006));
        //集群连接的时候需要一个set集合来保存主副redis和他的端口号
        JedisCluster cluster = new JedisCluster(set) ;
        cluster.set("testcluster","testCluster");
        System.out.println("cluster"+cluster.get("testcluster"));
        cluster.close();
    }
}