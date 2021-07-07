package com.example.taytsel_app2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class WordsActivity extends AppCompatActivity {
    Button[] btnWord = new Button[200]; // кнопка слова
    EditText search, addWord, addTranslate; // поиск, добавить свое слово, добавить перевод
    Button btnNewWord, BackLogo, BackDict; // кнопка добавить свое слово, логотип, назад в личный словрь
    int countWords; // счетчик
    LinearLayout l; // layout
    ArrayList<String> addedWords = new ArrayList<>(); // лист добавленных слов
    TextView notfound; // ошибка - не найдено
    Typeface tf, tff; // стили шрифтов
    int maxLength = 10; // максимальная длина ввода текста
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // убираем верхнюю строку
        // инициализация
        l = findViewById(R.id.LLWords);
        tf = Typeface.createFromAsset(getAssets(), "fonts/vagfont.otf");
        tff = Typeface.createFromAsset(getAssets(), "fonts/mikado.otf");
        l.removeAllViewsInLayout();
        countWords = 0;
        search = new EditText(this);
        search.setTextColor(Color.BLACK);
        search.setHint("Поиск...");
        search.setHintTextColor(Color.GRAY);
        search.setTypeface(tf);
        BackLogo = new Button(this);
        BackLogo.setBackgroundColor(Color.WHITE);
        BackLogo.setText(getString(R.string.easyenglish));
        BackLogo.setTypeface(tff);
        BackLogo.setTextSize(30);
        BackLogo.setTextColor(Color.rgb(0,255,174));
        BackLogo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) { // при нажатии на логотип...
                                            finish(); // возвращаемся в меню
                                        }
                                    });
        BackDict = new Button(this);
        BackDict.setText(getString(R.string.backdict));
        BackDict.setTypeface(tf);
        BackDict.setBackground(getResources().getDrawable(R.drawable.exit_button));
        BackDict.setTextColor(Color.WHITE);
        BackDict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        search.addTextChangedListener(new TextWatcher() { // TextWatcher для строки поиска
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) { // действие ПОСЛЕ изменения текста
                for (int j = 0; j < Data.dict.size(); j++) { // цикл
                    l.removeView(btnWord[j]); // удаляем все кнопки с layout
                    countWords--; // количество слов обнуляем
                }
                for (int i = 0; i < Data.dict.size(); i++) { // цикл
                    if (!btnWord[i].getText().toString().contains(search.getText())) { // если слово не содержит введого текста в поиске то...
                        // ничего
                    } else { // иначе
                        // удаляем слои
                        l.removeView(notfound);
                        l.removeView(btnNewWord);
                        // добавляем новый
                        l.addView(btnWord[i]);
                        countWords++; // количество слов увеличиваем
                    }
                }
                if (l.getChildCount() == 3 || search.getText().toString().contains("-") || search.getText().toString().equals(" ")) // если нет совпадений
                {
                    if (search.getText().toString().contains("-") || search.getText().toString().equals(" "))
                    {
                        for (int j = 0; j < Data.dict.size(); j++) { // цикл
                            l.removeView(btnWord[j]); // удаляем все кнопки с layout
                            countWords--; // количество слов обнуляем
                            l.removeView(notfound);
                            l.removeView(btnNewWord);
                        }
                    }
                    // создаем текст об ошибке "Ничего не найдено."
                    notfound = new TextView(getApplicationContext());
                    notfound.setText(getString(R.string.nothingfound));
                    notfound.setTypeface(tf);
                    notfound.setTextColor(Color.BLACK);
                    notfound.setTextSize(20);
                    // создаем кнопку "Добавить свое слово"
                    btnNewWord = new Button(getApplicationContext());
                    btnNewWord.setText(getString(R.string.addmyword));
                    btnNewWord.setTextColor(Color.BLUE);
                    btnNewWord.setTextSize(20);
                    btnNewWord.setTypeface(tf);
                    btnNewWord.setBackgroundColor(Color.WHITE);
                    btnNewWord.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { // при нажатии
                            addWordDialog();
                        }
                    });
                    // добавляем на Layout ошибку ничего не найдено и кнопку о добавлении нового слова
                    l.addView(notfound);
                    l.addView(btnNewWord);
                }
            }
        });
        // добавляем на Leyout логотип, кнопку назад и строку поиска
        l.addView(BackLogo);
        l.addView(search);
        l.addView(BackDict);
        if (!Data.isOpened) { // проверка открывался ли до этого словарь
            Data data = new Data(); // запуск конструктора
            Data.isOpened=true;
        }
        for(int i = 0; i<Data.dict.size(); i++){
            // создание кнопок со словами
            btnWord[i] = new Button(this);
            btnWord[i].setText(Data.dict.get(i) + " - " + Data.dictrus.get(i));
            btnWord[i].setTextSize(20);
            btnWord[i].setTypeface(tf);
            btnWord[i].setBackgroundColor(Color.WHITE);
            btnWord[i].setTextColor(Color.BLACK);
            for (int j = 0; j<Data.words.size();j++){ // цикл
                if(Data.words.get(j).equals(Data.dict.get(i))) // если слово есть в личном словаре то...
                {
                    btnWord[i].setTextColor(Color.rgb(41, 255, 140)); // текст - зеленый
                    btnWord[i].setText("✔ " + btnWord[i].getText()); // галочка
                    addedWords.add(Data.dict.get(i)); // добавляем слово в список добавленных слов
                }
            }
            btnWord[i].setTag(i);
            btnWord[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // при нажатии на слово
                    final Button b = (Button) view;
                        if(Data.words.contains(Data.dict.get((int)b.getTag()))){ // если слово уже есть в словаре
                            Toast.makeText(WordsActivity.this, getString(R.string.alreadyexist), Toast.LENGTH_SHORT).show(); // выводим сообщение
                        }
                        else { // иначе
                            // делаем AlertDialog
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(WordsActivity.this);
                            mBuilder.setTitle(getString(R.string.dict))
                                    .setMessage(getString(R.string.addtodict))
                                    .setCancelable(false)
                                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {// при нажатии на НЕТ
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // ничего
                                        }
                                    })
                                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) { // при нажатии на ДА
                                            // слово добавляется в личный словарь, с переводом, и степенью знания 0
                                            Data.words.add(Data.dict.get((int) b.getTag()));
                                            Data.ruswords.add(Data.dictrus.get((int) b.getTag()));
                                            Data.learnedLvl.add(0);
                                            b.setTextColor(Color.rgb(41, 255, 140));
                                            b.setText("✔ " +  b.getText());
                                            Menu.numberWordsInDict.setText(String.valueOf(Data.words.size()));
                                        }
                                    });
                            // создается AlerDialog
                            AlertDialog malert = mBuilder.create();
                            malert.show();

                        }

                }
            });
            l.addView(btnWord[i]); // добавлем на Layout слово
            countWords++; // увеличиваем переменную добавленных слов на layout
        }

    }
    @Override
    public void onBackPressed() { // при нажатии на кнопку назад
        finish();
        Intent intent = new Intent(this,DictionaryActivity.class); // открывается личный словарь
        startActivity(intent);
    }

    @Override
    protected void onStop() { // функция закрытия приложения
        super.onStop();
        Data.mSave();
    }
    public void addWordDialog(){ // функция добавления своего слова
        // AlertDialog для добавления своего слова
        final int v = 0, w = 0;
        final AlertDialog.Builder builder = new AlertDialog.Builder(WordsActivity.this);
        builder.setTitle(getString(R.string.addingword))
                .setMessage(getString(R.string.typeword));
        addWord = new EditText(WordsActivity.this);
        addWord.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        addWord.setTypeface(tf);
        builder.setView(addWord);
        builder.setPositiveButton(getString(R.string.next2), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) { // при нажатии
                int v=0;
                // проверка языка введеного текста
                for (int g = 0; g < Data.letters.size(); g++)
                {
                    if (addWord.getText().toString().contains(Data.letters.get(g)))
                    {
                        v++;
                    }
                }
                if (v==0) // если текст содержит недопустимые символы или символы другого языка
                {
                    v=0;
                    Toast.makeText(WordsActivity.this, getString(R.string.errorWord), Toast.LENGTH_SHORT).show(); // ошибка
                    dialogInterface.dismiss(); // закрытие AlertDialog
                    addWordDialog(); // открываем окно заново
                }
                else { // иначе
                    // новый AlertDialog для добавления перевода к слову
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(WordsActivity.this);
                    builder2.setTitle(getString(R.string.addingword))
                            .setMessage(getString(R.string.typetranslate));
                    addTranslate = new EditText(WordsActivity.this);
                    addTranslate.setTypeface(tf);
                    addTranslate.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                    builder2.setView(addTranslate);
                    builder2.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) { // при нажатии
                            int w=0, j =0;
                            // проверка языка введеного текста
                            for (int g = 0; g < Data.rusletters.size(); g++) {
                                if (addTranslate.getText().toString().contains(Data.rusletters.get(g))) {
                                    w++;
                                }
                            }
                            if (w == 0) { // если текст содержит недопустимые символы или символы другого языка
                                Toast.makeText(WordsActivity.this, getString(R.string.errorTranslate), Toast.LENGTH_SHORT).show(); // ошибка
                                dialogInterface.dismiss(); // закрытие AlertDialog
                                addWordDialog(); // открываем окно заново
                            } else { // иначе
                                for (int i = 0; i < Data.dict.size(); i++){
                                    if (Data.dict.get(i).equals(addWord.getText().toString()) && Data.dictrus.get(i).equals(addTranslate.getText().toString())) // если слово уже существует
                                    {
                                        Toast.makeText(WordsActivity.this, getString(R.string.errorAlreadyExist), Toast.LENGTH_SHORT).show(); // ошибка, что слово уже существует
                                        dialogInterface.dismiss(); // закрытие AlertDialog
                                        j++;
                                        addWordDialog(); // открываем окно заново
                                    }
                                }
                                if (j == 0) {
                                    Data.dict.add(addWord.getText().toString()); // добавляем слово в спиоск
                                    Data.dictrus.add(addTranslate.getText().toString()); // добавляем перевод слова в список
                                    Toast.makeText(WordsActivity.this, getString(R.string.successadd), Toast.LENGTH_SHORT).show(); // выводим сообщение
                                    // обновляем Activity
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                    // создаем AlertDialog
                    AlertDialog ad = builder2.create();
                    ad.show();
                }

            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) { // при нажатии
                dialogInterface.dismiss(); // закрывается
            }
        });
        // создаем AlertDialog
        AlertDialog ad = builder.create();
        ad.show();
    }
}