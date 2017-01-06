package com.dhcc4Practice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import com.dhcc.sun.JDBCTools;

// 练习
public class JDBCPractice {
    @Test
    // 利用PreparedStatement向数据库插入一条记录
public void test1(){
    Connection connection=null;
    PreparedStatement preparedStatement =null;
    ResultSet resultSet=null;
    try {
        connection=JDBCTools.getConnection();
        // 可以是 INSERT DELETE UPDATE 但是不能是SELECT
       String sql= "INSERT INTO person_info(name,age,birth)"+"VALUES('WangXian','37','1978-05-28')";
       sql="DELETE FROM person_info WHERE id=2010";// 删除
       sql="UPDATE person_info SET name='Tom' "+"WHERE id=2007";
      
        System.out.println(sql);
        
        preparedStatement=connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
     
        
    } catch (Exception e) {
       e.printStackTrace();
    }finally{
        JDBCTools.release(null, preparedStatement, connection);
    }
  }
}
