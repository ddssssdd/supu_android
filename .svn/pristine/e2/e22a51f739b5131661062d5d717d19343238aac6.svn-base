package cc.android.supu.tools;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import cc.android.supu.bean.MemberBean;

/**
 * 用户信息工具类
 * 
 */
public class UserInfoTools {

	/**
	 * 保存用户信息,isRecord:是否记录已登录，若为false则不记录
	 */
	public static void saveUserInfo(Context context, MemberBean userBean,
			boolean isRecord) {
		SharedPreferences sp = context.getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("isRecord", isRecord);

		if (userBean.getAccount() != null) {
			editor.putString("account", userBean.getAccount());
		}
		if (userBean.getAccount() != null) {
			editor.putString("password", userBean.getPassword());
		}
		if (userBean.getMemberId() != null) {
			editor.putString("memberId", userBean.getMemberId());
		}
		if (userBean.getMemberId() != null) {
			editor.putInt("level", userBean.getLevel());
		}
		if (userBean.getMemberId() != null) {
			editor.putString("price", userBean.getPrice());
		}
		if (userBean.getMemberId() != null) {
			editor.putInt("scores", userBean.getScores());
		}
		editor.commit();
	}

	/**
	 * 获取用户信息UserBean
	 * 
	 * @return
	 */
	public static MemberBean getUserBean(Context context) {
		SharedPreferences sp = context.getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		MemberBean userBean = new MemberBean();

		userBean.setAccount(sp.getString("account", ""));
		userBean.setPassword(sp.getString("password", ""));
		userBean.setMemberId(sp.getString("memberId", ""));
		userBean.setLevel(sp.getInt("level", 0));
		userBean.setPrice(sp.getString("price", ""));
		userBean.setScores(sp.getInt("scores", 0));
		return userBean;
	}

	/**
	 * 按返回键退出程序时调用 改变登录的状态。登录时选择记录登录，则保留用户信息，否则用户信息清空
	 * 
	 */
	// public static void changeLoginState(Context context) {
	// SharedPreferences sp = context.getSharedPreferences("userInfo",
	// Context.MODE_PRIVATE);
	// if (!sp.getBoolean("isRecord", false)) {
	// Editor editor = sp.edit();
	// editor.putString("account", "");
	// editor.putString("password", "");
	// editor.putString("memberId", "");
	// editor.commit();
	// }
	// }

	/**
	 * 清除用户数据
	 * 
	 * @param context
	 */
	public static void cleanUserInfo(Context context) {
		SharedPreferences sp = context.getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("account", "");
		editor.putString("password", "");
		editor.putString("memberId", "");
		editor.putInt("level", 0);
		editor.putString("price", "");
		editor.putInt("scores", 0);
		editor.commit();
	}

	/**
	 * 获取是否记住登陆的状态
	 */
	public static boolean getIsRecord(Context context) {
		SharedPreferences sp = context.getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		return sp.getBoolean("isRecord", true);
	}

	/**
	 * 判断用户是否成功登陆
	 */
	public static boolean isLogin(Context context) {
		MemberBean memberBean = getUserBean(context);
		if (memberBean == null) {
			return false;
		}
		String account = memberBean.getAccount();
		String memberId = memberBean.getMemberId();

		if (account.equals(""))
			return false;
		if (memberId.equals(""))
			return false;
		if (PublicParams.getMemberId() == null
				|| "".equals(PublicParams.getMemberId())) {
			PublicParams.setMEMBERID(memberId);
		}
		return true;
	}

	/**
	 * 保存是否开启推送
	 */
	public static void saveMessage(Context context, boolean isMessage) {
		SharedPreferences sp = context.getSharedPreferences("message",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("isMessage", isMessage);
		editor.commit();
	}

	/**
	 * 得到是否推送
	 * 
	 * @return
	 */
	public static boolean getMessage(Context context) {

		SharedPreferences sp = context.getSharedPreferences("message",
				Context.MODE_PRIVATE);
		boolean isMessage = false;
		isMessage = sp.getBoolean("isMessage", true);
		return isMessage;
	}

	/**
	 * 保存推送id
	 */
	public static void saveMessageId(Context context, String id) {
		SharedPreferences sp = context.getSharedPreferences("messageId",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("id", id);
		editor.commit();
	}

	/**
	 * 得到推送id
	 * 
	 * @return
	 */
	public static String getMessageId(Context context) {
		SharedPreferences sp = context.getSharedPreferences("messageId",
				Context.MODE_PRIVATE);
		String id = "";
		id = sp.getString("id", "");
		return id;
	}

	/**
	 * 是否显示图片
	 */
	public static void saveImage(Context context, boolean isImage) {
		SharedPreferences sp = context.getSharedPreferences("message",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("isImage", isImage);
		editor.commit();
	}

	/**
	 * 得到是否显示图片的状态
	 * 
	 * @return
	 */
	public static boolean getImage(Context context) {

		SharedPreferences sp = context.getSharedPreferences("message",
				Context.MODE_PRIVATE);
		boolean isImage = false;
		isImage = sp.getBoolean("isImage", true);
		return isImage;
	}

	/**
	 * 保存城市的数据 和 消息推送共用一个文件
	 */
	public static void saveCity(Context context, String empty, String version) {
		SharedPreferences sp = context.getSharedPreferences("message",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("isEmpty", empty);
		editor.putString("version", version);
		editor.commit();
	}

	/**
	 * 得到city数据 isEmpty 0 是没有存储 1是有存储 version 版本号 默认版本是1
	 * 
	 * @return
	 */
	public static ArrayList<String> getCity(Context context) {

		SharedPreferences sp = context.getSharedPreferences("message",
				Context.MODE_PRIVATE);
		ArrayList<String> cityList = new ArrayList<String>();
		String isEmpty = sp.getString("isEmpty", "0");
		String version = sp.getString("version", "1");
		cityList.add(isEmpty);
		cityList.add(version);

		return cityList;
	}

}
