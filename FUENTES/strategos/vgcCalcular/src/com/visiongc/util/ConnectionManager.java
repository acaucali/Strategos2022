/**
 * 
 */
package com.visiongc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;

/**
 * @author Kerwin
 *
 */
public class ConnectionManager 
{
	private static String uid;
	private static String pwd;
	private static String driver;
	private static String url;
	private static String formatoFecha;
	private static String debug;
	private static String rdbmsid;
	private static boolean bdObtenida;
	private static boolean usarPreparedStatement;
	private static boolean usarUCASE = true;
	private static boolean usarTimeStamp = false;

	public ConnectionManager(PropertyCalcularManager pm)
	{
		try
	    {
			pm.getProperty("dsn", "");
			uid = pm.getProperty("user", "");
			pwd = pm.getProperty("password", "");
			driver = pm.getProperty("driver", "");
			url = pm.getProperty("url", "");
			formatoFecha = pm.getProperty("formatoFecha", "");
			debug = pm.getProperty("debug", "");
	    }
	    catch (Exception e) 
	    {
	    	uid = "";
	    	pwd = "";
	    	driver = "";
	    	url = "";
	    	formatoFecha = "";
	    	debug = "";
	    }
	}
	
	public Connection getConnection() throws SQLException 
	{
		String ErrorNativo = "";
	    Connection cn;
	    
	    try 
	    {
	    	Class.forName(driver);
	    	cn = DriverManager.getConnection(url, uid, pwd);

	    	if (!bdObtenida) 
	    	{
	    		bdObtenida = true;
	            Statement stm = cn.createStatement();
	            ResultSet rs = stm.executeQuery("SELECT * FROM AFW_SISTEMA");

	            if (rs.next()) 
	            {
	            	rdbmsid = rs.getString("RDBMS_ID");
	            	if (rdbmsid.equalsIgnoreCase("INFORMIX")) 
	            	{
	            		usarPreparedStatement = true;
	            		usarUCASE = false;
	            		usarTimeStamp = true;
	            	}
	            }
	            
	            rs.close();
	            
	            try { rs.close(); rs = null;} catch (Exception localException3) { }
	            try { stm.close(); stm = null;} catch (Exception localException2) { }
	    	}
	        
	        return cn;
	    }
    	catch (Exception e2) 
    	{
    		ErrorNativo = ErrorNativo + "\n" + e2.toString();
    		throw new SQLException("Fuente de Datos JDBC no encontrada: \n" + ErrorNativo);
    	}
	}
	
	public static boolean usarPreparedStatement() 
	{
		return usarPreparedStatement;
	}

	public static boolean usarUCASE() 
	{
		return usarUCASE;
	}

	public static boolean usarTimeStamp() 
	{
		return usarTimeStamp;
	}
	
	public static String getFormatoFecha() 
	{
		return formatoFecha;
	}
	
	public static Boolean getDebug() 
	{
		return (debug.equals("1") ? true : false);
	}
	
	public String getRdbmsid()
	{
		return rdbmsid;
	}
}