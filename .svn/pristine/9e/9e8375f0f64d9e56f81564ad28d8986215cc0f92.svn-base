package cc.android.supu.ailpay;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.KeyEvent;

public class AlixOnCancelListener implements OnCancelListener {

	Activity mcontext;

	AlixOnCancelListener(Activity context) {
		mcontext = context;
	}

	public void onCancel(DialogInterface dialog) {
		mcontext.onKeyDown(KeyEvent.KEYCODE_BACK, null);
	}
}
