package rebel.engine;

import java.util.Date;

public class Tools {
    public static void logRenderCalls(Renderer2D renderer2D){

        System.out.println("rebel.engine.Tools.logRenderCalls()-------------------------[" + new Date() + "]");

        for(String call : renderer2D.getRenderCalls()){
            System.out.print("\t" + call + "\n");
        }
        System.out.println("\n");


    }
}
