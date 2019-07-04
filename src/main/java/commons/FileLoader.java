package commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is used to load the Resource as Files
 */

public class FileLoader {

    /**
     * This method searches for the resource name given in input
     * @param resourcePath path of the wanted resource (starting from the ../resource/ directory)
     * @return  a File object containing the wanted resource
     */
    public static File getResourceAsFile(String resourcePath) {
        try {
            System.out.println(resourcePath);
            FileLoader.class.getClassLoader();
            System.out.println("Sto cercando " + ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath));
            System.out.println("URL: " + ClassLoader.getSystemClassLoader().getResource(resourcePath));
            InputStream in;
            in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
            System.out.println("In value = " + in);
            if (in == null) {
                return null;
            }

            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                //copy stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
