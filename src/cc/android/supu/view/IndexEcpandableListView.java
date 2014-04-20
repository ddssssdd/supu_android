package cc.android.supu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class IndexEcpandableListView extends ExpandableListView {

	public IndexEcpandableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public IndexEcpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public IndexEcpandableListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
