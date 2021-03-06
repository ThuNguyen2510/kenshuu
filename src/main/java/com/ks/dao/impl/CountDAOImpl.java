package com.ks.dao.impl;

import java.util.List;

import com.ks.dao.CountDAO;
import com.ks.mapper.CountMapper;
import com.ks.model.Count;

public class CountDAOImpl extends BaseDAOImpl<Count> implements CountDAO {

	@Override
	public List<Count> getSummary() {
		StringBuilder sql = new StringBuilder(
				" SELECT authority_name,maleCount,femaleCount,nullGenCount,under19Count,over20Count,nullAgeCount FROM public.mst_role AS r");
		sql.append(
				" LEFT JOIN ( SELECT authority_id , COUNT (*) AS maleCount FROM public.mst_user WHERE gender_id = 1 GROUP BY authority_id ) AS mst_maleCount ON r.authority_id = mst_maleCount.authority_id ");
		sql.append(
				" LEFT JOIN ( SELECT authority_id , COUNT (*) AS femaleCount FROM public.mst_user WHERE gender_id = 2 GROUP BY authority_id ) AS mst_femaleCount ON r.authority_id = mst_femaleCount.authority_id ");
		sql.append(
				" LEFT JOIN ( SELECT authority_id , COUNT (*) AS nullGenCount FROM public.mst_user WHERE gender_id IS NULL GROUP BY authority_id ) AS mst_nullGenCount ON r.authority_id = mst_nullGenCount.authority_id ");
		sql.append(
				" LEFT JOIN ( SELECT authority_id , COUNT (*) AS under19Count FROM public.mst_user WHERE age >0  AND age<=19 GROUP BY authority_id ) AS mst_under19Count ON r.authority_id = mst_under19Count.authority_id ");
		sql.append(
				" LEFT JOIN ( SELECT authority_id , COUNT (*) AS over20Count FROM public.mst_user WHERE age>=20 GROUP BY authority_id ) AS mst_over20Count ON r.authority_id = mst_over20Count.authority_id ");
		sql.append(
				" LEFT JOIN ( SELECT authority_id , COUNT (*) AS nullAgeCount FROM public.mst_user WHERE age IS NULL GROUP BY authority_id ) AS mst_nullAgeCount ON r.authority_id = mst_nullAgeCount.authority_id ");

		StringBuilder sql2 = new StringBuilder("SELECT");
		sql2.append(" COUNT(*) FILTER (WHERE gender_id =1 ) AS maleCount, ");
		sql2.append(" COUNT(*) FILTER (WHERE gender_id =2 ) AS femaleCount, ");
		sql2.append(" COUNT(*) FILTER (WHERE gender_id IS NULL ) AS nullGenCount, ");
		sql2.append(" COUNT(*) FILTER (WHERE age>0 AND age <=19 ) AS under19Count, ");
		sql2.append(" COUNT(*) FILTER (WHERE age>=20 ) AS over20Count, ");
		sql2.append(" COUNT(*) FILTER (WHERE age IS NULL ) AS nullAgeCount, ");
		sql2.append("'?????????' AS authority_name FROM (SELECT * FROM public.mst_user WHERE authority_id IS NULL) AS u ");

		List<Count> countList = query(sql.toString(), new CountMapper());//?????????????????????????????????
		countList.add((Count) query(sql2.toString(), new CountMapper()).get(0));//?????????????????????????????????
		return countList;
	}

}
