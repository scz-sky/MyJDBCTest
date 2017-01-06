package com.dhcc.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import oracle.sql.DATE;

import org.junit.Test;

import com.dhcc.sun.JDBCTools;

public class DatebaseMateDate {
    /*
     * ResultSetMateDate:描述结果集的元数据
     * 可以得到结果集的基本信息：结果集中有那些列，列名，列的别名等
     */
   
    @Test
    public void testResultSetMateDate(){
        Connection connection =null;
        ResultSet resultSet =null;
        PreparedStatement preparedStatement =null;
        try {
            connection=JDBCTools.getConnection();
            String sql="SELECT username user_name ,age,email"+" FROM table1";
            //System.out.println(sql);
            preparedStatement =connection.prepareStatement(sql);
            resultSet =preparedStatement.executeQuery();
            // 1.得到ResultSetMateDate对象
            ResultSetMetaData rsmd=resultSet.getMetaData();
            //  2.得到列的个数
            int columnCount =rsmd.getColumnCount();
            System.out.println(columnCount);
            
            for(int  i=0;i<columnCount;i++){
                // 3.得到列名
                String columnName=rsmd.getColumnName(i+1);
                // 4.得到列的别名
                String columnLabel=rsmd.getColumnLabel(i+1);
                System.out.println(columnName+","+columnLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
             }finally{
              
             }
         }
    
    /*
     * DatebaseMateDateL是描述数据库的元数据对象
     * 可以由Connection得到
     */
    @Test
    public void testDatebaseMateDate(){
         Connection connection =null;
         ResultSet resultSet =null;
          try {
            connection=JDBCTools.getConnection();
            DatabaseMetaData date=connection.getMetaData();
            
            // 可以得到数据资源的一些信息
            // 得到数据库的版本号
            int version =date.getDatabaseMajorVersion();
            System.out.println(version);
            
            // 得到连接数据库的用户名
            String user=date.getUserName();
            System.out.println(user);
            //得到MYSQL中有哪些数据
           resultSet = date.getCatalogs();
            while(resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
        } catch (Exception e) {
       e.printStackTrace();
        }finally{
            JDBCTools.release(null, null, connection);
        }
    }
}
