package com.viberlabs.app.smartreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.webkit.WebView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;


/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 1/31/13
 * Time: 9:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class LicensesActivity extends SherlockActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.licenses_activity);

        ((WebView) findViewById(R.id.web_view)).loadUrl("file:///android_asset/licenses.html");
        // Show the Up button in the action bar:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more add_reminder, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpTo(this, new Intent(this,
                        ReminderListActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}