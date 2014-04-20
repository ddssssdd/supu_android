package cc.android.supu.adapter;

import java.util.List;

import cc.android.supu.R;
import cc.android.supu.SupuApplication;
import cc.android.supu.bean.GoodsActivityBean;
import cc.android.supu.tools.AsyncImageLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 优惠活动列表适配器
 * @author sheng
 *
 */
public class GoodsActivitesAdapter extends BaseAdapter {
	
	private Context mContext;
	private LayoutInflater inflater;
	private List<GoodsActivityBean> mList;
	private GoodsActivityBean activityBean;
	
	public GoodsActivitesAdapter(Context context, List<GoodsActivityBean> list){
		mContext = context;
		inflater = LayoutInflater.from(mContext);
		mList = list;
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
		final CacheView cacheView ;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.goodsactivities_item, null);
			cacheView = new CacheView(convertView);
			convertView.setTag(cacheView);
		}else{
			cacheView = (CacheView) convertView.getTag();
		}
		activityBean = mList.get(position);
		cacheView.getNameTv().setText(activityBean.name);
		cacheView.getDescriptionTv().setText(activityBean.description);
		String imgUrl = activityBean.activityImage;
		cacheView.getImg().setTag(imgUrl);
		Bitmap bitmap = AsyncImageLoader.loadDrawable(mContext,imgUrl, new AsyncImageLoader.ImageCallback() {
			
			@Override
			public void imageLoaded(Bitmap bitmap, String imageUrl) {
				// TODO Auto-generated method stub
				String url = (String) cacheView.getImg().getTag();
				if(url != null && bitmap != null){
					cacheView.getImg().setImageBitmap(bitmap);
				}
			}
		}, false, 1);
		if(bitmap != null){
			cacheView.getImg().setImageBitmap(bitmap);
		}else{
			cacheView.getImg().setImageBitmap(cacheView.getBitmap());
		}
		cacheView.getHeaderView().setVisibility(position == 0 ? View.VISIBLE : View.GONE);
		cacheView.getFooterView().setVisibility(position == getCount() - 1 ? View.VISIBLE : View.GONE);
		
		return convertView;
	}
	
	class CacheView{
		
		private View baseView;
		private TextView nameTv;
		private ImageView img;
		private TextView descriptionTv;
		private TextView headerView;
		private TextView footerView;
		
		public Bitmap getBitmap(){
			Bitmap	bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_activity);
			return bitmap;
		}
		public CacheView(View baseView){
			this.baseView = baseView;
		}
		
		public TextView getNameTv(){
			if(nameTv == null){
				nameTv = (TextView) baseView.findViewById(R.id.goodsactivities_item_name);
			}
			return nameTv;
		}
		
		public ImageView getImg(){
			if(img == null){
				img = (ImageView) baseView.findViewById(R.id.goodsactivities_item_img);
			}
			return img;
		}
		public TextView getDescriptionTv(){
			if(descriptionTv == null){
				descriptionTv = (TextView) baseView.findViewById(R.id.goodsactivities_item_Description);
			}
			return descriptionTv;
		}
		
		public TextView getHeaderView(){
			if(headerView == null){
				headerView = (TextView) baseView.findViewById(R.id.goodsactivities_item_headview);
			}
			return headerView;
		}
		
		public TextView getFooterView(){
			if(footerView == null){
				footerView = (TextView) baseView.findViewById(R.id.goodsactivities_item_footerview);
			}
			return footerView;
		}
	}
	
	

}
