package academy.tich;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileManagement {

  public FileManagement() {
  }

  public File writeFile(InputStream initialStream, String targetFileDir) throws IOException {
    byte[] buffer = new byte[initialStream.available()];
    initialStream.read(buffer);
    File targetFile = new File(targetFileDir);
    OutputStream outStream = new FileOutputStream(targetFile);
    outStream.write(buffer);
    outStream.close();
    return targetFile;
  }

}
