package cc.android.supu.adapter;

import java.util.ArrayList;

import cc.android.supu.R;
import cc.android.supu.bean.GreedBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author sss 孕育宝典Adapter
 */
public class GreedAdapter extends BaseAdapter {
	private ArrayList<GreedBean> greedBeans;
	private LayoutInflater inflater;

	public GreedAdapter(Context context, ArrayList<GreedBean> greedBeans) {
		this.greedBeans = greedBeans;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return greedBeans.size();
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
		final Item item;
		GreedBean bean = greedBeans.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.greed_list_item, parent, false);
			item = new Item(convertView);
			convertView.setTag(item);
		} else {
			item = (Item) convertView.getTag();
		}
		item.textView.setText(bean.categoryName);
		return convertView;
	}

	class Item {
		private TextView textView;
		private ImageView imageView;

		public Item(View view) {
			textView = (TextView) view.findViewById(R.id.itemvalue);
			imageView = (ImageView) view.findViewById(R.id.itemicon);
		}
	}

}
