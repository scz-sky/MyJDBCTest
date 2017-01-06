package com.dhcc.sun;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import org.junit.Test;

public class JDBCObject {
    /*
     * 使用perparedStatement 将有效的解决SQL注入的问题
     */
    @Test
    public void testSQLInjection2(){
//        String username="Tom";
//        String password="123456";
          String username="a' OR PASSWORD = ";
          String password=" OR '1'='1";// SQL注入
      
      String sql="SELECT * FROM table2 WHERE username=?"+" AND  password=?";
      System.out.println(sql);
      
      Connection connection=null;
      PreparedStatement preparStatement=null;
      ResultSet resultSet=null;

      try{
          connection=JDBCTools.getConnection();
          preparStatement=connection.prepareStatement(sql);
          
          preparStatement.setString(1, username);
          preparStatement.setString(2, password);
          
          resultSet=preparStatement.executeQuery();
          if(resultSet.next()){
              System.out.println("登录成功！");
          }else{
              System.out.println("用户名和密码不匹配或用户名不存在！");
          }
      }catch(Exception e){
          e.printStackTrace();
      }finally{
       JDBCTools.release(resultSet, preparStatement, connection);  
      }
  }
    /*
     * 
     * SQL注入
     */
   // @Test
    public void testSQLInjection(){
//        String username="Tom";
//        String password="123456";
        String username="a' OR PASSWORD = ";
        String password=" OR '1'='1";// SQL注入
        
        String sql="SELECT * FROM table2 WHERE username='"+username+"' AND "
                +"password='"+password+"'";
        System.out.println(sql);
        
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;

        try{
            connection=JDBCTools.getConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            if(resultSet.next()){
                System.out.println("登录成功！");
            }else{
                System.out.println("用户名和密码不匹配或用户名不存在！");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
         JDBCTools.release(resultSet, statement, connection);  
        }
    }
    
    //  使用 preparedStatement.更加简单方便的插入sql
  //  @Test
    public void testPreparedStatement(){
        Connection connection =null;
        PreparedStatement preparedStatement=null;
        try {
            connection =JDBCTools.getConnection();
            String sql="INSERT INTO table1(username,age,salary)"+"VALUES(?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            
            preparedStatement.setString(1, "FengLing");
            preparedStatement.setString(2,"29");
            preparedStatement.setString(3, "3300");
            
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCTools.release(null, preparedStatement, connection);
        }
    }
//    @Test
    public void testGetStudent(){
        // 1.得到查询的类型
        int  searchType=getSearchTypeFromConsole();
        // 2.具体查询学生信息
        Student student=searchStudent(searchType);
        // 3.打印学生信息
        printStudent(student);
    }
    /*
     * 打印学生信息：若学生存在则打印其信息，若不存在：打印查无此人
     * @param student
     */
    private void printStudent(Student student) {
      if(student!=null){
          System.out.println(student);
      }else{
          System.out.println("查无此人！");
      }
    }
    /*
     * 具体查询学生信息，返回一个Student对象 若不存在，则返回null
     * @param searchType:1或2
     * @return
     */
    private Student searchStudent(int searchType) {
        
//        String sql="SELECT FlowId,Type,IdCard,ExamCard," +
//                         "StudentName, Location, Grade " +
//                         "FROM examstudents " +
//                         "WHERE ";
        String sql="SELECT  *  FROM examstudents " + "WHERE ";
        Scanner scanner=new Scanner(System.in);
        // 1.根据输入的searchType,提示用户输入信息：
        // 1.1 若 searchType为1，提示：请输入准考证号 若为2 提示：请输入身份证号
        //2.根据searchType确定SQL
        if(searchType==1){
            System.out.println("请输入准考证号：");
            String examCard=scanner.next();
            sql=sql+"examcard='"+ examCard+"'";
        }else{
            System.out.println("请输入身份证号：");
            String examCard=scanner.next();
            sql=sql+"idcard='"+ examCard+"'";
        }
        //3.执行查询
        System.out.println(sql);
        Student student =getStudent(sql);
        //4.若存在查询结果，把查询结果封装为一个Student对象
        return student;
    }
    /*
     * 根据传入的SQL 返回 student对象
     * @param sql
     * @return 
     */
   private Student getStudent(String sql) {
    Student stu=null;
    Connection connection=null;
    Statement statement=null;
    ResultSet resultSet=null;
    
    try{
        connection=JDBCTools.getConnection();
        statement=connection.createStatement();
        resultSet=statement.executeQuery(sql);
        if(resultSet.next()){
            stu=new Student(resultSet.getInt(1),
                    resultSet.getInt(2), 
                    resultSet.getString(3), 
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7));
         }
    }catch(Exception e){
            e.printStackTrace();
        }finally{
         JDBCTools.release(resultSet, statement, connection);  
        }
    return stu;
       }
    /*
    * 从控制台读入一个整数，确定要查询的类型
    * @return:1.用准考证号查询 2. 用身份证查询  其他的无效，并提示用户重输入
    */
    private int getSearchTypeFromConsole() {
        System.out.println("请输入查询类型：1. 用准考证号查询  2.用身份证号查询");
        Scanner scanner=new Scanner(System.in);
        int type=scanner.nextInt();
        
        if(type!=1 && type!=2){
            System.out.println("输入有误，请重新输入！");
            throw new RuntimeException();
        }
        return type;
    }
   // @Test
    public void testAddNewStudent(){
        //1.获取从控制台输入的Student对象：
        Student student =getStudentFromConsole();
        addNewStudent2(student);
    }
    
    
    /*
     * 从控制台输入学生信息
     */
private Student getStudentFromConsole() {
    Scanner scanner =new Scanner(System.in);
    Student student =new Student();
    System.out.print("FlowId: ");
    student.setFlowId(scanner.nextInt());
    
    System.out.print("Type: ");
    student.setType(scanner.nextInt());
    

    System.out.print("IdCard: ");
    student.setIdCard(scanner.next());
    
    System.out.print("ExamCard: ");
    student.setExamCard(scanner.next());
    
    System.out.print("StudentName: ");
    student.setStudentName(scanner.next());
    
    System.out.print("Location: ");
    student.setLocation(scanner.next());
    
    System.out.print("Grade: ");
    student.setGrade(scanner.nextInt());
    
        return student;
    }



//建立一个class类 来对应数据表
public void addNewStudent2(Student student){
    //根据preparedStatement 准备sql语句
    String sql="INSERT INTO examstudents(Flowid,Type,IdCard,"+"ExamCard,StudentName,"
            + "Location,Grade)"   +"VALUES(?,?,?,?,?,?,?)";
  
    // 调用JDBCTools的update方法再传入一个一个占位符的值
    JDBCTools.update(sql,student.getFlowId(),student.getType(),student.getIdCard(),student.getExamCard(),
            student.getStudentName(),student.getLocation(),student.getGrade());
   
    
}
   public void addNewStudent(Student student){
    // 1.准备一条SQL语句：
       String sql =" INSERT INTO examstudents "
               +"VALUES("+student.getFlowId()
               +","+ student.getType()
               +",'"+student.getIdCard()
               +"','" +student.getExamCard()
               +"','" +student.getStudentName()
               +"','" +student.getLocation()
               +"',"+student.getGrade()+")";
       //  String sql =" INSERT INTO examstudents VALUES(11,8,' ',' ',' ',' ',95)";
        System.out.println(sql);
               
       // 2.调用JDBCTools类的update(sql)方法执行插入操作
       JDBCTools.update(sql);
   }
}