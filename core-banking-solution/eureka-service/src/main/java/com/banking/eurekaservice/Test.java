package com.banking.eurekaservice;

abstract class Test1{

}
abstract public class Test extends Test1{
    public static void main(String[] args) {

        
        try {
            System.out.println(doStuff("X"));
        }catch(Exception e){
            System.out.println("exc");
            doStuff("X");
        }
    }

    static int doStuff(String s){
        return Integer.parseInt(s);
    }

    Thread t = new Thread();
}
