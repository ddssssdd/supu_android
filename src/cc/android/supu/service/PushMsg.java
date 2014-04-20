package cc.android.supu.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * 开启/关闭消息推送
 * @author Administrator
 *
 */
public class PushMsg {
	
	
	private PendingIntent pendingIntent = null;
	private AlarmManager alarmManager = null;
	private static PushMsg instance = null;
	/** 消息推送是否已开启，true：已开启；false：未开启*/
//	public boolean isOpen;
	
	public static PushMsg getInstance(){
		if(instance == null){
			instance = new PushMsg();
		}
		return instance;
	}
	
	/**
	 * 开启消息推送
	 */
	public void openMsgPush(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"alarm_record", Activity.MODE_PRIVATE);
		alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, Push_Service.class);
		pendingIntent = PendingIntent.getBroadcast(context, 0,intent, 0);
		alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000*60*30,
				pendingIntent);
//		isOpen = true;
		//System.out.println("消息推送已开启！");
	}
	
	/**
	 * 关闭消息推送
	 */
	public void closeMsgPush(){
		if(pendingIntent != null){
			alarmManager.cancel(pendingIntent);
//			isOpen = false;
			System.out.println("消息推送已关闭！");
		}
	}
}
