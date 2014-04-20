package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.ConsultBean;
import cc.android.supu.bean.GoodsConsultBean;
import cc.android.supu.bean.MemberBean;
import cc.android.supu.bean.ReplyBean;

/**
 * 商品咨询信息解析器
 * @author sheng
 *
 */
public class GoodsConsultHandler extends DefaultJSONData {
	
	/** 当前页*/
	public int pageIndex;
	/** 每页显示条数*/
	public int pageSize;
	/** 总记录数*/
	public int recordCount;
	/** 咨询 列表 */
	public List<GoodsConsultBean>  consultsList;
	/** 某一个咨询的客户列表*/
	private List<MemberBean> memberList;
	
	private ConsultBean consultBean;
	
	private ReplyBean replyBean;
	
	private MemberBean memberBean;
	
	private GoodsConsultBean goodsConsultBean;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if(object == null){
			return;
		}
		
		JSONObject data = object.optJSONObject("Data");
		if(data != null){
			JSONObject pageInfo = data.optJSONObject("PageInfo");
			if(pageInfo != null){
				pageIndex = pageInfo.optInt("PageIndex");
				pageSize = pageInfo.optInt("PageSize");
				recordCount = pageInfo.optInt("RecordCount");
			}
			
			JSONArray consultList = data.optJSONArray("ConsultList");
			if(consultList != null){
				consultsList = new ArrayList<GoodsConsultBean>();
				for(int i=0;i<consultList.length();i++){
					goodsConsultBean = new GoodsConsultBean();
					JSONObject consutlObject = consultList.optJSONObject(i);
					if(consutlObject != null){
						
						JSONObject consult = consutlObject.optJSONObject("Consult");
						if(consult != null){
							consultBean = new ConsultBean();
							consultBean.id = consult.optString("Id");
							consultBean.consultContent = consult.optString("ConsultContent");
							consultBean.consultTime = consult.optString("ConsultTime");
							goodsConsultBean.consultBean = consultBean;
						}
						
						JSONObject reply = consutlObject.optJSONObject("Reply");
						if(reply != null){
							replyBean = new ReplyBean();
							replyBean.id = reply.optString("Id");
							replyBean.replyContent = reply.optString("ReplyContent");
							replyBean.replyTime = reply.optString("ReplyTime");
							goodsConsultBean.replyBean = replyBean;
						}
						
						JSONArray member = consutlObject.optJSONArray("Member");
						if(member != null){
							memberList = new ArrayList<MemberBean>();
							for(int j=0;j<member.length();j++){
								if(member.optJSONObject(j) != null){
									memberBean = new MemberBean();
									memberBean.account = member.optJSONObject(j).optString("Account");
									memberBean.level = member.optJSONObject(j).optInt("LevelCode");
									memberList.add(memberBean);
								}
							}
							goodsConsultBean.memberList = memberList;
						}
						
						consultsList.add(goodsConsultBean);
					}
				}
			}
		}
	}
}
