package cc.android.supu.adapter;

import java.util.ArrayList;
import cc.android.supu.GoodsDetailsActivity;
import cc.android.supu.R;
import cc.android.supu.bean.ActivityItemBean;
import cc.android.supu.tools.AsyncImageLoader;
import cc.android.supu.tools.AsyncListImageLoader;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ActivityAdapter extends BaseAdapter implements OnClickListener {
	private ArrayList<ActivityItemBean> itemBeans;
	private LayoutInflater inflater;
	private Context context;
//	private ListView mListView;
//
//	private AsyncListImageLoader imageLoader;

	// private boolean isScrolling = false;

	// public void setScrolling(boolean isScrolling) {
	// this.isScrolling = isScrolling;
	// }

	public ActivityAdapter(Context context, ListView listView, ArrayList<ActivityItemBean> itemBeans) {
		this.itemBeans = itemBeans;
//		this.mListView = listView;
		inflater = LayoutInflater.from(context);
		this.context = context;
//		imageLoader = new AsyncListImageLoader();
	}

	/**
	 * 暂停下载图片线程
	 */
	public void lockThread() {
		System.out.println("暂停下载");
//		imageLoader.lockThread();
	}

	/**
	 * 唤醒锁定的线程
	 */
	public void notifyThread() {
		// System.out.println("唤醒下载线程");
//		int start = mListView.getFirstVisiblePosition();
//		int end = mListView.getLastVisiblePosition();
//		if (end >= getCount()) {
//			end = getCount() - 1;
//		}
//		imageLoader.setLoadLimit(start, end);
//		imageLoader.unlock();
	}

	@Override
	public int getCount() {
		return itemBeans.size();
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
		final ActivityItemBean bean = itemBeans.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_item, parent, false);
			item = new Item(convertView);
			convertView.setTag(item);
			convertView.setOnClickListener(this);
		} else {
			item = (Item) convertView.getTag();
			item.iconLabel.setImageResource(R.drawable.defalut_middle);
			if (!bean.isLoaded) {
				item.progressBar.setVisibility(View.VISIBLE);
				item.iconLabel.setImageResource(R.drawable.defalut_middle);
			} /*
			 * else { }
			 */
		}
		// if (!isScrolling) {
		// String imgUrl = AsyncImageLoader.getImagUrl(bean.goodsSN,
		// bean.imgFile);
		String imgUrl = bean.imgFile;
		item.iconLabel.setTag(imgUrl);
		Bitmap bitmap = AsyncImageLoader.loadDrawable(context, imgUrl, new AsyncImageLoader.ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, String imageUrl) {
				String imgurl = (String) item.iconLabel.getTag();
				if (imageUrl.equals(imgurl)) {
					item.progressBar.setVisibility(View.GONE);
					bean.isLoaded = true;
					if (bitmap != null /* && !isScrolling */) {
						item.iconLabel.setImageBitmap(bitmap);
					}
				}
			}
		}, false, 1);
		if (bitmap != null) {
			bean.isLoaded = true;
			item.iconLabel.setImageBitmap(bitmap);
			item.progressBar.setVisibility(View.GONE);
		}/*
		 * else { item.iconLabel.setImageResource(R.drawable.defalut_middle); }
		 */
		// }
		item.nameLabel.setText(bean.goodsName);
		item.activityLabel.setText(bean.goodsSlogan);
		item.commentLabel.setText(bean.commentCount + "人评价");
		if (bean.isNoStock) {
			item.priceLabel.setTextColor(Color.parseColor("#666666"));
			String str = "￥" + bean.shopPrice;
			item.priceLabel.setText(str);
			item.noLabel.setVisibility(View.VISIBLE);
		} else {
			item.priceLabel.setText("￥" + bean.shopPrice);
			item.noLabel.setVisibility(View.GONE);
			item.priceLabel.setTextColor(Color.parseColor("#FF0000"));
		}
		convertView.setTag(R.id.tag_activityAdapter_iconLabel, bean);
		return convertView;
	}

	static class Item {
		private ImageView iconLabel;
		private TextView nameLabel;
		private TextView activityLabel;
		private TextView commentLabel;
		private TextView priceLabel;
		private TextView noLabel;
		private ProgressBar progressBar;

		public Item(View view) {
			iconLabel = (ImageView) view.findViewById(R.id.item_icon);
			nameLabel = (TextView) view.findViewById(R.id.nameLabel);
			activityLabel = (TextView) view.findViewById(R.id.activityLabel);
			commentLabel = (TextView) view.findViewById(R.id.commentLabel);
			priceLabel = (TextView) view.findViewById(R.id.priceLabel);
			noLabel = (TextView) view.findViewById(R.id.noLabel);
			progressBar = (ProgressBar) view.findViewById(R.id.item_progress);
		}
	}

	@Override
	public void onClick(View v) {
		ActivityItemBean bean = (ActivityItemBean) v.getTag(R.id.tag_activityAdapter_iconLabel);
		Intent intent = new Intent(context, GoodsDetailsActivity.class);
		intent.putExtra("goodsSN", bean.goodsSN);
		intent.putExtra("imgFile", bean.imgFile);
		context.startActivity(intent);
	}
}
