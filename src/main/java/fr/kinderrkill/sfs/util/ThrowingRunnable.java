package fr.kinderrkill.sfs.util;

@FunctionalInterface
public interface ThrowingRunnable extends Runnable{

    @Override
    default void run() {
        try {
            runtimeRun();
        } catch(Throwable throwable){
            throw new RuntimeException(throwable);
        }
    }

    void runtimeRun() throws Throwable;
}
