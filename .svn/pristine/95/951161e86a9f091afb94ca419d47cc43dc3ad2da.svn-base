package cc.android.supu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * 画斜线的textview
 * @author sheng
 *
 */
public class DrawLineTextView extends TextView {

	public DrawLineTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DrawLineTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DrawLineTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		Paint mpaint = new Paint();
		mpaint.setColor(Color.BLACK);
		mpaint.setAntiAlias(true);//锯齿不显示
		canvas.drawLine(0, this.getHeight()*3/4,
				this.getWidth(), this.getHeight()*1/4, mpaint);  
	}
}
