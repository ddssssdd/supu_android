//package cc.android.supu.adapter;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Matrix;
//import android.graphics.drawable.BitmapDrawable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.ZoomControls;
//import cc.android.supu.R;
//import cc.android.supu.tools.AsyncImageLoader;
//
///**
// * 商品详情GalelryAdapter
// */
//public class BigimageGalleryAdapter extends BaseAdapter {
//	private ArrayList<String> imageUrls;
//	private LayoutInflater inflater;
//	
//
//	public BigimageGalleryAdapter(Context context, ArrayList<String> imageUrls) {
//		inflater = LayoutInflater.from(context);
//		this.imageUrls = imageUrls;
//	}
//
//	@Override
//	public int getCount() {
//		return imageUrls.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return position;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		final ViewHolder holder ;
//		
//		if (convertView == null) {
//			convertView = inflater.inflate(R.layout.bigimage_gallery_item,
//					parent, false);
//			holder = new ViewHolder();
//			
//			holder.webView = (WebView) convertView.findViewById(R.id.bigWebView);
//			holder.bigImageView = (ImageView) convertView.findViewById(R.id.bigImageView);
//			holder.showImageView = (TextView) convertView.findViewById(R.id.showImageView);
//			holder.bigImageView.setVisibility(View.VISIBLE);
//			WebSettings settings = holder.webView.getSettings();
//			settings.setJavaScriptEnabled(true);
//			holder.webView.setInitialScale(100);
//			settings.setSupportZoom(true);
//			settings.setBuiltInZoomControls(true);
//			settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//
//			convertView.setTag(holder.webView);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//
//		}
//		String imgUrl = AsyncImageLoader.getImageUrl(imageUrls.get(position));
//		holder.webView.loadUrl(imgUrl);
//		holder.webView.setVisibility(View.GONE);
//		final Bitmap bitmap = AsyncImageLoader.loadDrawable(imgUrl,
//				new AsyncImageLoader.ImageCallback() {
//
//					@Override
//					public void imageLoaded(Bitmap bitmap, String imageUrl) {
//						if (bitmap != null) {
//							holder.bigImageView.setBackgroundDrawable(new BitmapDrawable(
//									bitmap));
//						}
//					}
//				}, false);
//
//		if (bitmap != null) {
//			holder.bigImageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
//			
//		}
//		holder.showImageView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(!holder.isShow){
//					holder.bigImageView.setVisibility(View.GONE);
//					holder.webView.setVisibility(View.VISIBLE);
//					holder.showImageView.setText("关闭缩放");
//					holder.isShow = !holder.isShow;
//				}else{
//					holder.bigImageView.setVisibility(View.VISIBLE);
//					holder.webView.setVisibility(View.GONE);
//					holder.showImageView.setText("开启缩放");
//					holder.isShow = !holder.isShow;
//				}
//			}
//		});
//		return convertView;
//	}
//	/**内部类**/
//	private class ViewHolder {
//		/** 货物图片， 进度条  放大缩小 按钮*/
//		private WebView webView;
//		private ImageView bigImageView;
//		private TextView showImageView;
//		private boolean isShow = false;
//	}
//}
