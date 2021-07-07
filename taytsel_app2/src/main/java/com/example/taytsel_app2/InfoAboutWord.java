package com.example.taytsel_app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InfoAboutWord extends AppCompatActivity {
    TextView word, rusword, LearnedAt; // слово, перевод слова, на сколько процентов выучено
    Button btnBack, btnDelete, btnLearnAgain; // кнопки "Назад", "Удалить", "Учить заново"
    int ThisWordLearnedLvl; // степень знания данного слова
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_about_word);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // экран без верхней строки
        // инициализация
        word = findViewById(R.id.word);
        rusword = findViewById(R.id.rusword);
        btnBack = findViewById(R.id.Back);
        LearnedAt = findViewById(R.id.LearnedAt);
        btnDelete = findViewById(R.id.Delete);
        btnLearnAgain = findViewById(R.id.LearnAgain);
        ThisWordLearnedLvl = Data.learnedLvl.get(Data.ClickedWord);
        btnLearnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // при нажатии обнуляется степень знания слова, меняется текст, пропадает кнопка
                Data.learnedLvl.set(Data.ClickedWord,0);
                LearnedAt.setText(getString(R.string.learned0));
                btnLearnAgain.setVisibility(View.INVISIBLE);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // при нажатии удаляется слово, его перевод, степень знания, меняется число слов в личном словаре, закрывается окно
                    Data.words.remove(Data.words.get(Data.ClickedWord));
                    Data.ruswords.remove(Data.ruswords.get(Data.ClickedWord));
                    Data.learnedLvl.remove(Data.learnedLvl.get(Data.ClickedWord));
                    Menu.numberWordsInDict.setText(String.valueOf(Data.words.size()));
                    Toast.makeText(InfoAboutWord.this, getString(R.string.deletedword), Toast.LENGTH_SHORT).show();
                    finish();
                Intent intent = new Intent(getApplicationContext(),DictionaryActivity.class);
                startActivity(intent);
                }
            });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // при нажатии закрывается окно и открывается словарь
                finish();
                Intent intent = new Intent(getApplicationContext(),DictionaryActivity.class);
                startActivity(intent);
            }
        });
        switch (ThisWordLearnedLvl)
        {
            // задается текст в зависимости от степени знания слова
            case 0: LearnedAt.setText(getString(R.string.learned0));btnLearnAgain.setVisibility(View.INVISIBLE);break;
            case 1: LearnedAt.setText(getString(R.string.learned25));break;
            case 2: LearnedAt.setText(getString(R.string.learned50));break;
            case 3: LearnedAt.setText(getString(R.string.learned75));break;
            case 4: LearnedAt.setText(getString(R.string.learned100));break;
        }
        word.setText(Data.words.get(Data.ClickedWord)); // показывается слово
        rusword.setText(Data.ruswords.get(Data.ClickedWord)); // показывается перевод
    }
    protected void onStop() {
        super.onStop();
        Data.mSave();
    }
    public void onBackPressed() { // при нажатии на кнопку назад
        //закрывается окно и открывается словарь
        finish();
        Intent intent = new Intent(getApplicationContext(),DictionaryActivity.class);
        startActivity(intent);
    }
}
