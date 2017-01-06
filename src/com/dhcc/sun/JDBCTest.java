package com.dhcc.sun;
//Drivar接口获取数据库连接
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.driver.OracleDriver;

import org.junit.Test;

import com.mysql.jdbc.Driver;
/*
 * Drivar 是一个接口：数据厂商必须提供实现的接口，能从其中的数据库连接
 *    1、加入mysql驱动
 *      ①解压mysql-connector-java-5.1.7.zip
 *      ②在当前项目下新建lib目录
 *      ③把mysql-connector-java-5.1.7-bin.jar复制到lib目录下
 *      ④右键build-path，add to buildpath 加入到类路径下
 *    
 */
public class JDBCTest {
    /*
     * 通过JDBC向指定的数据表插入一条记录
     * 1.Statement:用于执行SQL 语句的对象 
     * 1).通过Connection的cresateStatement()方法来获取 
     * 2).通过executeUpdate(sql)可以执行SQL语句
     * 3).传入的SQL 可以是INSRET(插入)  UPDATE(修改)  DELETE(删除)，但不能是SELECT
     * 
     * 2.Connection，Statement都是应用程序和数据库服务器的连接资源，使用后一定要关闭
     */
    @Test
    public void testStatemant() throws Exception{
        // 1,获取数据库连接
        Connection conn=getConnection2();
       // 3.准备插入的SQL语句
        String sql="INSERT INTO person_info(name,age,birth)"+"VALUES('SCZ2','22','1995-02-20')";
       // 4.执行插入   
       // 1)获取操作SQL语句的Statement对象：
        //调用Connection的createStatement()方法来获取
        Statement statement=conn.createStatement();
       //2) 调用Statement对象的executeUpdate(sql) 执行SQL语句进行插入
        statement.executeUpdate(sql);
       // 5.关闭Statement对象
        statement.close();
       //2.关闭连接
       conn.close();
    }

    // 测试一下如下的

    public void testGetConnection2() throws Exception{
       System.out.println(getConnection2());// user:scz_test   password:mytest  
                                                         //oracle.jdbc.driver.T4CConnection@65e579dc   
    }
   public Connection getConnection2() throws Exception{
       // 1.准备连接数据库的四个字符串       
     // 1).创建properties 对象
       Properties properties=new Properties();
     // 2).获取 jdbc.properties 对应的输入流
      InputStream in=
                this.getClass().getClassLoader().getSystemResourceAsStream("jdbc.properties");
       // 3). 加载 2) 对应的输入流
        properties.load(in);
        //4).具体决定user，password等4个字符串
        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String jdbcUrl=properties.getProperty("jdbcUrl");
        String driver=properties.getProperty("driver");
      
       // 2.加载数据库驱动程序（对应的Driver 实现类中有注册驱动的静态代码块)
        Class.forName(driver);
       // 3.通过DriverManager的getconnection()方法获取数据库连接
      return DriverManager.getConnection(jdbcUrl, user, password);
    }
// 
//    
    /*
     * DriverManager是驱动的管理类
     * 1）可以通过重载的getConnection() 方法获取数据库的连接 较为方便
     * 2）可以同时管理多个驱动程序：若注册了多个数据库连接，则调用getConnection()
     *     方法时传入的参数不同，即返回不同的数据库连接
     */

    // 一。准备连接数据库的四个字符串
   public void testDriverManager() throws Exception{
        //driverClass：驱动的全类名
        String driverClass=null;
        // JDBC URL
        String jdbcUrl=null;
        //user
        String user=null;
        // password
        String password=null;
        // 读取类路径下的文件 jdbc.properties 文件
        InputStream in=getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties =new Properties();
        properties.load(in);//这个方法就是一个输入流或字节流来读取 jdbc.properties 文件
        driverClass=properties.getProperty("driver");       
        jdbcUrl = properties.getProperty("jdbcUrl");     
        user=properties.getProperty("user");
        password=properties.getProperty("password");
        // 二 加载数据库驱动程序（对应的Driver 实现类中有注册驱动的静态代码块）
        Class.forName(driverClass);//为什么要执行Class.forName('驱动类名')方法：将驱动类的class文件装载到内存中，
        //并且形成一个描述此驱动类结构的Class类实例，并且初始化此驱动类，这样jvm就可以使用它了，
        //这就是Class.forName()方法的含义。
        
        //  三通过DriverManager的getConnection() 方法获取数据库连接
        Connection connection=DriverManager.getConnection(jdbcUrl, user, password);
        System.out.println(connection);
    }
    
    
   public void testDriver() throws Exception{
       // 1.创建一个Driver实现类的对象
    Driver driver=new com.mysql.jdbc.Driver(); //localhost -- 127.0.0.1
    
    // 2.准备连接数据库的基本信息：url,user,password
    String url1="jdbc:mysql://127.0.0.1:3306/test";
   Properties info=new Properties();
   info.put("user", "root");
   info.put("password", "123456");
   
   // 3.调用Driver接口的 connect(url, info)获取数据库哦连接
   Connection connection =driver.connect(url1, info);
   System.out.println(connection);//com.mysql.jdbc.JDBC4Connection@1175e2db

   
  
  }
   /*
    * 编写一个通用的方法，在不修改源程序的情况下，可以获取任何数据库的连接
    * 解决方案：
    * 把数据库驱动Driver  实现类的全类名、url  user password 放入一个配置文件中，通过修改配置文件的方式
    * 实现和具体的数据库解耦。
      */
   
   public Connection getConnection() throws Exception{
       String driverClass=null;
       String jdbcUrl=null;
       String user=null;
       String password=null;
       // 读取类路径下的文件 jdbc.properties 文件
       InputStream in=getClass().getClassLoader().getResourceAsStream("jdbc.properties");
       Properties properties =new Properties();
       properties.load(in);
       driverClass=properties.getProperty("driver");    
       jdbcUrl = properties.getProperty("jdbcUrl");  
       user=properties.getProperty("user");   
       password=properties.getProperty("password");
       
       
       //通过反射创建 Driver对象
       //Driver mysqlDriver=(Driver)Class.forName(driverClass).newInstance();
       OracleDriver oracleDriver = (oracle.jdbc.driver.OracleDriver)Class.forName(driverClass).newInstance();
       Properties info=new Properties();
       info.put("user", user);
       info.put("password", password);
      // 通过Driver的connect方法获取获取数据库连接
       //Connection mysqlConnection=mysqlDriver.connect(jdbcUrl, info);  
        Connection oracleConnection=oracleDriver.connect(jdbcUrl, info);  
       
       //return mysqlConnection;
       return oracleConnection;
   }
   
   public void testgetConnection() throws Exception{
       System.out.println(getConnection());
                                                         // com.mysql.jdbc.JDBC4Connection@2401f4c3
                                                         // oracle.jdbc.driver.T4CConnection@424c0bc4
   } 

}






