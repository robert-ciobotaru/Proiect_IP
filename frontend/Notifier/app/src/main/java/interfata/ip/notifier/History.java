package interfata.ip.notifier;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    String[] titles=new String[50];
    int[] pics= new int[50];
    String[] contents=new String[50];


    List<Notificare> rowItems;
    ListView mylistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rowItems = new ArrayList<Notificare>();

        /*titles = getResources().getStringArray(R.array.Member_names);

        pics = getResources().obtainTypedArray(R.array.profile_pics);

        contents = getResources().getStringArray(R.array.statues);*/

        titles[0]="Title1";
        pics[0]=1;
        contents[0]="Content1";

        titles[1]="Title2";
        pics[1]=1;
        contents[1]="Content2";




        for (int i = 0; i < titles.length && titles[i]!=null; i++ ) {
            Notificare item2 = new Notificare(titles[i], contents[i], pics[i]);
            rowItems.add(item2);
        }

        mylistview = (ListView) findViewById(R.id.listview);
        NotificationAdapter adapter = new NotificationAdapter(this, rowItems);
        mylistview.setAdapter(adapter);




    }
}
