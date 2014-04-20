package cc.android.supu.bean;

import java.io.Serializable;
/**
 * 站内消息bean
 * @author zsx
 *
 */
public class UserMessageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 消息内容*/
	public String messageContent;
	
	/** 消息时间*/
	public String releaseTime;
	
	/** 会员消息关系编号*/
	public String RID;
	
	/** 消息编号*/
	public String MID;
	
	/** 是否已读*/
	public int isSee;
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(messageContent, releaseTime, RID, 
				MID, isSee);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof UserMessageBean) {
			UserMessageBean other = (UserMessageBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
