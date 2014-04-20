package cc.android.supu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * @author sss 修改滑动惯性
 */
public class FllingGallery extends Gallery {
	

	public FllingGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FllingGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FllingGallery(Context context) {
		super(context);
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		
		int keyCode;

		if (isScrollingLeft(e1, e2)) {

			keyCode = KeyEvent.KEYCODE_DPAD_LEFT;

		} else {

			keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;

		}

		onKeyDown(keyCode, null);

		return true;

	}
	
	@Override

	public boolean onScroll(MotionEvent paramMotionEvent1,MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {

	float f = paramFloat1 + 3;

	return super.onScroll(paramMotionEvent1, paramMotionEvent2, f,paramFloat2);

	}


	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {

		return e2.getX() > e1.getX();

	}
}
