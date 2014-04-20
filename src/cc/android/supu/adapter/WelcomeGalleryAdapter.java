package cc.android.supu.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cc.android.supu.R;
import cc.android.supu.bean.BannerBean;
import cc.android.supu.tools.AsyncImageLoader;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 欢迎也Gallery的adapter
 * 
 * @author sheng
 * 
 */
public class WelcomeGalleryAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<BannerBean> list;
	private LayoutInflater inflater;

	public WelcomeGalleryAdapter(Context context, ArrayList<BannerBean> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ImageView img;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.welcom_gallery_item, null);
			img = (ImageView) convertView.findViewById(R.id.img);
			convertView.setTag(img);
		} else {
			img = (ImageView) convertView.getTag();
		}

		String imgUrl = list.get(position).picUrl;
		img.setTag(imgUrl);
		final LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		Bitmap bitmap = AsyncImageLoader.loadIndexDrawable(context, imgUrl, new AsyncImageLoader.ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, String imageUrl) {
				// TODO Auto-generated method stub
				String url = (String) img.getTag();
				if (bitmap != null && url.equals(imageUrl)) {
					// System.out.println("imageUrl=" + imageUrl);
					img.setLayoutParams(params);
					img.setImageBitmap(bitmap);
				} else {
					img.setBackgroundResource(R.drawable.welcome_bg);
				}
			}
		}, true, true, 10, false);
		if (bitmap != null) {
			img.setLayoutParams(params);
			img.setImageBitmap(bitmap);
		} /*else {
			img.setBackgroundResource(R.drawable.welcome_bg);
		}*/
		return convertView;
	}

	/**
	 * 判断是否需要更新图片
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private boolean isUpdateImg(String startTime, String endTime) {
		Date nowTime = Calendar.getInstance().getTime();
		int value = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Long time = new Long(startTime);
		String d = format.format(time);
		Date startTimeFormat = null;
		try {
			startTimeFormat = format.parse(d);
			value = startTimeFormat.compareTo(nowTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (value > 0) {
			return true;
		} else {
			return false;
		}
	}

}
