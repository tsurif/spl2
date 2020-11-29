package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;



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
        assertEquals(future.get(), str);
    }
    @Test
    void getTest(){
        String str="someResult";
        future.resolve(str);
        assertEquals(future.get(), str);
    }

    @Test
    void getTest_NOT_Null_Result(){
        String str="someResult";
        future.resolve(str);
        assertNotNull(future.get());
    }

    @Test
    void getTest_Null_Result(){
        assertNull(future.get());
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
