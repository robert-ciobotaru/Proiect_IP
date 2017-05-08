package interfata.ip.notifier.InternalFile;

import java.io.File;

/**
 * Created by Vlad on 08.05.2017.
 */

public class FileIO {
    File file;
    FileIO(String filename){
         this.file = new File(context.getFilesDir(), filename);
    }
}
