package cc.android.supu.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import cc.android.supu.R;

/**
 * 异步加载图片
 * 
 * @author sss
 * 
 */
public final class AsyncListImageLoader {
	private static final String imgUrl = "http://pic.supuy.com/";
	public static String PATH = null;
	static {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			PATH = Environment.getExternalStorageDirectory().toString() + "/supu/product_img/";
		} else {
			PATH = "/data/data/cc.android.supu/files/product_img/";
		}
	}
	private static String imagePathPrefix = "";
	private static String imageServiceUrl = "";
	/** 是否显示列表中的图片 */
	public static boolean isShow = true;

	/** 线程控制 */
	private Object lock = new Object();
	private boolean mAllowLoad = true;
	private boolean firstLoad = true;
	private int mStartLoadLimit = 0;
	private int mStopLoadLimit = 5;

	public static interface OnImageLoadListener {
		public void onImageLoad(Integer t, Drawable drawable);

		public void onError(Integer t);
	}

	public void setLoadLimit(int startLoadLimit, int stopLoadLimit) {
		if (startLoadLimit > stopLoadLimit) {
			return;
		}
		mStartLoadLimit = startLoadLimit;
		mStopLoadLimit = stopLoadLimit;
	}

	public void restore() {
		mAllowLoad = true;
		firstLoad = true;
	}

	public void lockThread() {
		mAllowLoad = false;
		firstLoad = false;
	}

	public void unlock() {
		mAllowLoad = true;
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public Bitmap loadImage(int position, final String imageUrl, final ImageCallback imageCallback) {

		final int mPosition = position;
		final boolean saveSDCard = true;
		final Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				imageCallback.imageLoaded((Bitmap) msg.obj, imageUrl);
			};
		};
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				Bitmap bitmap = null;
				if (imageCache.containsKey(imageUrl)) {
					SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
					bitmap = softReference.get();
					if (bitmap != null) {
						Message message = handler.obtainMessage(0, bitmap);
						handler.sendMessage(message);
					}
				}
				try {
					bitmap = readBitmapSDcard(imageUrl, true);
				} catch (Exception e) {
					Message message = handler.obtainMessage(0, bitmap);
					handler.sendMessage(message);
				}
				if (bitmap != null) {
					Message message = handler.obtainMessage(0, bitmap);
					handler.sendMessage(message);
				}
				// while (!mAllowLoad) {
				// synchronized (lock) {
				// try {
				// lock.wait();
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				// }
				// }
				if (/* mAllowLoad && */(firstLoad || (mPosition <= mStopLoadLimit && mPosition >= mStartLoadLimit))) {
					try {
						System.out.println("position:" + mPosition);
						loadBitmapFromNet(imageUrl, handler, saveSDCard);
					} catch (Exception e) {// 内存溢出
						Message message = handler.obtainMessage(0, null);
						handler.sendMessage(message);
					}
				}
			}
		});

		return null;
	}

	public static void setImagePathPrefix(String imagePathPrefix) {
		AsyncListImageLoader.imagePathPrefix = imagePathPrefix;
	}

	public static void setImageServiceUrl(String imageServiceUrl) {
		AsyncListImageLoader.imageServiceUrl = imageServiceUrl;
	}

	public static String getCategoryImagUrl(String imgName) {
		return "http://img.supuy.com/images/phonecategorys/" + imgName;
	}

	public void savePath(Context context) {
		Editor editor = context.getSharedPreferences("imgpath", Context.MODE_PRIVATE).edit();
		if (!"".equals(imagePathPrefix) && imagePathPrefix != null) {
			editor.putString("prefix", imagePathPrefix);
		}
		if (!"".equals(imageServiceUrl) && imageServiceUrl != null) {
			editor.putString("serviceurl", imageServiceUrl);
		}
		editor.commit();
	}

	public void getPath(Context context) {
		SharedPreferences pathPreferences = context.getSharedPreferences("imgpath", Context.MODE_PRIVATE);
		imagePathPrefix = pathPreferences.getString("prefix", "");
		imageServiceUrl = pathPreferences.getString("serviceurl", "");
	}

	private final HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();;
	private final ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 3, 1, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	public String getImageUrl(String url, Context context) {
		if (imageServiceUrl == null || "".equals(imageServiceUrl.trim()))
			getPath(context);
		return imageServiceUrl + url;
	}

	/**
	 * 将图片切成圆角
	 * 
	 * @param bitmap
	 * @param pixels
	 *            度数
	 * 
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output;
		try {
			output = null;
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = pixels;
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		} catch (Exception e) {// 内存溢出
			return null;
		}
		return output;
	}

	/**
	 * @param imgUrl
	 * @param imageCallback
	 * @param saveSDCard
	 *            是否保存在SD卡，保存传true,不保存传false;
	 * @param isSmall
	 *            是否要加载大小为原图的1/2
	 * @param corner
	 *            切的角度大小
	 * @return
	 */
	public Bitmap loadCornerDrawable(final String imgUrl, final ImageCallback imageCallback, final boolean saveSDCard,
			final boolean isSmall, final int corner) {
		final Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				imageCallback.imageLoaded((Bitmap) msg.obj, imgUrl);
			};
		};
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				Bitmap bitmap = null;
				try {
					if (imageCache.containsKey(imgUrl)) {
						SoftReference<Bitmap> softReference = imageCache.get(imgUrl);
						bitmap = softReference.get();
						if (bitmap != null) {
							Message message = handler.obtainMessage(0, bitmap);
							handler.sendMessage(message);
						} else if (saveSDCard) {
							bitmap = readBitmapSDcard(imgUrl, isSmall);
							if (bitmap != null) {
								Message message = handler.obtainMessage(0, bitmap);
								handler.sendMessage(message);
							} else {
								loadCornerBitmapFromNet(imgUrl, handler, true, corner);
							}
						} else {
							loadCornerBitmapFromNet(imgUrl, handler, true, corner);
						}
					} else if (saveSDCard) {
						bitmap = readBitmapSDcard(imgUrl, isSmall);
						if (bitmap != null) {
							Message message = handler.obtainMessage(0, bitmap);
							handler.sendMessage(message);
						} else {
							loadCornerBitmapFromNet(imgUrl, handler, true, corner);
						}
					} else {
						loadCornerBitmapFromNet(imgUrl, handler, true, corner);
					}
				} catch (Exception e) {// 内存溢出
					e.printStackTrace();
					Message message = handler.obtainMessage(0, null);
					handler.sendMessage(message);
				}
			}
		});
		return null;
	}

	/**
	 * 从网络加载图片
	 * 
	 * @param imgUrl
	 * @param imageCallback
	 */
	private void loadCornerBitmapFromNet(final String imgUrl, Handler handler, final boolean savecard, final int corner) {
		if (PATH != null) {
			File file = new File(PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		Bitmap tempbitmap = loadImageFromUrl(imgUrl);
		Bitmap bitmap = null;
		if (tempbitmap != null) {
			bitmap = toRoundCorner(tempbitmap, corner);
			imageCache.put(imgUrl, new SoftReference<Bitmap>(bitmap));
		}
		Message message = handler.obtainMessage(0, bitmap);
		handler.sendMessage(message);
		if (savecard && PATH != null && bitmap != null) {
			String imgName = imgUrl.substring(0, imgUrl.lastIndexOf('/')) + "_"
					+ imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.length());
			imgName = imgName.substring(imgName.lastIndexOf('/') + 1, imgUrl.length());
			File imageFile = new File(PATH + imgName);
			imageFile.canWrite();
			try {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
				bos.flush();
				bos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @param imgUrl
	 * @param imageCallback
	 * @param saveSDCard
	 *            是否保存在SD卡，保存传true,不保存传false;
	 * @param type
	 *            0为最小图，1为中图，2为大图
	 * @return
	 */
	public Bitmap loadDrawable(Context context, final String imgUrl, final ImageCallback imageCallback,
			final boolean saveSDCard, int type) {
		return loadIndexDrawable(context, imgUrl, imageCallback, saveSDCard, false, type, true);
	}

	public static String getImagUrl(String prodcutId, String imgName) {
		return imgUrl + prodcutId + "/" + imgName;
	}

	/**
	 * @param imgUrl
	 * @param imageCallback
	 * @param saveSDCard
	 *            是否保存在SD卡，保存传true,不保存传false;
	 * @param isSmall
	 *            是否要加载大小为原图的1/2
	 * @return
	 */
	public Bitmap loadIndexDrawable(Context context, final String imgUrl, final ImageCallback imageCallback,
			final boolean saveSDCard, boolean showImage, int type, final boolean isSmall) {
		Bitmap bitmap1 = null;
		if (!isShow && !showImage) {
			try {
				if (type == 0) {
					bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_normal);
				} else if (type == 2) {
					bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.defalut_big);
				} else if (type == 3) {
					bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.defalut_widthmiddle);
				} else {
					bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.defalut_middle);
				}
			} catch (Exception e) {// 内存溢出
				// TODO: handle exception
				return null;
			}
			return bitmap1;
		} else {
			final Handler handler = new Handler() {
				public void handleMessage(android.os.Message msg) {
					if (imageCallback != null) {
						imageCallback.imageLoaded((Bitmap) msg.obj, imgUrl);
					}
				};
			};
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					Bitmap bitmap = null;
					try {
						if (imageCache.containsKey(imgUrl)) {
							SoftReference<Bitmap> softReference = imageCache.get(imgUrl);
							bitmap = softReference.get();
							if (bitmap != null) {
								Message message = handler.obtainMessage(0, bitmap);
								handler.sendMessage(message);
							} else if (saveSDCard) {
								bitmap = readBitmapSDcard(imgUrl, isSmall);
								if (bitmap != null) {
									Message message = handler.obtainMessage(0, bitmap);
									handler.sendMessage(message);
								} else {
									loadBitmapFromNet(imgUrl, handler, saveSDCard);
								}
							} else {
								loadBitmapFromNet(imgUrl, handler, saveSDCard);
							}
						} else if (saveSDCard) {
							bitmap = readBitmapSDcard(imgUrl, isSmall);
							if (bitmap != null) {
								Message message = handler.obtainMessage(0, bitmap);
								handler.sendMessage(message);
							} else {
								loadBitmapFromNet(imgUrl, handler, saveSDCard);
							}
						} else {
							loadBitmapFromNet(imgUrl, handler, saveSDCard);
						}
					} catch (Exception e) {// 内存溢出
						Message message = handler.obtainMessage(0, null);
						handler.sendMessage(message);
					}
				}
			});
			return null;
		}
	}

	/**
	 * 从网络加载图片
	 * 
	 * @param imgUrl
	 * @param imageCallback
	 */
	private void loadBitmapFromNet(final String imgUrl, final Handler handler, final boolean savecard) {
		if (PATH != null) {
			File file = new File(PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		Bitmap bitmap = loadImageFromUrl(imgUrl);
		if (bitmap != null) {
			imageCache.put(imgUrl, new SoftReference<Bitmap>(bitmap));
		}
		Message message = handler.obtainMessage(0, bitmap);
		handler.sendMessage(message);

		if (savecard && PATH != null && bitmap != null) {
			String imgName = imgUrl.substring(0, imgUrl.lastIndexOf('/')) + "_"
					+ imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.length());
			imgName = imgName.substring(imgName.lastIndexOf('/') + 1, imgUrl.length());
			File imageFile = new File(PATH + imgName);
			imageFile.canWrite();
			try {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
				bos.flush();
				bos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static Bitmap loadImageFromUrl(String imageUrl) {
		URL url;
		InputStream inputStream = null;
		try {
			url = new URL(imageUrl);
			inputStream = (InputStream) url.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return BitmapFactory.decodeStream(inputStream);// BitmapDrawable.createFromStream(inputStream,
														// "src").;
	}

	/**
	 * 从SD卡加载图片
	 * 
	 * @param filename
	 * @param isSmall
	 *            是否要加载大小为原图的1/2
	 * @return
	 */
	private Bitmap readBitmapSDcard(final String imgUrl, final boolean isSmall) {
		if (PATH == null || imgUrl == null) {
			return null;
		}
		String filename = imgUrl.substring(0, imgUrl.lastIndexOf('/')) + "_"
				+ imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.length());
		filename = filename.substring(filename.lastIndexOf('/') + 1, imgUrl.length());

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = isSmall ? 2 : 1;
		Bitmap bitmap;
		try {
			bitmap = BitmapFactory.decodeFile(PATH + filename, options);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		if (bitmap != null) {
			imageCache.put(imgUrl, new SoftReference<Bitmap>(bitmap));
		}
		return bitmap;
	}

	/**
	 * 从SD卡加载图片
	 * 
	 * @param filename
	 * @param isSmall
	 *            是否要加载大小为原图的1/2
	 * @return
	 */
	// public static Bitmap readBitmapSDcard(final String imgUrl, final boolean
	// isSmall) {
	// if (imgUrl == null) {
	// return null;
	// }
	//
	// String filename = imgUrl.substring(0, imgUrl.lastIndexOf('/')) + "_"
	// + imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.length());
	// filename = filename.substring(filename.lastIndexOf('/') + 1,
	// imgUrl.length());
	//
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = false;
	// options.inSampleSize = isSmall ? 2 : 1;
	// Bitmap bitmap = BitmapFactory.decodeFile(PATH + filename, options);
	//
	// return bitmap;
	// }

	public interface ImageCallback {
		public void imageLoaded(Bitmap bitmap, String imageUrl);
	}

}
