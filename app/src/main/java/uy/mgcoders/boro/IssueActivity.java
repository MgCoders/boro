package uy.mgcoders.boro;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import uy.mgcoders.boro.objects.Issue;


public class IssueActivity extends ActionBarActivity {

    public TextView name;
    public TextView summary;
    public TextView state;
    public TextView description;
    private Toolbar toolbar;
    private Issue mIssue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        mIssue = (Issue) b.getSerializable("selectedIssue");

        name = (TextView) findViewById(R.id.issue_name);
        summary = (TextView) findViewById(R.id.issue_summary);
        state = (TextView) findViewById(R.id.issue_state);
        description = (TextView) findViewById(R.id.issue_description);

        name.setText(mIssue.getName());
        summary.setText(mIssue.getSummary());
        state.setText(mIssue.getState());
        description.setText(mIssue.getDescription());



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_issue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
