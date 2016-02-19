package com.gp.Hibernate.update更新;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 高攀
 * @下午2:39:04
 */

public class UpdateHelper {

	/**
	 * 把需不为空的字段才设置到实体，才更新到数据库
	 * 
	 * @param objectOut
	 *            外面构造好的实体
	 * @param ObjectMysql
	 *            和数据库关联的实体
	 * @param primarykey
	 *            要忽略设置的主键
	 * @param ignores
	 *            需要忽略的字段，比如密码，固定字段之类的，将不会设置到数据库关联实体里面
	 */
	public static void Help(Object objectOut, Object ObjectMysql,
			String primarykey, String... ignores) throws Exception {
		try {
			if (primarykey == null) {
				throw new Exception("需要设置主键");
			}
			List<String> ignoresList = new ArrayList<String>();
			for (int i = 0; i < ignores.length; i++) {
				String s = ignores[i];
				ignoresList.add(s);
			}
			Class clazzOut = objectOut.getClass();
			Class clazzMysql = ObjectMysql.getClass();
			Field[] filedOut = clazzOut.getDeclaredFields();
			Field[] filedMysql = clazzMysql.getDeclaredFields();
			for (Field fieldout : filedOut) {
				fieldout.setAccessible(true);
				String nameOut = fieldout.getName();
				if (nameOut.equals(primarykey) || ignoresList.contains(nameOut)) {
					continue;
				}
				if (fieldout.get(objectOut) != null) {
					String typeOut = fieldout.getType().getSimpleName();
					for (Field fieldmysql : filedMysql) {
						fieldmysql.setAccessible(true);
						String nameMysql = fieldmysql.getName();
						String typeMysql = fieldmysql.getType().getSimpleName();
						if (nameOut.equals(nameMysql)
								&& typeOut.equals(typeMysql)) {
							fieldmysql.set(ObjectMysql,
									fieldmysql.get(objectOut));
						}
					}
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	// 例子：
	// 更新商家用户
	/*public void updateMer(User user) throws Exception{
		Session session = hib.getSession();
		Transaction ts = null;
		try{
			ts = session.beginTransaction();
			if(!isUserExist(user.getUsername(), session)){
				throw new Exception("用户不存在！");
			}
			User usermysql = (User) session.get(User.class, user.getUserid());
			// 只把需要的更新进去，主键密码忽略
			UserDaoImpl.UpdateHelper(user, usermysql,"userid","password","addtime","username");
			session.update(usermysql);
			ts.commit();
		} catch(Exception e){
			ts.rollback();
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			session.close();
		}
	}*/
}
