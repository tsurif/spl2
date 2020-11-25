package bgu.spl.mics.application.passiveObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EwokTest {

    private Ewok ewok;

    @BeforeEach
    public void setUP(){ewok=new Ewok();}

    @AfterEach
    void breakUp(){ ewok = null;}

    @Test
    void acquireTest() {
        ewok.acquire();
        assertFalse(ewok.available);
    }

    @Test
    void releaseTest() {
        ewok.release();
        assertTrue(ewok.available);
    }
}