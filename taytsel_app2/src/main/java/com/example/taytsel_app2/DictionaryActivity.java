package com.example.taytsel_app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DictionaryActivity extends AppCompatActivity {
Button[] btnWord = new Button[200]; // кнопка со словом и его переводом
EditText search; // строка поиска
Button btnAddWords, BackLogo; // кнопка "Добавить слова" и логотип
    Typeface tf, tff; // стили шрифтов
    LinearLayout l; // layout
    TextView notfound; // при несовпадении запросов ошибка о том, что ничего не найдено
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // экран без верхней строки
        // инициализация
        tf = Typeface.createFromAsset(getAssets(), "fonts/vagfont.otf"); // шрифт VAG
        tff = Typeface.createFromAsset(getAssets(), "fonts/mikado.otf"); // шрифт Mikado
        l = findViewById(R.id.LLWords); // layout
        l.removeAllViewsInLayout();  // удаление всех элементов из layout
        search = new EditText(this); // создается новая строка
        BackLogo = new Button(this); // создается логотип
        BackLogo.setBackgroundColor(Color.WHITE); // фоновый цвет
        BackLogo.setText(getString(R.string.easyenglish)); // текст
        BackLogo.setTypeface(tff); // стиль шрифта
        BackLogo.setTextSize(30); // размер текста
        BackLogo.setTextColor(Color.rgb(0,255,174)); // цвет текста
        BackLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }); // действие при нажатии (выход из Activity)
        search.setTextColor(Color.BLACK); // цвет текста для поисковой строки
        search.setHint("Поиск...");
        search.setHintTextColor(Color.GRAY);
        search.setTypeface(tf);
        search.addTextChangedListener(new TextWatcher() { // TextWatcher для поисковой строки
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                for (int j = 0; j<Data.words.size(); j++)
                {
                    l.removeView(btnWord[j]); // цикл на удаление всех кнопок слов
                }
                for (int i = 0; i<Data.words.size(); i++){
                    if(!btnWord[i].getText().toString().contains(search.getText())) // если слово [i] НЕ содержит то, что написано в поисковой строке то...
                    {
                        // ничего
                    }
                    else
                    {
                        l.removeView(notfound); // удаление ошибки о том, что ничего не найдено
                        l.addView(btnWord[i]); // добавление слова [i] на список
                    }
                }
                if (l.getChildCount() == 3 || search.getText().toString().contains("-") || search.getText().toString().equals(" ")) {
                    // создаем ошибку "не найдено"
                    if (search.getText().toString().contains("-") || search.getText().toString().equals(" ")) {
                        for (int j = 0; j < Data.words.size(); j++) { // цикл
                            l.removeView(btnWord[j]); // удаляем все кнопки с layout
                        }
                        l.removeView(notfound);
                    }
                    notfound = new TextView(getApplicationContext());
                    notfound.setText(getString(R.string.nothingfound));
                    notfound.setTypeface(tf);
                    notfound.setTextColor(Color.BLACK);
                    notfound.setTextSize(20);
                    l.addView(notfound);
                }
            }
        });
        l.addView(BackLogo); // добавляем логотип на экран
        l.addView(search); // добавляем строку поиска на экран
        if (!Data.isOpened) { // если словарь еще не открывался то проигрывает конструктор
            Data data = new Data();
            Data.isOpened=true;
        }
        // создаем кнопку "Добавить слова"
        btnAddWords = new Button(this);
        btnAddWords.setText(getString(R.string.addwords));
        btnAddWords.setTypeface(tf);
        btnAddWords.setBackground(getResources().getDrawable(R.drawable.next_button));
        btnAddWords.setTextColor(Color.WHITE);
        btnAddWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDict();
            }
        });
        l.addView(btnAddWords);
        for(int i = 0; i<Data.words.size(); i++){
            //создаем строчки слов и их переводов (кнопки)
            final int a = i;
            btnWord[i] = new Button(this);
            btnWord[i].setText(Data.words.get(i) + " - " + Data.ruswords.get(i));
            btnWord[i].setTextSize(20);
            btnWord[i].setTypeface(tf);
            btnWord[i].setBackgroundColor(Color.WHITE);
            btnWord[i].setTextColor(Color.BLACK);
            btnWord[i].setTag(i);
            btnWord[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Data.ClickedWord = a; // задаем id кликнутого слова
                    openInfo(); // открываем информацию о слове
                }
            });
            l.addView(btnWord[i]); // добавляем на экран слово и перевод (кнопку)
        }

    }
    // функция открытия общего словаря
    public void openDict(){
        Intent intent = new Intent(this,WordsActivity.class);
        startActivity(intent);
        finish();
    }
    // функция открытия ифнормации о слове
    public void openInfo(){
        Intent intent = new Intent(this,InfoAboutWord.class);
        startActivity(intent);
        finish();
    }
    // функция закрытия приложения
    protected void onStop() {
        super.onStop();
        Data.mSave();
    }

}
