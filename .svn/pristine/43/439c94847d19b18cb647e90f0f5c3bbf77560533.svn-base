package cc.android.supu.bean;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 支付方式bean
 * @author sheng
 *
 */
public class PaymentBean implements Parcelable {

	
	/** 支付方式编号*/
	public String paymentID;
	
	/** 支付方式代码*/
	public String paymentCode;
	
	/** 支付方式名称*/
	public String paymentName;
	
	/** 支付方式说明*/
	public String paymentDesc;
	
	/** 支付方式排序*/
	public String ordering;
	
	/** 是否在线支付*/
	public String isOnline;
	
	public boolean isVisible;
	
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(paymentID, paymentCode, paymentName, paymentDesc, ordering, isOnline, isVisible);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof PaymentBean) {
			PaymentBean other = (PaymentBean) o;
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
		dest.writeString(paymentID);
		dest.writeString(paymentCode);
		dest.writeString(paymentName);
		dest.writeString(paymentDesc);
		dest.writeString(ordering);
		dest.writeString(isOnline);
		boolean[] boo = {isVisible};
		dest.writeBooleanArray(boo);
	}
	
	public static final Parcelable.Creator<PaymentBean> CREATOR = new Creator<PaymentBean>() {
		
		@Override
		public PaymentBean[] newArray(int size) {
			// TODO Auto-generated method stub
			
			return null;
		}
		
		@Override
		public PaymentBean createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			PaymentBean payment = new PaymentBean();
			payment.paymentID = source.readString();
			payment.paymentCode = source.readString();
			payment.paymentName = source.readString();
			payment.paymentDesc = source.readString();
			payment.ordering = source.readString();
			payment.isOnline = source.readString();
			boolean[] boo = new boolean[1];
			source.readBooleanArray(boo);
			payment.isVisible = boo[0] ;
			return payment;
		}
	};
	
}
