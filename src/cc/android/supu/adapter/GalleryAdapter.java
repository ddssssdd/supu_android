package cc.android.supu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import cc.android.supu.R;
import cc.android.supu.tools.AsyncImageLoader;
import cc.android.supu.view.MyImageView;

/**
 * Gallery的适配器类，主要用于加载图片
 * 
 * @author zsx
 * 
 */
public class GalleryAdapter extends BaseAdapter {
	private ArrayList<String> bmpList;
	private Context context;
	private LayoutInflater inflater;

	public GalleryAdapter(Context context, ArrayList<String> bmpList) {
		this.context = context;
		this.bmpList = bmpList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return bmpList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		 if (convertView == null) {
		 convertView = inflater.inflate(R.layout.bigimage_gallery_item,
		 parent, false);
		 holder = new ViewHolder(convertView);
		 convertView.setTag(holder);
		 } else {
		 holder = (ViewHolder) convertView.getTag();
		 }
		 
		String imgUrl = AsyncImageLoader.getImageUrl(bmpList.get(position));
		holder.view.setTag(imgUrl);
		Bitmap bitmap = AsyncImageLoader.loadIndexDrawable(context,imgUrl,
				new AsyncImageLoader.ImageCallback() {

					@Override
					public void imageLoaded(Bitmap bitmap, String imageUrl) {
						 String url = (String) holder.view.getTag();
						if (bitmap != null&&imageUrl.equals(url)) {
							 holder.view.setImageBitmap(bitmap);
							 holder.View1.setVisibility(View.GONE);
							 holder.progressbar.setVisibility(View.GONE);
						}
					}
				},true, false, 2, false);
		if (bitmap != null) {
			 holder.view.setImageBitmap(bitmap);
			 holder.View1.setVisibility(View.GONE);
			 holder.progressbar.setVisibility(View.GONE);
		} else {
			 holder.View1.setVisibility(View.VISIBLE);
			 holder.progressbar.setVisibility(View.VISIBLE);
		}
		return convertView;
	
	}

	private class ViewHolder {
		/** 货物图片， 进度条 放大缩小 按钮 */
		private MyImageView view;
		private ProgressBar progressbar;
		
		private ImageView View1;

		public ViewHolder(View v) {
			view = (MyImageView) v.findViewById(R.id.bigImageView);
			View1 = (ImageView)v.findViewById(R.id.bigImageView1);
			progressbar = (ProgressBar) v
					.findViewById(R.id.BigSmall_progressbar);
		}
	}
}
