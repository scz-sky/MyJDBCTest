package com.dhcc2;
// DBUtils 工具类具体的原理和每一个Handler
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.dhcc.sun.JDBCTools;


public class DBUtilsTest {
    /*
     * ScalarHandler:把结果集转化为一个数值（可以是任意基本类型和字符串 Date等)返回
     */
    @Test
    public void testScalarHandler(){
        Connection connection=null;
        try {
            connection=JDBCTools.getConnection();
        //    String sql="SELECT name "+" FROM customer WHERE id= ?"; 查看第三条name的名字是什么
            String sql="SELECT count(id) "+" FROM customer";// 查看几条记录 3

 //         Object result=
//                    queryRunner.query(connection, sql, new ScalarHandler(),3);  查看第三条name的名字是什么
       Object result=
                  queryRunner.query(connection, sql, new ScalarHandler());// 查看几条记录
            System.out.println(result);// name:SunHua返回的是一个名字
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCTools.release(null, null, connection); 
        }
    }
    /*
     *MapListHandler:将结果集转为一个Map的List
     *Map对应查询的一条记录： 键：SQL查询的列名（不是列的别名）   值：列的值
     *而MapListHandler：返回的多条Map记录对应的Map的集合
     */
    @Test
    public void testMapListHandler(){
        Connection connection=null;
        try {
            connection=JDBCTools.getConnection();
            String sql="SELECT id ,name,age,birth"+" FROM customer";
            
          List<Map<String,Object>>result=
                   (List<Map<String, Object>>) queryRunner.query(connection, sql, new MapListHandler());
            System.out.println(result);// 搜索所有的
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCTools.release(null, null, connection);
        }
    }
    /*
     * MapHandler:返回SQL对应的第一条记录对应的Map对象
     * 键：SQL查询的列名（不是列的别名）  值：列的值
     */
    @Test
    public void testMapHandler(){
        Connection connection=null;
        try {
            connection=JDBCTools.getConnection();
            String sql="SELECT id ,name,age,birth"+" FROM customer";
            
           Map<String,Object> result=
                   (Map<String, Object>) queryRunner.query(connection, sql, new MapHandler());
            System.out.println(result);// 搜索一个
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCTools.release(null, null, connection);
        }
    }
    
    
    /*
     * BeanListHandler:把结果集转为一个List,该List 不为null，但可能空集合（size()方法返回0）
     *   若SQL语句查询的的确能够查询到记录，List中存放创建BeanListHandler传入的Class对象，对应的对象
     */
    @Test
    public void testBeanListHandler(){
     
        Connection connection=null;
        try {
            connection=JDBCTools.getConnection();
            String sql="SELECT id ,name,age,birth"+" FROM customer";
            
           List<Customer> customer=
                  (List<Customer>) queryRunner.query(connection, sql, new BeanListHandler(Customer.class));
            System.out.println(customer);// 搜索出全部的
     
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCTools.release(null, null, connection);
        }
    }
    
    /*
     * BeanHandler:把结果集的第一条记录转为创建BeanHandler对象时传入的Class参数对应的对象
     *  比如String sql="SELECT id ,name,age,birth"+" FROM customer WHERE id=? ";传入的第一条记录传入(Customer.class）对应的对象
     */
    @Test
    public void testBeanHandler(){
       // QueryRunner queryRunner=new QueryRunner();
        Connection connection=null;
        try {
            connection=JDBCTools.getConnection();
            String sql="SELECT id ,name,age,birth"+" FROM customer WHERE id=? ";
            
            Customer customer=(Customer) queryRunner.query(connection, sql, new BeanHandler(Customer.class), 3);
            System.out.println(customer);
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCTools.release(null, null, connection);
        }
    }
    QueryRunner queryRunner=new QueryRunner();
    
    class MyResultSetHandler implements ResultSetHandler{

        @Override
        public Object handle(ResultSet resultSet)throws SQLException{
//                    System.out.println("handle-----------");
//                    return "zifuchuang";
            // 查询一下全部的
            List<Customer> customers=new ArrayList<>();
            while(resultSet.next()){
                int id=resultSet.getInt(1);
                String name=resultSet.getString(2);
                int age=resultSet.getInt(3);
                Date birth=resultSet.getDate(4);
                
                Customer customer=new Customer(id,name,age,birth);
                customers.add(customer);
            }
            return customers;
                }
        }
   /*
    * QueryRunner 的query方法的返回值取决于ResultSetHandler的handle方法的返回值
    */
    @Test
    public void testQuery() throws Exception{
        QueryRunner queryRunner=new QueryRunner();
        Connection connection=null;
        try {
            connection=JDBCTools.getConnection();
            String sql="SELECT * FROM customer";
            
            Object obj=queryRunner.query(connection, sql, new MyResultSetHandler());
            System.out.println(obj);
            
        } catch (Exception e) {
          e.printStackTrace();
        }finally{
            JDBCTools.release(null, null, connection);
        }
    }
    
    /*
     * DBUtils 工具类
     * 测试QueryRunner类的update方法
     * 该方法可用于 INSERT UPDATE 和DELETE方法
     */
    
    @Test
    public void testQueryRunner(){
        // 创建QueryRunner的实现类
        QueryRunner queryRunner=new QueryRunner();
        // 使用其update方法
        String sql="DELETE from table2"+" WHERE id in (?,?)";
        System.out.println(sql);
        Connection connection =null;
        
        try {
         connection =JDBCTools.getConnection();
         queryRunner.update(connection, sql, 2,3);// 使用其 queryRunner的update方法，删除了id为2和3的记录
            
        } catch (Exception e) {
           e.printStackTrace();
        }finally{
            JDBCTools.release(null, null, connection);
        }
    }
    
}
