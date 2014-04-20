package cc.android.supu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class SupuGridView extends GridView {

	public SupuGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SupuGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SupuGridView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
