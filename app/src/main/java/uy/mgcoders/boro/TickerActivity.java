package uy.mgcoders.boro;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import uy.mgcoders.boro.comm.ApiClient;
import uy.mgcoders.boro.exp.BoroException;
import uy.mgcoders.boro.objects.Issue;
import uy.mgcoders.boro.objects.WorkItem;
import uy.mgcoders.boro.objects.WorkItemTypes;
import uy.mgcoders.boro.objects.WorkType;


public class TickerActivity extends ActionBarActivity {

    private Button mStartPause;
    private Button mStop;
    private Chronometer mTicker;
    private Issue mIssue;
    private long timeWhenStopped;
    private Spinner mWorkTypesSpinner;
    private List<WorkType> mWorkTypes = new ArrayList<>();
    private TextView mDescription;
    private TextView mProject;
    private TextView mIssueName;
    private Button mRegister;
    private RegisterWorkTask mregiRegisterWorkTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker);

        Bundle b = getIntent().getExtras();
        mIssue = (Issue) b.getSerializable("selectedIssue");
        WorkItemTypes types = (WorkItemTypes) b.getSerializable("workTypes");
        if (types != null) mWorkTypes.addAll(types.getWorkTypes());


        mStartPause = (Button) findViewById(R.id.btnStart);
        mStop = (Button) findViewById(R.id.btnStop);
        mTicker = (Chronometer) findViewById(R.id.chronometer);
        mWorkTypesSpinner = (Spinner) findViewById(R.id.spinnerWorkTypes);
        mDescription = (TextView) findViewById(R.id.textWorkDescription);
        mProject = (TextView) findViewById(R.id.textWorkProject);
        mIssueName = (TextView) findViewById(R.id.textWorkIssue);
        mRegister = (Button) findViewById(R.id.btnWorkRegister);

        mStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartPause.getText().equals(getString(R.string.start))) {
                    mTicker.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    mTicker.start();
                    mStartPause.setText(getString(R.string.pause));
                } else if (mStartPause.getText().equals(getString(R.string.pause))) {
                    mTicker.stop();
                    timeWhenStopped = mTicker.getBase() - SystemClock.elapsedRealtime();
                    mStartPause.setText(getString(R.string.start));
                }
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTicker.stop();
                mTicker.setBase(SystemClock.elapsedRealtime());
                timeWhenStopped = 0;
            }
        });

        mWorkTypesSpinner.setAdapter(new ArrayAdapter<WorkType>(TickerActivity.this, android.R.layout.simple_spinner_dropdown_item, mWorkTypes) {

        });


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegisterWork();
            }
        });

        mStartPause.setText(R.string.pause);
        mTicker.start();
        mProject.setText(mIssue.getProjectShortName());
        mIssueName.setText(mIssue.getName());


    }

    private void attemptRegisterWork() {

        mTicker.stop();
        timeWhenStopped = mTicker.getBase() - SystemClock.elapsedRealtime();

        WorkItem w = new WorkItem();
        w.setDate(new Date());
        w.setDescription(mDescription.getText().toString());
        w.setWorkType((WorkType) mWorkTypesSpinner.getSelectedItem());

        w.setDuration(TimeUnit.MILLISECONDS.toMinutes(timeWhenStopped));

        if (mregiRegisterWorkTask == null)
            mregiRegisterWorkTask = new RegisterWorkTask(w, mIssue.getId());
        mregiRegisterWorkTask.execute();

        Log.v("WORK", w.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ticker, menu);
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
     * Represents an asynchronous task to register work.
     */
    public class RegisterWorkTask extends AsyncTask<Void, Void, Boolean> {

        private final WorkItem mWork;
        private final String mIssueId;

        RegisterWorkTask(WorkItem work, String issueId) {
            mWork = work;
            mIssueId = issueId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            ApiClient client = ApiClient.getInstance();
            try {
                return client.registerWork(mWork, mIssueId);
            } catch (BoroException e) {
                Log.v("REGISTER_WORK", e.getLocalizedMessage(), e);
                return false;
            }


        }

        @Override
        protected void onPostExecute(Boolean ok) {
            if (ok)
                Toast.makeText(TickerActivity.this, "GOOD!", Toast.LENGTH_LONG);
            else Toast.makeText(TickerActivity.this, "NAAA!", Toast.LENGTH_LONG);
        }

        @Override
        protected void onCancelled() {
            mregiRegisterWorkTask = null;
        }
    }


}
