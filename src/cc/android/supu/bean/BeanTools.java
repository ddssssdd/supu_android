package cc.android.supu.bean;

import java.lang.reflect.Array;

/**
 * Bean的工具类
 * 
 * @author bluceshang
 * 
 */
public final class BeanTools {
	/**
	 * 按一定的程序生成hashcode, 生成的策略是：</p>
	 * 1.把某個非零數值，如17(最好是质数)保存在一个result的int类型的变量中</p> a.为该域计算int类型的散列码c:
	 * i.如果该域是boolean类型，则计算(f?0:1) ii.如果是byte,char,short,int类型，则计算(int)f.
	 * iii.如果是long类型，则计算(int)(f^(f>>>32))
	 * iv.如果是float类型，则计算Float.floatToIntBits(f);
	 * v.如果是double类型，则计算Double.doubleToLongBits(f)得到一个long类型的值，
	 * 然后按2.a.iii.对该long型计算散列值 vi.如果是一个对象的引用，并且该类的equals方法通过递归调用equals的方式
	 * 来比较这个域，则同样对这个域递归调用hashCode,如果要求一个更为复杂的比较
	 * 则为这个域计算一个规范表示，然后针对这个范式调用hashCode,如果这个域为null,则返回nu0
	 * vii.如果是一个数组，则把每个元素当做单独的域来处理。
	 * 
	 * @param objects
	 *            对像要生成hashcode的所有属性
	 * @return 返回hashcode.
	 */
	public static int createHashcode(Object... objects) {
		if (objects == null)
			return 0;
		int result = 17;
		for (Object object : objects) {
			if (object == null)
				continue;
			if (object instanceof Byte || object instanceof Short || object instanceof Character
					|| object instanceof Integer) {
				result = result * 37 + (Integer) object;
			} else if (object instanceof Boolean) {
				result += (Boolean) object ? 0 : 1;
			} else if (object instanceof Float) {
				result += Float.floatToIntBits((Float) object);
			} else if (object instanceof Long) {
				Long l = (Long) object;
				result += (int) (l ^ (l >>> 32));
			} else if (object instanceof Double) {
				Double d = (Double) object;
				Long l = Double.doubleToLongBits(d);
				result += (int) (l ^ (l >>> 32));
			} else if (object.getClass().isArray()) {
				for (int i = 0; i < Array.getLength(object); i++) {
					result += createHashcode(Array.get(object, i));
				}
			} else {
				result += object.hashCode();
			}
		}
		return result;
	}
}
