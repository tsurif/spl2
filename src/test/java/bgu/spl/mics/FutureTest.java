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

    @AfterEach
    void breakUp(){ future = null;}

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

    @Test
    void getTest(long timeout, TimeUnit timeUnit){

    }

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
