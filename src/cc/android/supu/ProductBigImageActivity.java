package cc.android.supu;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cc.android.supu.adapter.GalleryAdapter;
import cc.android.supu.view.MyGallery;
import cc.android.supu.view.MyImageView;

/**
 * 商品大图
 */
public class ProductBigImageActivity extends BaseActivity implements
		OnTouchListener {

	private ArrayList<String> images;
	private MyGallery gallery;
	private GalleryAdapter adapter;
	private int position;
	private MyImageView img;

	private RelativeLayout heartLayout;
	/** 绘制的心形图片 */
	private Bitmap heartImage;

	private ImageView heartImageBg;
	// 屏幕的宽度
	public static int screenWidth;
	// 屏幕的高度
	public static int screenHeight;
	private boolean isScale = false; // 是否缩放
	private float beforeLenght = 0.0f; // 两触点距离
	private float afterLenght = 0.0f;
	private float currentScale = 1.0f;

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	protected String setTitle() {
		return "商品大图";
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void initPage() {

		// 获取屏幕的大小
		num = 0;
		screenWidth = getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();
		screenHeight = getWindow().getWindowManager().getDefaultDisplay()
				.getHeight();
//		System.out.println("screenWidth"+screenWidth);
		images = getIntent().getStringArrayListExtra("images");
		heartLayout = (RelativeLayout) findViewById(R.id.heartLayout);
		position = getIntent().getIntExtra("position", 0);
		// Bigposition = position;
		gallery = (MyGallery) findViewById(R.id.bigimg_gallery);
		gallery.setCallbackDuringFling(false);
		gallery.setVerticalFadingEdgeEnabled(false);
		gallery.setHorizontalFadingEdgeEnabled(false);// );//
														// 设置view在水平滚动时，水平边不淡出。
		heartImageBg = (ImageView) findViewById(R.id.bigimg_hearts);
		if (images != null) {

			adapter = new GalleryAdapter(ProductBigImageActivity.this, images);
			gallery.setOnItemSelectedListener(itemSelectedListener);
			if (position > images.size())
				position = position % images.size();
			gallery.setAdapter(adapter);
			gallery.setSelection(position);
			if (images.size() > 1) {
				heartLayout.setVisibility(View.VISIBLE);
				heartImage = drawPoint(images.size(), position);
				heartImageBg.setImageBitmap(heartImage);
			} else {
				heartLayout.setVisibility(View.GONE);
			}
		} else {
			Toast.makeText(this, "商品大图不存在!", Toast.LENGTH_LONG).show();
			this.finish();
		}
	}

	@Override
	protected int setBottomIndex() {
		return 1;
	}

	@Override
	public void dealwithMessage(Message msg) {

	}

	@Override
	protected void onDestroy() {
		if (heartImage != null)
			heartImage.recycle();
		heartImage = null;
		super.onDestroy();
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.product_bigimg;
	}
	
	private int num = 0;

	private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

			if (heartImage != null)
				heartImage.recycle();
			heartImage = drawPoint(images.size(), position);
			heartImageBg.setImageBitmap(heartImage);
			if(num != position){
				if (img != null) {
					if(screenWidth<400){
						img.zoomTo(0.34f);
					}else if(screenWidth <500){
						img.zoomTo(0.5f);
					}else if(screenWidth >600 && screenWidth <900){
						img.zoomTo(0.83f);
					}
					
				}
				img = (MyImageView) view.findViewById(R.id.bigImageView);
			}
			// Bigposition = position;
			// adapter.notifyDataSetChanged();
			num = position;
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};

	/**
	 * 绘制图片点
	 */
	public Bitmap drawPoint(int num, int position) {
		if (num <= 0) {// 加上判断，有时再颜色频繁切换时，会出现异常
			num = 1;
		}
		Bitmap normal = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.point_gray)).getBitmap();
		Bitmap select = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.point_red)).getBitmap();
		int width = normal.getWidth() + 10;
		int height = normal.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(num * width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		int x = 0;
		for (int i = 0; i < num; i++) {
			if (i == position) {
				canvas.drawBitmap(select, x, 0, null);
			} else {
				canvas.drawBitmap(normal, x, 0, null);
			}
			x += width;
		}
		return bitmap;
	}

	/**
	 * 计算两点间的距离
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Log.i("lyc", "touched---------------");
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:
			beforeLenght = spacing(event);
			if (beforeLenght > 5f) {
				isScale = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			/* 处理拖动 */
			// if (isScale) {
			// afterLenght = spacing(event);
			// if (afterLenght < 5f)
			// break;
			// float gapLenght = afterLenght - beforeLenght;
			// if (gapLenght == 0) {
			// break;
			// } else if (Math.abs(gapLenght) > 5f) {
			// float scaleRate = gapLenght / 854;
			// Animation myAnimation_Scale = new ScaleAnimation(
			// currentScale, currentScale + scaleRate,
			// currentScale, currentScale + scaleRate,
			// Animation.RELATIVE_TO_SELF, 0.5f,
			// Animation.RELATIVE_TO_SELF, 0.5f);
			// myAnimation_Scale.setDuration(100);
			// myAnimation_Scale.setFillAfter(true);
			// myAnimation_Scale.setFillEnabled(true);
			// currentScale = currentScale + scaleRate;
			// gallery.getSelectedView().setLayoutParams(
			// new Gallery.LayoutParams(
			// (int) (480 * (currentScale)),
			// (int) (854 * (currentScale))));
			// beforeLenght = afterLenght;
			// }
			// return true;
			// }
			break;
		case MotionEvent.ACTION_POINTER_UP:
			isScale = false;
			break;
		}

		return false;
	}

}
