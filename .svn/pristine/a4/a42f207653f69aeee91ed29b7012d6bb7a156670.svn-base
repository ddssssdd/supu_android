package cc.android.supu.adapter;

import java.util.ArrayList;

import cc.android.supu.R;
import cc.android.supu.bean.IndexPicBean;
import cc.android.supu.tools.AsyncImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;

/**
 * @author sss 首页顶部图片Gallery
 */
public class IndexGalleryAdapter extends BaseAdapter {
	private ArrayList<IndexPicBean> picBeans;
	private Context context;
	private LayoutInflater inflater;
	private Bitmap defaultBitmap;

	public IndexGalleryAdapter(Context context, ArrayList<IndexPicBean> picBeans) {
		this.context = context;
		this.picBeans = picBeans;
		inflater = LayoutInflater.from(context);
		defaultBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.defalut_big);
	}

	@Override
	public int getCount() {
		return picBeans.size();
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
		IndexPicBean picBean = picBeans.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.index_gallery_item, parent, false);
			item = new Item(convertView);
			convertView.setTag(item);
		} else {
			item = (Item) convertView.getTag();
		}
		item.imageView.setTag(picBean.picUrl);
		Bitmap bitmap = AsyncImageLoader.loadIndexDrawable(context,picBean.picUrl, new AsyncImageLoader.ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, String imageUrl) {
				String imgUrl = (String) item.imageView.getTag();
				if (bitmap != null && imageUrl.equals(imgUrl)) {
					item.imageView.setImageBitmap(bitmap);
					item.progressBar.setVisibility(View.GONE);
				}
			}
		}, true, true, 2,false);
		if (bitmap != null) {
			item.imageView.setImageBitmap(bitmap);
			item.progressBar.setVisibility(View.GONE);
		} else {
			item.imageView.setImageBitmap(defaultBitmap);
			item.progressBar.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	private class Item {
		private ImageView imageView;
		private ProgressBar progressBar;

		public Item(View view) {
			imageView = (ImageView) view.findViewById(R.id.itop_item_img);
			progressBar = (ProgressBar) view.findViewById(R.id.itop_item_progressbar);
		}
	}
}
