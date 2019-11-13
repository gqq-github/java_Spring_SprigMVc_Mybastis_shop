package cn.gq.item.details.controller.test;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Freemaker {

    public static void   testFreenaker() throws IOException, TemplateException {
        /*
        第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        第二步：设置模板文件所在的路径。
        第三步：设置模板文件使用的字符集。一般就是utf-8.
        第四步：加载一个模板，创建一个模板对象。
        第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
        第七步：调用模板对象的process方法输出文件。
        第八步：关闭流。
         */
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("F:\\宜立方商城\\Eshop-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        configuration.setDefaultEncoding("utf-8");
//        Template template = configuration.getTemplate("hello.ftl");
        Template template = configuration.getTemplate("student.ftl");
        Map map = new HashMap() ;
       map.put("hello","hello freemaker");
        map.put("student",new Student(1,"小明",19,"北京"));
        List<Student> stulist  = new ArrayList<>();
        stulist.add(new Student(1,"小明1",19,"北京"));
        stulist.add(new Student(2,"小明2",19,"北京"));
        stulist.add(new Student(3,"小明3",19,"北京"));
        stulist.add(new Student(4,"小明4",19,"北京"));
        stulist.add(new Student(5,"小明5",19,"北京"));
        stulist.add(new Student(6,"小明6",19,"北京"));
        map.put("stulist",stulist);
        map.put("date",new Date());

        Writer out = new FileWriter("E:\\BaiduNetdiskDownload\\6第六阶段 项目实战二（14天）\\宜立方商城(14天）\\e3商城_day01\\黑马32期\\01.教案-3.0\\02.教案-AB-3.0\\freemaker\\student.html");
       template.process(map,out);
       out.close();
    }

    public static void main(String[] args) throws IOException, TemplateException {
        Freemaker.testFreenaker();
    }
}



