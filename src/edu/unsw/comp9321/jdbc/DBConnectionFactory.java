package edu.unsw.comp9321.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.unsw.comp9321.exception.ServiceLocatorException;

public class DBConnectionFactory {
	
	static Logger logger = Logger.getLogger(DBConnectionFactory.class.getName());
	private static DBConnectionFactory factory = null;
	private DataSource ds = null;
	private InitialContext ctx;
	private Context subctx;
	
	public DBConnectionFactory() throws ServiceLocatorException {
		try{
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/cs9321");
			logger.info("Database found:"+ds.toString());
		}catch(NamingException e){
			logger.severe("Cannot find context, throwing exception"+e.getMessage());
			e.printStackTrace();
			throw new ServiceLocatorException("");
		}
	}
	
	public DataSource getDataSource(){
		return ds;
	}
	
	public static Connection getConnection() throws ServiceLocatorException, SQLException{
		
		if(factory==null)
			factory = new DBConnectionFactory();
		Connection conn = factory.getDataSource().getConnection();
		
		return conn;
	}
	
	public Connection createConnection() throws ServiceLocatorException {
		try {
			//return getDataSource().getConnection("SA", "d41d8cd98f00b204e9800998ecf8427e");
			return getDataSource().getConnection();
		} catch (SQLException e) {
			throw new ServiceLocatorException("Unable to create connection: " + e.getMessage(), e);
		}
	}
}
