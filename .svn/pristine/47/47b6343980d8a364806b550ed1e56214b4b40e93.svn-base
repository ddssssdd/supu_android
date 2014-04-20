package cc.android.supu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

public class ThreeGallery extends Gallery {
	private static final int OFFSETX = 100;

	public ThreeGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ThreeGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ThreeGallery(Context context) {
		super(context);
	}
	float startX;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			startX = ev.getX();
		} else {
			float abs = Math.abs(startX - ev.getX());
			if (abs > OFFSETX) {
				return true;
			}
		}
		return super.onInterceptTouchEvent(ev);
	}
}
