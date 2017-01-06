package com.dhcc.sun;
// 创建一个student类 调用get set方法 toString方法 有参的构造器 空参的构造器
public class Student {
   private  int flowId;//流水号
   private  int type;//考试的类型
   private  String idCard;//身份证号
   private  String examCard;//准考证号
   private  String studentName;//学生名
   private  String location;//学生地址
   private  int grade;//考试分数
public int getFlowId() {
    return flowId;
}
public void setFlowId(int flowId) {
    this.flowId = flowId;
}
public int getType() {
    return type;
}
public void setType(int type) {
    this.type = type;
}
public String getIdCard() {
    return idCard;
}
public void setIdCard(String idCard) {
    this.idCard = idCard;
}
public String getExamCard() {
    return examCard;
}
public void setExamCard(String examCard) {
    this.examCard = examCard;
}
public String getStudentName() {
    return studentName;
}
public void setStudentName(String studentName) {
    this.studentName = studentName;
}
public String getLocation() {
    return location;
}
public void setLocation(String location) {
    this.location = location;
}
public int getGrade() {
    return grade;
}
public void setGrade(int grade) {
    this.grade = grade;
}
public Student(int flowId, int type, String idCard, String examCard,
        String studentName, String location, int grade) {
    super();
    this.flowId = flowId;
    this.type = type;
    this.idCard = idCard;
    this.examCard = examCard;
    this.studentName = studentName;
    this.location = location;
    this.grade = grade;
  }
public Student() {
    super();
  }
@Override
public String toString() {
    return "Student [flowId=" + flowId + ", type=" + type + ", idCard="
            + idCard + ", examCard=" + examCard + ", studentName="
            + studentName + ", location=" + location + ", grade=" + grade + "]";
  }
   
}
