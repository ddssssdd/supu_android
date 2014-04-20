package cc.android.supu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.android.supu.GoodsDetailsActivity;
import cc.android.supu.R;
import cc.android.supu.bean.GoodsBean;
import cc.android.supu.tools.AsyncImageLoader;

/**
 * 购物车首页中的listview的适配器
 * 
 * @author sheng
 * 
 */
public class ShopCarListAdapter extends BaseAdapter implements OnClickListener {

	private Context mContext;
	private ArrayList<GoodsBean> list;
	private LayoutInflater inflater;
	/** 是否处于编辑状态 */
	private boolean isEditting = false;

	private boolean isScrolling = false;

	public boolean isEditting() {
		return isEditting;
	};

	public void setScrolling(boolean isScrolling) {
		this.isScrolling = isScrolling;
	}

	public ShopCarListAdapter(Context context, ArrayList<GoodsBean> list) {
		mContext = context;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
	}

	public void edit(boolean isEditting) {
		this.isEditting = isEditting;
	}

	public void setData(ArrayList<GoodsBean> list) {
		if (list != null) {
			this.list = list;
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final CacheView cacheView;
		final GoodsBean goodsBean = list.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.shopcar_item, null);
			convertView.setOnClickListener(this);
			cacheView = new CacheView(convertView);
			convertView.setTag(cacheView);
		} else {
			cacheView = (CacheView) convertView.getTag();
			if (isEditting) {
				String imgUrl = (String) cacheView.countEt.getTag();
				String tempCount = cacheView.countEt.getText().toString();
				if (tempCount != null && !tempCount.equals("")) {
					for (GoodsBean bean : list) {
						if (!bean.isGift) {
							if (bean.imgFile.equals(imgUrl)) {
								if(bean.isNoStock){
									bean.tempCount = bean.count + "";
								}else{
									bean.tempCount = cacheView.countEt.getText().toString();
								}
							}
						}
					}
				}
			}
		}
		String imgUrl = goodsBean.imgFile;
		if (!isScrolling) {
			final ImageView imageView = cacheView.imageView;
			imageView.setTag(imgUrl);
			Bitmap bitmap = AsyncImageLoader.loadDrawable(mContext, imgUrl, new AsyncImageLoader.ImageCallback() {

				@Override
				public void imageLoaded(Bitmap bitmap, String imageUrl) {
					String url = (String) imageView.getTag();
					if (bitmap != null && url.equals(imageUrl)) {
						imageView.setImageBitmap(bitmap);
					}
				}
			}, false, 1);

			if (bitmap != null) {
				imageView.setImageBitmap(bitmap);
			} else {
				imageView.setImageBitmap(null);
			}
		}
		cacheView.goodNameTv.setText(goodsBean.goodsName);
		cacheView.shopPriceTv.setText("￥" + goodsBean.shopPrice);
		if (goodsBean.isNoStock) {
			cacheView.shopPriceTv.setTextColor(Color.GRAY);
//			if (isEditting && goodsBean.stockCount != -1) {
//				cacheView.stockTv.setVisibility(View.VISIBLE);
//				cacheView.stockCountTv.setVisibility(View.VISIBLE);
//				cacheView.stockCountTv.setText(goodsBean.stockCount + "");
//			} else {
//				cacheView.stockTv.setVisibility(View.INVISIBLE);
//				cacheView.stockCountTv.setVisibility(View.INVISIBLE);
//			}
		} else {
			cacheView.shopPriceTv.setTextColor(Color.RED);
//			cacheView.stockTv.setVisibility(View.INVISIBLE);
//			cacheView.stockCountTv.setVisibility(View.INVISIBLE);
		}

		cacheView.noStock.setVisibility(goodsBean.isGift || goodsBean.isNoStock ? View.VISIBLE : View.GONE);

