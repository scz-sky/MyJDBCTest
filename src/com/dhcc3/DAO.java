package com.dhcc3;

import java.sql.Connection;
import java.util.List;

/*访问数据的DAO入口
 * 里面定义着访问数据表的各种方法
 * @param T: DAO处理的实体类的类型
 */

    
public interface DAO <T>{
    /*
     * 批量处理的方法
     * @param connection
     * @param sql
     * @param  args：填充占位符的Object [] (数组类型)的可变参数
     */
    void batch(Connection connection,String sql,Object [ ] ...args); 
    
    /*
     * 返回T的一个集合
     * @param connection
     * @param sql
     * @param  args
     */
            List<T> getForList(Connection connection,String sql,Object...args);
    /*
     * 返回具体的一个值，例如总人数，平均工资，某一个人的email等
     * @param connection
     * @param sql
     * @param  args
     */
              <E> E getForValue(Connection connection,String sql,Object...args);
    /*
     * 返回T的一个对象
     * @param connection
     * @param sql
     * @param  args
     * @return
     */
               T get(Connection connection,String sql,Object...args) throws Exception;
    
    /*
     * INSERT UPDATE  DELECT 
     * @param connection:数据库连接
     * @param sql:SQL语句
     * @param  args:填充占位符的可变参数
     */
    
           void update(Connection connection,String sql,Object...args);
    
    
}
    

