package cc.android.supu.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.android.supu.GoodsDetailsActivity;
import cc.android.supu.R;
import cc.android.supu.bean.IndexGoodBean;
import cc.android.supu.tools.AsyncImageLoader;

/**
 * @author sss 首页顶部图片Gallery
 */
public class IndexGoodsGalleryAdapter extends BaseAdapter implements OnClickListener {
	private ArrayList<IndexGoodBean> goodBeans;
	private Context context;
	private LayoutInflater inflater;
	private Bitmap defatultBitmap;
	private int width;

	public IndexGoodsGalleryAdapter(Context context, ArrayList<IndexGoodBean> goodBeans) {
		this.context = context;
		this.goodBeans = goodBeans;
		inflater = LayoutInflater.from(context);
		defatultBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.defalut_middle);
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

		width = (dm.widthPixels - 40) / 3;
//		System.out.println("width" + width);
	}

	@Override
	public int getCount() {
		return (goodBeans.size() + 2) / 3;
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
		final GalleryItem galleryItem;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.index_productgallery_item, parent, false);
			galleryItem = new GalleryItem(convertView);
			convertView.setTag(galleryItem);
		} else {
			galleryItem = (GalleryItem) convertView.getTag();
		}
		showItem(galleryItem, position);
		if (position + 1 < goodBeans.size()) {
			showItem(galleryItem, position + 1);
		}
		if (position + 2 < goodBeans.size()) {
			showItem(galleryItem, position + 2);
		}

		return convertView;
	}

	private void showItem(final GalleryItem galleryItem, final int position) {
		IndexGoodBean good = goodBeans.get(position);
		String imgUrl = good.imageFile;
		galleryItem.getImageView(position).setTag(imgUrl);
		// System.out.println("good.imageFile:" + imgUrl);
		Bitmap bitmap = AsyncImageLoader.loadIndexDrawable(context, imgUrl, new AsyncImageLoader.ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, String imageUrl) {
				String imgUrl = (String) galleryItem.getImageView(position).getTag();
				if (bitmap != null && imageUrl.equals(imgUrl)) {
					galleryItem.getImageView(position).setImageBitmap(bitmap);
					galleryItem.getProgressBar(position).setVisibility(View.GONE);
				}
			}
		}, true, true, 1, false);
		if (bitmap != null) {
			galleryItem.getImageView(position).setImageBitmap(bitmap);
			galleryItem.getProgressBar(position).setVisibility(View.GONE);
		} else {
			galleryItem.getImageView(position).setImageBitmap(defatultBitmap);
			galleryItem.getProgressBar(position).setVisibility(View.VISIBLE);
		}
		galleryItem.getPriceLabel(position).setText("￥" + good.price + "元");
		galleryItem.getLayout(position).setTag(good);
		galleryItem.getLayout(position).setOnClickListener(this);
	}

	class GalleryItem {
		private RelativeLayout layout_one, layout_two, layout_three;
		private ImageView imageView_one, imageView_two, imageView_three;
		private TextView priceLabel_one, priceLabel_two, priceLabel_three;
		private ProgressBar progress_one, progress_two, progress_three;
		private View view;

		public GalleryItem(View view) {
			this.view = view;
		}

		public ProgressBar getProgressBar(int index) {
			index = index % 3;
			switch (index) {
			case 0:
				return getProgress_one();
			case 1:
				return getProgress_two();
			case 2:
				return getProgress_three();
			default:
				return getProgress_one();
			}
		}

		public ProgressBar getProgress_one() {
			if (progress_one == null)
				progress_one = (ProgressBar) view.findViewById(R.id.item1_progress);
			return progress_one;
		}

		public ProgressBar getProgress_two() {
			if (progress_two == null)
				progress_two = (ProgressBar) view.findViewById(R.id.item2_progress);
			return progress_two;
		}

		public ProgressBar getProgress_three() {
			if (progress_three == null)
				progress_three = (ProgressBar) view.findViewById(R.id.item3_progress);
			return progress_three;
		}

		public RelativeLayout getLayout(int index) {
			index = index % 3;
			switch (index) {
			case 0:
				return getLayout_one();
			case 1:
				return getLayout_two();
			case 2:
				return getLayout_three();
			default:
				return getLayout_one();
			}
		}

		public ImageView getImageView(int index) {
			index = index % 3;
			switch (index) {
			case 0:
				return getImageView_one();
			case 1:
				return getImageView_two();
			case 2:
				return getImageView_three();
			}
			return getImageView_one();
		}

		public TextView getPriceLabel(int index) {
			index = index % 3;
			switch (index) {
			case 0:
				return getPriceLabel_one();
			case 1:
				return getPriceLabel_two();
			case 2:
				return getPriceLabel_three();
			}
			return getPriceLabel_one();
		}

		public RelativeLayout getLayout_one() {
			if (layout_one == null)
				layout_one = (RelativeLayout) view.findViewById(R.id.item1_layout);
			LayoutParams para = layout_one.getLayoutParams();
			para.height = width;
			para.width = width;
			layout_one.setLayoutParams(para);
			return layout_one;
		}

		public RelativeLayout getLayout_two() {
			if (layout_two == null)
				layout_two = (RelativeLayout) view.findViewById(R.id.item2_layout);
			LayoutParams para = layout_two.getLayoutParams();
			para.height = width;
			para.width = width;
			layout_two.setLayoutParams(para);
			return layout_two;
		}

		public RelativeLayout getLayout_three() {
			if (layout_three == null)
				layout_three = (RelativeLayout) view.findViewById(R.id.item3_layout);
			LayoutParams para = layout_three.getLayoutParams();
			para.height = width;
			para.width = width;
			layout_three.setLayoutParams(para);
			return layout_three;
		}

		public ImageView getImageView_one() {
			if (imageView_one == null)
				imageView_one = (ImageView) view.findViewById(R.id.item1_icon);
			LayoutParams para = imageView_one.getLayoutParams();
			para.height = width;
			para.width = width;
			imageView_one.setLayoutParams(para);
			return imageView_one;
		}

		public ImageView getImageView_two() {
			if (imageView_two == null)
				imageView_two = (ImageView) view.findViewById(R.id.item2_icon);
			LayoutParams para = imageView_two.getLayoutParams();
			para.height = width;
			para.width = width;
			imageView_two.setLayoutParams(para);
			return imageView_two;
		}

		public ImageView getImageView_three() {
			if (imageView_three == null)
				imageView_three = (ImageView) view.findViewById(R.id.item3_icon);
			LayoutParams para = imageView_three.getLayoutParams();
			para.height = width;
			para.width = width;
			imageView_three.setLayoutParams(para);
			// int width = imageView_three.getWidth();
			// imageView_three.setLayoutParams(new LayoutParams(width, width));
			return imageView_three;
		}

		public TextView getPriceLabel_one() {
			if (priceLabel_one == null)
				priceLabel_one = (TextView) view.findViewById(R.id.item1_price);
			return priceLabel_one;
		}

		public TextView getPriceLabel_two() {
			if (priceLabel_two == null)
				priceLabel_two = (TextView) view.findViewById(R.id.item2_price);
			return priceLabel_two;
		}

		public TextView getPriceLabel_three() {
			if (priceLabel_three == null)
				priceLabel_three = (TextView) view.findViewById(R.id.item3_price);
			return priceLabel_three;
		}
	}

	@Override
	public void onClick(View v) {
		IndexGoodBean good = (IndexGoodBean) v.getTag();
		Intent intent = new Intent(context, GoodsDetailsActivity.class);
		intent.putExtra("goodsSN", good.goodsSN);
		intent.putExtra("imgFile", good.imageFile);
		context.startActivity(intent);
		// Toast.makeText(context, good.goodsSN, Toast.LENGTH_SHORT).show();
	}
}
