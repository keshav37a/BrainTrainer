package com.example.keshav.braintrainer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton, button0, button1, button2, button3, playAgainButton;
    int num1, num2, score = 0, totalQuestionsAttempted=0;
    Random random;
    ConstraintLayout gameLayout;
    TextView sumTextView, correctIncorrect, scoreTextView, timerTextView;
    ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goButton = findViewById(R.id.goButton);
        gameLayout = findViewById(R.id.gameLayout);
        mediaPlayer = mediaPlayer.create(this, R.raw.joke);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        playAgainButton = findViewById(R.id.playAgainButton);
        timerTextView = findViewById(R.id.timerTextView);
        goButton.setVisibility(View.VISIBLE);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goButton.setVisibility(View.INVISIBLE);
                gameLayout.setVisibility(View.VISIBLE);
                mediaPlayer.start();
                startTimer();
                nextQuestion();
            }
        });

    }
    public void playAgain(View view)
    {
        score = 0;
        totalQuestionsAttempted = 0;
        timerTextView.setText("10s");
        scoreTextView.setText(Integer.toString(score) + "/" +Integer.toString(totalQuestionsAttempted));
        startTimer();
        nextQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
    }

    public void startTimer()
    {
        CountDownTimer countDownTimer = new CountDownTimer(30100, 1000 ) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf((int)millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {
                totalQuestionsAttempted++;
                correctIncorrect.setText("Done");
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void nextQuestion()
    {
        random = new Random();
        num1 = random.nextInt(101);
        num2 = random.nextInt(101);
        sumTextView = findViewById(R.id.sumTextView);
        sumTextView.setText(Integer.toString(num1) + " + " + Integer.toString(num2));
        correctIncorrect = findViewById(R.id.correctIncorrect);
        scoreTextView = findViewById(R.id.scoreTextView);

        locationOfCorrectAnswer = random.nextInt(4);
        int correctAnswer = num1 + num2;

        for(int i=0; i<4; i++)
        {
            if(i == locationOfCorrectAnswer) {
                answers.add(i, correctAnswer);
            }
            else
            {
                int wrongAnswer =  random.nextInt(202);
                while(wrongAnswer == correctAnswer)
                {
                    wrongAnswer = random.nextInt(202);
                }
                answers.add(i,wrongAnswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
        answers.clear();
    }
    public void chooseAnswer(View view)
    {
        Log.i("Tag:", view.getTag().toString());
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer)))
        {
            Log.i("Correct", "Good Job!");
            correctIncorrect.setText("Correct!");
            score++;
            totalQuestionsAttempted++;
            scoreTextView.setText(Integer.toString(score) + "/" +Integer.toString(totalQuestionsAttempted));
            nextQuestion();
        }
        else
        {
            Log.i("Incorrect", "Try Again");
            correctIncorrect.setText("Incorrect!");
            totalQuestionsAttempted++;
            scoreTextView.setText(Integer.toString(score) + "/" +Integer.toString(totalQuestionsAttempted));
            nextQuestion();
        }
    }
}
