package cc.android.supu.bean;

import java.io.Serializable;

/**
 * 订单bean
 * 
 * @author zsx
 * 
 */
public class OrderBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 订单编号 */
	public String OrderSN;

	/** 订单流水号 */
	public String RunningNumber;

	/** 收货人 */
	public String Consignee;

	/** 省编号 */
	public String ProvinceID;

	/** 市编号 */
	public String CityID;
	/** 县区编号 */
	public String AreaID;
	/** 邮政编码 */
	public String ZipCode;
	/** 收货地址 */
	public String Address;
	/** 电话 */
	public String Tel;
	/** 手机 */
	public String Mobile;
	/** 优惠金额 */
	public String Discount;
	/** 订单金额 */
	public String OrderAmount;
	/** 订单状态 */
	public String OrderStatus;
	/** 邮箱 */
	public String Email;
	/** 支付方式 */
	public String PayName;
	/** 支付状态 */
	public String PayStatus;
	/** 配送方式 */
	public String ShippingName;
	/** 配送费 */
	public String ShippingFee;
	/** 订单用户账号 */
	public String Account;
	/** 订单用户编号 */
	public String MemberId;
	/** 订单提交时间 */
	public String AddTime;

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(OrderSN, RunningNumber, Consignee,
				ProvinceID, CityID, AreaID, ZipCode, Address, Tel, Mobile,
				Discount, OrderAmount, OrderStatus, Email, PayName, PayStatus,
				ShippingName, ShippingFee,Account,MemberId,AddTime);
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof OrderBean) {
			OrderBean other = (OrderBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
