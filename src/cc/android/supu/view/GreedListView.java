package cc.android.supu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class GreedListView extends ListView {

	public GreedListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GreedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GreedListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		// if (expandSpec > heightMeasureSpec)
		// expandSpec = heightMeasureSpec;
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
