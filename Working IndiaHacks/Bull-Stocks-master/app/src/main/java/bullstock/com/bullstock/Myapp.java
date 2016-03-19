package bullstock.com.bullstock;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by shrey on 09-01-2016.
 */
public class Myapp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Parse.enableLocalDatastore(this);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this,"DBiYlNr4nkN1izDOfKRE2pqt4W4p4mZFCbmPTxey","JTt7UbmDp4TKMOeYUZcdelrhjl6YlvDmUxR7aOBK");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL,true);

        ParseUser cu = ParseUser.getCurrentUser();
        Log.e("shreuser",cu+"");



    }

}
