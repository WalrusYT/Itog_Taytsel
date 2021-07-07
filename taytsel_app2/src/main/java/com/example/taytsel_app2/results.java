package com.example.taytsel_app2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class results extends AppCompatActivity {
TextView rightWords, wrongWords, learnedWords;
    Drawable res_bad, res_good, res_super;
    int countRight, countWrong, countLearned;
    Button btnMenu;
    ConstraintLayout mres;
    TextView mark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // инициализация
        setContentView(R.layout.activity_results);
        rightWords = findViewById(R.id.rightwords);
        wrongWords = findViewById(R.id.wrongwords);
        learnedWords = findViewById(R.id.lrndwords);
        mark = findViewById(R.id.mark);
        btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mres = findViewById(R.id.res);
        res_bad=getResources().getDrawable(R.drawable.res_bad);
        res_good=getResources().getDrawable(R.drawable.res_good);
        res_super = getResources().getDrawable(R.drawable.res_super);
        countRight = Data.countRightWords;
        countWrong = Data.countWrongWords;
        countLearned = Data.countLearnedWords;
        if (countRight < 0)
        {
            rightWords.setText("0");
        }
        else {
            rightWords.setText(String.valueOf(countRight));
        }
        wrongWords.setText(String.valueOf(countWrong));
        learnedWords.setText(String.valueOf(countLearned));
        if (countWrong >= countRight/2){ // если количество неправильно отгаданых слов больше половины правильных то...
            wrongWords.setTextColor(Color.rgb(244, 67, 54)); // цвет текста красный
            rightWords.setTextColor(Color.rgb(244, 67, 54)); // цвет текста красный
            mres.setBackground(res_bad); // фон для плохого резултьтата
            mark.setText(getString(R.string.bad)); // текст - ПЛОХО
            mark.setTextColor(Color.rgb(244, 67, 54)); // цвет текста красный
        }
        else // иначе...
        {
            if (countWrong < countRight/2) // если количество неправильно отгаданых слов меньше половины правильных то...
            {
                if (countWrong <= countRight/10) // если количество неправильно отгаданых слов меньше или равно 1/10 правильных то...
                {
                    wrongWords.setTextColor(Color.rgb(121, 240, 0)); // цвет текста зеленый
                    rightWords.setTextColor(Color.rgb(121, 240, 0)); // цвет текста зеленый
                    mres.setBackground(res_super); // фон для отличного результата
                    mark.setText(getString(R.string.best)); // текст - ОТЛИЧНО
                    mark.setTextColor(Color.rgb(255, 152, 0)); // цвет текста золотой
                }
                else // если количество неправильно отгаданых слов больше 1/10 правильных то...
                {
                    wrongWords.setTextColor(Color.rgb(66, 186, 248)); // цвет текста синий
                    rightWords.setTextColor(Color.rgb(66, 186, 248)); // цвет текста синий
                    mres.setBackground(res_good); // фон для хорошего результата
                    mark.setText(getString(R.string.good)); // текст - ХОРОШО
                    mark.setTextColor(Color.rgb(33, 150, 243)); // цвет текста синий
                }
            }
        }

        }
    protected void onStop() { // функция закрытия приложения
        super.onStop();
        Data.mSave();
    }
    }
