/**
 * 
 */
package com.hspdu.mhl.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import com.hspdu.mhl.utils.JDBCUtilsByDruid;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *
 */
public class Test {
	public static void main(String[] args) {
		 Connection connection = null;
	        try {
	            connection = JDBCUtilsByDruid.getConnection();
	        } catch (SQLException e) {
	           throw  new RuntimeException(e); //将编译异常->运行异常 ,抛出
	        } finally {
	            JDBCUtilsByDruid.close(null, null, connection);
	        }
	        System.out.println(connection);
	  }
}
