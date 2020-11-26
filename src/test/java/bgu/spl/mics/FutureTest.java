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

//    @Test
//    void getTest_Block_Thread(){
//        Thread t=new Thread(()->{
//            future.get();
//        });
//        t.start();
//        assertTrue(Thread.holdsLock(t));
//    }

//    @Test
//    void getTest_With_Time_Unit_Without_Blocking(){
//        String str = "someResult";
//        assertFalse(future.isDone());
//        future.resolve(str);
//        assertEquals(future.get(100,TimeUnit.MILLISECONDS),str);
//    }

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
