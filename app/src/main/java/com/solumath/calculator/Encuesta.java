package com.solumath.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import androidx.appcompat.widget.Toolbar;

import com.mrntlu.toastie.Toastie;


public class Encuesta extends AppCompatActivity {

    Toolbar toolbar ;
    ImageButton settings,next_button,true_button,false_button;
    private Button btnSetting;

    private int[] questions = {R.string.q1, R.string.q2, R.string.q3, R.string.q4, R.string.q5};
    private boolean[] answers = {true, true, false, false, true};

    private int curQuestionIdx = 0;

    private TextView txtQuestion;

    private Button btnNext;

    private Button btnTrue;

    private Button btnFalse;
    private TextView timerText, quickMathQuestion, quickMathScore, winningMessage,stopwatchText;
    private Chronometer chronometer;

    private Button postScore;

    private int score = 0;
    private CountDownTimer countDownTimer;
    private Chronometer scoreChronometer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        settings = findViewById(R.id.setting_button);
        next_button = findViewById(R.id.next_button);
        scoreChronometer = new Chronometer(this);
        scoreChronometer.setBase(SystemClock.elapsedRealtime());
        scoreChronometer.start();


        chronometer = findViewById(R.id.cronometro);
        chronometer.start();

        settings.setOnClickListener(view -> {
            Intent i = new Intent(Encuesta.this, SettingActivity.class);
            startActivityForResult(i, 0);
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        // You can also use Context initialization
        txtQuestion = (TextView) findViewById(R.id.question_text_view);

        txtQuestion.setText(ObtieneRandom(questions));




        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curQuestionIdx = (curQuestionIdx + 1) % questions.length;
                nextQuestion();
            }
        });

        true_button = (ImageButton) findViewById(R.id.true_button);
        true_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                reportAnswerEvt("true");
                 postScore();
            }
        });


        false_button = (ImageButton) findViewById(R.id.false_button);
        false_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                reportAnswerEvt("false");

            }
        });


    }

    private void nextQuestion() {
        txtQuestion.setText(questions[curQuestionIdx]);
    }

    private boolean checkAnswer(boolean answer) {
        String q = txtQuestion.getText().toString().trim();

        if (answer == answers[curQuestionIdx]) {
            score = score + 20;
            Toastie.success(this,"Correcto",Toast.LENGTH_SHORT).show();
            curQuestionIdx = (curQuestionIdx + 1) % questions.length;
            nextQuestion();


        } else {
            Toastie.info(this,"Incorrecto",Toast.LENGTH_SHORT).show();


            nextQuestion();


        }
        return answers[curQuestionIdx];
    }

    private void reportAnswerEvt(String answer) {
        // TODO: Report a custom event.
        // Event Name: Answer
        // Event Parameters:
        //  -- question: String
        //  -- answer: String
        //  -- answerTime: String

        // Initialize parameters.
        Bundle bundle = new Bundle();
        bundle.putString("question", txtQuestion.getText().toString().trim());
        bundle.putString("answer", answer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        bundle.putString("answerTime", sdf.format(new Date()));

        // Report a custom event.
      ;
    }

    private void postScore() {
        // TODO: Report the score by using the SUBMITSCORE event.
        // Initialize parameters.
        Bundle bundle = new Bundle();

    }
    public int ObtieneRandom(int [] array)
    {
        Random random = new Random();
        return array[random.nextInt(array.length-1)];
    }

}