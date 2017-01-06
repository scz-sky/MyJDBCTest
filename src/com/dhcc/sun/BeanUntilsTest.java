package com.dhcc.sun;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

public class BeanUntilsTest {
    @Test
    // 取值
  public void testGetProperty() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
      Object object=new Student();
      System.out.println(object);
      
      BeanUtils.setProperty(object, "idCard", "310110199301235769");
      System.out.println(object);
      
      Object val=BeanUtils.getProperty(object, "idCard");
      System.out.println(val);
}

      @Test
      // 赋值
    public void testSetProperty() throws IllegalAccessException, InvocationTargetException{
      
        Object object=new Student();
        System.out.println(object);
        
        //搭建环境：需要同时加入：commons-beanutils-1.8.0.jar和Commons-logg-1.1.1.jar
        // 属性值是通过 get set 方法定义的！
        BeanUtils.setProperty(object, "idCard", "370122199808202212");
        System.out.println(object);
    }
}