		if (goodsBean.isGift) {
			cacheView.noStock.setText("赠品");
			cacheView.shopPriceTv.setVisibility(View.GONE);
			cacheView.imgLayout.setVisibility(View.GONE);
			cacheView.imageView.setVisibility(View.GONE);
			cacheView.connector.setVisibility(View.GONE);
			cacheView.countTv.setText(goodsBean.count + "");
		} else {
			cacheView.noStock.setText("缺货");
			cacheView.shopPriceTv.setVisibility(View.VISIBLE);
			cacheView.imgLayout.setVisibility(View.VISIBLE);
			cacheView.imageView.setVisibility(View.VISIBLE);
			cacheView.connector.setVisibility(View.VISIBLE);
			cacheView.countEt.setText(goodsBean.tempCount);
			cacheView.countEt.setTag(imgUrl);
		}
		System.out.println("goodsBean.count="+goodsBean.count);

		if (isEditting) {
			cacheView.countTv.setVisibility(goodsBean.isGift ? View.VISIBLE : View.GONE);
			cacheView.countEt.setVisibility(goodsBean.isGift ? View.GONE : View.VISIBLE);
			cacheView.countEt.setOnFocusChangeListener((OnFocusChangeListener) mContext);
			cacheView.countEt.setOnClickListener((OnClickListener) mContext);
			cacheView.deleteBtn.setOnClickListener((OnClickListener) mContext);
			cacheView.deleteBtn.setVisibility(goodsBean.isGift ? View.GONE : View.VISIBLE);
			cacheView.deleteBtn.setTag(R.id.tag_shopcart_position, position);
			convertView.setTag(R.id.tag_shopcart_position, position);
		} else {
			cacheView.countTv.setText(goodsBean.count + "");
			cacheView.countTv.setVisibility(View.VISIBLE);
			cacheView.countEt.setVisibility(View.GONE);
			cacheView.deleteBtn.setVisibility(View.GONE);
		}

		convertView.setTag(R.id.tag_shopcart_countEt, cacheView.countEt);
		convertView.setTag(R.id.carbean, goodsBean);
		
		convertView.setClickable(!isEditting && !goodsBean.isGift);
		
		
		return convertView;
	}

	class CacheView {
		/** 图片 */
		private ImageView imageView;
		/** 商品名 */
		private TextView goodNameTv;
		/** 价格 */
		private TextView shopPriceTv;
		/** 数量 */
		private TextView countTv;
		/** 进度条 */
		private ProgressBar progressBar;
		/** 数量编辑框 */
		private EditText countEt;
		/** 删除按钮 */
		private ImageView deleteBtn;
		/** 显示缺货/赠品 */
		private TextView noStock;
		/** "X"号 */
		private TextView connector;
		/** 库存 */
		private TextView stockTv;
		/** 库存数量 */
		private TextView stockCountTv;

		private RelativeLayout imgLayout;

		public CacheView(View baseView) {
			imageView = (ImageView) baseView.findViewById(R.id.shopcar_item_image);
			goodNameTv = (TextView) baseView.findViewById(R.id.shopcar_item_goodsName);
			shopPriceTv = (TextView) baseView.findViewById(R.id.shopcar_item_shopPrice);
			countTv = (TextView) baseView.findViewById(R.id.shopcar_item_count);
			progressBar = (ProgressBar) baseView.findViewById(R.id.shopcar_item_progressBar);
			countEt = (EditText) baseView.findViewById(R.id.shopcart_item_countEt);
			deleteBtn = (ImageView) baseView.findViewById(R.id.shopcart_item_deleteBtn);
			noStock = (TextView) baseView.findViewById(R.id.shopcar_item_isNoStock);
			connector = (TextView) baseView.findViewById(R.id.shopcar_item_connector);
			stockTv = (TextView) baseView.findViewById(R.id.shopcar_item_stock);
			stockCountTv = (TextView) baseView.findViewById(R.id.shopcar_item_stockCount);
			imgLayout = (RelativeLayout) baseView.findViewById(R.id.imglayout);
		}

	}

	@Override
	public void onClick(View v) {
		GoodsBean goodsBean = (GoodsBean) v.getTag(R.id.carbean);
		Intent intent = new Intent(mContext, GoodsDetailsActivity.class);
		intent.putExtra("goodsSN", goodsBean.goodsSN);
		mContext.startActivity(intent);
	}

}
