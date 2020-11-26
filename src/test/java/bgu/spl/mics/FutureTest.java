package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {

    private Future<String> future;

    @BeforeEach
    void setUp(){
        future = new Future<>();
    }

    @Test
    void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        assertTrue(str.equals(future.get()));
    }
    @Test
    void getTest(){

    }

//    @Test
//    void getTestWithTimeUnit(){
//        Thread t1 = new Thread(()-> {
//            future.get();
//        });
//        Thread t2 = new Thread(()-> {
//            assertNull(future.get(1000,TimeUnit.MILLISECONDS));
//        });
//        t1.start();
//        try {
//            Thread.sleep(4000);
//        }
//        catch (InterruptedException e){
//
//        }
//         t2.start();
//    }

    @Test
    void isDoneTest(){
        String str="someResult";
        future.resolve(str);
        assertTrue(future.isDone());
    }

    @Test
    void isNotDoneTest(){
        assertFalse(future.isDone());
    }

}
