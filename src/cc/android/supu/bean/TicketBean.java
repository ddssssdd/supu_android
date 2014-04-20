package cc.android.supu.bean;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 优惠券bean
 * @author sheng
 *
 */
public class TicketBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 优惠券编码*/
	public String ticketNo;
	
	/** 优惠券编号*/
	public String ticketId;
	
	/** 优惠券名称*/
	public String ticketName;
	
	/** 优惠券开始时间*/
	public String beginTime;
	
	/** 优惠券结束时间*/
	public String endTime;
	
	/** 优惠券绑定时间*/
	public String bindingTime;
	
	/** 优惠券说明*/
	public String ticketDescribe;
	
	/** 优惠折扣*/
	public String discount;
	
	/** 优惠金额*/
	public String discountAmount;
	
	/** 优惠金额是否叠加*/
	public boolean discountCumulative;
	
	/** 优惠券状态,1:正常状态     2:锁定状态   3:删除状态*/
	public int status;
	
	/** 是否已使用*/
	public boolean isUsed;
	/** 补助的金额*/
	public float grantAmount;
	/** 优惠券赠品列表*/
	public ArrayList<TicketsGiftBean> ticketsGiftsList;
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(ticketNo, ticketId, ticketName, 
				beginTime, endTime, status, isUsed, bindingTime, discount, 
				discountAmount, discountCumulative, grantAmount, ticketsGiftsList);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof TicketBean) {
			TicketBean other = (TicketBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
