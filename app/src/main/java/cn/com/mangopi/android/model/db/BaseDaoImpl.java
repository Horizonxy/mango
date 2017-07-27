package cn.com.mangopi.android.model.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

/**
 * @description BaseDao
 */
public class BaseDaoImpl<T, PK extends Serializable> {

	public Dao<T, PK> baseDao;
 
	public DatabaseHelper helper;
	
	//private DatabaseConnection conn;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(Context context, Class<T> clazz){
        try {
        	this.baseDao = getHelper(context).getDao(clazz);
		} catch (SQLException e) {
			e.printStackTrace();
       }
    } 
	
	private DatabaseHelper getHelper(Context context) {
		if (helper == null) { 
			helper = OpenHelperManager.getHelper(context, DatabaseHelper.class); 
					//DatabaseHelper.getHelper(context);
		} 
		return helper; 
	}

	/*public DatabaseConnection getConn(Context context) {
		if (conn == null) {
			conn = getHelper(context).getConnectionSource().getSpecialConnection();
		}
		return conn;
	}*/
	
	public void close(){
		helper.close();
	}
 
	public void close(Class<T> clazz){
		helper.close(clazz);;
	}
	
	public int save(T entity){
		try {
			return baseDao.create(entity);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int saveOrUpdate(T entity) {
		try {
			CreateOrUpdateStatus status = baseDao.createOrUpdate(entity);
			return status.getNumLinesChanged();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public void saveList(final List<T> entites){
		try {
			TransactionManager.callInTransaction(helper.getConnectionSource(),
					new Callable<Void>() {

						@Override
						public Void call() throws Exception {
							for (T t : entites) {
								baseDao.create(t);
							}
							return null;
						}
					});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public int update(T entity){
		try {
			return baseDao.update(entity);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int updateByColumn(Map<String, Object> map, Map<String, Object> byMap) {
		try {
			UpdateBuilder<T, PK> update = baseDao.updateBuilder();
			for (String k : map.keySet()) {
				update.updateColumnValue(k, map.get(k));
			}
			if(byMap.size() > 0){
				Where<T, PK> where = update.where();
				for (String byK : byMap.keySet()) {
					where.eq(byK, byMap.get(byK));
				}
				where.and(byMap.size());
			}
			return update.update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int delete(T entity){
		try {
			return baseDao.delete(entity);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void deleteAll(Class<T> clazz) throws SQLException {
		TableUtils.clearTable(helper.getConnectionSource(), clazz);
	}
	
	public int deleteById(PK id){
		try {
			return baseDao.deleteById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int deleteList(final List<T> entites){
		try {
			return baseDao.delete(entites);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int deleteByColumns(Map<String, Object> map){
		try {
			DeleteBuilder<T, PK> builder = baseDao.deleteBuilder();
			Where<T, PK> where = builder.where();
			
			int i = 0;
			int size = map.size();
			for(Entry<String, Object> entry : map.entrySet()){
			    i++;
				if(size == i)
					where = where.eq(entry.getKey(), entry.getValue());
				else
					where = where.eq(entry.getKey(), entry.getValue()).and();
			}
			return builder.delete();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public T findById(PK id){
		try {
			return baseDao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<T> findAll(){
		try {
			return baseDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<T> findByColumns(Map<String, Object> map){
		try {
			return baseDao.queryForFieldValues(map);
			
			/*Where<T, PK> where = baseDao.queryBuilder().where();

			int i = 0;
			int size = map.size();
			for(Entry<String, Object> entry : map.entrySet()){    
			    i++;
				if(size == i)
					where = where.eq(entry.getKey(), entry.getValue());
				else
					where = where.eq(entry.getKey(), entry.getValue()).and();
			}  
			
			return where.query();		*/	
		} catch ( Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<T> findByColumn(String column, Object value){
		try {
			return baseDao.queryForEq(column, value);
					//baseDao.queryBuilder().where().eq(column, value).query();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<T> findByGtId(String columnId, PK id, long max){
		try {
			return baseDao.queryBuilder().limit(max).where().gt(columnId, id).query();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public T findByMaxId(String idName) throws SQLException {
		return baseDao.queryBuilder().limit((long)1).orderBy(idName, false).queryForFirst();
	}
	
    public List<T> findFromOffsetByLimit(long offset, long limit) {
        try {
        	return baseDao.queryBuilder().offset(offset).limit(limit).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<T> findFromOffsetByLimit(long offset, long limit, String columnName, boolean orderBy) {
        try {
        	return baseDao.queryBuilder().orderBy(columnName, orderBy).offset(offset).limit(limit).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<T> findFromOffsetWithLimitByColumns(Map<String, Object> map, long offset, long limit) {
        try {
        	Where<T, PK> where = baseDao.queryBuilder().offset(offset).limit(limit).where();

			int i = 0;
			int size = map.size();
			for(Entry<String, Object> entry : map.entrySet()){
			    i++;
				if(size == i)
					where = where.eq(entry.getKey(), entry.getValue());
				else
					where = where.eq(entry.getKey(), entry.getValue()).and();
			}  
			
			return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<T> findFromOffsetWithLimitByColumns(Map<String, Object> map, long offset, long limit, String columnName, boolean orderBy) {
        try {
        	Where<T, PK> where = baseDao.queryBuilder().orderBy(columnName, orderBy).offset(offset).limit(limit).where();

			int i = 0;
			int size = map.size();
			for(Entry<String, Object> entry : map.entrySet()){
			    i++;
				if(size == i)
					where = where.eq(entry.getKey(), entry.getValue());
				else
					where = where.eq(entry.getKey(), entry.getValue()).and();
			}  
			
			return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}