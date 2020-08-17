package com.example.tk.myAbstract;

public abstract class Person implements People {
/*
    @Autowired
    private Dog dog;
*/

    public abstract void say();

    public abstract void smile();

/*    public void cry(){
        dog.fun();
    }*/

    @Override
    public void doWork() {
        smile();
    }
}
