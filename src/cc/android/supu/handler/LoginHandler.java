package cc.android.supu.handler;

import org.json.JSONObject;

import cc.android.supu.VipActivity;
import cc.android.supu.bean.MemberBean;
import cc.android.supu.tools.PublicParams;
import cc.android.supu.tools.UserInfoTools;

/**
 * 登录handler
 * 
 * @author zsx
 * 
 */
public class LoginHandler extends DefaultJSONData {

	/** 用户的编号   */
	public static String memberId = "";
	/**  用户名 */
	public static String account = "";
	/**  等级 */
	public static int level = 0;
	/**  用户头像*/
	public static String imageUrl ="";
	/**  账号剩余 */
	public static String price ="";
	/**  积分 */
	public static int scores = 0;
	/** 用户bean */
	public MemberBean memberBean;

	@Override
	public void parse(JSONObject object) {
		if (object == null) {
			return;
		}
		memberBean = new MemberBean();
		object = object.optJSONObject("Data");
		memberId = object.optJSONObject("Member").optString("MemberId");
		account = object.optJSONObject("Member").optString("Account");
		level = object.optJSONObject("Member").optInt("Level");
		imageUrl = object.optJSONObject("Member").optString("ImageUrl");
		price = object.optJSONObject("Member").optString("Price");
		scores =  object.optJSONObject("Member").optInt("Scores");
		
	}


	/**
	 * 清空用户账号
	 */
	public static void setAccount() {
		account = "";
	}
}
