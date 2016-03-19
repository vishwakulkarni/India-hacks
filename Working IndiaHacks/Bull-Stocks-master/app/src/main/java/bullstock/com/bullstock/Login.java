package bullstock.com.bullstock;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends android.support.v4.app.Fragment {

    public static final String PREFS_NAME = "Credentials";

    Button login_btn;
    EditText username_edit,password_edit;
    //final ProgressDialog pd = new ProgressDialog(getContext());


    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_login, container, false);



        login_btn = (Button) rootview.findViewById(R.id.login_button);
        username_edit = (EditText) rootview.findViewById(R.id.login_username);
        password_edit = (EditText) rootview.findViewById(R.id.login_password);



        final String username = username_edit.getText().toString();
        final String password = password_edit.getText().toString();

        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to LoginSignupActivity.class
            Intent intent = new Intent(getContext(),
                    LoginSignupActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to Welcome.class
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(getContext(),
                        LoginSignupActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }





        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getContext(),"Enter valid",Toast.LENGTH_SHORT).show();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getContext(),"Enter ", Toast.LENGTH_LONG).show();
                    //return;
                }


                /*pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setMessage("Please Wait...");
                pd.setIndeterminate(true);
                pd.setCancelable(false);
                //pd.show();*/
                ParseQuery po = ParseQuery.getQuery("Credentials");
                Log.e("shrey0","here");
                po.findInBackground(new FindCallback() {
                    @Override
                    public void done(List objects, ParseException e) {
                        if( e == null){
                            Log.e("shrey0","yyayyay");
                        }
                        else{
                            Log.e("shrey0",""+e);
                        }
                    }

                    @Override
                    public void done(Object o, Throwable throwable) {
                        Log.e("shrey0","qwertyy"+o.getClass());
                    }
                });

                //pd.show();

                //Toast.makeText(getContext(),"Enter 435 ",Toast.LENGTH_SHORT).show();



            }
        });
        return rootview;
    }

}


