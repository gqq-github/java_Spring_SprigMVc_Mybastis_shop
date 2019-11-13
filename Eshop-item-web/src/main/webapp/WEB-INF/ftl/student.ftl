<html>
<title>freemaker的练习</title>
<body>
  <div>
      对象语法的使用
      id:${student.id} &nbsp;&nbsp;
      name:${student.name}&nbsp;&nbsp;
      age:${student.age}&nbsp;&nbsp;
      addres:${student.addres}&nbsp;&nbsp;
  </div>
 <div>
     <table bgcolor="1">
         <tr bgcolor="#f0f8ff">
             <th>编号</th>
             <th>id</th>
             <th>name</th>
             <th>age</th>
             <th>addres</th>
         </tr>
         <#list stulist as stu>
           <#if stu_index%2==0 >
               <tr bgcolor="red">
           <#else>
           <tr bgcolor="aqua">
           </#if>

               <td>${stu_index}</td>
               <td>${stu.id}</td>
               <td>${stu.name}</td>
               <td>${stu.age}</td>
               <td>${stu.addres}</td>
         </tr>
         </#list>
     </table>
 </div>
  <#--日期:${date?date}-->
  <#--日期1:${date?time}-->
  <#--日期2:${date?datetime}-->
  日期3:${date?string("yyyy-MM-dd HH:mm:ss")} <br>
  是否为null:
  ${val!"null"}
  <#if val??>
     ! null<br>
  <#else>
  null
  <br>
  </#if>
 引用模板的测试:
  <#include "hello.ftl">
</body>
</html>