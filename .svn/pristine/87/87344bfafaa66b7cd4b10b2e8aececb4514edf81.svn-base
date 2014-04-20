package cc.android.supu.adapter;

/**
 * 收货地址的Adapter
 * zsx
 */
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cc.android.supu.R;
import cc.android.supu.bean.ConsigneeBean;

public class AdressAdapter extends BaseAdapter {
	/**地址列表**/
	private ArrayList<ConsigneeBean> list;
	/**布局文件**/
	private LayoutInflater mInflater;

	public AdressAdapter(Context context, ArrayList<ConsigneeBean> consigneeBean) {
		list = consigneeBean;
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adress_list_item, null);
			holder = new ViewHolder();
			holder.Addressee = (TextView) convertView
					.findViewById(R.id.Addressee);
			holder.call = (TextView) convertView.findViewById(R.id.call);
			holder.Address = (TextView) convertView.findViewById(R.id.Address);
			holder.zipCode = (TextView) convertView.findViewById(R.id.zipCode);
			holder.AdressDefault = (TextView) convertView
					.findViewById(R.id.AdressDefault);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.Addressee.setText(list.get(position).Consignee);

		if (!list.get(position).Mobile.trim().equals("")) {
			holder.call.setText(list.get(position).Mobile);
		} else {
			holder.call.setText(list.get(position).Tel);
		}

		holder.Address.setText(list.get(position).Address+list.get(position).AddressInfo);
		holder.zipCode.setText(list.get(position).ZipCode);
		if (list.get(position).IsDefault) {
			holder.AdressDefault.setVisibility(View.VISIBLE);
		} else {
			holder.AdressDefault.setVisibility(View.GONE);
		}

		return convertView;
	}
	/**内部类**/
	class ViewHolder {
		/** 收货人 电话 地址 编码 是否是默认地址 */
		private TextView Addressee;
		private TextView call;
		private TextView Address;
		private TextView zipCode;
		private TextView AdressDefault;
	}
}
