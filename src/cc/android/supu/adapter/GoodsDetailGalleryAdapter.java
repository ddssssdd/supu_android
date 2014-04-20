package cc.android.supu.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cc.android.supu.ProductBigImageActivity;
import cc.android.supu.R;
import cc.android.supu.bean.GoodsImageBean;
import cc.android.supu.tools.AsyncImageLoader;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 商品详情页面的gallery适配器
 * 
 * @author sheng
 * 
 */
public class GoodsDetailGalleryAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<HashMap<Integer, String>> mList;
	private LayoutInflater inflater;
	private DisplayMetrics metrics;

	public GoodsDetailGalleryAdapter(Context context, ArrayList<String> list, DisplayMetrics metrics) {
		mContext = context;
		inflater = LayoutInflater.from(mContext);
		this.metrics = metrics;
		getList(list);
	}

	/**
	 * 
	 * @return
	 */
	private void getList(ArrayList<String> list) {
		this.mList = new ArrayList<HashMap<Integer, String>>();
		int size = list.size();
		HashMap<Integer, String> map = null;
		for (int i = 0; i < size; i++) {
			int temp = i % 2;
			if (temp == 0) {
				map = new HashMap<Integer, String>();
				map.put(1, list.get(i));
			} else {
				map.put(2, list.get(i));
			}
			if (temp == 1 || i == (size - 1)) {
				this.mList.add(map);
			}
		}
		list.clear();
		list = null;
	}

	@Override
	public int getCount() {
		return this.mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CacheView cacheView = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.goodsdetailgallery_item, null);
			cacheView = new CacheView(convertView);
			convertView.setTag(cacheView);
		} else {
			cacheView = (CacheView) convertView.getTag();
		}
		HashMap<Integer, String> map = this.mList.get(position);
		String imgUrl1 = map.get(1);
		String imgUrl2 = null;
		if (map.get(2) != null) {
			imgUrl2 = map.get(2);
		}

		final ImageView img1 = cacheView.getImg1();
		img1.setTag(R.id.tag_goodsdetail_item_img1, position);

		final ImageView img2 = cacheView.getImg2();
		img2.setTag(R.id.tag_goodsdetail_item_img2, position);

		setImage(img1, imgUrl1);
		if (imgUrl2 != null && !imgUrl2.equals("")) {
			img2.setVisibility(View.VISIBLE);
			setImage(img2, imgUrl2);
		} else {
			img2.setVisibility(View.INVISIBLE);
		}
		convertView.setTag(R.id.tag_goodsdetail_item_img1, img1);
		convertView.setTag(R.id.tag_goodsdetail_item_img2, img2);
		return convertView;
	}

	/**
	 * 
	 * @param img
	 * @param imgUrl
	 */
	private void setImage(final ImageView img, String imgUrl) {

		img.setTag(imgUrl);
		Bitmap bitmap = AsyncImageLoader.loadDrawable(mContext, imgUrl, new AsyncImageLoader.ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, String imageUrl) {
				// TODO Auto-generated method stub
				if (imageUrl.equals((String) img.getTag())) {
					if (bitmap != null) {
						img.setImageBitmap(bitmap);
					} else {
						img.setImageBitmap(null);
					}
				}
			}
		}, false, 1);
		if (bitmap != null) {
			img.setImageBitmap(bitmap);
		} else {
			img.setImageBitmap(null);
		}
	}

	class CacheView {

		private View baseView;

		private ImageView img1;

		private ImageView img2;

		// private LinearLayout layout2;
		private int imgWidth;
		private int imgHeight;
		private int spacing;

		public CacheView(View baseView) {
			this.baseView = baseView;
			spacing = (int) (20 * metrics.density);
			this.imgWidth = (int) ((metrics.widthPixels - spacing * 3) / 2);
			this.imgHeight = (int) imgWidth;
		}

		public ImageView getImg1() {
			if (img1 == null) {
				img1 = (ImageView) baseView.findViewById(R.id.goodsdetails_item_image1);
				LayoutParams lp1 = new LayoutParams((int) imgWidth, imgHeight);
				lp1.setMargins(spacing, 0, spacing, 0);
				img1.setLayoutParams(lp1);
			}

			return img1;
		}

		public ImageView getImg2() {
			if (img2 == null) {
				img2 = (ImageView) baseView.findViewById(R.id.goodsdetails_item_image2);

				LayoutParams lp2 = new LayoutParams(imgWidth, imgHeight);
				lp2.setMargins(0, 0, spacing, 0);
				img2.setLayoutParams(lp2);
			}
			return img2;
		}

		// public LinearLayout getLayout2() {
		// if (layout2 == null) {
		// layout2 = (LinearLayout)
		// baseView.findViewById(R.id.goodsdetails_item_layout2);
		// }
		// return layout2;
		// }
	}

	// /**
	// * 获得图片url的list
	// *
	// * @param list
	// * @return ArrayList<String> 图片url的list
	// */
	// private ArrayList<String> getImageList() {
	// ArrayList<String> listImage = new ArrayList<String>();
	// for (int i = 0; i < list.size(); i++) {
	// HashMap<Integer, GoodsImageBean> map = list.get(i);
	// String imgUrl1 = AsyncImageLoader.getImagUrl(map.get(1).goodsSN,
	// map.get(1).name + map.get(1).extension);
	// if (imgUrl1 != null) {
	// listImage.add(imgUrl1);
	// }
	// String imgUrl2 = null;
	// if (map.get(2) != null) {
	// imgUrl2 = AsyncImageLoader.getImagUrl(map.get(2).goodsSN, map.get(2).name
	// + map.get(2).extension);
	// }
	// if (imgUrl2 != null) {
	// listImage.add(imgUrl2);
	// }
	// }
	// return listImage;
	// }

}
