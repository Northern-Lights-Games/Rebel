package rebel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReader {
    public static String readFile(String path){
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

    public static String readFile(InputStream inputStream){
        String total = "";

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

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
