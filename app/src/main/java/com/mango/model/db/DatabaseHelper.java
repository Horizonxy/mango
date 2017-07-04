package com.mango.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mango.model.bean.CommonBean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 数据库helper
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static DatabaseHelper instance;
	private static final String DB_NAME = "mango";
	private static final int DB_VERSION = 1;

	@SuppressWarnings("rawtypes")
	private Map<String, Dao> daos = new HashMap<String, Dao>();

	private Context context;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, CommonBean.class);
		} catch (SQLException e11) {
			e11.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//		try {
//			TableUtils.dropTable(connectionSource, CommonVo.class, true);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		onCreate(database, connectionSource);
	}

	public synchronized void addColumn(SQLiteDatabase db, String tableName, String columnName, String columnType,
                                       Object defaultField) {
		try {
			if (db != null) {
				Cursor c = db.rawQuery("SELECT * from " + tableName + " limit 1 ", null);
				boolean flag = false;

				if (c != null) {
					for (int i = 0; i < c.getColumnCount(); i++) {
						if (columnName.equalsIgnoreCase(c.getColumnName(i))) {
							flag = true;
							break;
						}
					}
					if (flag == false) {
						String sql = "alter table " + tableName + " add " + columnName + " " + columnType;
						if (defaultField != null) {
							sql += " default " + defaultField;
						}
						db.execSQL(sql);
					}
					c.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单例获取该Helper
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized DatabaseHelper getHelper(Context context) {
		context = context.getApplicationContext();
		if (instance == null) {
			synchronized (DatabaseHelper.class) {
				instance = new DatabaseHelper(context);
			}
		}

		return instance;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized Dao getDao(Class clazz) throws SQLException {
		Dao dao = null;
		String className = clazz.getSimpleName();

		if (daos.containsKey(className)) {
			dao = daos.get(className);
		}
		if (dao == null) {
			dao = super.getDao(clazz);
			daos.put(className, dao);
		}
		return dao;
	}

	/**
	 * 释放资源
	 */
	@Override
	public void close() {
		super.close();

		for (String key : daos.keySet()) {
			daos.put(key, null);
			// Dao dao = daos.get(key);
			// dao = null;
		}
	}

	@SuppressWarnings("rawtypes")
	public void close(Class clazz) {
		String className = clazz.getSimpleName();

		if (daos.containsKey(className)) {
			daos.put(className, null);
		}
	}

}
