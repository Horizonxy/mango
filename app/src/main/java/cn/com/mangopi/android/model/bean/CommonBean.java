package cn.com.mangopi.android.model.bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * 数据缓存实体
 *
 * @author 蒋先明
 *
 * @date 2016年1月27日
 */
public class CommonBean implements Serializable {

	public static final String ATY = "aty";   //所在页面
	public static final String PAGE_NO = "data_page_no";   //数据所属页码
	public static final String DATA_TYPE = "data_type";   //数据类别
	public static final String MEMBER_ID = "member_id";   //用户id
	
	/**
	 * 数据db id
	 */
	@DatabaseField(generatedId = true, unique = true)
	private long id;

	/**
	 * 数据db id
	 */
	@DatabaseField
	private long member_id;
	/**
	 * 数据内容
	 */
	@DatabaseField(dataType = DataType.SERIALIZABLE)
	private Serializable data;
	/**
	 * 所在页面
	 */
	@DatabaseField
	private String aty;
	/**
	 * 数据内容id
	 */
	@DatabaseField
	private long data_id;
	/**
	 * 数据所属页码
	 */
	@DatabaseField
	private long data_page_no;
	/**
	 * 关联内容id
	 */
	@DatabaseField
	private long refrence_id;
	/**
	 * 数据类别
	 */
	@DatabaseField
	private String data_type;
	
	public CommonBean() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMember_id() {
		return member_id;
	}

	public void setMember_id(long member_id) {
		this.member_id = member_id;
	}

	public Serializable getData() {
		return data;
	}

	public void setData(Serializable data) {
		this.data = data;
	}

	public String getAty() {
		return aty;
	}

	public void setAty(String aty) {
		this.aty = aty;
	}

	public long getData_id() {
		return data_id;
	}

	public void setData_id(long data_id) {
		this.data_id = data_id;
	}

	public long getData_page_no() {
		return data_page_no;
	}

	public void setData_page_no(long data_page_no) {
		this.data_page_no = data_page_no;
	}

	public long getRefrence_id() {
		return refrence_id;
	}

	public void setRefrence_id(long refrence_id) {
		this.refrence_id = refrence_id;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
}
