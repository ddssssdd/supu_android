package cc.android.supu.bean;

import java.io.Serializable;

/**
 * 订单详情bean
 * 
 * @author zsx
 * 
 */
public class OrderInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 订单详细编号 */
	public int Id;
	
	/** 订单编号 */
	public int OrderSN;

	/** 商品编号 */
	public String GoodsSN;

	/** 商品名称 */
	public String GoodsName;

	/** 商品图片的URL */
	public String ImgFile;
	/** 商品价格 */
	public String Price;
	/** 优惠金额 */
	public String Discount;
	/** 数量 */
	public int Count;
	/** 是否赠品 */
	public int isGift;
	/** 积分 */
	public String Integral;
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(Id,OrderSN,GoodsSN,GoodsName,ImgFile,Price,Discount,Count,isGift,Integral );
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof OrderInfoBean) {
			OrderInfoBean other = (OrderInfoBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
