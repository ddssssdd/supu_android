package cc.android.supu.adapter;

/**
 * 孕婴宝典的Adapter
 * zsx
 */
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
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

public class GreedInfoAapter extends BaseAdapter {
	/** 孕婴宝典列表 **/
	private ArrayList<ArticleGoodsBean> list;
	/** 布局文件 **/
	private LayoutInflater mInflater;

	private Context context;

	/** 构造函数 **/
	public GreedInfoAapter(Context context, ArrayList<ArticleGoodsBean> articleGoodsBean) {
		this.context = context;
		list = articleGoodsBean;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			convertView = mInflater.inflate(R.layout.greedinfo__list_item, null);
			holder = new ViewHolder();
			holder.greedInfoImage = (ImageView) convertView.findViewById(R.id.greedInfoImage);
			holder.ProgressBar = (ProgressBar) convertView.findViewById(R.id.greedInfo_progressbar);

			holder.greedinfoName = (TextView) convertView.findViewById(R.id.greedinfoName);
			holder.greedinfoContent = (TextView) convertView.findViewById(R.id.greedinfoContent);
			holder.greedShop = (TextView) convertView.findViewById(R.id.greedShop);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// System.out.println("list.get(position).GoodsName"+list.get(position).GoodsName);
		holder.greedinfoName.setText(list.get(position).GoodsName + "");
		holder.greedinfoContent.setText(list.get(position).GoodsDescription + "");
		holder.greedShop.setText("￥" + list.get(position).Price + "");
		// 图片的处理
		String imgUrl = list.get(position).ImgFile;
		holder.greedInfoImage.setTag(imgUrl);
		Bitmap bitmap = AsyncImageLoader.loadDrawable(context, imgUrl, new AsyncImageLoader.ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, String imageUrl) {
				String imgUrl = (String) holder.greedInfoImage.getTag();
				if (bitmap != null && imageUrl.equals(imgUrl)) {
					holder.greedInfoImage.setImageBitmap(bitmap);
					holder.ProgressBar.setVisibility(View.GONE);
				}
			}
		}, true, 1);
		if (bitmap != null) {
			holder.greedInfoImage.setImageBitmap(bitmap);
			holder.ProgressBar.setVisibility(View.GONE);
		} else {
			holder.ProgressBar.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	/** 内部类 **/
	private class ViewHolder {
		/** 商品的图片 名字 介绍 价格 */
		private ImageView greedInfoImage;
		private ProgressBar ProgressBar;
		private TextView greedinfoName;
		private TextView greedinfoContent;
		private TextView greedShop;
	}
}
