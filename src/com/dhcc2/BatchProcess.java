package com.dhcc2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.junit.Test;

import com.dhcc.sun.JDBCTools;

// 批量处理

/*
 * 向Oracle的customers数据表中插入1万条记录
 * 测试如何插入，用时最短          
 * 1.使用Statement  Time:5728     
 * 2.使用PreparedStatement  Time:5594 
 * 3.使用Batch    批量操作的sql语句越多 batch的时间就越少，是最简便的
 */
public class BatchProcess {
   
// Batch
    @Test
    public void  testBatch(){
        Connection connection =null;
        PreparedStatement  preparedStatement=null;
        String sql=null;
        try {
            connection=JDBCTools.getConnection();
            JDBCTools.beginTx(connection);  // 开始事务
            sql="INSERT INTO table2 VALUES(?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            
            // 批量操作，需要把它放在事务里面
            long begin =System.currentTimeMillis();//开始时间
            for(int i=0;i<10000;i++){
               preparedStatement.setInt(1, i+1);
               preparedStatement.setString(2, "username_"+i);
               preparedStatement.setString(3, "password");
               preparedStatement.setString(4, "balance");
               
            // 1.“积攒” SQL
               preparedStatement.addBatch();
            //2.当“积攒” 到一定的程度，就统一的执行一次，并且清空先前的“积攒”的SQL
               if((i+1)%300==0){
                   preparedStatement.executeBatch();
                   preparedStatement.clearBatch();
               }
            }
            // 3.若总条数不是批量数值的整数倍，则还需要再额外的执行一次
            if(10000%300 !=0){
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }
            long end=System.currentTimeMillis();// 结束时间
            System.out.println("Time:"+(end-begin));//664
            
            JDBCTools.commit(connection);// 提交事务
        } catch (Exception e) {
          e.printStackTrace();
          JDBCTools.rollback(connection);// 处理异常的回滚事务
        }finally{
            JDBCTools.release(null, preparedStatement, connection);
        }
    }
    
    
    
    //PreparedStatement
    @Test
    public void  testBatchwhihPreparedStatement(){
        Connection connection =null;
        PreparedStatement  preparedStatement=null;
        String sql=null;
        try {
            connection=JDBCTools.getConnection();
            JDBCTools.beginTx(connection);  // 开始事务
            sql="INSERT INTO table2 VALUES(?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            
            // 批量操作，需要把它放在事务里面
            long begin =System.currentTimeMillis();//开始时间
            for(int i=0;i<10000;i++){
               preparedStatement.setInt(1, i+1);
               preparedStatement.setString(2, "username_"+i);
               preparedStatement.setString(3, "password");
               preparedStatement.setString(4, "balance");
               
               preparedStatement.executeUpdate();
            }
            long end=System.currentTimeMillis();
            System.out.println("Time:"+(end-begin));//Time:5594
            
            JDBCTools.commit(connection);// 提交事务
        } catch (Exception e) {
          e.printStackTrace();
          JDBCTools.rollback(connection);// 处理异常的回滚事务
        }finally{
            JDBCTools.release(null, preparedStatement, connection);
        }
    }
    
    
    
   // Statement
    @Test
    public void  testBatchwhihStatement(){
        Connection connection =null;
        Statement  statement=null;
        String sql=null;
        try {
            connection=JDBCTools.getConnection();
            JDBCTools.beginTx(connection);  // 开始事务
            statement=connection.createStatement();
            
            // 批量操作，需要把它放在事务里面
            long begin =System.currentTimeMillis();//开始时间
            for(int i=0;i<10000;i++){
                sql="INSERT INTO table2 VALUES("+(i+1)+",'username_"+i+"','password','balance')";
              
                statement.executeUpdate(sql);
            }
            long end=System.currentTimeMillis();
            System.out.println("Time:"+(end-begin));//Time:5728
            
            JDBCTools.commit(connection);// 提交事务
        } catch (Exception e) {
          e.printStackTrace();
          JDBCTools.rollback(connection);// 处理异常的回滚事务
        }finally{
            JDBCTools.release(null, statement, connection);
        }
    }
}
