package com.dhcc3;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
// 提供接口的具体实现类
/*
 * 使用QueryRunner 提供其具体的实现
 * @param<T> : 子类需传入的泛型类型
 */
public class JdbcDAOImpl<T> implements DAO<T> {

    private QueryRunner queryRunner=null;
    private Class<T> type;
    public JdbcDAOImpl(){
        queryRunner=new QueryRunner();
        type=ReflectUtils.getSuperGenericType(getClass());
    }
    @Override
    public void batch(Connection connection, String sql, Object[]... args) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<T> getForList(Connection connection, String sql, Object... args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <E> E getForValue(Connection connection, String sql, Object... args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T get(Connection connection, String sql, Object... args) throws Exception {
       return queryRunner.query(connection, sql, new BeanHandler<>(type), args);
        
    }

    @Override
    public void update(Connection connection, String sql, Object... args) {
        // TODO Auto-generated method stub
        
    }

 
}
