package cc.android.supu.handler;

import org.json.JSONObject;

/**
 * 消息推送handler
 * 
 * @author zsx
 * 
 */
public class MessagePushHandler extends DefaultJSONData {
	
	/**消息ID*/
	public String Id;
	/**推送消息的内容*/
	public String PushMessage;
	/**推送时间*/
	public String PushTime;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if(object == null){
			return;
		}
		object = object.optJSONObject("Data");
		Id = object.optString("Id");
		PushMessage = object.optString("PushMessage");
		PushTime = object.optString("PushTime");
//		System.out.println("PushMessage"+PushMessage);
	}
}
