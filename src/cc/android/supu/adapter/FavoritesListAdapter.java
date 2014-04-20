package cc.android.supu.adapter;

/**
 * 收藏列表的Adapter
 * zsx
 */
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cc.android.supu.R;
import cc.android.supu.bean.GoodsBean;
import cc.android.supu.tools.AsyncImageLoader;
import cc.android.supu.view.DrawLineTextView;

public class FavoritesListAdapter extends BaseAdapter {
	/** 收藏列表 **/
	private List<GoodsBean> list;
	/** 布局文件 **/
	private LayoutInflater mInflater;
	/** 上下文 **/
	private Context context;
	/** 是否显示删除 **/
	private boolean isEdit = false;
	/* 是否滑动 */
	private boolean isScrolling = false;

	public FavoritesListAdapter(Context context, List<GoodsBean> goodsBean) {

		this.context = context;
		list = goodsBean;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 设置是否是编辑状态
	 * 
	 * @param isEdit
	 *            true表示在编辑，false表示不再编辑状态。
	 */
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public void setIsScrolling(boolean isScrolling) {
		this.isScrolling = isScrolling;
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
		GoodsBean goodsBean = list.get(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.favorites_list_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.GoodsName.setText(list.get(position).goodsName);
		holder.GoodsSlogan.setText(list.get(position).goodsSlogan);
		holder.CommentCount.setText(list.get(position).commentCount);
		holder.MarketPrice.setText("￥" + list.get(position).marketPrice + "元");

		holder.ShopPrice.setText("￥" + list.get(position).shopPrice + "元");
		// 图片的处理
		// String imgUrl =
		// AsyncImageLoader.getImagUrl(list.get(position).goodsSN,
		// list.get(position).imgFile);
		String imgUrl = list.get(position).imgFile;
		// if (!isScrolling) {
		holder.GOODSImage.setTag(imgUrl);
		Bitmap bitmap = AsyncImageLoader.loadDrawable(context, imgUrl,
				new AsyncImageLoader.ImageCallback() {

					@Override
					public void imageLoaded(Bitmap bitmap, String imageUrl) {
						String imgUrl = (String) holder.GOODSImage.getTag();
						if (bitmap != null && imageUrl.equals(imgUrl)) {
							holder.GOODSImage.setImageBitmap(bitmap);
							// holder.progressBar.setVisibility(View.GONE);
						}
					}
				}, true, 1);
		if (bitmap != null) {
			holder.GOODSImage.setImageBitmap(bitmap);
			// holder.progressBar.setVisibility(View.GONE);
			// } else {
			// holder.GOODSImage.setImageBitmap(holder.getBitmap());
			// // holder.progressBar.setVisibility(View.VISIBLE);
			// }
		} else {
			holder.GOODSImage.setImageBitmap(holder.getBitmap());
		}

		holder.delete.setTag(goodsBean);
		holder.delete.setOnClickListener((OnClickListener) context);
		holder.delete.setVisibility(isEdit ? View.VISIBLE : View.GONE);

		convertView.setTag(R.id.tag_favoritesAdapter_iconLabel,
				holder.GOODSImage);
		return convertView;
	}

	/** 内部类 **/
	private class ViewHolder {
		/** 货物图片， 货物的名字 ，广告语，评论数，市场价，速普价 */
		private ImageView GOODSImage;
		private TextView GoodsName;
		private TextView GoodsSlogan;
		private TextView CommentCount;
		private cc.android.supu.view.DrawLineTextView MarketPrice;
		private TextView ShopPrice;
		private ProgressBar progressBar;
		private TextView delete;

		private ViewHolder(View view) {
			progressBar = (ProgressBar) view
					.findViewById(R.id.favorites_progressbar);
			GOODSImage = (ImageView) view.findViewById(R.id.GOODSImage);
			GoodsName = (TextView) view.findViewById(R.id.GoodsName);
			GoodsSlogan = (TextView) view.findViewById(R.id.GoodsSlogan);
			CommentCount = (TextView) view.findViewById(R.id.CommentCount);
			MarketPrice = (DrawLineTextView) view
					.findViewById(R.id.MarketPrice);
			ShopPrice = (TextView) view.findViewById(R.id.ShopPrice);
			delete = (TextView) view.findViewById(R.id.favorites_delete);
		}

		public Bitmap getBitmap() {
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.defalut_middle);
			return bitmap;
		}

	}
}
