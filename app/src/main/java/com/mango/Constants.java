package com.mango;

public class Constants {

	public static final int PAGE_SIZE = 20;

	public static final String END_POIND = "https://www.mangopi.com.cn/";

	public static final String BASE_DIR = "mango";
	public static final String IMG_CACHE_DIR = BASE_DIR.concat("/image_cache");
	public static final String PICTURE_DIR = BASE_DIR.concat("/image_cache");

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
	public static final int GENDER_WUMEN = 0;
	public static final String INDEX_THREEE_ADVERT = "index_three_advert";
	public static final String INDEX_BANNER = "index_banner";

	public static final String PUBLIC = "public";
	public static final String STUDENT = "student";
	public static final String TUTOR = "tutor";
	public static final String COMMUNITY = "community";
	public static final String COMPANY = "company";

	public static final int TREND_ENTITY_TYPE_ID = 7;

	public static final String BUNDLE_CARD_LIST = "bundle_member_card_list";
	public static final String BUNDLE_ID = "bundle_id";
}
