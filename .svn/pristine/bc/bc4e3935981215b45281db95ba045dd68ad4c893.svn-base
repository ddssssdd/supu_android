package cc.android.supu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class GoodsDetailGallery extends Gallery {

	public int x;
	public int y;

	public GoodsDetailGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public GoodsDetailGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GoodsDetailGallery(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		x = (int) e.getX();
		y = (int) e.getY();

		return super.onSingleTapUp(e);
	}

	//
	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	// if (ev.getAction() == MotionEvent.ACTION_MOVE) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

		int keyCode;

		if (isScrollingLeft(e1, e2)) {

			keyCode = KeyEvent.KEYCODE_DPAD_LEFT;

		} else {

			keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;

		}

		onKeyDown(keyCode, null);

		return false;

	}

	// @Override
	// public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent
	// paramMotionEvent2, float paramFloat1,
	// float paramFloat2) {
	//
	// float f = paramFloat1 + 1;
	//
	// return super.onScroll(paramMotionEvent1, paramMotionEvent2, f,
	// paramFloat2);
	//
	// }
	//
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {

		if (e1 == null)
			return true;
		if (e2 == null)
			return false;
		return e2.getX() > e1.getX();

	}

}
