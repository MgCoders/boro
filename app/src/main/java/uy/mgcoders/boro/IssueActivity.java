package uy.mgcoders.boro;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import uy.mgcoders.boro.comm.ApiClient;
import uy.mgcoders.boro.exp.BoroException;
import uy.mgcoders.boro.objects.Issue;
import uy.mgcoders.boro.objects.WorkItemTypes;


public class IssueActivity extends ActionBarActivity {

    public TextView name;
    public TextView summary;
    public TextView state;
    public TextView description;
    private Toolbar toolbar;
    private Issue mIssue;
    private Button mClose;
    private Button mRegisterTime;
    private WorkItemTypes mTypes;
    private WorkTypesTask mWorkTypesTask;

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

        mClose = (Button) findViewById(R.id.btnClose);
        mRegisterTime = (Button) findViewById(R.id.btnRegisterTime);

        name.setText(mIssue.getName());
        summary.setText(mIssue.getSummary());
        state.setText(mIssue.getState());
        description.setText(mIssue.getDescription());

        mRegisterTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mTypes == null)
                    Toast.makeText(IssueActivity.this, getString(R.string.timetrack_disabled), Toast.LENGTH_LONG);
                else {
                    Intent intent = new Intent(IssueActivity.this, TickerActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("workTypes", mTypes);
                    b.putSerializable("selectedIssue", mIssue); //TODO: no me gusta mucho traer el issue así.
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });

        attemptRetreiveWorkTypes(); //Async retreive work types.



    }

    private void attemptRetreiveWorkTypes() {
        if (mWorkTypesTask == null) {
            mWorkTypesTask = new WorkTypesTask(mIssue.getProjectShortName());
            mWorkTypesTask.execute();
        }
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

    /**
     * Represents an asynchronous task to retreive project's work types.
     */
    public class WorkTypesTask extends AsyncTask<Void, Void, WorkItemTypes> {

        private final String mProjectId;

        WorkTypesTask(String projectId) {
            mProjectId = projectId;
        }

        @Override
        protected WorkItemTypes doInBackground(Void... params) {

            ApiClient client = ApiClient.getInstance();
            try {
                return client.getWorkTypes(mProjectId);
            } catch (BoroException e) {
                Log.v("GET_TYPES", e.getLocalizedMessage(), e);
                return null;
            }


        }

        @Override
        protected void onPostExecute(WorkItemTypes types) {
            mTypes = types;
        }

        @Override
        protected void onCancelled() {
            mWorkTypesTask = null;
        }
    }
}
