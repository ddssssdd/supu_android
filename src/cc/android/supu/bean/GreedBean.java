package cc.android.supu.bean;

import java.io.Serializable;

/**
 * @author sss 孕育宝典Bean
 */
public class GreedBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public String ID;
	public String categoryName;
	public int sort;
	public String picUrl;

	@Override
	public int hashCode() {
		return BeanTools.createHashcode(ID, categoryName, sort, picUrl);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof GreedBean) {
			GreedBean other = (GreedBean) o;
			if (this.toString().equals(other.toString()))
				return true;
			else
				return false;
		} else
			return false;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ID:").append(ID).append(" categoryName:").append(categoryName).append(" sort:").append(sort)
				.append(" picUrl:").append(picUrl);
		return buffer.toString();
	}
}
