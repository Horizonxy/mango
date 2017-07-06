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

}
