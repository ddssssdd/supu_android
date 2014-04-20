package cc.android.supu.bean;

import java.io.Serializable;
/**
 * 物流信息bean
 * @author sheng
 *
 */
public class ProcessBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/** 进程说明*/
	public String note;
	
	/** 时间*/
	public String time;
	
	
	@Override
	public int hashCode() {
		return BeanTools.createHashcode(note, time);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof ProcessBean) {
			ProcessBean other = (ProcessBean) o;
			if (this.toString().equals(other.toString()))
				return true;
			else
				return false;
		} else
			return false;
	}
}
