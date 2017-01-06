package com.dhcc2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

import com.dhcc.sun.JDBCTest;


// 连接池
public class ConnectionPool {
    
    /*
     * 1.加载dbcp的properties配置文件：配置文件中的键需要来自BasicDataSource的属性
     * 2.调用BasicDataSourceFactory的createDataSource方法，创建DataSource实例
     * 3.从DataSource 实例中获取数据库连接
     */
    @Test
    public void testDBCPwithDataSourceFactory() throws Exception{
        Properties properties=new Properties();
        InputStream inStream=
                JDBCTest.class.getClassLoader().getResourceAsStream("dbcp.properties");
        properties.load(inStream);
    
        DataSource dataSource=
                BasicDataSourceFactory.createDataSource(properties);
        
        System.out.println(dataSource.getConnection());
        BasicDataSource basicDataSource=
                (BasicDataSource)dataSource;
        
        System.out.println(basicDataSource.getMaxWaitMillis());
    }
/*
 * 使用DBCP数据库连接池
 * 1.加入jar包（2个jar 包）。  依赖于 commons pool这个包
 * 2.创建数据库连接池
 * 3.为数据库实例指定必须的属性
 * 4.从数据库获取数据库连接
 *
 */
    @Test
    public void testDBCP() throws SQLException{
        
        BasicDataSource dataSource=null;//DataSource 数据库连接池
        
        // 1.创建DBCP数据源实例
        dataSource=new BasicDataSource();
        // 2.为数据源实例指定必须的属性
       dataSource.setUsername("root");
       dataSource.setPassword("123456");
       dataSource.setUrl("jdbc:mysql://localhost:3306/test");
       dataSource.setDriverClassName("com.mysql.jdbc.Driver");
       
       // 3.指定数据源的一些可选的属性
        // 1).指定数据库连接池中初始化连接数的个数
       dataSource.setInitialSize(5);
       
       // 2)指定最大的连接数：同一时刻可以同时向数据库申请的连接数
        dataSource.setMaxConnLifetimeMillis(5);
        // 3).指定最小的连接数：数据库连接池中保存的最少的空闲连接的数量
        dataSource.setMinIdle(2);
       // 4).等待数据库连接池分配连接的最长时间，单位为毫秒。超出该时间则会抛出异常
        dataSource.setMaxWaitMillis(1000*5);
        
       // 4.从数据源中获取数据库连接
       Connection connection=dataSource.getConnection();
       System.out.println(connection);
       
       connection=dataSource.getConnection();
       System.out.println(connection);
       
       connection=dataSource.getConnection();
       System.out.println(connection);
       
       connection=dataSource.getConnection();
       System.out.println(connection);
       
       connection=dataSource.getConnection();
       System.out.println(connection);
       
       // 第六个在没有连接的话会抛异常
      Connection connection2=dataSource.getConnection();
       System.out.println(connection);
       
       new Thread(){
           public void run(){
               Connection conn;
               try {
//               conn =dataSource.getConnection();
//               System.out.println(conn.getClass());
            } catch (Exception e) {
               e.printStackTrace();
            }
           };
           
       }.start();// 一个线程获取连接 一个线程关闭连接
       
     try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
     connection2.close();
   }
}
    
    
//687241927, URL=jdbc:mysql:localhost:3306/test, UserName=root@localhost, MySQL-AB JDBC Driver

// 查看一下这个类 .getClass()
//  System.out.println(connection.getClass());
//class org.apache.commons.dbcp2.PoolingDataSource$PoolGuardConnectionWrapper
