package bgu.spl.mics;

public class MicroServiceMock extends MicroService {
    /**
     * @param name the micro-service name (used mainly for debugging purposes -
     *             does not have to be unique)
     */
    public MicroServiceMock(String name) {
        super(name);
    }

    @Override
    protected void initialize() {

    }
}
