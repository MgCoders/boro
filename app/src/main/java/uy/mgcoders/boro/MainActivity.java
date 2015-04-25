package uy.mgcoders.boro;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import uy.mgcoders.boro.comm.ApiClient;
import uy.mgcoders.boro.exp.BoroException;
import uy.mgcoders.boro.objects.Issue;
import uy.mgcoders.boro.util.IssueAdapter;
import uy.mgcoders.boro.util.Query;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private IssueAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private UserIssuesTask mIssuesTask;
    private List<Issue> mIssues = new ArrayList<>();

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new IssueAdapter(mIssues);
        mRecyclerView.setAdapter(mAdapter);


        attemptRetreiveIssues(new Query(Query.ASSIGNED_TO_ME));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void attemptRetreiveIssues(Query query) {
        if (mIssuesTask == null) {
            mIssuesTask = new UserIssuesTask(query);
            mIssuesTask.execute();
        }
    }

    /**
     * Represents an asynchronous task to retreive user's issues.
     */
    public class UserIssuesTask extends AsyncTask<Void, Void, List<Issue>> {

        private final Query mQuery;

        UserIssuesTask(Query query) {
            mQuery = query;
        }

        @Override
        protected List<Issue> doInBackground(Void... params) {

            ApiClient client = ApiClient.getInstance();
            try {
                return client.getTasks(mQuery);
            } catch (BoroException e) {
                Log.v("GET_ISSUES", e.getLocalizedMessage(), e);
                return new ArrayList<>();
            }


        }

        @Override
        protected void onPostExecute(List<Issue> issues) {
            mAdapter.updateList(issues);
        }

        @Override
        protected void onCancelled() {
            mIssuesTask = null;
        }
    }
}
