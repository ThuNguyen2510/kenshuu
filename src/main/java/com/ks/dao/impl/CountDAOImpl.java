package com.ks.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ks.dao.CountDAO;
import com.ks.mapper.CountMapper;
import com.ks.mapper.UserMapper;
import com.ks.model.Count;
import com.ks.model.User;

public class CountDAOImpl extends BaseDAOImpl<Count> implements CountDAO {

	@Override
	public List<Count> getSummary() {

		Map<String, String> conditions = new HashMap<>();
		conditions.put(" maleCount", " gender_id = 1");
		conditions.put(" femaleCount", " gender_id = 2");
		conditions.put(" nullGenCount", " gender_id IS NULL");
		conditions.put(" under19Count", " age >0 AND age <=19");
		conditions.put(" over20Count", " age >= 20 ");
		conditions.put(" nullAgeCount", " age IS NULL");
		StringBuilder sql = new StringBuilder(
				" SELECT authority_name,maleCount,femaleCount,nullGenCount,under19Count,over20Count,nullAgeCount FROM public.mst_role AS r");
		Iterator it = conditions.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();//elementを取る
			String field = (String) pair.getKey();//　フィールド名を取る
			String condition = (String) pair.getValue();//要件分を取る
			String tbName = "mst_" + field.trim();//テーブル名を作成する
			sql.append(" LEFT JOIN ( SELECT authority_id , COUNT (*) AS " + field + " FROM public.mst_user WHERE "
					+ condition + " GROUP BY authority_id ) AS " + tbName);
			sql.append(" ON r.authority_id = " + tbName + ".authority_id");
		}
		List<Count> countList = query(sql.toString(), new CountMapper());//あった役職に対して取る
		countList.add(getNullAuthorityId());//未登録役職を追加する
		return countList;
	}

	private Count getNullAuthorityId() {
		StringBuilder sql = new StringBuilder(
				" SELECT * FROM public.mst_user WHERE authority_id IS NULL ");
		List<User> nullCountList = query(sql.toString(), new UserMapper());
		Count count = new Count();//未登録の役職
		count.setAuthorityName("未登者");
		for (int i = 0; i < nullCountList.size(); i++) {
			User element = nullCountList.get(i);
			int genId = element.getGenderId();
			int age = element.getAge();
			if (genId == 1) {
				count.setMale(count.getMale() + 1);//男性を数える
			} else if (genId == 2) {
				count.setFemale(count.getFemale() + 1);//女性を数える
			} else if (genId == 0) {
				count.setNullGender(count.getNullGender() + 1);//性未登録を数える
			}
			if (age > 0 && age <= 19) {
				count.setUnder19(count.getUnder19() + 1);//19歳以下を数える
			} else if (age >= 20) {
				count.setOver20(count.getOver20() + 1);//２０歳以上を数える
			} else if (age == 0) {
				count.setNullAge(count.getNullAge() + 1);//年齢未登録を数える
			}
		}
		return count;
	}

}
