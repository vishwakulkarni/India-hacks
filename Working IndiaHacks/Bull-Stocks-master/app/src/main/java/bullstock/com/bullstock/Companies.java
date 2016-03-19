package bullstock.com.bullstock;


import android.app.Activity;
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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Companies extends Fragment {

View rootview;
    public Companies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_companies, container, false);

        ParseQuery<ParseObject> p = ParseQuery.getQuery("Company");

        p.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    String[] comp = new String[10];
                    for(int i = 0;i<objects.size();i++) {
                        ParseObject o = objects.get(i);
                        comp[i] = o.get("company_name").toString();
                    }


                        int[] imageId = {
                                1,2,3,4,5,6,7
                        } ;

                        CustomList adapter = new
                                CustomList(getActivity(), comp, imageId);

                        ListView list=(ListView) rootview.findViewById(R.id.list_companies);
                        list.setAdapter(adapter);

                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                //Toast.makeText(getContext(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }
            });



        return rootview;
    }

}

class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final int[] imageId;
    public CustomList(Activity context,
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
