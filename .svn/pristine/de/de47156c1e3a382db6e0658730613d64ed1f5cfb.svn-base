package cc.android.supu.bean;


import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
/**
 * 配送方式bean
 * @author sheng
 *
 */
public class ShippingBean implements Parcelable {

	
	
	/** 配送方式编号*/
	public String shippingID;
	
	/** 配送方式代码*/
	public String shippingCode;
	
	/** 配送方式名称*/
	public String shippingName;
	
	/** 配送方式说明*/
	public String shippingDesc;
	
	/** 配送方式排序*/
	public String ordering;
	
	/** 配送支付首重*/
	public String basicFee;
	
	/** 配送方式续重*/
	public String stepFee;
	
	/** 配送支付到货时间*/
	public String transitTime;
	
	/** 配送支付*/
	public String freeLimit;
	
	public boolean isVisible;
	

	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(shippingID, shippingCode, shippingName, shippingDesc, ordering, ordering,
				basicFee, stepFee, transitTime, freeLimit, isVisible);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof ShippingBean) {
			ShippingBean other = (ShippingBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(shippingID);
		dest.writeString(shippingCode);
		dest.writeString(shippingName);
		dest.writeString(shippingDesc);
		dest.writeString(ordering);
		dest.writeString(basicFee);
		dest.writeString(stepFee);
		dest.writeString(transitTime);
		dest.writeString(freeLimit);
		boolean[] boo = {isVisible};
		dest.writeBooleanArray(boo);
	}
	
	public static final Parcelable.Creator<ShippingBean> CREATOR = new Creator<ShippingBean>() {
		
		@Override
		public ShippingBean[] newArray(int size) {
			// TODO Auto-generated method stub
			
			return null;
		}
		
		@Override
		public ShippingBean createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			ShippingBean shipping = new ShippingBean();
			shipping.shippingID = source.readString();
			shipping.shippingCode = source.readString();
			shipping.shippingName = source.readString();
			shipping.shippingDesc = source.readString();
			shipping.ordering = source.readString();
			shipping.basicFee = source.readString();
			shipping.stepFee = source.readString();
			shipping.transitTime = source.readString();
			shipping.freeLimit = source.readString();
			boolean[] boo = new boolean[1];
			source.readBooleanArray(boo);
			shipping.isVisible = boo[0] ;
			return shipping;
		}
	};
}
