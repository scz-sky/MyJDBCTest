package com.dhcc.test;
/*
 * 1.先利用SQL进行查询，得到结果集
 * 2.利用反射创建实体类的对象，创建Student对象
 * 3.获取结果集的别名：idCard  studentName
 * 4.再获取结果集每一列的值，结合3得到一个MAP ，键：列的别名，
 *           值：列的值：{flowId:5,type:6,idCard:xxx ........}
 * 5.再利用反射为2的对应的属性赋值：属性即为Map的键，值即为Map的值
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.dhcc.reflection.ReflectUtils;
import com.dhcc.sun.JDBCTools;
import com.dhcc.sun.Student;

public class TestReflection {
    @Test
    public void testResultSetMetaDate(){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
       
        try{
           String sql="SELECT  flowId flow_id,type, idcard id_Card,"+
                    " examCard exam_card, studentName student_name,"+
                    "location,grade "+"FROM examstudents WHERE type=?";
            System.out.println(sql);
            connection=JDBCTools.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            // 赋值
            preparedStatement.setInt(1,7);
            // 执行查询
            resultSet=preparedStatement.executeQuery();
           // 使用了泛型<String,Object> 键 只能是String型  值 是Object型
            Map<String,Object>values=new HashMap<String,Object>();
            
          //1.得到ResultSetMetaDate的对象：调用ResultSet的getMetaDate方法
            ResultSetMetaData rsmd=resultSet.getMetaData();
            
            while(resultSet.next()){
          // 2.打印每一列的列名
            for(int i=0;i<rsmd.getColumnCount();i++){
                String columnLabel=rsmd.getColumnLabel(i+1);
                Object columnValue=resultSet.getObject(columnLabel);
                
                values.put(columnLabel, columnValue);// columnLabel列 columnValue 值
            }
          }
            System.out.println(values);
            // 利用反射赋值   ???
            Class clazz=Student.class;
            Object object=clazz.newInstance();
            for(Map.Entry<String, Object>entry:values.entrySet()){
                String fieldName =entry.getKey();
                Object fieldValue=entry.getValue();
                
              System.out.println(fieldName+": "+fieldValue);
             //ReflectUtils.setFieldValue(object,fieldName,fieldValue);
            }
           System.out.println(object);
        }catch(Exception e){
                e.printStackTrace();
            }finally{
             JDBCTools.release(resultSet, preparedStatement, connection);  
            }

    }
   @Test
    public void testGet(){
        String sql="SELECT * FROM table1 WHERE age=?";
        System.out.println(sql);
        Table1 table1=get(Table1.class,sql,27);
        System.out.println(table1);
        
       sql="SELECT  flowId ,type, idcard ,"+
               " examCard , studentName ,"+
               "location,grade "+"FROM examstudents WHERE type=?";
       System.out.println(sql);
       Student stu=get(Student.class,sql,5);
       System.out.println(stu);
    }
    public<T> T get(Class<T> clazz,String sql,Object...args){
        //entity:实体
        T entity =null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
       
        try{
            //1.得到ResuleSet对象
            connection=JDBCTools.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            // 填充占位符
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1, args[i]);
            }
            // 执行查询
            resultSet=preparedStatement.executeQuery();
            // 2. 得到ResuleSetMetaDate对象
            ResultSetMetaData rsmd=resultSet.getMetaData();
            // 3.创建一个Map<String,Object>对象, 键：SQL 查询的列的别名  值：列的值
             Map<String,Object>values=new HashMap<>();
             Map<String,String>values2=new HashMap<>();
            //4.处理结果集 利用ResultSetMeteDate 填充3对应的Map 对象
            if(resultSet.next()){
                for(int i=0;i<rsmd.getColumnCount();i++){
                    String columnLabel=rsmd.getColumnLabel(i+1);
                    Object columnValue=resultSet.getObject(i+1);
                    
                    values.put(columnLabel, columnValue);
                    values2.put(columnLabel, String.valueOf(columnValue));
                }
            }
            //5.若Map不为空集，利用反射为Class 对象的属性赋值
            if(values.size()>0){
                entity=clazz.newInstance();
                // 5.遍历Map对象 利用反射为Class对象的对应的属性赋值
                for(Map.Entry<String, Object>entry:values.entrySet()){
                    String fieldName=entry.getKey();
                    Object value=entry.getValue();
                    
                 //   ReflectionUtils.setFieldValue(entity,fieldName,value);
                    ReflectUtils.setFieldValue(entity, values2);
                    System.out.println(fieldName+": "+value);
                  
                    
                }
            }
            
        }catch(Exception e){
                e.printStackTrace();
            }finally{
             JDBCTools.release(resultSet, preparedStatement, connection);  
            }

        return entity;
    }
    
    
    public Table1 gettable1(String sql,Object...args){
        // 获取table1
        Table1 table1=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
       
        try{
            connection=JDBCTools.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            // 填充占位符
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1, args[i]);
            }
            // 执行查询
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
              // 把结果一个一个获取出来，然后创建一个对象
             table1=new Table1();
              // 一个一个赋值
           table1.setUsername(resultSet.getString(1));
           table1.setAge(resultSet.getInt(2));
           table1.setEmail(resultSet.getString(3));
           
             }
        }catch(Exception e){
                e.printStackTrace();
            }finally{
             JDBCTools.release(resultSet, preparedStatement, connection);  
            }
       return table1;
       
    }
   public Student getStudent(String sql,Object...args){
       // 获取Student
       Student stu=null;
       Connection connection=null;
       PreparedStatement preparedStatement=null;
       ResultSet resultSet=null;
      
       try{
           connection=JDBCTools.getConnection();
           preparedStatement=connection.prepareStatement(sql);
           // 填充占位符
           for(int i=0;i<args.length;i++){
               preparedStatement.setObject(i+1, args[i]);
           }
           // 执行查询
           resultSet=preparedStatement.executeQuery();
           if(resultSet.next()){
               // 把结果一个一个获取出来，然后创建一个对象
               stu=new Student();
             // 一个一个赋值
                   stu.setFlowId(resultSet.getInt(1));
                   stu.setType(resultSet.getInt(2));
                   stu.setIdCard(resultSet.getString(3));
              // .......
            }
       }catch(Exception e){
               e.printStackTrace();
           }finally{
            JDBCTools.release(resultSet, preparedStatement, connection);  
           }
       return stu;
      
   }
}
