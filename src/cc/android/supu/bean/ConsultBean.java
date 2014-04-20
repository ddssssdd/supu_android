package cc.android.supu.bean;

import java.io.Serializable;
/**
 * 咨询bean
 * @author sheng
 *
 */
public class ConsultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 商品咨询的id*/
	public String id;
	
	/** 商品咨询内容*/
	public String consultContent;
	
	/** 咨询时间*/
	public String consultTime;
	
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(id, consultContent, consultTime);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof ConsultBean) {
			ConsultBean other = (ConsultBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
