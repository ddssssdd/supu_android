package cc.android.supu.adapter;

/**
 * 最近浏览的Adapter
 * zsx
 */
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cc.android.supu.R;
import cc.android.supu.bean.ArticleGoodsBean;
import cc.android.supu.tools.AsyncImageLoader;

public class RecentViewAdapter extends BaseAdapter {
	/** 最近浏览列表 **/
	private ArrayList<ArticleGoodsBean> list;
	/** 布局文件 **/
	private LayoutInflater mInflater;

	private Context mContext;

	public RecentViewAdapter(Context context,
			ArrayList<ArticleGoodsBean> articleGoodsBean) {
		mContext = context;
		list = articleGoodsBean;
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
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.recentview_item, null);
			holder = new ViewHolder(convertView);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.recentViewName.setText(list.get(position).GoodsName + "");
		holder.recentViewShop.setText("￥" + list.get(position).Price + "元");
		// 图片的处理
		String imgUrl = list.get(position).ImgFile;
		holder.recentViewImage.setTag(imgUrl);
		Bitmap bitmap = AsyncImageLoader.loadDrawable(mContext, imgUrl,
				new AsyncImageLoader.ImageCallback() {

					@Override
					public void imageLoaded(Bitmap bitmap, String imageUrl) {
						String imgUrl = (String) holder.recentViewImage
								.getTag();
						if (bitmap != null && imageUrl.equals(imgUrl)) {
							holder.recentViewImage.setImageBitmap(bitmap);
							// holder.ProgressBar.setVisibility(View.GONE);
						}
					}
				}, true, 1);
		if (bitmap != null) {
			holder.recentViewImage.setImageBitmap(bitmap);
			// holder.ProgressBar.setVisibility(View.GONE);
		} else {
			holder.recentViewImage.setImageBitmap(holder.getBitmap());
			// holder.ProgressBar.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	class ViewHolder {
		/** 商品的图片 名字 介绍 价格 */
		private ImageView recentViewImage;
		private ProgressBar ProgressBar;
		private TextView recentViewName;
		private TextView recentViewShop;

		public ViewHolder(View view) {
			recentViewImage = (ImageView) view
					.findViewById(R.id.recentViewImage);
			ProgressBar = (ProgressBar) view
					.findViewById(R.id.recentViewprogressbar);

			recentViewName = (TextView) view.findViewById(R.id.recentViewName);
			recentViewShop = (TextView) view.findViewById(R.id.recentViewShop);
		}

		public Bitmap getBitmap() {
			Bitmap bitmap = BitmapFactory.decodeResource(
					mContext.getResources(), R.drawable.defalut_middle);
			return bitmap;
		}
	}
}
