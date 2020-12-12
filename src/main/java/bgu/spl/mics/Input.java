package bgu.spl.mics;
import com.google.gson.Gson;
import bgu.spl.mics.application.passiveObjects.Attack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

/**
 * an input object to store the information from the json file
 * @attacks Attack-passive object we need to handle
 * @R2D2 sleep time for Deactivation-Event
 * @Lando sleep time for BombDestroyer-Event
 * @Ewoks how many Ewoks we need to handle the attacks
 */
public class Input {
    private Attack[] attacks;
    private int R2D2;
    private int Lando;
    private int Ewoks;

    public int getEwoks() {
        return Ewoks;
    }
    public void setEwoks(int ewoks) {
        Ewoks = ewoks;
    }
    public int getLando() {
        return Lando;
    }
    public void setLando(int lando) {
        Lando = lando;
    }
    public int getR2D2() {
        return R2D2;
    }
    public void setR2D2(int r2d2) {
        R2D2 = r2d2;
    }
    public Attack[] getAttacks() {
        return attacks;
    }
    public void setAttacks(Attack[] attacks) {
        this.attacks = attacks;
    }
}
