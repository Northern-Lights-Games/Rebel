package rebel;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {
    public static final String readFile(String path){
        String total = "";

        try {
            BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(path));

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                total += line + "\n";
            }

            return total;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
