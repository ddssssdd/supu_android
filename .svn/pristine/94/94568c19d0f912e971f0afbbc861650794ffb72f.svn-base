package cc.android.supu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author sss 解决ListView与ScrollView中，高度冲突的问题
 */
public class OrderInfoListView extends ListView {

	public OrderInfoListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public OrderInfoListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OrderInfoListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
