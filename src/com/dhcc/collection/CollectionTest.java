package com.dhcc.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.dhcc.sun.Student;

public class CollectionTest {

    /**
     * 常用的集合：数组 array，列表 List，键值对集合 Map
     * 
     * 2.列表  List 是个接口，实现类有ArrayList、LinkedList  方法：add(),remove(),size()
     * 
     * 3.键值对集合 Map 也是个接口， 实现类有HashMap,LinkMap  方法： put(key,value),get(key),remove(key)
     *  
     */
    
    @Test
    public void test() {
        List list1 = new ArrayList();
        list1.add("123");//String
        list1.add(123);//Integer
        list1.add(true);//Boolean
        Student s = new Student();
        list1.add(s);//学生对象
        Map map = new HashMap();
        map.put("id", 12);
        map.put(100, "wang");
        map.put("name", "zhang");
        list1.add(map);
        
        List<String> list2 = new ArrayList<String>();
        list2.add("123");//0
        list2.add("234");//1
        list2.add("567");//2
        list2.add("897");//3
        
        System.out.println("普通for循环。。。。");
        for(int i =0 ; i<list2.size(); i++){
           String str = list2.get(i);
           System.out.println(str);
        }
        
        System.out.println("foreach -循环。。。。");
        //foreach 
        for(String str : list2){
            System.out.println(str);
        }
        
    }
    
    /**
     * 
     *  map: 1. key-id，value-1 2. key-name，value-“wang” 3. key-grade，value-99
     *  
     *  student: 1.id:1, 2.name:"wang" , 3. grade : 90
     */
    @Test
    public void testMap(){
        Map map = new HashMap();
        map.put("id", 12);
        map.put(100, "wang");
        map.put("name", "zhang");
        map.put("grade", 90);
        map.put("object", new Student());
        map.put(new Student(), new Student());
        List list1 = new ArrayList();
        list1.add("SCZ is Pig！");
        map.put("list", list1);
        int g = (int)map.get("grade");
        System.out.println(g);
        
        List list2 = (ArrayList)map.get("list");
        System.out.println(list2.get(0));
        
        
        Map<String,Integer> map1 = new HashMap<String,Integer>();
        map1.put("1", 1);
        
        Map<String,Object> map2 = new HashMap<String,Object>();
        String key = "1";
        map2.put(key, 1);
        map2.put("2", "234");
        map2.put("3",true);
        map2.put("list", list1);
        System.out.println("下面输出MAP：============》");
        
        //Entry<String,Object> entry
        for(Map.Entry<String,Object> entry : map2.entrySet()){
            String fieldName =entry.getKey();
            Object fieldValue=entry.getValue();
            
            System.out.println("key:"+fieldName+"----- value:"+fieldValue);
        }
        
    }

}
