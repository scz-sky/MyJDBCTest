package com.dhcc.test;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.junit.Test;

import com.dhcc.sun.JDBCTools;

// 获取插入记录的主键值
public class TestKey {
    /*
     * 读取Blob数据
     * 1.使用getBlob方法获取到Blob对象
     * 2.调用Blob的getBinaryStream()方法得到输入流，在使用IO 操作即可。
     */
 @Test 
 public void readBlob(){
     Connection connection =null;
     ResultSet resultSet =null;
     PreparedStatement preparedStatement =null;
     try {
         connection=JDBCTools.getConnection();
         String sql="SELECT id,username,age,picture"+" FROM table3 where id=13";
         //System.out.println(sql);
         preparedStatement =connection.prepareStatement(sql);
         resultSet =preparedStatement.executeQuery();
       
         if(resultSet.next()){
             int id=resultSet.getInt(1);
             String username=resultSet.getString(2);
             int age=resultSet.getInt(3);
             
             System.out.println(id+","+username+","+age);
             Blob picture =resultSet.getBlob(4);
             
             //获取输入流
             InputStream in=picture.getBinaryStream();
             System.out.println(in.available());
             // 输出
             OutputStream out=new FileOutputStream("100.jpg");
             
             byte[] buffer=new byte[1024];
             int len=0;
             while((len=in.read(buffer)) !=-1){
                 out.write(buffer, 0, len);
                 out.close();
                 in.close();
             }
         }
     } catch (Exception e) {
         e.printStackTrace();
          }finally{
           JDBCTools.release(resultSet, preparedStatement, connection);
          }
      }

   /*                              插入Blob
    * 插入Blob类型的数据必须使用PerparedStatement ：
    *     因为BLOB类型的数据无法使用字符串拼写的
    *     
    * 调用：setBlob(int index,InputStream inputStream)
    */
@Test
public void testInterBlob(){
    Connection connection=null;
    PreparedStatement preparedStatement =null;
    try {
        connection=JDBCTools.getConnection();
        String sql="INSERT INTO table3(id,username,age,picture )"+"VALUES(?,?,?,?)";
        preparedStatement=connection.prepareStatement(sql);
         
       //填充占位符
       preparedStatement.setInt(1, 13);
       preparedStatement.setString(2, "ChenHuanghuang");
       preparedStatement.setInt(3, 27);
       //插入图片
       InputStream inputStream =new FileInputStream("111.jpg");
       preparedStatement.setBlob(4, inputStream);
       
       preparedStatement.executeUpdate();
       // 通过getGeneratedKeys() 获取包含了新生成的主键的ResultSet对象
       ResultSet rs=preparedStatement.getGeneratedKeys();

   } catch (Exception e) {
   e.printStackTrace();
   }finally{
       JDBCTools.release(null, preparedStatement, connection);
   }
}

    // 取得数据库自动生成的主键
    @Test
 public void testGetKeyValue(){
     Connection connection=null;
     PreparedStatement preparedStatement =null;
     try {
         connection=JDBCTools.getConnection();
         String sql="INSERT INTO table3(id,username,age )"+"VALUES(?,?,?)";
         System.out.println(sql);
        //preparedStatement=connection.prepareStatement(sql);
        //使用重载的preparedStatement（sql,flag）来生成PreparedStatement 对象
        preparedStatement=connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        //填充占位符
        preparedStatement.setInt(1, 11);
        preparedStatement.setString(2, "LiHuag");
        preparedStatement.setInt(3, 35);
        
        preparedStatement.executeUpdate();
        // 通过getGeneratedKeys() 获取包含了新生成的主键的ResultSet对象
        ResultSet rs=preparedStatement.getGeneratedKeys();
        if(rs.next()){
            System.out.println(rs.getObject(1));
        }
        ResultSetMetaData rsmd=rs.getMetaData();
        for(int i=0;i<rsmd.getColumnCount();i++){
            System.out.println(rsmd.getCatalogName(i+1));
        }
    } catch (Exception e) {
    e.printStackTrace();
    }finally{
        JDBCTools.release(null, preparedStatement, connection);
    }
 }
}
