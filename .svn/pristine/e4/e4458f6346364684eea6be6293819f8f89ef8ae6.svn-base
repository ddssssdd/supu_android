package cc.android.supu.tools;

import java.text.DecimalFormat;

/**
 * 价格数值计算类
 * 
 * @author sheng
 * 
 */
public class PriceTools {

	/**
	 * 将价格格式化为0.00型 的String，例："78.5"--->"78.50"
	 * 
	 * @return String
	 */
	public static String formatStr(String str) {
		if (str == null || str.equals(""))
			return "0.00";
		else {
			DecimalFormat df = new DecimalFormat();
			String pattern = "0.00";
			df.applyPattern(pattern);
			str = df.format(Double.parseDouble(str));
		}
		return str;
	}
	

	/**
	 * 价格字符串转为int型，例如："78.5"--->7850
	 * 
	 * @param str
	 * @return
	 */
	public static double stringToInt(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return 0d;
		}
	}

	/**
	 * 将int型数值转化为价格格式的字符串，例：7850 ---> "78.50"
	 * 
	 * @param sum
	 *            需要转换的int型数值
	 * @return
	 * 
	 */
	public static String intToString(double sum) {
		return sum + "";
	}

	/**
	 * 比较两个价格字符串的数值大小
	 * 
	 * @param str1
	 * @param str2
	 * @return 1：str1的数值 > str2的数值; -1:str1的数值 < str2的数值; 0:str1的数值 = str2的数值
	 *         -2:不符价格格式
	 */
	public static int compare(String str1, String str2) {
		str1 = formatStr(str1);
		str2 = formatStr(str2);
		int pointIndex_1 = str1.indexOf('.');
		int pointIndex_2 = str2.indexOf('.');
		long pointBefore_1 = Long.parseLong(str1.substring(0, pointIndex_1));
		long pointBefore_2 = Long.parseLong(str2.substring(0, pointIndex_2));

		if (pointBefore_1 > pointBefore_2) {
			return 1;
		} else if (pointBefore_1 < pointBefore_2) {
			return -1;
		} else {
			long pointAfter_1 = Long.parseLong(str1.substring(pointIndex_1 + 1, str1.length()));
			long pointAfter_2 = Long.parseLong(str2.substring(pointIndex_2 + 1, str2.length()));
			if (pointAfter_1 > pointAfter_2) {
				return 1;
			} else if (pointAfter_1 < pointAfter_2) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
