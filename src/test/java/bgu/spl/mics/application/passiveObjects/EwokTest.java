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
    public void setUp(){ewok=new Ewok();}

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