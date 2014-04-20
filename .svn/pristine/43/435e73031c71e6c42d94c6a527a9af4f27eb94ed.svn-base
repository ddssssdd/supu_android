package cc.android.supu.bean;

import java.io.Serializable;
/**
 * 商品优惠活动bean
 * @author sheng
 *
 */
public class GoodsActivityBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 活动编号*/
	public String id;
	
	/** 活动名称*/
	public String name;
	
	/** 活动描述*/
	public String description;
	
	/** 活动图片*/
	public String activityImage;
	
	
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(id, name, description, activityImage);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof GoodsActivityBean) {
			GoodsActivityBean other = (GoodsActivityBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
