package com.zewei.zewei.ui.timing;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.zewei.zewei.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Stardust on 2018/2/24.
 */
@EActivity(R.layout.activity_timing)
public class TimingActivity extends AppCompatActivity {

    @ViewById(R.id.progress)
    DonutProgress mProgress;

    @ViewById(R.id.time)
    TextView mTime;

    private long mStartTime;
    private long mEndTime;

    @AfterViews
    void setupViews() {
        mStartTime = System.currentTimeMillis();
        mEndTime = System.currentTimeMillis() + 10 * 60 * 1000;
        startTiming();
    }

    private void startTiming() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateTime();
                    }
                });
            }
        }, 0, 500);
    }

    private void updateTime() {
        long total = mEndTime - mStartTime;
        long remaining = mEndTime - System.currentTimeMillis();
        float progress = (float) (total - remaining) * 100 / total;
        long remainingSeconds = remaining / 1000;
        long remainingMinutes = remainingSeconds / 60;
        remainingSeconds = remainingSeconds % 60;
        mTime.setText(format(remainingMinutes) + ":" + format(remainingSeconds));
        mProgress.setProgress(progress);

    }

    private String format(long time) {
        if (time < 10) {
            return "0" + time;
        }
        return String.valueOf(time);
    }


}
