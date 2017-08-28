package cn.com.mangopi.android;

public class Constants {

	public static final int PAGE_SIZE = 20;

	public static final String END_POIND = "https://www.mangopi.com.cn/";

	public static final String BASE_DIR = "mango";
	public static final String IMG_CACHE_DIR = BASE_DIR.concat("/image_cache");
	public static final String PICTURE_DIR = BASE_DIR.concat("/photo");
	public static final String IMG_WEB_CACHE_DIR = BASE_DIR.concat("/image_web");

	public static final int REQ_PERMISSIONS = 1000;

	/** 缓存100天 */
	public static final long CACHE_TIME = 60 * 60 * 24 * 100;
	/** 10秒超时 */
	public static final long TIME_OUT = 10;
	/** 缓存容量 */
	public static final long SIZE_OF_CACHE = 100 * 1024 * 1024;

	public static final String SESS_ID = "sess_id";
	public static final String FILE_PREFIX = "file://";
	public static final int GENDER_MAN = 1;
	public static final int GENDER_FEMALE = 0;
	public static final String INDEX_TWO_ADVERT = "index_two_banner";
	public static final String INDEX_THREEE_ADVERT = "index_three_advert";
	public static final String INDEX_BANNER = "index_banner";

	public static final String UNION_PAY = "unionpay";
	public static final String ALI_PAY = "alipay";
	public static final String WECHAT_PAY = "wechatpay";

	public static final String BUNDLE_CARD_LIST = "bundle_member_card_list";
	public static final String BUNDLE_CLASSIFY = "bundle_member_card_list";
	public static final String BUNDLE_ID = "bundle_id";
    public static final String BUNDLE_URL = "bundle_url";
	public static final String BUNDLE_BULLETIN = "bundle_bulletin";
    public static final String BUNDLE_CLASSIFY_LIST = "bundle_mclassify_list";
	public static final String BUNDLE_COURSE_DETAIL = "bundle_course_detail";
    public static final String BUNDLE_TEXT = "bundle_text";
	public static final String BUNDLE_TITLE = "bundle_title";
	public static final String BUNDLE_ORDER = "bundle_order";
    public static final String BUNDLE_ORDER_ID = "bundle_order_id";
	public static final String BUNDLE_ORDER_RELATION = "bundle_order_relation";
    public static final String BUNDLE_WEBVIEW_URL = "bundle_webview_url";
    public static final String BUNDLE_RIGHT_TEXT = "bundle_right_text";
    public static final String BUNDLE_LIMIT_NUM = "bundle_limit_num";
    public static final String BUNDLE_TYPE = "bundle_type";
    public static final String BUNDLE_CONTENT = "bundle_content";
    public static final String BUNDLE_MUST = "bundle_must";
    public static final String BUNDLE_AMOUNT = "bundle_amount";
    public static final String BUNDLE_URL_LIST = "bundle_url_list";
	public static final String BUNDLE_POSITION = "bundle_position";
	public static final String BUNDLE_INPUT_TYPE = "bundle_input_type";

	public static final String FORM_DATA = "multipart/form-data";

	public static enum UserIndentity {

		PUBLIC("public", "自由人"),
		STUDENT("student", "学生"),
		TUTOR("tutor", "导师"),
		COMMUNITY("community", "社团"),
		COMPANY("company", "企业");

		private String indentity;
		private String indentityLabel;

		UserIndentity(String indentity, String indentityLabel) {
			this.indentity = indentity;
			this.indentityLabel = indentityLabel;
		}

		public static UserIndentity get(String indentity) {
			for (UserIndentity inst : values()) {
				if (indentity.equals(inst.indentity)) {
					return inst;
				}
			}
			return UserIndentity.PUBLIC;
		}

		public String getIndentity() {
			return indentity;
		}

		public void setIndentity(String indentity) {
			this.indentity = indentity;
		}

		public String getIndentityLabel() {
			return indentityLabel;
		}

		public void setIndentityLabel(String indentityLabel) {
			this.indentityLabel = indentityLabel;
		}
	}

	public static enum EntityType {
		HOME(1),
		MEMBER(4),
		ORDER(5),
		WORKS(6),
		TREND(7),
		COURSE(8),
		COURSE_CLASSIFY(9),
		NONE(-1);

		private int typeId;

		EntityType(int typeId) {
			this.typeId = typeId;
		}

		public static EntityType get(int type) {
			for (EntityType inst : values()) {
				if (type == inst.typeId) {
					return inst;
				}
			}
			return EntityType.NONE;
		}

		public int getTypeId() {
			return typeId;
		}

		public void setTypeId(int typeId) {
			this.typeId = typeId;
		}
	}
}
