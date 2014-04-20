package cc.android.supu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class DialogActivity extends Activity {

	private String title;
	private String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogfirst);
		Intent intent = getIntent();
		if (null != intent) {
			title = intent.getStringExtra("title");
			content = intent.getStringExtra("content");
			showDialog();
		}
	}

	/**
	 * 弹出对话框
	 */
	private void showDialog() {
		Dialog dialog = new AlertDialog.Builder(this)
				.setMessage(content)
				.setTitle(title)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
//						Intent intent = new Intent(DialogActivity.this,
//								WelcomeActivity.class);
//						DialogActivity.this.startActivity(intent);
//						finish();
						finish();
						dialog.dismiss();
					}

				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						finish();
						dialog.dismiss();
					}

				}).create();
		dialog.show();
	}
}
