package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String arg[]){
        //第一个参数是db的版本号，第二个是生成的包名
        Schema schema = new Schema(1,"www.greendao.com");

        Entity son = schema.addEntity("Son");//创建一个son表
        //给son表添加属性
        son.addStringProperty("name");
        son.addIntProperty("age");
        son.addIdProperty();
       //Property fatherId =  son.addLongProperty("fatherId").getProperty();//设置外键

        Entity father = schema.addEntity("Father");//创建一个father表
        //给father表添加属性
        father.addIdProperty();
        father.addStringProperty("name");
        father.addIntProperty("age");
        Property sonId =  father.addLongProperty("sonId").getProperty();//设置外键

        //son.addToOne(fater,fatherId);//建立关联

        //建立father表一对多的关联
        father.addToOne(son,sonId);
        son.addToMany(father,sonId).setName("fathers");

        try {
            //设置生成文件的路径
            new DaoGenerator().generateAll(schema,"app/src/main/java");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
/**
 1对1


 Entity son = schema.addEntity("Son");//创建一个son表
 //给son表添加属性
 son.addStringProperty("name");
 son.addIntProperty("age");
 son.addIdProperty();
 Property fatherId =  son.addLongProperty("fatherId").getProperty();//设置外键


 Entity father = schema.addEntity("Father");//创建一个father表
 father.addIdProperty();
 father.addStringProperty("name");
 father.addIntProperty("age");

 son.addToOne(fater,fatherId);//建立关联
 */

/**
 1对多
 Entity son = schema.addEntity("Son");//创建一个son表
 //给son表添加属性
 son.addStringProperty("name");
 son.addIntProperty("age");
 son.addIdProperty();

 Entity father = schema.addEntity("Father");//创建一个father表
 father.addIdProperty();
 father.addStringProperty("name");
 father.addIntProperty("age");
 Property sonId =  father.addLongProperty("sonId").getProperty();//设置外键

 //建立father表一对多的关联
 father.addToOne(son,sonId);
 son.addToMany(father,sonId).setName("father");
 */