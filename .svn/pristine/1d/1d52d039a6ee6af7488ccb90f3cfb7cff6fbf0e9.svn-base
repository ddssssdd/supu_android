package cc.android.supu;

import java.io.File;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.service.PushMsg;
import cc.android.supu.tools.AsyncImageLoader;
import cc.android.supu.tools.UserInfoTools;
import cc.android.supu.view.MySlipSwitch;
import cc.android.supu.view.MySlipSwitch.OnSwitchListener;

public class SetupActivity extends BaseActivity {
	/** 列表显示 */
	private MySlipSwitch slipswitch_MSLShow;
	/** 消息推送 */
	private MySlipSwitch slipswitch_MSLMessage;

	private Button ClearButton;
	/** 是否推送消息 */
	private boolean isMessage;
	/** 是否显示 */
	private boolean isImage;
	/** 是否显示图片 */
	private TextView SetImageShow;
	/** 是否推送消息 */
	private TextView SetMessageSend;
	/** 是否可以点击 */
	private boolean idBotton = true;
	// /** 进度条 */
	private ProgressDialog progressDialog1 = null;
	
	private PushMsg pushMsg;
	@Override
	protected void initPage() {
		// SetImageShow = (TextView)findViewById(R.id.SetImageShow);
		// SetMessageSend = (TextView)findViewById(R.id.SetMessageSend);
		// isImage = AsyncImageLoader.isShow;
		// if(isImage){
		// SetImageShow.setBackgroundResource(R.drawable.on_a);
		// }else{
		// SetImageShow.setBackgroundResource(R.drawable.off_a);
		// }
		// isMessage = UserInfoTools.getMessage(this);
		// if (isMessage) {
		// SetMessageSend.setBackgroundResource(R.drawable.on_a);
		// }else{
		// SetMessageSend.setBackgroundResource(R.drawable.off_a);
		// }
		//
		// SetImageShow.setOnClickListener(this);
		// SetMessageSend.setOnClickListener(this);
		findViewBy();

	}

