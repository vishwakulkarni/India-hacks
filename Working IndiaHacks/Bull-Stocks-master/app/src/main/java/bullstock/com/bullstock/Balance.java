package bullstock.com.bullstock;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Balance extends Fragment {


    public Balance() {
        // Required empty public constructor
    }

    View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootview = inflater.inflate(R.layout.fragment_balance, container, false);

        final TextView a = (TextView) rootview.findViewById(R.id.balqqqq);
        final SharedPreferences credentialsSharedPref = getContext().getSharedPreferences(Login.PREFS_NAME, getContext().MODE_PRIVATE);

        ParseQuery<ParseObject> q = ParseQuery.getQuery("users");
        //q.whereEqualTo("username",credentialsSharedPref.getString("username","un"));

        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Toast.makeText(getContext(),credentialsSharedPref.getString("username","un") + " " + objects.size(),Toast.LENGTH_SHORT).show();
                if(objects.size()>0){
                    for(int i =0;i<objects.size();i++) {
                        if (objects.get(i).getString("username").equals(credentialsSharedPref.getString("username","un"))) {
                            a.setText("Rs. " + objects.get(i).getInt("balance"));
                        }
                    }
                }
            }
        });




        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");




        BarChart chart = (BarChart)rootview.findViewById(R.id.barchart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);







        ParseQuery<ParseObject> p = ParseQuery.getQuery("transactions");
        p.whereEqualTo("user_name",credentialsSharedPref.getString("username","unidentified"));
        p.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){


                    String[] comp = new String[objects.size()];

                    for(int i = 0;i<objects.size();i++){
                        ParseObject o = objects.get(i);
                        comp[i] = o.get("company").toString();

                    }

                    int[] imageId = {
                            1,2,3,4,5,6,7
                    } ;

                    CustomList_balance adapter = new
                            CustomList_balance(getActivity(), comp, imageId);
                    ListView list=(ListView)rootview.findViewById(R.id.list_balance);
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            //Toast.makeText(getContext(), "You Clicked at " +comp[+ position], Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });





        return rootview;
    }

}

class CustomList_balance extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final int[] imageId;
    public CustomList_balance(Activity context,
                          String[] web, int[] imageId) {
        super(context, R.layout.listview_companies, web);

        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listview_companies, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);

        //imageView.setImageResource(imageId[position]);
        return rowView;
    }
}

