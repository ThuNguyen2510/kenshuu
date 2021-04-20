package com.ks.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ks.dao.CountDAO;
import com.ks.mapper.CountMapper;
import com.ks.model.Count;

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
		StringBuilder sql2 = new StringBuilder("SELECT");
		Iterator it = conditions.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();//elementを取る
			String field = (String) pair.getKey();//　フィールド名を取る
			String condition = (String) pair.getValue();//要件分を取る
			String tbName = "mst_" + field.trim();//テーブル名を作成する
			sql.append(" LEFT JOIN ( SELECT authority_id , COUNT (*) AS " + field + " FROM public.mst_user WHERE "
					+ condition + " GROUP BY authority_id ) AS " + tbName);
			sql.append(" ON r.authority_id = " + tbName + ".authority_id");
			sql2.append(" COUNT(*) FILTER (WHERE "+condition+" ) AS "+field +",");
		}
		sql2.append("'未登録' AS authority_name FROM (SELECT * FROM public.mst_user WHERE authority_id IS NULL) AS u ");
		List<Count> countList = query(sql.toString(), new CountMapper());//あった役職に対して取る
		countList.add((Count) query(sql2.toString(),new CountMapper()).get(0));//未登録の役職を追加する
		return countList;
	}


}
