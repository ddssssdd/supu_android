package cc.android.supu.bean;

import java.io.Serializable;

/**
 * @author sss 活动Bean
 */
public class ActivityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 活动编号 */
	public String ID;
	/** 活动名称 */
	public String name;
	/** 活动详情 */
	public String description;
	/** 活动图片 */
	public String activityImage;

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ID:").append(ID).append(" name:").append(name).append(" desctiption:").append(description)
				.append(" activityImage:").append(activityImage);
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		return BeanTools.createHashcode(ID, name, description, activityImage);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof ActivityBean) {
			ActivityBean other = (ActivityBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else
			return false;
	}
}
