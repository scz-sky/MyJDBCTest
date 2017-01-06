package com.dhcc.sun;
/**
 * 
 * @author Administrator
 * POJO 普通的java类
 *
 */
public class ClassInfo {
    private String id;
    private String classNum;
    private String className;
    private String classGrade;
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return this.id;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(String classGrade) {
        this.classGrade = classGrade;
    }
    
}
