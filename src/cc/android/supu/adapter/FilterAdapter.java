package cc.android.supu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cc.android.supu.R;
import cc.android.supu.bean.FilterBean;

public class FilterAdapter<T extends FilterBean> extends BaseAdapter {

	private ArrayList<T> arrayList;
	private LayoutInflater inflater;

	private ItemChoosedListener listener;

	public FilterAdapter(Context context, ArrayList<T> arrayList,
			ItemChoosedListener listener) {
		this.arrayList = arrayList;
		this.listener = listener;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return arrayList.size();
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
		FilterBean bean = arrayList.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.filter_item,
					parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(bean.name);
		// if (ID != null && ID.equals(bean.id)) {
		// textView.setTextColor(Color.WHITE);
		// textView.setBackgroundResource(R.drawable.filter_item_choosed);
		// if (listener != null)
		// listener.itemChoosed(position, bean, true);
		// } else {
		// if (arrayList.size() == 1) {
		// textView.setTextColor(Color.WHITE);
		// textView.setBackgroundResource(R.drawable.filter_item_choosed);
		// if (listener != null)
		// listener.itemChoosed(position, bean);
		// } else {
		switch (bean.type) {
		case -1:
			holder.textView.setTextColor(Color.parseColor("#363636"));
			holder.textView.setBackgroundResource(R.drawable.filter_item_null);
			break;
		case 0:
			holder.textView.setTextColor(Color.BLACK);
			holder.textView.setBackgroundResource(R.drawable.filter_item_normal);
			break;
		case 1:
			holder.textView.setTextColor(Color.WHITE);
			holder.textView.setBackgroundResource(R.drawable.filter_item_choosed);
			if (listener != null)
				listener.itemChoosed(position, bean, false);
			break;
		// }
		// }
		}
		return convertView;
	}

	/**
	 * @author sss 当点击监听器
	 */
	public static interface ItemChoosedListener {

		/**
		 * @param position
		 *            Item的位置
		 * @param value
		 *            里面的值
		 * @param isDefault
		 *            是否是默认的选中还是点击选中
		 */
		void itemChoosed(int position, FilterBean bean, boolean isDefault);
	}

	private class ViewHolder {

		private View view;
		private TextView textView;

		private ViewHolder(View view) {
			textView = (TextView) view.findViewById(R.id.filter_item);
		}
	}
}