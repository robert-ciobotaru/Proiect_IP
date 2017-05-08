package interfata.ip.notifier.InternalFile;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Vlad on 08.05.2017.
 */

public class FileIO {
    String file_name;
    String info;
    public FileIO(String file_name){
        this.file_name=file_name;
    }

    public void saveInfo(String info, Context c) throws FileNotFoundException {

        try {
            FileOutputStream file= c.openFileOutput(this.file_name,MODE_PRIVATE);
            file.write(info.getBytes());
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void loadInfo( Context c) throws IOException {
        String info2;
        FileInputStream file= c.openFileInput(this.file_name);
        InputStreamReader inputStreamReader= new InputStreamReader(file);
        BufferedReader bufferReader= new BufferedReader(inputStreamReader);
        StringBuffer stringBuffer= new StringBuffer();
        while((info2=bufferReader.readLine())!=null){
            stringBuffer.append(info2 + "\n");
        }
        this.info=stringBuffer.toString();
    }


    public String getFile_name() {
        return file_name;
    }

    public String getInfo() {
        return info;
    }
}
