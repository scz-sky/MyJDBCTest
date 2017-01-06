package com.dhcc.sun;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import org.junit.Test;
import java.sql.Statement;
public class JDBCStatement {
    /*
     * 通过JDBC向指定的数据表插入一条记录
     * 1.Statement：用于执行SQL 语句的对象 
     * 1).通过Connection的createStatement()方法来获取
     * 2).通过executeUpdate(sql)可以执行SQL语句
     * 3).传入的SQL 可以是INSRET(插入)  UPDATE(修改)  DELETE(删除)，但不能是SELECT
 
     * 2.Connection，Statement都是应用程序和数据库服务器的连接资源，使用后一定要关闭
     * 需要在finally中关闭.Connection和Statement对象
     * 
     * 3.关闭的顺序是：先关闭，后获取的。即先关闭Statement 后关闭Connection 
     * 异常可以不处理，连接一定要关闭
     */

    /*
     * 通用的更新的方法 包括INSERT  UPDATE DELETE
     * 版本1.
     */
    public void update(String sql){
       Connection con=null;
      Statement statement=null;
        try {
            //con=getConnection();
            // 有了工具类之后 直接这样操作    con=JDBCTools.getConnection();
            con=JDBCTools.getConnection();
            statement=con.createStatement();
            statement.execute(sql);
        } catch (Exception e) {
           e.printStackTrace();
        }finally{
            // 有了工具类之后  直接这样关闭
            JDBCTools.release(null, statement, con);
            }
    }           
//            if(statement !=null){
//                try{
//                    statement.close();
//                }catch(Exception e2){
//                    e2.printStackTrace();
//                }
//            }
//            if(con!=null){
//                try{
//                    con.close();
//                }catch(Exception e2){
//                    e2.printStackTrace();
//                }
//            }
//        }
 //}

    @Test
    public void testStatement2() throws Exception{
        //1.获取数据库的连接
        Connection con=null;
        Statement statement=null;
        //3.插入SQL语句
        String sql;
        try {
            con= getConnection();
            sql =null;
            //sql= "INSERT INTO person_info(name,age,birth)"+"VALUES('SLL','25','1997-05-18')";
           // sql="DELETE FROM person_info WHERE id=1996 ";
           // sql="UPDATE person_info SET name='TOM' "+"WHERE id=1995";
           //   sql= "INSERT INTO person_info(name,age,birth)"+"VALUES('Tina','26','1999-04-15')";
            //  sql="DELETE FROM person_info WHERE id=2003 ";
            sql= "INSERT INTO person_info(name,age,birth)"+"VALUES('Join','29','1988-05-28')";
      
               
            // 4.执行插入的语句
            // 4.1获取操作SQL的Statement对象：调用connection的createStatement()方法   create:产生创造
      statement=con.createStatement();
            //4.2调用Statement对象的executeUpdate(sql) 执行SQL语句进行插入
       statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
           try {
                //5 .关闭Statement对象
               if(statement!=null)
                       statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
            // 2.关闭连接
                if(con!=null)
                        con.close();
        }
        }
    }
   
public void Statement1() throws Exception{   
        System.out.println(getConnection());
     }
    public Connection getConnection() throws Exception{
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
}

