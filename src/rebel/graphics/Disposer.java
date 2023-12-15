package rebel.graphics;

import java.util.LinkedList;

public class Disposer {
    private static final LinkedList<Disposable> disposables = new LinkedList<>();

    public static void add(Disposable disposable){
        disposables.add(disposable);
    }

    public static void disposeAll(){

        for(Disposable d : disposables) {
            System.out.println("Disposing: " + d.getClass().getSimpleName());
            d.dispose();
        }

        disposables.clear();
    }

}
