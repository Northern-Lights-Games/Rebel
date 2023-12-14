package rebel;

import rebel.graphics.Renderer2D;

import java.util.Date;

public class Tools {
    public static void logRenderCalls(Renderer2D renderer2D){

        System.out.println("rebel.engine.graphics.Tools.logRenderCalls()-------------------------[" + new Date() + "]");

        for(String call : renderer2D.getRenderCalls()){
            System.out.print("\t" + call + "\n");
        }
        System.out.println("\n");


    }
}
