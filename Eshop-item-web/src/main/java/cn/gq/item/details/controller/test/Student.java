package cn.gq.item.details.controller.test;

public class Student {
    private int id ;
    private String name ;
    private int age ;
    private String addres ;

    public Student(int id, String name, int age, String addres) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.addres = addres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }
}
