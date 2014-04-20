package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.UserMessageBean;

/**
 * 站内消息处理
 * 
 * @author zsx
 * 
 */
public class UserMessageHandler extends DefaultJSONData {

	/** 当前页索引 */
	public int pageIndex;
	/** 每页显示条数 */
	public int pageSize;
	/** 总记录数 */
	public int recordCount;
	/** 站内信息列表 */
	public List<UserMessageBean> messageList;
	/** 站内信息bean */
	private UserMessageBean userMessageBean;

	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub

		if (object == null) {
			return;
		}
		JSONObject data = object.optJSONObject("Data");
		pageIndex = data.optJSONObject("PageInfo").optInt("PageIndex");
		pageSize = data.optJSONObject("PageInfo").optInt("PageSize");
		recordCount = data.optJSONObject("PageInfo").optInt("RecordCount");

		messageList = new ArrayList<UserMessageBean>();
		JSONArray MessageList = data.optJSONArray("MessageList");
		if (MessageList != null) {
			for (int index = 0; index < MessageList.length(); index++) {
				if (MessageList.optJSONObject(index) != null) {
					userMessageBean = new UserMessageBean();
					userMessageBean.messageContent = MessageList.optJSONObject(
							index).optString("MessageContent");
					userMessageBean.releaseTime = MessageList.optJSONObject(
							index).optString("ReleaseTime");
					userMessageBean.RID = MessageList.optJSONObject(index)
							.optString("RID");
					userMessageBean.MID = MessageList.optJSONObject(index)
							.optString("MID");
					userMessageBean.isSee = MessageList.optJSONObject(index)
							.optInt("IsSee");

					messageList.add(userMessageBean);
				}
			}
		}
	}
}
