package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.CommentBean;
import cc.android.supu.bean.GoodsCommentBean;
import cc.android.supu.bean.MemberBean;

/** 
 * 购买评论列表信息解析器
 * @author sheng
 *
 */
public class GoodsCommentHandler extends DefaultJSONData {
	
	/** 当前页*/
	public int pageIndex;
	/** 每页显示条数*/
	public int pageSize;
	/** 总记录数*/
	public int recordCount;
	
	public List<GoodsCommentBean> list;
	
	private CommentBean commentBean;
	
	private MemberBean memberBean;
	
	private GoodsCommentBean goodsCommentBean;
	
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
			
			JSONArray commentList = data.optJSONArray("CommentList");
			if(commentList != null){
				list = new ArrayList<GoodsCommentBean>();
				for(int i=0;i<commentList.length();i++){
					if(commentList.optJSONObject(i) != null){
						goodsCommentBean = new GoodsCommentBean();
						
						JSONObject comment = commentList.optJSONObject(i).optJSONObject("Comment");
						if(comment != null){
							commentBean = new CommentBean();
							commentBean.id = comment.optString("Id");
							commentBean.goodsScore = comment.optInt("GoodsScore");
							commentBean.commentContent = comment.optString("CommentContent");
							commentBean.commentTime = comment.optString("CommentTime");
							goodsCommentBean.commentBean = commentBean;
						}
						
						
						JSONObject member = commentList.optJSONObject(i).optJSONObject("Member");
						if(member != null){
							memberBean = new MemberBean();
							memberBean.account = member.optString("Account");
							memberBean.level = member.optInt("LevelCode");
							//member.optString("ImageUrl");
							goodsCommentBean.memberBean = memberBean;
						}
						
						list.add(goodsCommentBean);	
					}
				}
			}
		}
		
	}
}
