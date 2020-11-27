package bgu.spl.mics;

public class CallbackMock<T> implements Callback<T>{
    boolean isCalled =false;
    @Override
    public void call(T c) {
       isCalled =true;
    }
}