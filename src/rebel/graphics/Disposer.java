package rebel.graphics;

import java.util.LinkedList;

/***
 * A Disposer is responsible for registering OpenGL objects like VBOs/VAOs/Shaders that have to be disposed
 * when the application is closed
 */
public class Disposer {
    private static final LinkedList<Disposable> disposables = new LinkedList<>();

    public static void add(Disposable disposable){
        disposables.add(disposable);
    }

    public static void disposeAll(){

        for(Disposable d : disposables) {
            d.dispose();
        }

        disposables.clear();
    }

}
