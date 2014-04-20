package cc.android.supu;

import java.util.LinkedList;

import cc.android.supu.tools.CrashHandler;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

/**
 * @author sss
 */
public class SupuApplication extends Application {

	private static LinkedList<Activity> activities = new LinkedList<Activity>();
	public static Class<?> indexActivity = IndexActivity.class;

	private static Class<?>[] bottomActivities = new Class<?>[] { IndexActivity.class, CategoryActivity.class,
			MoreActivity.class, ShopCarActivity.class, LoginActivity.class, VipActivity.class };

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}

	public static void setBottomActivities(Class<?>[] bottomActivities) {
		SupuApplication.bottomActivities = bottomActivities;
	}

	/**
	 * 添加新的activity
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean add(Activity activity) {
		// int position = 0;
		// boolean isExist = false;
		// 导航栏activity进栈，删除非导航栏activity
		if (isBottomActivity(activity)) {
			for (int i = 0; i < activities.size() - 1; i++) {

				if (!isBottomActivity(activities.get(i))) {
					popActivity(activities.get(i));
					i--;
				}
				// if (i > 0) {
				// 获得重复activity位置
				if (i > 0 && activities.get(i).getClass().equals(activity.getClass())) {
					// isExist = true;
					// position = i;
					activities.get(i).finish();
					activities.remove(i);
					i--;
					// }
				}
			}

		}

		if (!activities.add(activity)) {
			return false;
		}
		// 删除重复activity
		// if (isExist) {
		// isExist = false;
		// activities.remove(position);
		// }

		return true;
	}

	/**
	 * 关闭除参数activity外的所有activity
	 * 
	 * @param activity
	 */
	public static void finish(Activity activity) {
		for (Activity iterable : activities) {
			if (activity != iterable) {
				iterable.finish();
			}
		}
	}

	/**
	 * 关闭所有的activity
	 */
	public static void exit() {
		for (Activity activity : activities) {
			if (activity != null) {
				activity.finish();
			}
		}
		System.out.println("退出系统");
		// System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 出现的Crash
	 */
	public static void crashApper() {
		for (Activity activity : activities) {
			activity.finish();
		}
		System.out.println("出现的Crash");
	}

	/**
	 * 删除指定activity
	 * 
	 * @param activity
	 */
	public static void popActivity(Activity activity) {

		if (activity != null) {
			activity.finish();
			activities.remove(activity);
		}

	}

	/**
	 * 获得当前activity
	 * 
	 * @return
	 */
	public static Activity currentActivity() {
		Activity activity = activities.get(activities.size() - 1);

		return activity;
	}

	/**
	 * activity是否为底部导航
	 * 
	 * @return
	 */
	public static boolean isBottomActivity(Activity activity) {

		for (int i = 0; i < bottomActivities.length; i++) {
			if (activity.getClass() != bottomActivities[i]) {

			} else {
				return true;
			}
		}

		return false;
	}

	/**
	 * 如需返回IndexActivity则返回IndexActivity
	 * 
	 * @param activity
	 * @param context
	 */
	public static void backIndex(Context context) {

		if (activities.size() <= 0) {
			return;
		}

		if (isBottomActivity(activities.get(activities.size() - 1))) {
			Intent intent = new Intent();
			intent.setClass(context, indexActivity);
			context.startActivity(intent);
		}
	}

	/**
	 * 删除已经finish的activity
	 * 
	 * @param activity
	 */
	public static void removeActivity(Activity activity) {

		if (activity != null) {
			activities.remove(activity);
		}
	}

	/**
	 * 删除非底部导航activity
	 */
	public static void delNotBottomActivity() {
		for (int i = 0; i < activities.size(); i++) {

			if (!isBottomActivity(activities.get(i))) {
				popActivity(activities.get(i));
				if (i >= 1) {
					i--;
				}
			}
		}
	}
}
