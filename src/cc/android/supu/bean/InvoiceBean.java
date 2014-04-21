package cc.android.supu.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class InvoiceBean implements Parcelable {
	
	
	public String invoiceID;
	public String invoiceName;
	public String highDescription;
	public String description;
	public String ordering;
	public boolean isSelected;
	public int hashCode(){
		return BeanTools.createHashcode(invoiceID,invoiceName,highDescription,description,ordering);
	}
	public int getOrder(){
		int result = Integer.parseInt(ordering);
		return result;
	}
	@Override
	public boolean equals(Object o){
		if (o==null)
			return false;
		if (this==o)
			return true;
		if (o instanceof InvoiceBean){
			InvoiceBean other = (InvoiceBean)o;
			return this.hashCode()==other.hashCode();
		}
		return false;
	}
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(invoiceID);
		dest.writeString(invoiceName);
		dest.writeString(highDescription);
		dest.writeString(description);
		dest.writeString(ordering);
		
	}
	public static final Parcelable.Creator<InvoiceBean> CREATOR = new Creator<InvoiceBean>(){
		@Override
		public InvoiceBean[] newArray(int size){
			return null;
		}
		@Override
		public InvoiceBean createFromParcel(Parcel source){
			InvoiceBean invoice = new InvoiceBean();
			invoice.invoiceID = source.readString();
			invoice.invoiceName = source.readString();
			invoice.highDescription = source.readString();
			invoice.description = source.readString();
			invoice.ordering = source.readString();
			return invoice;
		}
		
	};
}
