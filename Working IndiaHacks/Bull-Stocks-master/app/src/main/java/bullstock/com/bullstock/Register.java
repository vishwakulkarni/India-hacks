package bullstock.com.bullstock;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    Button register_btn;
    EditText username_edit,password_edit,phone_edit;

    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_register, container, false);

        register_btn = (Button) rootview.findViewById(R.id.register_button);
        username_edit = (EditText) rootview.findViewById(R.id.register_username);
        password_edit = (EditText) rootview.findViewById(R.id.register_password);
        phone_edit = (EditText) rootview.findViewById(R.id.register_phone);

        final String username = username_edit.getText().toString();
        final String password = password_edit.getText().toString();
        final String phone = phone_edit.getText().toString();



        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Enter valid",Toast.LENGTH_LONG).show();



                final ProgressDialog pd = new ProgressDialog(getContext());
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setMessage("Please Wait...");
                pd.setIndeterminate(true);
                pd.setCancelable(false);
                //pd.show();


                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        ParseObject testObject = new ParseObject("Credentials");
                        testObject.put("username", "bar123");
                        Log.e("shrey3","dsfh");
                        testObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                Log.e("shrey67","here");
                                if(e == null){
                                    Intent i = new Intent(getContext(),MainActivity.class);
                                    startActivity(i);
                                    getActivity().finish();
                                    Log.e("shrey2","yayayy");
                                }else {
                                    Log.e("shrey1",e+"");
                                }
                            }
                        });
                        //pd.dismiss();
                    }
                };

                mThread.start();


            }
        });

        return rootview;
    }

}
