package com.example.keepingscore.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.keepingscore.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private CountDownTimer countdownTimer;

    private Button button;
    private Button timebutton;
    private TextView score_text;
    private TextView high_score_overall;

    private int highscore;
    private int timesPressed;



    public TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        button = root.findViewById(R.id.fast_button);
        timebutton = root.findViewById(R.id.timer_button);
        score_text = root.findViewById(R.id.scoretext);

        countdownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                textView.setText("The Final Countdown " + millisUntilFinished/1000);
            }

            public void onFinish() {
                timebutton.setEnabled(true);
                button.setEnabled(false);
                textView.setText("STOP!");

                if (timesPressed > highscore)
                {
                    highscore = timesPressed;
                    textView.setText("The high score is " + highscore);
                }
                timesPressed = 0;
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                int defaultValue = getResources().getInteger(R.integer.big_high_score_dash);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getString(R.string.high_score2), highscore);
                editor.commit();
            }
        };

        timebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(true);
                timebutton.setEnabled(false);
                countdownTimer.start();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//                int defaultValue = getResources().getInteger(R.integer.big_high_score_dash);
//                timesPressed = sharedPref.getInt(getString(R.string.reset_score), defaultValue);

                timesPressed = timesPressed + 1;

//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putInt(getString(R.string.saved_button_press_count_key), timesPressed);
//                editor.commit();

                score_text.setText("Button has been pressed " + Integer.toString(timesPressed) + " times!");
            }
        });

        return root;
    }
}