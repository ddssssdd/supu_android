package cc.android.supu.bean;

import java.io.Serializable;
/**
 * 商品咨询的回复bean
 * @author sheng
 *
 */
public class ReplyBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 回复的id*/
	public String id;
	
	/** 回复内容 */
	public String replyContent;
	
	/** 回复时间*/
	public String replyTime;
	
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(id, replyContent, replyTime);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof ReplyBean) {
			ReplyBean other = (ReplyBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
