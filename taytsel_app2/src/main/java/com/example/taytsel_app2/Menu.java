package com.example.taytsel_app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    Button btnStart, btnDict, btnInfo, btnExit; // кнопки: "Старт", "Мой словарь", "Справка", "Выход"
    public static final String KEY_DICTWORDS = "dictwords", KEY_MYWORDS = "mywords", KEY_PROGRESS = "progress", KEY_LEARNED = "learned"; // ключи для сохранения данных о словах в общем и личном словарях, их прогрессе в обучении и о количестве выученных слов
    public static TextView numberWordsInDict, numberLearnedWords; // показывают количество слов в личном словаре и количество выученных слов
    public String Dictwords, Mywords, wordProgress; // хранят данные о словах в общем и личном словарях, их прогрессе
    public int learned; // хранит данные о количестве выученных слов
    public SharedPreferences mSPref; // переменная для сохранения данных
    public static Menu linkIlya; // связка для других активити
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        linkIlya = this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // убирает верхнюю строку
        // инициализация
        mSPref = getPreferences(Context.MODE_PRIVATE);
        btnStart = findViewById(R.id.btnStart);
        btnDict = findViewById(R.id.btnDict);
        numberWordsInDict = findViewById(R.id.wordsindict);
        numberLearnedWords = findViewById(R.id.learnedwords);
        btnInfo = findViewById(R.id.info);
        btnExit = findViewById(R.id.btnExit);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAbout();
            }
        }); // при нажатии открывает About активити
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }); // при нажатии приложение закрывается
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // при нажатии..
                if (Data.words.size()<5){ // проверка сколько слов в словаре
                    Toast.makeText(Menu.this, getString(R.string.errorWords), Toast.LENGTH_SHORT).show(); // ошибка если слов меньше 5
                }
                else {
                    openGame(); // запускает игру, если слов 5 или больше
                }
            }
        });
        btnDict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDict();
            }
        }); // при нажатии открывает словарь
        // обнуление переменных для загрузки новых данных из SharedPreferences
        Dictwords = "";
        Mywords = "";
        wordProgress = "";
        mLoad(); // загрузка данных
        // даем значения о количестве слов в словаре и выученных слов
        numberWordsInDict.setText(String.valueOf(Data.words.size()));
        numberLearnedWords.setText(String.valueOf(learned));
    }
    public void openAbout(){ // открыть справку
        Intent intent = new Intent(this,About.class);
        startActivity(intent);
    }
    public void openGame(){ // запустить игру
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void openDict(){ // открыть словарь
        Intent intent = new Intent(this,DictionaryActivity.class);
        startActivity(intent);
    }
    protected void onStop() { // закрытие приложения
        super.onStop();
        Data.mSave();
    }
    protected void mLoad() { // загрузка данных
        if (mSPref.contains(KEY_LEARNED)) // если переменная хранящая данные хранит ключ KEY_LEARNED (количество выученных слов), то
        {
            learned = mSPref.getInt(KEY_LEARNED, 0); // присваиваем новое значение переменной
        }
        if (mSPref.contains(KEY_PROGRESS)) { // если переменная хранящая данные хранит ключ KEY_PROGRESS (информация о степенях знания слов)
            wordProgress = mSPref.getString(KEY_PROGRESS, ""); // присваиваем новое значение переменной
            if (wordProgress!="") // если переменная пустая
            {
                Data.learnedLvl.clear(); // очищаем список со степенями знаний слов
                for (int i =0; i<wordProgress.length(); i++){ // цикл
                   Data.learnedLvl.add(Integer.valueOf(String.valueOf(wordProgress.charAt(i)))); // присваиваем новые значения элементам в списке
                }
            }
        }
        if (mSPref.contains(KEY_DICTWORDS)) { // если переменная хранящая данные хранит ключ KEY_DICTWORD (количество слов в общем словаре), то
            Dictwords = mSPref.getString(KEY_DICTWORDS, ""); // присваиваем новое значение переменной
            if (Dictwords!="") // если переменная пустая
            {
                Data.dict.clear(); // очищаем общий словарь
                Data.dictrus.clear(); // очищаем общий словарь (перевод)
                String newRusWord="", newEngWord=""; // обнуление переменных для создания слов
                int wordLang = 0; // обнуляем переменную хранящую язык слова (английский/русский)
                for (int i = 0; i < Dictwords.length(); i++) // цикл
                {
                    if (Dictwords.charAt(i) == '-') // если текущий символ - "-"
                    {
                        Data.dict.add(newEngWord); // мы добавляем созданное слово в общий словарь
                        newEngWord=""; // обнуление переменных для создания слов
                        i++; // увеличиваем переменную i
                        wordLang = 1; // присваиваем язык слову
                    }
                    else // если текущий символ - НЕ "-"
                    {
                        if (Dictwords.charAt(i) == '!') // если текущий символ - "!"
                        {
                            Data.dictrus.add(newRusWord); // мы добавляем перевод созданного слова в общий словарь
                            newRusWord=""; // обнуление переменных для создания перевода слова
                            i++; // увеличиваем переменную i
                            wordLang = 0; // присваиваем язык слову
                        }
                    }
                    if (i!=Dictwords.length()) { // если i НЕ равен длине общего словаря
                        switch (wordLang) { // если язык слова...
                            case 0: // английский
                                newEngWord = newEngWord + Dictwords.charAt(i);break; // добавляем в словарь
                            case 1: // русский
                                newRusWord = newRusWord + Dictwords.charAt(i);break; // добавляем перевод в словарь
                        }
                    }
                }
            }
        }
        if (mSPref.contains(KEY_MYWORDS)) { // если переменная хранящая данные хранит ключ KEY_MYWORDS (количество слов в личном словаре), то
            Mywords = mSPref.getString(KEY_MYWORDS, ""); // присваиваем новое значение переменной
            if (Mywords!="") // если переменная пустая
            {
                Data.words.clear(); // очищаем личный словарь
                Data.ruswords.clear(); // очищаем личный словарь (перевод)
                String newRusWord="", newEngWord="";  // обнуление переменных для создания слов
                int wordLang = 0; // обнуляем переменную хранящую язык слова (английский/русский)
                for (int i = 0; i < Mywords.length(); i++) // цикл
                {
                    if (Mywords.charAt(i) == '-') // если текущий символ - "-"
                    {
                        Data.words.add(newEngWord); // мы добавляем созданное слово в личный словарь
                        newEngWord=""; // обнуление переменных для создания слов
                        i++; // увеличиваем переменную i
                        wordLang = 1; // присваиваем язык слову
                    }
                    else // если текущий символ - НЕ "-"
                    {
                        if (Mywords.charAt(i) == '!') // если текущий символ - "!"
                        {
                            Data.ruswords.add(newRusWord); // мы добавляем перевод созданного слова в личный словарь
                            newRusWord=""; // обнуление переменных для создания перевода слова
                            i++; // увеличиваем переменную i
                            wordLang = 0; // присваиваем язык слову
                        }
                    }
                    if (i!=Mywords.length()) { // если i НЕ равен длине личного словаря
                        switch (wordLang) { // если язык слова...
                            case 0: // английский
                                newEngWord = newEngWord + Mywords.charAt(i);break;
                            case 1: // русский
                                newRusWord = newRusWord + Mywords.charAt(i);break;
                        }
                    }
                }
                numberWordsInDict.setText(String.valueOf(Data.words.size())); // обновляем число слов в словаре
            }
                }
            }

        }

