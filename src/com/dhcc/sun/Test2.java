package com.dhcc.sun;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import org.junit.Test;

// 练习 
public class Test2 {
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
              sql= "INSERT INTO person_info(name,age,birth)"+"VALUES('WYC','30','1986-11-28')";//插入
              sql="DELETE FROM person_info WHERE age=22";//删除
              sql= "INSERT INTO person_info(name,age,birth)"+"VALUES('TOM','25','1997-05-18')";
              sql="UPDATE person_info SET name='Wyc'" +"WHERE age=30";
              sql="UPDATE person_info SET name='Tom'"+ "WHERE id=2007";
               
            // 4.执行插入的语句
            // 4.1获取操作SQL的Statement对象：调用connection的createStatement()方法
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




/*public void test2(){
 *    1.获取数据库的连接
 * Connection con =null;
 * Statement statement=null;
 * 
      3.插入SQL语句
      String sql;
      try{
      con=getconnection();
      sql=null;
       sql="INSERT INTO table1(username,age,salary)"+"VALUES('Sala','28','3000')";// 插入
       sql="DELETE FROM table1 WHERE age=28";// 删除
       sql="INSERT INTO table1(username,age,salary)"+"VALUES('Sala','27','3500')";
     
     4.执行插入语句 
 *    4.1获取操作SQL的statemen对象，调用connection的createStatement()方法
 *    statement=con.createStatement();
 *    4.2 调用statement的对象的executeUpdate(sql) 执行sql语句进行插入
 * statement.executeUpdate(sql);
 * 
 *     }catch(exception e){
 *       e.printStackTrace();
 *     }finally{
 *     try{
 *     // 关闭statement对象
 *     if(statement!=null)
 *           statement.colse();
 *     }catch(exception e){
 *       e.printStackTrace();
 *     }finally{
 *     // 2关闭连接
 *     if(con !=null)
 *            con.colse();
 *      }
 *     }
 *   }
 * 
 * 通过JDBC向指定的数据表插入一条记录
 *   1.获取数据库的连接 
 *    Connection con=null;
 *     Statement statement=null;
 *     
 *
 * 3.插入SQL语句
 * String sql;
 * try{
 *  con=getConnection;
 *  sql=null;
 *  sql="insert  into table1(username,age,salary)"+"values("YLL","29","4000")";
 *     4.执行SQL语句
 *     4.1.获取sql的statement的对象，调用connection的createStatement();方法
 *     Statement=con.createStatement();
 *        4.2 调用statement对象的executeUpdate(sql)方法,执行sql语句
 *      statement.executeUpdate(sql);  
 *   
 *     }catch(Exception,e){
 *                 e.printStackTrace();
 *     }finally{
 *         try{
 *      //5 关闭Statement对象
 *      if(statement!=null)
 *                statement.close;
 *     }catch(Exception,e){
 *            e.printStackTrace();
 *      }fially{
 *       2.关闭连接
 *          if(con!=null)
 *                 con.close;
 *   }
 * }
 
 * 
 * 
 * 数据库：  1.准备连接数据库的四个字符串 
 *    1.1创建properties对象 1.2获取jdbc.properties对应的输入流 1.3 加载1.2对应的输入流 1.4具体决定user password四个字符串
 *                2.加载数据库驱动程序
 *                3.通过 DriverManager的getConnection()方法获取数据库连接
 *       
 *  //测试一下
 * public void Statement() throws Exception{
 * System.out.println(getConnection());
 *  }
 * public Connection getConnection() throws Exception{ 
 * 1.连接数据库资源的四个字符串
 *     1.1 创建properties对象
 *     Properties properties=new properties();
 *     1.2获取 jdbc.properties对应的输入流
 *   inputStream in=
 *            this.getClass.getClassLoader.getSystemResourceAsStream("jdbc.properties");
 *     1.3加载1.2对应的输入流
 *   properties.load(in);
 *     1.4具体决定user password等四个字符串
 *   String user=properties.getProperty("user");
 *   String password =properties.getProperty("password");
 *   String jdbcUrl=properties.getProperty("jdbcUrl");
 *   String driver =properties.getProperty(dirver);
 *   
 *  2.加载数据库驱动程序
 *  Class.ForName(driver);
 *  3.通过driverManager的getConnection()方法获取数据库连接
 *  return driverManager.getConnection(jdbcUrl,user,password);
 * 
 *  }
 * 
 */