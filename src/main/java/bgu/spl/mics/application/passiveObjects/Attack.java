package bgu.spl.mics.application.passiveObjects;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Passive data-object representing an attack object.
 * You must not alter any of the given public methods of this class.
 * <p>
 * YDo not add any additional members/method to this class (except for getters).
 */
public class Attack {
    final List<Integer> serials;
    final int duration;

    /**
     * Constructor.
     */
    public Attack(List<Integer> serialNumbers, int duration) {

        this.serials =serialNumbers;
        this.duration = duration;



    }
    public List<Integer>getSerials(){return serials;}
    public int getDuration(){return duration;}
    public void sort(){//TODO change the logic to avoid this methos
        serials.sort((Integer x, Integer y)->Integer.compare(x,y));;
    }
}
