package com.gp.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

//import org.hibernate.Query;
//import org.hibernate.Session;
//
//import com.keyhua.bp.util.BeanUtil;
//import com.keyhua.outdoor.entity.User;
//import com.keyhua.protocol.json.JSONArray;
//import com.keyhua.protocol.json.JSONObject;

/**
 * @author 高攀
 * @上午11:15:25
 * 通用的like查询
 * 如 搜索用户（收索的条件只要是User实体有的字段，都可以模糊出来（int类型不能模糊，只能等于），条件放在param里面就可以了）
 */
public class CommonLikeQuery {

	/*public JSONObject queryMerListByCondition(int pagenum, int pagesize,Map<String,Object> param) {
		JSONObject res = new JSONObject();
		Session session = hib.getSession();
		List<String> fields = GetEntityFields(User.class);
		JSONArray ja = new JSONArray();
		StringBuffer sqlBuffer = new StringBuffer("from User where 1=1 and "); 
		String hql = "from User where 1=1 and ";
		int count = 0;
		int pagecount = 0;
		Set<Entry<String, Object>> set = param.entrySet();
		for (Entry<String, Object> entry : set) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if(!fields.contains(key)){ // 只能给予实体有的字段作为查询
				continue;
			}
			if(value instanceof String){
				sqlBuffer.append(" "+key+" like :"+key+" and ");
			}
			if(value instanceof Integer){
				sqlBuffer.append(" "+key+" = :"+key+" and ");
			}
		}
		String hqlFinal = sqlBuffer.toString().substring(0,sqlBuffer.toString().lastIndexOf("and"));
		Query query = session.createQuery(hqlFinal);
		for (Entry<String, Object> entry : set) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if(!fields.contains(key)){ // 只能给予实体有的字段作为查询
				continue;
			}
			if(value instanceof String){
				query.setString(key, "%"+value+"%");
			}
			if(value instanceof Integer){
				query.setInteger(key, Integer.parseInt(value+""));
			}
		}
		count = query.list().size(); // 没有加分页时的总数量
		List<User> list = new ArrayList<User>();
		try {
			list = query.setFirstResult((pagenum - 1) * pagesize).setMaxResults(pagesize).list();
			for (int i = 0; i < list.size(); i++) {
				User mu = list.get(i);
				Map<String, Object> map = BeanUtil.convertBean(mu);
				ja.put(map);
			}
			
			res.put("userlist", ja);
			res.put("count", count);
			if(count/pagesize<=0 || count%pagesize!=0){
				pagecount = count/pagesize + 1;
			}else{
				pagecount = count/pagesize;
			}
			res.put("pagecount", pagecount);
			res.put("pagenum", pagenum);
			res.put("pagesize", pagesize);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return res;
	}*/
}
