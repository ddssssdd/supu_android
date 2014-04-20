package cc.android.supu.adapter;

import java.util.List;

import cc.android.supu.R;
import cc.android.supu.bean.PaymentBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 支付方式列表适配器
 * @author sheng
 *
 */
public class PayMentListAdapter extends BaseAdapter {
	
	private List<PaymentBean> mList;
	private PaymentBean paymentBean;
	private LayoutInflater inflater;
	private Context mContext;
	
	public PayMentListAdapter(Context context, List<PaymentBean> list){
		mContext = context;
		mList = list;
		inflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CacheView cacheView = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.selectpayment_item, null);
			cacheView = new CacheView();
			cacheView.nameTv = (TextView) convertView.findViewById(R.id.name);
			cacheView.checkmark = (ImageView) convertView.findViewById(R.id.checkmark);
			convertView.setTag(cacheView);
		}else{
			cacheView = (CacheView) convertView.getTag();
		}
		paymentBean = mList.get(position);
		cacheView.nameTv.setText(paymentBean.paymentName);
		cacheView.checkmark.setVisibility(paymentBean.isVisible ? View.VISIBLE : View.INVISIBLE);
		convertView.setTag(R.id.tag_payment_checkmark, cacheView.checkmark);
		return convertView;
	}
	
	class CacheView{
		TextView nameTv;
		ImageView checkmark;
	}

}
