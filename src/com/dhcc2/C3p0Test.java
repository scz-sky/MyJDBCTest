package com.dhcc2;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import com.dhcc.sun.JDBCTools;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0Test {
    
    @Test
    // 测试一下 更改JDBCTools工具类之后 能否连接数据库连接池
    public void testJDBCTools() throws Exception{
        Connection connection=JDBCTools.getConnection();
        System.out.println(connection);
    }
    /*
     * 1.创建c3p0-config.xml文件，参考帮助文档Appendix B: Configuration Files, etc.
     * 2.创建ComboPooledDataSource实例
     *  DataSource dataSource =new ComboPooledDataSource("helloc3p0");
     *  3.从DataSource中获取数据库连接
     */
    @Test
    public void testC3p0WithConfigFile() throws Exception{
        // 2.创建ComboPooledDataSource实例
        DataSource dataSource =new ComboPooledDataSource("helloc3p0");// ComboPooledDataSource:数据库连接池，封装数据库连接
        
        System.out.println(dataSource.getConnection());
        //从DataSource中获取数据库连接
        ComboPooledDataSource combopooledDataSource=
                                             (ComboPooledDataSource) dataSource;
        System.out.println(combopooledDataSource.getMaxStatements());
    }
    
    @Test
    public void testC3p0() throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource(); 
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        cpds.setUser("root");
        cpds.setPassword("123456");
    
        System.out.println(cpds.getConnection());
   }
}