	/**
	 * 初始化控件
	 */
	private void findViewBy() {
		ClearButton = (Button) findViewById(R.id.setUpClear);
		ClearButton.setOnClickListener(this);
		slipswitch_MSLShow = (MySlipSwitch) findViewById(R.id.myslipswitch);
		slipswitch_MSLShow.setImageResource(R.drawable.on, R.drawable.off,
				R.drawable.login_switch_btn);
		if (AsyncImageLoader.isShow) {
			slipswitch_MSLShow.setSwitchState(true);
		} else {
			slipswitch_MSLShow.setSwitchState(false);
		}
		slipswitch_MSLShow.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean isSwitchOn) {
				if (isSwitchOn) {
					AsyncImageLoader.isShow = true;
					UserInfoTools.saveImage(SetupActivity.this, true);
					Toast.makeText(SetupActivity.this, "图片显示",
							Toast.LENGTH_SHORT).show();
				} else {
					AsyncImageLoader.isShow = false;
					UserInfoTools.saveImage(SetupActivity.this, false);
					Toast.makeText(SetupActivity.this, "图片隐藏",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		isMessage = UserInfoTools.getMessage(this);

		slipswitch_MSLMessage = (MySlipSwitch) findViewById(R.id.myslipswitch_b);
		slipswitch_MSLMessage.setImageResource(R.drawable.on, R.drawable.off,
				R.drawable.login_switch_btn);

		if (isMessage) {
			slipswitch_MSLMessage.setSwitchState(true);
		} else {
			slipswitch_MSLMessage.setSwitchState(false);
		}
		slipswitch_MSLMessage.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean isSwitchOn) {
				if (isSwitchOn) {
					
					System.out.println("开启服务启动事件");
//					startService(new Intent(SetupActivity.this,
//							Messageservice.class));
					pushMsg = PushMsg.getInstance();
					pushMsg.openMsgPush(SetupActivity.this);
					UserInfoTools.saveMessage(SetupActivity.this, true);
					Toast.makeText(SetupActivity.this, "已经开启推送",
							Toast.LENGTH_SHORT).show();
				} else {
//					stopService(new Intent(SetupActivity.this,
//							Messageservice.class));
					pushMsg = PushMsg.getInstance();
					pushMsg.closeMsgPush();
					UserInfoTools.saveMessage(SetupActivity.this, false);
					Toast.makeText(SetupActivity.this, "已经关闭推送",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	protected void onSubActivityClick(View view) {
		switch (view.getId()) {
		case R.id.setUpClear:
			if (idBotton) {
				
				clearImageView();
			}
			break;
		// case R.id.SetImageShow:
		// setImage();
		// break;
		// case R.id.SetMessageSend:
		// setMessage();
		// break;

		default:
			break;
		}
	}

	// /**
	// * 点击推送事件
	// */
	// private void setMessage() {
	// if (isMessage) {
	// SetMessageSend.setBackgroundResource(R.drawable.off_a);
	// stopService(new Intent(SetupActivity.this, Messageservice.class));
	// UserInfoTools.saveMessage(SetupActivity.this, false);
	// Toast.makeText(SetupActivity.this, "已经关闭推送", Toast.LENGTH_SHORT)
	// .show();
	// isMessage = !isMessage;
	// } else {
	// SetMessageSend.setBackgroundResource(R.drawable.on_a);
	// startService(new Intent(SetupActivity.this, Messageservice.class));
	// UserInfoTools.saveMessage(SetupActivity.this, true);
	// Toast.makeText(SetupActivity.this, "已经开启推送", Toast.LENGTH_SHORT)
	// .show();
	// isMessage = !isMessage;
	// }
	//
	// }

	// /**
	// * 点击显示图片事件
	// */
	// private void setImage(){
	// if(isImage){
	// SetImageShow.setBackgroundResource(R.drawable.off_a);
	// AsyncImageLoader.isShow = false;
	// UserInfoTools.saveImage(SetupActivity.this, false);
	// isImage = !isImage;
	// Toast.makeText(SetupActivity.this, "图片隐藏",Toast.LENGTH_SHORT).show();
	// }else{
	// SetImageShow.setBackgroundResource(R.drawable.on_a);
	// AsyncImageLoader.isShow = true;
	// UserInfoTools.saveImage(SetupActivity.this, true);
	// isImage = !isImage;
	// Toast.makeText(SetupActivity.this, "图片显示",Toast.LENGTH_SHORT).show();
	// }
	//
	// }
	/**
	 * 清除缓存
	 */
	private void clearImageView() {
		if (progressDialog1 != null) {
			progressDialog1.cancel();
		}
		progressDialog1 = new ProgressDialog(this);
		progressDialog1.setIndeterminate(true);
		progressDialog1.setCancelable(true);
		progressDialog1.setMessage("正在删除,请稍候...");
		progressDialog1.show();
		idBotton = false;
		// System.out.println("111111删除开始");
		String path = AsyncImageLoader.PATH;
		if (path != null) {
			if (!isNull(path)) {
				Toast.makeText(SetupActivity.this, "已无缓存", Toast.LENGTH_SHORT)
						.show();
				idBotton = true;
				if (progressDialog1 != null && progressDialog1.isShowing())
					progressDialog1.cancel();
				return;
			}
			if (delFolder(path)) {
				Toast.makeText(SetupActivity.this, "清除缓存成功", Toast.LENGTH_SHORT)
						.show();
				idBotton = true;
				if (progressDialog1 != null && progressDialog1.isShowing())
					progressDialog1.cancel();
			} else {
				// 没有sd卡
				Toast.makeText(SetupActivity.this, "已无缓存数据", Toast.LENGTH_SHORT)
						.show();
				idBotton = true;
				if (progressDialog1 != null && progressDialog1.isShowing())
					progressDialog1.cancel();
			}
		} else {
			Toast.makeText(SetupActivity.this, "已无缓存数据", Toast.LENGTH_SHORT)
					.show();
			idBotton = true;
			if (progressDialog1 != null && progressDialog1.isShowing())
				progressDialog1.cancel();
		}
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "设置";
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.setup;
	}

	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 4;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 */
	public boolean delFolder(String folderPath) {

		boolean flags = false;
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
			flags = true;
		} catch (Exception e) {
			// System.out.println("111111删除出错");
			flags = false;
			e.printStackTrace();
		}
		return flags;
	}

	/**
	 * 判断删除的缓存是否为空
	 */
	private boolean isNull(String path) {
		File file = new File(path);

		if (!file.exists())
			return false;
		if (!file.isDirectory())
			return false;
		String[] tempList = file.list();
		if (tempList.length <= 0) {
			return false;
		}

		return true;
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public void delAllFile(String path) {
		// System.out.println("111111删除进入");
		File file = new File(path);

		if (!file.exists())
			return;
		if (!file.isDirectory())
			return;
		String[] tempList = file.list();
		if (tempList.length <= 0) {
			return;
		}
		File temp = null;

		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

}
