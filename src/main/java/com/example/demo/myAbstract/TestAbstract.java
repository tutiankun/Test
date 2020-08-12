package com.example.demo.myAbstract;

import com.example.demo.Interface.Impl.UsaHelloImpl;
import com.example.demo.config.BeanConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAbstract {
    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);

    @Test
    public void test1(){
        Teacher teacher = annotationConfigApplicationContext.getBean(Teacher.class);

        if (teacher instanceof People){
            System.out.println("teacher是people的一个实例");
        }
        if (People.class.isInstance(teacher)){
            System.out.println(1111);
        }
        if (People.class.isAssignableFrom(Teacher.class)){
            System.out.println(222);
        }
        if (People.class.isAssignableFrom(UsaHelloImpl.class)){
            System.out.println(333);
        }

    }

    @Test
    public void test2(){
        InitBean initBean = annotationConfigApplicationContext.getBean(InitBean.class);
        //Teacher bean = annotationConfigApplicationContext.getBean(Teacher.class);
        Teacher bean = (Teacher) initBean.getBean(1);
        bean.say();
        bean.test();
        System.out.println(bean);


    }
}
