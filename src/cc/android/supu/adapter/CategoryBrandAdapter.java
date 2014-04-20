package cc.android.supu.adapter;

import java.util.ArrayList;

import cc.android.supu.R;
import cc.android.supu.SupuApplication;
import cc.android.supu.bean.CategoryBrandBean;
import cc.android.supu.tools.AsyncImageLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author sss 分类Adapter
 */
public class CategoryBrandAdapter extends BaseAdapter {
	private ArrayList<CategoryBrandBean> categoryBrands;
	private LayoutInflater inflater;
	private boolean hasImg;
	private Context context1;

	public CategoryBrandAdapter(Context context, ArrayList<CategoryBrandBean> categoryBrands, boolean hasImg) {
		this.categoryBrands = categoryBrands;
		this.hasImg = hasImg;
		this.context1 = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return categoryBrands.size();
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
		CategoryBrandBean bean = categoryBrands.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.categorybrand_list_item, parent, false);
			item = new Item(convertView);
			convertView.setTag(item);
		} else {
			item = (Item) convertView.getTag();
		}
		item.textView.setText(bean.name);
		Bitmap bitmap1 = BitmapFactory.decodeResource(context1.getResources(), R.drawable.default_normal);
		if (hasImg) {
			String url = bean.categoryImg;
			item.imageView.setTag(url);
			Bitmap bitmap = AsyncImageLoader.loadIndexDrawable(context1, url, new AsyncImageLoader.ImageCallback() {

				@Override
				public void imageLoaded(Bitmap bitmap, String imageUrl) {
					String url = (String) item.imageView.getTag();
					if (bitmap != null && imageUrl.equals(url))
						item.imageView.setImageBitmap(bitmap);
				}
			}, false, false, 0, false);
			if (bitmap != null)
				item.imageView.setImageBitmap(bitmap);
			else {
				item.imageView.setImageBitmap(bitmap1);
			}
		} else {
			// item.imageView.setImageBitmap(bitmap1);
			item.imageView.setVisibility(View.GONE);
		}
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
