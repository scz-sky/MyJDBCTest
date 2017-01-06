package com.dhcc3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.junit.Test;

import com.dhcc.sun.JDBCTools;

// 调用函数&存储过程
public class Storage {
    
    // JDBC调用存储过程
    @Test
    public void testCallableStatement(){
        Connection connection=null;
        CallableStatement callableStatement=null;
        
        try {
            connection=JDBCTools.getConnection();
          //1.通过Connection对象的prepareCall()方法创建一个 CallableStatement对象的实例。
            // 在使用Connection对象的preparedCall()方法时，需要传入一个String类型的字符集。
              //该字符串用于指明如何调用存储过程 
            String sql="{?=call  table2(?,?)}";
            callableStatement=connection.prepareCall(sql);
            
            //2.通过CallbleStatement对象的reisterOutParameter()方法注册OUT参数
            callableStatement.registerOutParameter(1, Types.NUMERIC);
            callableStatement.registerOutParameter(2, Types.NUMERIC);
            
            //3.通过CallbleStatement对象的setXxx方法设定IN 或IN OUT 参数，若想将参数默认值设为null,   
            //可以使用setNull()方法
            callableStatement.setInt(1, 23);
            
         // 4.通过CallbleStatement对象的execute()方法执行存储过程，
            //还需要通过CallableStatement对象的getXxx方法获取其返回值
            callableStatement.execute();
            
            // 5.如果所调用的是带返回参数的存储过程
            // 还需要通过CallableStatement对象的getXxx()方法获取其返回值
            double table2=callableStatement.getDouble(1);
            long empCount=callableStatement.getLong(2);
            System.out.println(table2);

        } catch (Exception e) {
                 e.printStackTrace();
        }finally{
            JDBCTools.release(null, null, connection);
        }
    }
}




