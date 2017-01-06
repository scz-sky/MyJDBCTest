package com.dhcc3;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import com.dhcc.sun.JDBCTools;
import com.dhcc2.Customer;

public class CustomerDAOTest {
CustomerDAO customerDao=new CustomerDAO();

    @Test
    public void testBatch() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetForList() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetForValue() {
        fail("Not yet implemented");
    }

    @Test
    public void testGet() {
        Connection connection=null;
        try {
            connection=JDBCTools.getConnection();
            String sql="SELECT * FROM customer WHERE id=?";
            Customer customer =customerDao.get(connection, sql, 2);
            System.out.println(customer);
        } catch (Exception e) {
           e.printStackTrace();
        }finally{
            JDBCTools.release(null, null, connection);
        }
        
    }

    @Test
    public void testUpdate() {
        fail("Not yet implemented");
    }

}
