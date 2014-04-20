package cc.android.supu.adapter;

/**
 * 订单列表的Adapter
 * zsx
 */
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cc.android.supu.R;
import cc.android.supu.bean.OrderBean;

public class OrderListAdapter extends BaseAdapter {
	/** 订单列表 **/
	private List<OrderBean> list;
	/** 布局文件 **/
	private LayoutInflater mInflater;
	/** 上下文 **/
	private Context context;

	public OrderListAdapter(Context context, List<OrderBean> orderBean) {

		this.context = context;
		list = orderBean;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		OrderBean orderBean = list.get(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.order_item, null);
			holder = new ViewHolder();

			holder.OrderSN = (TextView) convertView.findViewById(R.id.OrderSN);
			holder.OrderAmount = (TextView) convertView
					.findViewById(R.id.OrderAmount);
			holder.OrderAddTime = (TextView) convertView
					.findViewById(R.id.OrderAddTime);
			holder.OrderStatus = (TextView) convertView
					.findViewById(R.id.OrderStatus);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.OrderSN.setText(orderBean.OrderSN);
		holder.OrderAmount.setText("￥"
				+ getOrderAmount(orderBean.OrderAmount, orderBean.ShippingFee)+"元");
		holder.OrderAddTime.setText(getTime(orderBean.AddTime));
		holder.OrderStatus.setText(orderBean.OrderStatus);

		return convertView;
	}

	/** 内部类 **/
	private class ViewHolder {
		/** 订单号 总价 下单时间 状态 */
		private TextView OrderSN;
		private TextView OrderAmount;
		private TextView OrderAddTime;
		private TextView OrderStatus;
	}

	/**
	 * 得到订单的总价
	 * 
	 * @param OrderAmount
	 * @param ShippingFee
	 * @return
	 */
	private String getOrderAmount(String OrderAmount, String ShippingFee) {
		double count = 0.00;
//		System.out.println("OrderAmount"+OrderAmount);
//		System.out.println("ShippingFee"+ShippingFee);
		double count1 = Double.parseDouble(OrderAmount);
		double count2 = Double.parseDouble(ShippingFee);
		DecimalFormat df = new DecimalFormat("0.00");
		if (count1 + count2 >= 0) {
			count = count1 + count2;
			
		}
//		System.out.println("count"+df.format(count));
		return df.format(count);
	}

	/**
	 * 得到时间
	 */
	private String getTime(String scrTime) {
		String strTime = scrTime.trim();
		int length = strTime.length();
		if (length < 13) {
			for (int j = 0; j < 13 - length; j++) {
				strTime = strTime + "0";
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Long time = new Long(strTime);
		return format.format(time);
	}

}
