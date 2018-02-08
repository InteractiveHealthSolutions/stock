package com.ihs.stock.api.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class DAOCustomQuery {

	private Session s;

	public DAOCustomQuery(Session session)
	{
		this.s = session;
	}
	
	public List getDataBySQL(String sql) {
		return s.createSQLQuery(sql).list();
	}
	public List getDataBySQLMapResult(String sql) {
		return s.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}
