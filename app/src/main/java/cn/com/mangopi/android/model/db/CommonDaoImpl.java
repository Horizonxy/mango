package cn.com.mangopi.android.model.db;

import android.content.Context;

import cn.com.mangopi.android.model.bean.CommonBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共数据缓存dao
 */
public class CommonDaoImpl extends BaseDaoImpl<CommonBean, Long> {
	
	private Map<String, Object> map;
	
	public CommonDaoImpl(Context context) {
		super(context, CommonBean.class);
		this.map = new HashMap<String, Object>();
	}
	
	public void deleteAll() {
		try {
			super.deleteAll(CommonBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据页面删除内容
	 */
	public void deleteByAty(String aty, int memberId){
		map.clear();
		map.put(CommonBean.MEMBER_ID, memberId);
		map.put(CommonBean.ATY, aty);
		deleteByColumns(map);
	}
	
	/**
	 * 根据页码、页面删除内容
	 */
	public void deleteByNo(int pageNo,String aty, int memberId){
		map.clear();
		map.put(CommonBean.MEMBER_ID, memberId);
		map.put(CommonBean.PAGE_NO, pageNo);
		map.put(CommonBean.ATY, aty);
		deleteByColumns(map);
	}
	/**
	 * 根据页码、页面、类型删除内容
	 */
	public void deleteByNo(int pageNo, String aty, String dataType, int memberId){
		map.clear();
		map.put(CommonBean.MEMBER_ID, memberId);
		map.put(CommonBean.PAGE_NO, pageNo);
		map.put(CommonBean.ATY, aty);
		map.put(CommonBean.DATA_TYPE, dataType);
		deleteByColumns(map);
	}
	
	/**
	 * 保存数据
	 * @param pageNo  页码
	 * @param aty  页面
	 * @param content  存储内容
	 */
	public void saveData(int pageNo, String aty, String content, int memberId){
		CommonBean data = new CommonBean();
		data.setData_page_no(pageNo);
		data.setMember_id(memberId);
		data.setAty(aty);
		data.setData(content);
		save(data);
	}

	/**
	 * 重置map
	 * @param pageNo  页码
	 * @param aty  页面
	 * @return
	 */
	public Map<String, Object> getReSetMap(int pageNo, String aty ) {
		map.clear();
		map.put(CommonBean.ATY, aty);
		map.put(CommonBean.PAGE_NO, pageNo);
		return map;
	}
	/**
	 * 重置map
	 * @param pageNo  页码
	 * @param aty  页面
	 * @param dataType  数据类型
	 * @return
	 */
	public Map<String, Object> getReSetMap(int pageNo, String aty, String dataType ) {
		map.clear();
		map.put(CommonBean.ATY, aty);
		map.put(CommonBean.PAGE_NO, pageNo);
		map.put(CommonBean.DATA_TYPE, dataType);
		return map;
	}

	/**
	 * 保存数据
	 * @param pageNo  页码
	 * @param aty  页面
	 * @param content  存储内容
	 * @param dataType  存储内容
	 * @param memberId  用户id
	 */
	public void saveData(int pageNo, String aty, String content, String dataType, int memberId){
		CommonBean data = new CommonBean();
		data.setMember_id(memberId);
		data.setData_page_no(pageNo);
		data.setAty(aty);
		data.setData(content);
		data.setData_type(dataType);
		save(data);
	}
	
	/**
	 * 根据页面、类型删除内容
	 */
	public void deleteByAty(String aty, String dataType, int memberId){
		map.clear();
		map.put(CommonBean.MEMBER_ID, memberId);
		map.put(CommonBean.ATY, aty);
		map.put(CommonBean.DATA_TYPE, dataType);
		deleteByColumns(map);
	}
	
	public void saveData(String dataType, Serializable data){
		List<CommonBean> List = findByColumn(CommonBean.DATA_TYPE, dataType);
		if(List != null && List.size() > 0){
			CommonBean common = List.get(0);
			common.setData(data);
			update(common);
		} else {
			CommonBean common = new CommonBean();
			common.setData_type(dataType);
			common.setData(data);
			save(common);
		}
	}
}
