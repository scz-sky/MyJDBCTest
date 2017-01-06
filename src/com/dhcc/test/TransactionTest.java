package com.dhcc.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.Test;

import com.dhcc.sun.JDBCTools;

// 处理事务
/*
 * 关于事物：
 *  1.如果有多少个操作，每个操作使用的是自己单独的连接，则无法保证事务
 *  2.具体步骤：
 *          1).事务操作开始前，开始事务：取消Connection 的默认提交行为
 *   connection.setAutoCommit(false);
 *          2).如果事务的操作都成功，则提交事务：connection.commit();
 *          3).回滚事务：若出现异常，则在catch 块中回滚事务   connection.rollback();
 *   
 */
public class TransactionTest {
    /*
     * 测试事务的隔离级别
     *     在JDBC程序中可以通过Connection的setTransactionIsolation 来设置事务的隔离级别
     */
    @Test
    public void TransactionIsolationUpdate(){
        Connection connection=null;
        try {
            connection =JDBCTools.getConnection();
            connection.setAutoCommit(false);
            
            String sql="UPDATE table2 SET balance="+"balance-500 WHERE id=1";
            update(connection,sql);
            connection.commit();
            
        } catch (Exception e) {
          e.printStackTrace();
        }finally{
            
        }
    }
    

@Test
public void TransactionIsolationRead(){
    String sql="SELECT balance FROM table2 WHERE id=1";
    Integer  balance =Integer.valueOf(getForValue(sql));
    System.out.println(balance);
}
    // 返回某条记录的某一个字段的值 或者一个统计的值（一共有多少条记录等）
    public <E>  E getForValue(String sql,Object...args){
        // 得到结果集 结果集应该只有一行 且只有一行
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
       
        try{
            // 1.得到结果集
            connection=JDBCTools.getConnection();
            // 默认的隔离级别
            System.out.println(connection.getTransactionIsolation()); //  int TRANSACTION_REPEATABLE_READ  = 4;
            // 读的是未提交的数据
          //connection.setTransactionIsolation(connection.TRANSACTION_READ_UNCOMMITTED);
            //读的是已提交的数据
           connection.setTransactionIsolation(connection.TRANSACTION_READ_COMMITTED);
            preparedStatement=connection.prepareStatement(sql);
       
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1, args[i]);
            }
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
             return(E) resultSet.getObject(1);
           }
        }catch(Exception exe){
            exe.printStackTrace();
        }finally{
            JDBCTools.release(resultSet, preparedStatement, connection);
        }
        // 2.取得结果
        return null;
    }
    
 //   @Test
    public void testTransaction() throws Exception{
       Connection connection =null;
       try {
           connection=JDBCTools.getConnection(); 
           System.out.println(connection.getAutoCommit());
           // 开始事务：取消默认提交
           connection.setAutoCommit(false);
           String sql="UPDATE table2 SET balance="+"balance-500 WHERE id=1";
           System.out.println(sql);
           update(connection,sql);
           
           int i=10/0;
           System.out.println(i);
           
           sql="UPDATE table2 SET balance="+"balance+500 WHERE id=2";
           update(connection,sql);
           //提交事务
           connection.commit();
    } catch (Exception e) {
       e.printStackTrace();
       // 回滚事务
       connection.rollback();
    }finally{
        JDBCTools.release(null, null, connection);
    }
    }

    private void update(Connection connection,String sql,Object...args) {
        PreparedStatement preparedStatement=null;
        try {
           preparedStatement=connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1, args[i]);
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
           e.printStackTrace();
        }finally{
            JDBCTools.release(null, preparedStatement, null);
        }
    }

  
}
