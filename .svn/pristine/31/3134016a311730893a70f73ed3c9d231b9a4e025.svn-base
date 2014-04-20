package cc.android.supu;

import java.util.TreeMap;

import android.webkit.WebView;
import android.widget.Toast;

import cc.android.supu.handler.DefaultJSONData;
import cc.android.supu.handler.GoodsDesciptionHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;

/**
 * 商品描述页面
 * @author sheng
 *
 */
public class GoodsDescriptionActivity extends BaseActivity {
	
	/** 商品编号*/
	private String goodsSN; 
	/** 商品描述信息解析类*/
	private GoodsDesciptionHandler jsonData;
	private WebView webView;
	
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "商品描述";
	}
	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}
	@Override
	protected void backBtnOnClick() {
		// TODO Auto-generated method stub
		finish();
	}
	
	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		if(getIntent() != null){
			goodsSN = getIntent().getStringExtra("goodsSN");
		}
		webView = (WebView) findViewById(R.id.goodsdescri_webview);
		requestServer(requestGoodsDescription);
		
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.goodsdescription;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	/**
	 * 商品详情请求
	 */
	PageRequest requestGoodsDescription = new PageRequest() {
		
		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("GoodsSN", goodsSN);
			jsonData = new GoodsDesciptionHandler();
			Tools.requestToParse(GoodsDescriptionActivity.this, ConstantUrl.GETGOODESCRIPTION, "GetGoodsDescription", params, jsonData, false);
			if(Tools.responseValue == 1){
				if(jsonData.result_code == 0){
					handler.sendEmptyMessage(STATE_SUCCESS);
				}else{
					handler.sendEmptyMessage(STATE_FAILURE);
				}
			}else{
				handler.sendEmptyMessage(STATE_ERROR);
			}
		}
	};
	
	protected void dealwithMessage(android.os.Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			String data = jsonData.note;
			webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
			break;
		case STATE_FAILURE:
			Toast.makeText(this, "服务器异常！", Toast.LENGTH_SHORT).show();
			break;
		case STATE_ERROR:
			Toast.makeText(this, "请求异常！", Toast.LENGTH_SHORT).show();
			break;
		}
	};

}
