package cc.android.supu.handler;

import org.json.JSONObject;

/**
 * 注册handler
 * 
 * @author zsx
 * 
 */
public class RegistHandler extends DefaultJSONData {
	
	/**用户编号*/
	public String memberId;
	/**用户账号*/
	public String account;
	/**用户等级*/
	public int level;
	/**用户账户余额*/
	public String price;
	/**用户积分*/
	public int scores;
	/** 注册送券提示信息 */
	public String recommendTicketMessage;
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if(object == null){
			return;
		}
		object = object.optJSONObject("Data");
		memberId = object.optString("MemberId");
		account = object.optString("Account");
		level = object.optInt("Level");
		price =  object.optString("Price");
		scores = object.optInt("Scores");
		recommendTicketMessage = object.optString("RecommendTicketMessage");

	}
}
