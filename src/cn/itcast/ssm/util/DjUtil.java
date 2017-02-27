package cn.itcast.ssm.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * DJ共通类
 * 
 * 
 */
public class DjUtil {

	public static String toLower(String obj) {
		if (obj == null) {
			return StringUtils.EMPTY;
		} else {
			return obj.toLowerCase().trim();
		}
	}

	public static String toUpper(String obj) {
		if (obj == null) {
			return StringUtils.EMPTY;
		} else {
			return obj.toUpperCase().trim();
		}
	}

	/**
	 * 将对象转换成字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toStr(Object obj) {
		if (obj == null) {
			return StringUtils.EMPTY;
		} else {
			return obj.toString().trim();
		}
	}

	/**
	 * 将对象转换成Long
	 * 
	 * @param obj
	 * @return
	 */
	public static Long toLong(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).longValue();
		} else if (obj instanceof Double) {
			return new BigDecimal((Double) obj).longValue();
		} else {
			return Long.parseLong(obj.toString().trim());
		}
	}

	/**
	 * 将对象转换成Integer
	 * 
	 * @param obj
	 * @return
	 */
	public static Integer toInt(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).intValue();
		} else if (obj instanceof Double) {
			return new BigDecimal((Double)obj).intValue();
		} else {
			return Integer.parseInt(obj.toString().trim());
		}
	}

	/**
	 * 将对象转换成Double
	 * 
	 * @param obj
	 * @return
	 */
	public static Double toDouble(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).doubleValue();
		} else {
			return Double.parseDouble(obj.toString().trim());
		}
	}

	/**
	 * 用equals比较2个对象，对应null的问题<br>
	 * 
	 * @param a
	 *            对象a
	 * @param b
	 *            对象b
	 * @return
	 */
	public static boolean equals(Object a, Object b) {

		if (a == b) {
			return true;
		} else {
			if (a == null || b == null) {
				return false;
			} else if (a.equals(b)) {
				return true;
			} else {
				return a.toString().equals(b.toString());
			}
		}
	}

	/**
	 * 获取本服务器的国际化信息
	 * 
	 * @return
	 */
	public static String getLocale() {
		return Locale.getDefault().toString();
	}

	/**
	 * 判断list是否为空
	 * 
	 * @return
	 */
	public static <T> boolean isEmptyList(List<T> list) {
		if (list == null) {
			return true;
		} else if (list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断map是否为空
	 * 
	 * @return
	 */
	public static <T, V> boolean isEmptyMap(Map<T, V> map) {
		if (map == null) {
			return true;
		} else if (map.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断array是否为空
	 * 
	 * @return
	 */
	public static <T> boolean isEmptyArray(T[] array) {
		if (array == null) {
			return true;
		} else if (array.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断String是否为空
	 * 
	 * @return
	 */
	public static boolean isEmptyString(String str) {
		return ((str == null) || (str.length() == 0));
	}

	public static Byte toByte(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).byteValue();
		} else {
			DecimalFormat format = new DecimalFormat("0000000000000000000");
			try {
				return format.parse(obj.toString().trim()).byteValue();
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * 获取单位图形的个数
	 * 
	 * @param path
	 * @return width height
	 */
	public static int getUnitCount(int length, int unit) {
		if (length % unit == 0) {
			return length / unit;
		} else {
			return length / unit + 1;
		}
	}
	
	public static BigDecimal toBigDecimal( Object value ) {  
        BigDecimal ret = null;  
        if( value != null ) {  
            if( value instanceof BigDecimal ) {  
                ret = (BigDecimal) value;  
            } else if( value instanceof String ) {  
                ret = new BigDecimal( (String) value );  
            } else if( value instanceof BigInteger ) {  
                ret = new BigDecimal( (BigInteger) value );
            } else if( value instanceof Number ) {  
                ret = new BigDecimal( ((Number)value).doubleValue() );  
            }
        }  
        return ret;  
    }  

}
