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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Buy extends Fragment {

    View rootview;
    Spinner company_list;
    int balance;
    public Buy() {
        // Required empty public constructor
    }
    ParseObject obj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview =  inflater.inflate(R.layout.fragment_buy, container, false);

        company_list = (Spinner) rootview.findViewById(R.id.company_list_spinner);

        ParseQuery<ParseObject> p = ParseQuery.getQuery("Company");

        p.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    String[] comp = new String[objects.size()];
                    for(int i = 0;i<objects.size();i++) {
                        ParseObject o = objects.get(i);
                        comp[i] = o.get("company_name").toString();
                    }


                    int[] imageId = {
                            1,2,3,4,5,6,7
                    } ;

                    ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(
                            getContext(), android.R.layout.simple_spinner_dropdown_item, comp);

                    company_list.setAdapter(spinner_adapter);

                    CustomList_buy adapter = new
                            CustomList_buy(getActivity(), comp, imageId);
                    ListView list=(ListView)rootview.findViewById(R.id.list_companies);
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            //Toast.makeText(getContext(), "You Clicked at " +comp[+ position], Toast.LENGTH_SHORT).show();
                            company_list.setSelection(position);
                        }
                    });


                }
            }
        });

        Button b = (Button) rootview.findViewById(R.id.buy_share);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getContext(),"u clicked processing",Toast.LENGTH_SHORT).show();

                final EditText e1 = (EditText) rootview.findViewById(R.id.buy_amount);
                if(e1.getText().toString().isEmpty())    return;
                SharedPreferences credentialsSharedPref = getContext().getSharedPreferences(Login.PREFS_NAME, getContext().MODE_PRIVATE);


                ParseQuery<ParseObject> p1 = ParseQuery.getQuery("users");
                p1.whereEqualTo("username",credentialsSharedPref.getString("username","unidentified"));
                p1.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e == null && objects.size()>0){
                            obj = objects.get(0);
                            balance = objects.get(0).getInt("balance");
                        }
                    }
                });

                if(balance < Integer.parseInt(e1.getText().toString())){
                    return;
                }
                Toast.makeText(getContext(),"still processing",Toast.LENGTH_SHORT).show();

                ParseObject p = new ParseObject("transactions");
                p.add("user_name",credentialsSharedPref.getString("username","unidentified"));
                p.add("company",company_list.getSelectedItem().toString());
                p.add("num_shares",e1.getText().toString());

                p.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        //Toast.makeText(getContext(),"exception raised",Toast.LENGTH_SHORT).show();

                        if(e == null){

                            obj.put("balance",balance-Integer.parseInt(e1.getText().toString()));
                            obj.saveInBackground();
                            Toast.makeText(getContext(),"Shares Bought! Balance has been updated",Toast.LENGTH_SHORT).show();
                            e1.setText("");
                        }
                        else
                        {
                            Toast.makeText(getContext(),""+e,Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        return rootview;
    }

}


class CustomList_buy extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final int[] imageId;
    public CustomList_buy(Activity context,
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
