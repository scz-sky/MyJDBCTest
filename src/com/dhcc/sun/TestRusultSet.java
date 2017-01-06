package com.dhcc.sun;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import org.junit.Test;
/*
 *  RusultSet:结果集  封装了使用JDBC进行查询的结果
 *      1.调用Statement对象的executeQuery(sql）可以得到的结果集
 *      2.ResultSet返回的实际上就是一张数据表。有一个指针指向数据表的第一样的前面。
 *      可以调用next()方法检测下一行是否有效，若有效该方法返回true，且指针下移，
 *       Iterator对象的hasNext() 和next()方法的结合体
 *      3.当指针定位到一行时，可以通过调用getXxx(index) 或getXxx(columnName)获取每一列的值
 *       例如：getInt(1),getString("name")
 *      4.ResultSet 当然也需要进行关闭
 */

public class TestRusultSet {
    @Test
    public void testRusultSet(){
   //获取id=2004 的person_info数据表的的记录，并打印
      Connection con=null;
      Statement statement=null;
      ResultSet rs=null;
      
      try{         
   // 1.获取Connection
    con=JDBCTools.getConnection();
  // 2.获取Statement
    statement=con.createStatement();
    
  // 3.准备SQL
  //String sql="SELECT id,name,age,birth "+" FROM person_info WHERE id=2004";// 查询一条记录时
    String sql="SELECT id,name,age,birth "+" FROM person_info ";// 查询多条记录时
  // 4.执行查询，得到 RusultSet
    rs=statement.executeQuery(sql);
  //5 处理RusultSet
    // 查询一条数据的时候用if sql语句加where ,查询多条的时候用while 不加where
    while(rs.next()){
        int id=rs.getInt(1);
        String name=rs.getString("name");
     // String name=rs.getString("name");
        int age=rs.getInt(3);
        Date birth=rs.getDate(4);
            
         System.out.println(id);
         System.out.println(name);
         System.out.println(age);
         System.out.println(birth);
        
    }
  // 6.关闭数据库连接  
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          JDBCTools.release(rs, statement, con);
      }
 
    }
}
   