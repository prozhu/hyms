package cn.itcast.ssm.exception;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestRedis {
	 private Jedis jedis; 
	    
	    @Before
	    public void setup() {
	        //连接redis服务器，192.168.0.100:6379
	        jedis = new Jedis("192.168.2.21", 6379);
	        //权限认证
	       // jedis.auth("admin");  
	    }
	    
	    /**
	     * redis存储字符串
	     */
	    @Test
	    public void testString() {
	        //-----添加数据----------  
	        jedis.set("name","xinxin");//向key-->name中放入了value-->xinxin  
	        System.out.println(jedis.get("name"));//执行结果：xinxin  
	        
	        jedis.append("name", " is my lover"); //拼接
	        System.out.println(jedis.get("name")); 
	        
	        jedis.del("name");  //删除某个键
	        System.out.println(jedis.get("name"));
	        //设置多个键值对
	        jedis.mset("name","liuling","age","23","qq","476777XXX");
	        jedis.incr("age"); //进行加1操作
	        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));
	        
	    }
}
