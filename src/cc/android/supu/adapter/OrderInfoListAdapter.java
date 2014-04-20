package cc.android.supu.adapter;

import java.util.List;

import cc.android.supu.R;
import cc.android.supu.bean.OrderInfoBean;
import cc.android.supu.tools.AsyncImageLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 订单详情中的listview的适配器
 * 
 * @author sheng
 * 
 */
public class OrderInfoListAdapter extends BaseAdapter {

	private Context mContext;

	private LayoutInflater inflater;

	private List<OrderInfoBean> mList;

	public OrderInfoListAdapter(Context context, List<OrderInfoBean> list) {
		mContext = context;
		mList = list;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		OrderInfoBean orderInfoBean = mList.get(position);
		final CacheView cacheView;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.orderinfo_list_item, null);
			cacheView = new CacheView();
			cacheView.imageView = (ImageView) convertView.findViewById(R.id.GOODSImage);
			cacheView.progressBar = (ProgressBar) convertView.findViewById(R.id.orderInfo_progressbar);
			cacheView.goodsNameTv = (TextView) convertView.findViewById(R.id.infoGoodsName);
			cacheView.priceTv = (TextView) convertView.findViewById(R.id.infoGOODSPrice);
			cacheView.countTv = (TextView) convertView.findViewById(R.id.infoCount);
			convertView.setTag(cacheView);
		} else {
			cacheView = (CacheView) convertView.getTag();
		}

		String imgUrl = orderInfoBean.ImgFile;
		cacheView.imageView.setTag(imgUrl);
		Bitmap bitmap = AsyncImageLoader.loadDrawable(mContext, imgUrl, new AsyncImageLoader.ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, String imageUrl) {
				// TODO Auto-generated method stub
				String imgUrl = (String) cacheView.imageView.getTag();
				if (bitmap != null && imageUrl.equals(imgUrl)) {
					cacheView.imageView.setImageBitmap(bitmap);
					cacheView.progressBar.setVisibility(View.GONE);
				}
			}
		}, false, 1);
		if (bitmap != null) {
			cacheView.imageView.setImageBitmap(bitmap);
			cacheView.progressBar.setVisibility(View.GONE);
		}

		cacheView.goodsNameTv.setText(orderInfoBean.GoodsName);
		cacheView.priceTv.setText(orderInfoBean.Price);
		cacheView.countTv.setText(orderInfoBean.Count + "");

		return convertView;
	}

	class CacheView {
		/** 商品图片 */
		ImageView imageView;
		/** 图片的加载进度条 */
		ProgressBar progressBar;
		/** 商品名 */
		TextView goodsNameTv;
		/** 商品价格 */
		TextView priceTv;
		/** 商品数量 */
		TextView countTv;
	}

	/**
	 * 将金额的表示方式格式化为￥0.00
	 * 
	 * @return
	 */
	private String floatToStr(float f) {
		String str = "￥" + f;
		if (str.length() - str.lastIndexOf(".") - 1 == 1) {
			str = str + "0";
		}
		return str;
	}

}
