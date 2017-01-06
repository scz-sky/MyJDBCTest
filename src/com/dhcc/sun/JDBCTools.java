package com.dhcc.sun;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCTools {
    
    // 处理数据库事务的
 // 提交事务
    public static void commit(Connection connection){
        if(connection !=null){
            try{
                connection.commit();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    // 回滚事务
    public static void rollback(Connection connection){
        if(connection!=null){
            try {      
                connection.rollback();
            } catch (Exception e) {
              e.printStackTrace();
            }
        }
    }
    // 开始事务
    public  static void beginTx(Connection connection){
        if(connection!=null){
            try{
                connection.setAutoCommit(false);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    /*
     * 执行sql语句 ，使用preparedStatement
     * @param sql
     * @param args:填写sql 占位符的可变参数
     */
    public static void update(String sql,Object...args){
        Connection connection =null;
        PreparedStatement preparedStatement=null;
        try {
            connection=JDBCTools.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i] );//不知道传进去的类型是什么就调用setObject
            }
            preparedStatement.executeUpdate();// 执行sql语句
          } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCTools.release(null, preparedStatement, connection);
        }
    }
      
/*
 * 执行SQL的方法
 *   包含 insert delete update而不包括：select  
 */
    public static void update(String sql){
        Connection connection=null;
       Statement statement=null;
         try {
             // 1,获取数据库连接
             connection =getConnection();
            //2 获取操作SQL的Statement对象：调用connection的createStatement()方法
             statement=connection.createStatement();
            // 4.发送SQL语句：调用Statement 对象的executeUpdate(sql)方法
             statement.executeUpdate(sql);
         }catch(Exception e){
             e.printStackTrace();
             }finally{
        //5.关闭数据库资源：由里向外关闭 
               release(null,statement,connection);
         }
    }
/*
 * 操作JDBC的工具类，其中封装了一些工具方法
 *   verson 1
 *   工具类：获取连接，关闭资源！
 */

   // 关闭Statement和Connection    释放数据库资源
   // 静态方法
    public static void release( ResultSet rs,Statement statement,Connection con){
        if(rs !=null){
             try {
                rs.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
        if(statement !=null){
            try{
                statement.close();
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
        if(con!=null){
            try{
                //数据库连接池的Connection对象进行close时，并不是真正的进行关闭，而是把该数据库连接归还给连接池
                con.close();
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
    }

    
  
//      1.获取连接的方法
//    通过读取配置文件从数据库服务器获取一个连接
//     
    // 2.获取数据库资源的方法
    public void Statement1() throws Exception{   
        System.out.println(getConnection());
     }
    
    
    
    
 // 连接池
    private static DataSource datasource =null;
    static{
        //只需要一次实例化
        datasource=new ComboPooledDataSource("helloc3p0");
    }
    //获取数据库连接的方法
    // 通过读取配置文件从数据库获取一个连接
    public static Connection getConnection() throws Exception{
        return datasource.getConnection();
        
     }
  }
/*
    public static Connection getConnection() throws Exception{
        // 1.准备连接数据库的四个字符串       
      // 1).创建properties 对象
        Properties properties=new Properties();
      // 2).获取 jdbc.properties 对应的输入流
       InputStream in=
               JDBCTools.class.getClassLoader().getSystemResourceAsStream("jdbc.properties");
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

*/
