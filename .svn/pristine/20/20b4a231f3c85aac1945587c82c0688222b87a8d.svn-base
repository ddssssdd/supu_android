package cc.android.supu.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.android.supu.R;
import cc.android.supu.bean.GoodsCommentBean;
import android.content.Context;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 商品评论列表的适配器
 * @author sheng
 *
 */
public class GoodsCommentAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<GoodsCommentBean> mList;
	private LayoutInflater inflater;
	private GoodsCommentBean goodsCommentBean;
	
	public GoodsCommentAdapter(Context context, List<GoodsCommentBean> list){
		mContext = context;
		mList = list;
		inflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CacheView cacheView = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.goodscomment_item, null);
			cacheView = new CacheView(convertView);
			convertView.setTag(cacheView);
		}else{
			cacheView = (CacheView) convertView.getTag();
		}
		goodsCommentBean = mList.get(position);
		
		cacheView.getAccountTv().setText(goodsCommentBean.memberBean.account);
		cacheView.getContentTv().setText(goodsCommentBean.commentBean.commentContent);
		cacheView.getTimeTv().setText(fomatTime(goodsCommentBean.commentBean.commentTime));
		//设置评分
		int score = goodsCommentBean.commentBean.goodsScore;
		switch (score) {
		case 1:
			cacheView.getScore1().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore2().setImageResource(R.drawable.heart_grey_small);
			cacheView.getScore3().setImageResource(R.drawable.heart_grey_small);
			cacheView.getScore4().setImageResource(R.drawable.heart_grey_small);
			cacheView.getScore5().setImageResource(R.drawable.heart_grey_small);
			break;
		case 2:
			cacheView.getScore1().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore2().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore3().setImageResource(R.drawable.heart_grey_small);
			cacheView.getScore4().setImageResource(R.drawable.heart_grey_small);
			cacheView.getScore5().setImageResource(R.drawable.heart_grey_small);
			break;
		case 3:
			cacheView.getScore1().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore2().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore3().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore4().setImageResource(R.drawable.heart_grey_small);
			cacheView.getScore5().setImageResource(R.drawable.heart_grey_small);
			break;
		case 4:
			cacheView.getScore1().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore2().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore3().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore4().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore5().setImageResource(R.drawable.heart_grey_small);
			break;
		case 5:
			cacheView.getScore1().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore2().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore3().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore4().setImageResource(R.drawable.heart_red_small);
			cacheView.getScore5().setImageResource(R.drawable.heart_red_small);
			break;
		}
		
		//设置用户等级
		int level = goodsCommentBean.memberBean.level;
		switch (level) {
		case 1:
			cacheView.getLevelImag().setImageResource(R.drawable.one);
			break;
		case 2:
			cacheView.getLevelImag().setImageResource(R.drawable.two);
			break;
		case 3:
			cacheView.getLevelImag().setImageResource(R.drawable.three);
			break;
		case 4:
			cacheView.getLevelImag().setImageResource(R.drawable.four);
			break;
		}
		
		
		return convertView;
	}
	
	class CacheView{
		
		private View baseView;
		private TextView accountTv;
		private TextView contentTv;
		private TextView timeTv;
		private ImageView levelImag;
		private ImageView score1;
		private ImageView score2;
		private ImageView score3;
		private ImageView score4;
		private ImageView score5;
		private ImageView star;
		
		
		public CacheView(View baseView){
			this.baseView = baseView;
		}
		
		public TextView getAccountTv(){
			if(accountTv == null){
				accountTv=(TextView) baseView.findViewById(R.id.goodscomment_account);
			}
			return accountTv;
		}
		
		public TextView getContentTv(){
			if(contentTv == null){
				contentTv=(TextView) baseView.findViewById(R.id.goodscomment_commentContent);
			}
			return contentTv;
		}
		
		public TextView getTimeTv(){
			if(timeTv == null){
				timeTv=(TextView) baseView.findViewById(R.id.goodscomment_commentTime);
			}
			return timeTv;
		}
		
		public ImageView getLevelImag(){
			if(levelImag == null){
				levelImag = (ImageView) baseView.findViewById(R.id.goodscomment_star_levelImg);
			}
			return levelImag;
		}
		
		public ImageView getScore1(){
			if(score1 == null){
				score1 = (ImageView) baseView.findViewById(R.id.goodscomment_score1);
			}
			return score1;
		}
		
		public ImageView getScore2(){
			if(score2 == null){
				score2 = (ImageView) baseView.findViewById(R.id.goodscomment_score2);
			}
			return score2;
		}
		public ImageView getScore3(){
			if(score3 == null){
				score3 = (ImageView) baseView.findViewById(R.id.goodscomment_score3);
			}
			return score3;
		}
		
		public ImageView getScore4(){
			if(score4 == null){
				score4 = (ImageView) baseView.findViewById(R.id.goodscomment_score4);
			}
			return score4;
		}
		
		public ImageView getScore5(){
			if(score5 == null){
				score5 = (ImageView) baseView.findViewById(R.id.goodscomment_score5);
			}
			return score5;
		}
		
		public ImageView getStar(){
			star = new ImageView(mContext);
			LinearLayout.LayoutParams lp = new LinearLayout
					.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.gravity = Gravity.CENTER_VERTICAL|Gravity.LEFT;
			lp.leftMargin = 3;
			star.setLayoutParams(lp);
			star.setImageResource(R.drawable.star);
			return star;
		}
	}
	
	private String fomatTime(String scrTime){
		String strTime = scrTime.trim();
		int length = strTime.length();
		if(length < 13){
			for(int j=0;j<13-length;j++){
				strTime = strTime+"0";
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Long time = new Long(strTime);
		return  format.format(time);
	}
}
