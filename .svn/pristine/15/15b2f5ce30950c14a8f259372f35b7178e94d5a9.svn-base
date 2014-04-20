package cc.android.supu.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import cc.android.supu.R;
import cc.android.supu.bean.IndexGoodBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class IndexProductAdapter extends BaseExpandableListAdapter {
	private Context context;
	private LinkedHashMap<String, ArrayList<IndexGoodBean>> goods;
	private LayoutInflater layoutInflater;

	public IndexProductAdapter(Context context, LinkedHashMap<String, ArrayList<IndexGoodBean>> goods) {
		this.context = context;
		this.goods = goods;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getGroupCount() {
		return goods.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		String key = getKey(groupPosition);
		return goods.get(key);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		String key = getKey(groupPosition);
		if (goods.get(key) != null)
			return goods.get(key).get(childPosition);
		else
			return new IndexGoodBean("", "", "0");
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		TextView textView;
		if (convertView == null) {
			convertView =  layoutInflater.inflate(R.layout.index_producttitle_item, parent, false);
			textView = (TextView) convertView.findViewById(R.id.index_producttitle);
			convertView.setTag(textView);
		} else {
			textView = (TextView) convertView.getTag();
		}
		textView.setText(getKey(groupPosition));
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		final ProductGalleryItem galleryItem;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.index_product_item, parent, false);
			galleryItem = new ProductGalleryItem(convertView);
			convertView.setTag(galleryItem);
		} else {
			galleryItem = (ProductGalleryItem) convertView.getTag();
		}
		ArrayList<IndexGoodBean> goodBeans = goods.get(getKey(groupPosition));
		if (goodBeans != null) {
			int size = goodBeans.size();
//			if (size > 3) {
//				galleryItem.pointsview.setVisibility(View.GONE);
//			} else {
//				galleryItem.pointsview.setVisibility(View.VISIBLE);
//			}
			if (size > 0) {
				IndexGoodsGalleryAdapter adapter = new IndexGoodsGalleryAdapter(context, goodBeans);
				galleryItem.gallery.setAdapter(adapter);
			}
		}
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/**
	 * @param position
	 * @return 根据Index获取相应的Key
	 */
	private String getKey(int position) {
		Set<String> keys = goods.keySet();
		String key = "";
		int i = 0;
		for (String s : keys) {
			if (i == position) {
				key = s;
				break;
			} else {
				i++;
			}
		}
		return key;
	}

	class ProductGalleryItem {
		private Gallery gallery;
		//private ImageView pointsview;

		public ProductGalleryItem(View view) {
			gallery = (Gallery) view.findViewById(R.id.indexproductgallery);
		//	pointsview = (ImageView) view.findViewById(R.id.indexproductpoints);
		}
	}
}
