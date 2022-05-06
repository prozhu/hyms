package cn.prozhu.ssm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
/**
 * 生成时间表示的随机数
 * @author Administrator
 *
 */
public class RandomUtils {
    
    private  RandomUtils() {
    }

    /**
     * 
     * @description: 生成时间表示的随机数
     * @author ：zc
     * @date ：2017-2-7 上午8:06:46 
     * @return
     */
    public static String getSerialNumber() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String serialNumber = sdf.format(date);
        
        return serialNumber + (System.nanoTime() + "").substring(0, 5);
    }
    
    /**
     * @description: 将Date日期格式化
     * @author ：zc
     * @date ：2017-2-13 下午4:29:21 
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    
    
    /**
     * @description: 将Date日期格式化
     * @author ：zc
     * @date ：2017-2-13 下午4:29:21 
     * @param date
     * @return
     */
    public static String formatTime1(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    
    
    /**
     * @description: 将字符串格式的时间 转为 Date 类型
     * @author ：zc
     * @date ：2017-2-13 下午4:31:08 
     * @param date
     * @return
     * @throws ParseException
     */
   public static Date dateFromString(String date)  {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       try {
        return sdf.parse(date);
    } catch (ParseException e) {
        e.printStackTrace();
    }
       return null;
   }
    
    /**
     * 
     * @description: 生成大写字母的随机uuid
     * @author ：zc
     * @date ：2017-2-10 上午9:19:53 
     * @return uuid
     */
    public  static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
    
}
