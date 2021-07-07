package com.example.taytsel_app2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Random rnd = new Random(); // для генерации случайных чисед
    Button btn[] = new Button [17]; // кнопки с буквами
    Button btnW[] = new Button [12]; // ячейки с бувами слова
    Drawable wrongfon, fon; // фоны игры
    Button BackLogo; // логотип
    TextView txt[] = new TextView[2]; // Текст "Введите перевод" и перевод слова
    TableLayout tbl, tbll; // таблицы для кнопок с буквами
    String word, usedwords, resultword = ""; // загаданное слово, пройденные слова за игру, полувщиееся слово
    LinearLayout BtnsLayout, WordLayout, ButtonNextLayout, ButtonOkayLayout, TxtLayout, TranslateLayout, StopLearningLayout; // layoutы для элементов игры
    ConstraintLayout mgame; // общий layout
    Typeface tf; // стиль шрифта
    int isLetter, btnNum, wordLength, rndlet, missingCount, knownCount, sCount, countWords,numWord, setType=0, rndGameType; // рандом перменная для случайной расстановки пропусков в слове, номер кнопки, длина слова, случаный id слова, количество пропущенных букв, количество знаемых букв
    // ... счетчик, счетчик слов, номер слова, установление типа игры, переменная для рандомного определения типа игры
    boolean btnisExist; // существует ли кнопка
    Data data = new Data(); // запуск конструктора на классе с данными "Data"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // экран без верхней строки
        startApp(); // старт приложения
    }
    public void startApp() {
        // инициализация
        wrongfon=getResources().getDrawable(R.drawable.fon_game_wrong);
        fon=getResources().getDrawable(R.drawable.fon_game);
                tbl = new TableLayout(getApplicationContext());
                tbll = new TableLayout(getApplicationContext());
                tf = Typeface.createFromAsset(getAssets(), "fonts/gothampro.otf");
                BtnsLayout = findViewById(R.id.lv);
                WordLayout = findViewById(R.id.wordLayout);
                ButtonNextLayout = findViewById(R.id.buttonNextLayout);
                ButtonOkayLayout = findViewById(R.id.buttonOkayLayout);
                BackLogo = findViewById(R.id.BackBtn);
                BackLogo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // при нажатии открывается результат игры
                        finish();
                        openRes();
                    }
                });
                TxtLayout = findViewById(R.id.txtLayout);
                mgame = findViewById(R.id.game);
                TranslateLayout = findViewById(R.id.translateLayout);
                StopLearningLayout = findViewById(R.id.stopLearningLayout);
                // обнуление переменных
                usedwords = "";
                Data.countRightWords=0;
                Data.countWrongWords=0;
                Data.countLearnedWords=0;
                startNewGame(); // старт игры
    }
    public void spawnText()
    {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // параметры layout
        p.weight = 1; // вес для параментров
        // создание текста с просьбой ввести перевод
        txt[0] = new TextView(this);
        txt[0].setText(getString(R.string.typetranslate));
        txt[0].setTypeface(tf);
        txt[0].setTextSize(30);
        txt[0].setTextColor(Color.WHITE);
        txt[0].setGravity(Gravity.CENTER_HORIZONTAL);
        txt[0].setLayoutParams(p);
        // создание текста с переводом
        txt[1] = new TextView(this);
        txt[1].setTextSize(35);
        txt[1].setTypeface(tf);
        txt[1].setTextColor(Color.rgb(0, 87, 75));
        txt[1].setGravity(Gravity.CENTER_HORIZONTAL);
        txt[1].setLayoutParams(p);
        if (rndGameType == 1)
        {
            txt[1].setText(Data.words.get(numWord)); // если тип игры 1, нам нужно перевести английское слово
        }
        else {
            txt[1].setText(Data.ruswords.get(numWord)); // если тип игры 0, нам нужно перевести русское слово
        }
        // добавление на экран
        TxtLayout.addView(txt[0]);
        TranslateLayout.addView(txt[1]);
    }
    public void spawnButtons() { // генерация кнопок
       LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f); // параметры для linear layout
       TableRow.LayoutParams ppp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1.0f); // парамтры для кнопки и таблицы
        for (int j = 0; j < 4; j++) { // создание кнопок в стиле таблицы 4x4
            TableRow tbr = new TableRow(this);
            tbr.setLayoutParams(p);
            for (int i = 0; i < 4; i++) {
                btnNum++;
                btn[btnNum] = new Button(this);
                btn[btnNum].setBackground(this.getResources().getDrawable(R.drawable.custom_button));
                btn[btnNum].setTextColor(Color.WHITE);
                if (rndGameType == 1)
                {
                    btn[btnNum].setText(data.rusletters.get(rnd.nextInt(31))); // если тип игры 1, то разбросаны русские буквы
                }
                else {
                    btn[btnNum].setText(data.letters.get(rnd.nextInt(25))); // если тип игры 0, то разбросаны английские буквы
                }
                btn[btnNum].setTag(0);
                btn[btnNum].setTag("unpressed");
                btn[btnNum].setTypeface(tf);
                btn[btnNum].setLayoutParams(ppp);
                tbr.addView(btn[btnNum]);
                btn[btnNum].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 1; i < 17; i++) {
                            // при нажатии меняется цвет кнопки, и ставится тэг pressed (кнопка нажата), а остальные становятся unpressed
                            btn[i].setBackground(getResources().getDrawable(R.drawable.custom_button));
                            btn[i].setTag("unpressed");
                        }
                        view.setBackground(getResources().getDrawable(R.drawable.pressed_button));
                        view.setTag("pressed");
                    }
                });
            }
            // добавление на layout
            tbl.setLayoutParams(p);
            tbl.addView(tbr);
        }

    }
    public void spawnLetters() // генерация слова
    {
        resultword = ""; // обнуления результата слова
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);// параметры для layour
        p.weight = 1;
        for (int i = 0; i<wordLength; i++){
            // создание ячеек
            btnW[i] = new Button(this);
            btnW[i].setLayoutParams(p);
            btnW[i].setBackgroundColor(Color.rgb(0,255,139));
            btnW[i].setTextColor(Color.WHITE);
            btnW[i].setTypeface(tf);
            btnW[i].setTextSize(30);
            isLetter = rnd.nextInt((2 - 1) + 1) + 1; // генерация типа ячейки рандом
            do {
                rndlet = rnd.nextInt((16 - 1) + 1) + 1;
            } while (btn[rndlet].getTag().equals(1));
            // если ячейка первая то она будет пропуском
            if (i == 0)
            {
                btn[rndlet].setText(String.valueOf(word.charAt(i)));
                btn[rndlet].setTag(1);
                btnW[i].setText("_");
                btnW[i].setTag(0);
            }
            else {
                // если ячейка последняя то она будет буквой
                if (i == wordLength - 1) {
                    btnW[i].setText(String.valueOf(word.charAt(i)));
                    btnW[i].setTag(1);
                    sCount++;
                    knownCount++;
                }
                else
                {
                    // если isLetter - 0, текущая ячейка будет пропуском, если 1 то будет буквой
                    if (isLetter == 1)
                    {
                        btn[rndlet].setText(String.valueOf(word.charAt(i)));
                        btn[rndlet].setTag(1);
                        btnW[i].setText("_");
                        btnW[i].setTag(0);
                        missingCount++;
                    }
                    else
                    {
                        btnW[i].setText(String.valueOf(word.charAt(i)));
                        btnW[i].setTag(1);
                        sCount++;
                    }
                }
            }
            btnW[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // при нажатии на ячейку с буквой...
                    Button b = (Button) view;
                    if (!b.getText().equals("_")) { // если не пропуск, то отменяем действие
                        for (int i = 1; i < 17; i++) {
                            if (btn[i].getTag().equals("pressed")) {
                                btn[i].setTag("unpressed");
                                btn[i].setBackground(getResources().getDrawable(R.drawable.custom_button));
                            }
                            // удаляем лишние кнопки с layout
                            ButtonNextLayout.removeAllViewsInLayout();
                            StopLearningLayout.removeAllViewsInLayout();
                            ButtonOkayLayout.removeAllViewsInLayout();
                            ButtonNextLayout.removeAllViewsInLayout();
                        }
                    }
                    if (view.getTag().equals(3) && !b.getText().equals("_")) //если нажатая кнопка это не пропуск и имеет tag 3 то...
                    {
                        for (int k = 1; k < 17; k++) // запускаем цикл
                        {
                            if (btn[k].getVisibility() == View.INVISIBLE && b.getText().equals(btn[k].getText())) // если кнопка невидима И нажатая буква рава активной кнопке с буквой...
                            {
                                // ... то делаем кнопку видимой и возвращаем пропуск
                                btn[k].setVisibility(View.VISIBLE);
                                b.setText("_");
                                b.setTag(0);
                                sCount--;
                                if(btnisExist) // если кнопка существует
                                {
                                    // удаляем кнопку дальше
                                    ButtonNextLayout.removeAllViewsInLayout();
                                }
                            }
                        }
                    }
                    else { //если нажатая кнопка это пропуск и НЕ имеет tag 3 то...
                        if (view.getTag().equals(0)) {
                            for (int k = 1; k < 17; k++) { // цикл
                                if (btn[k].getTag().equals("pressed")) { // если тэг кнопки k - pressed, то делаем unpressed (если кнопка активна, делаем её неактивной)
                                    btn[k].setVisibility(View.INVISIBLE);
                                    btn[k].setTag("unpressed");
                                    btn[k].setBackground(getResources().getDrawable(R.drawable.custom_button));
                                    b.setText(btn[k].getText());
                                    b.setTag(3);
                                    sCount++;
                                }
                            }
                        }
                    }
                    if (sCount == wordLength) // если количество букв в ячейке равняется длине слова то...
                    {
                        // создаем кнопки: "Далее", "Понятно", "Не хочу учить это слово"
                        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        p.gravity = Gravity.CENTER_HORIZONTAL;
                        final Button btnNext = new Button(getApplicationContext());
                        final Button btnOkay = new Button(getApplicationContext());
                        final Button btnStopLearning = new Button(getApplicationContext());
                        btnNext.setVisibility(View.VISIBLE);
                        btnOkay.setVisibility(View.INVISIBLE);
                        btnStopLearning.setVisibility(View.INVISIBLE);
                        btnNext.setText(getString(R.string.next));
                        btnNext.setStateListAnimator(null);
                        btnNext.setTypeface(tf);
                        btnNext.setLayoutParams(p);
                        btnNext.setBackground(getResources().getDrawable(R.drawable.next_button));
                        btnNext.setTextColor(Color.WHITE);
                        btnOkay.setText(getString(R.string.okay));
                        btnOkay.setTypeface(tf);
                        btnOkay.setStateListAnimator(null);
                        btnOkay.setLayoutParams(p);
                        btnOkay.setBackground(getResources().getDrawable(R.drawable.next_button));
                        btnOkay.setTextColor(Color.WHITE);
                        btnStopLearning.setText(getString(R.string.notlearn));
                        btnStopLearning.setTypeface(tf);
                        btnStopLearning.setLayoutParams(p);
                        btnStopLearning.setBackground(getResources().getDrawable(R.drawable.dont_learn));
                        btnStopLearning.setTextColor(Color.WHITE);
                        btnOkay.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View view) { // при нажатии на Понял
                                                           if (countWords ==5 || Data.words.size() == 0) // если слова закончились то...
                                                           {
                                                               // конец игры, открываем результаты
                                                               finish();
                                                               openRes();
                                                           }
                                                           else { // иначе
                                                               // кнопки исчезают стартует следующая игра со следующим словом
                                                               btnOkay.setVisibility(View.INVISIBLE);
                                                               btnStopLearning.setVisibility(View.INVISIBLE);
                                                               startNewGame();
                                                           }
                                                       }
                                                   });
                        btnNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) { // при нажатии на Далее
                                for (int i = 0; i < wordLength; i++) { // цикл
                                    resultword = resultword + btnW[i].getText(); // составляем слово сделанное игроком
                                }
                                if (rndGameType == 1) { // если тип игры - 1
                                    if (resultword.equals(word)) {  // и если сделанное слово равняется загадонному
                                        usedwords = usedwords + txt[1].getText(); // добавляем слово в список угаданных
                                        countWords++; // увеличиваем переменную на 1
                                        if (Data.learnedLvl.get(numWord) == 4) { // если степень знания равняется 4 (100%)
                                            //то считаем слово полностью выученным, поздравляем игрока и удаляем слово из личного словаря, вместе со всеми его данными
                                            Toast.makeText(MainActivity.this, getString(R.string.congrats), Toast.LENGTH_SHORT).show();
                                            Data.learnedWords++;
                                            Data.words.remove(txt[1].getText());
                                            Data.ruswords.remove(word);
                                            Data.learnedLvl.remove(numWord);
                                            Data.countLearnedWords++;
                                            Data.countRightWords++;
                                            Menu.numberWordsInDict.setText(String.valueOf(Data.words.size()));
                                            Menu.numberLearnedWords.setText(String.valueOf(Data.learnedWords));
                                        } else { //иначе
                                            //увеличиваем степень знания и говорим о том, что слово угаданно верно
                                            Data.learnedLvl.set(numWord, Data.learnedLvl.get(numWord) + 1);
                                            Toast.makeText(MainActivity.this, getString(R.string.right), Toast.LENGTH_SHORT).show();
                                            Data.countRightWords++;

                                        }
                                        if (countWords == 5) { // если слова закончились то...
                                            // конец игры, открываем результаты
                                            finish();
                                            openRes();
                                        } else { // иначе
                                            // кнопки исчезают стартует следующая игра со следующим словом
                                            startNewGame();
                                        }
                                    } else { // если сделанное слово НЕ равняется загадонному

                                        for (int i = 0; i < wordLength; i++) { // цикл
                                            //показываем слово и делаем фон красным
                                            btnW[i].setText(String.valueOf(word.charAt(i)));
                                            btnW[i].setBackgroundColor(Color.rgb(255,101,101));
                                        }
                                        for (int i = 1; i < 17; i++) {
                                            //скрываем все кнопки с буквами
                                            btn[i].setVisibility(View.INVISIBLE);
                                        }
                                        // создаем внешность экрана в стиле ошибки, добавляем кнопки
                                        btnNext.setVisibility(View.INVISIBLE);
                                        btnOkay.setVisibility(View.VISIBLE);
                                        btnStopLearning.setVisibility(View.VISIBLE);
                                        txt[0].setText(getString(R.string.wrong));
                                        Data.countWrongWords++;
                                        Data.countRightWords--;
                                        BackLogo.setEnabled(false);
                                        txt[1].setTextColor(Color.rgb(108, 0, 0));
                                        BackLogo.setBackgroundColor(Color.rgb(152, 0, 0));
                                        mgame.setBackground(wrongfon);
                                        Data.learnedLvl.set(numWord, Data.learnedLvl.get(numWord) - 1);
                                    }
                                } else { // если тип игры - 0
                                    // все тоже самое что и в 303-354 строках, но показывается английское слово а не русское
                                    if (resultword.equals(word)) {
                                        usedwords = usedwords + word;
                                        countWords++;
                                        if (Data.learnedLvl.get(numWord) == 4) {
                                            Toast.makeText(MainActivity.this, getString(R.string.congrats), Toast.LENGTH_SHORT).show();
                                            Data.learnedWords++;
                                            Data.words.remove(word);
                                            Data.learnedLvl.remove(numWord);
                                            Data.ruswords.remove(txt[1].getText());
                                            Data.countLearnedWords++;
                                            Data.countRightWords++;
                                            Menu.numberWordsInDict.setText(String.valueOf(Data.words.size()));
                                            Menu.numberLearnedWords.setText(String.valueOf(Data.learnedWords));
                                        } else {
                                            Data.learnedLvl.set(numWord, Data.learnedLvl.get(numWord) + 1);
                                            Toast.makeText(MainActivity.this, getString(R.string.right), Toast.LENGTH_SHORT).show();
                                            Data.countRightWords++;
                                        }
                                        if (countWords == 5) {
                                            finish();
                                            openRes();
                                        } else {
                                            startNewGame();
                                        }
                                    } else {

                                        for (int i = 0; i < wordLength; i++) {
                                            btnW[i].setText(String.valueOf(word.charAt(i)));
                                            btnW[i].setBackgroundColor(Color.rgb(255,101,101));
                                        }
                                        for (int i = 1; i < 17; i++) {
                                            btn[i].setVisibility(View.INVISIBLE);
                                        }
                                        btnNext.setVisibility(View.INVISIBLE);
                                        btnOkay.setVisibility(View.VISIBLE);
                                        btnStopLearning.setVisibility(View.VISIBLE);
                                        mgame.setBackground(wrongfon);
                                        txt[0].setText(getString(R.string.wrong));
                                        Data.countWrongWords++;
                                        Data.countRightWords--;
                                        BackLogo.setBackgroundColor(Color.rgb(255,101,101));
                                        txt[1].setTextColor(Color.rgb(108, 0, 0));
                                        Data.learnedLvl.set(numWord, Data.learnedLvl.get(numWord) - 1);
                                    }
                                }
                            }
                        });
                        btnStopLearning.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) { // при нажатии на кнопку "Не хочу учить это слово"
                                if (btnStopLearning.getText().equals(getString(R.string.notlearn))) {
                                    // меняется кнопка на "Изучать слово"
                                    btnStopLearning.setText(getString(R.string.learn));
                                    btnStopLearning.setBackground(getResources().getDrawable(R.drawable.stoplearning_button));
                                    btnStopLearning.setTextColor(Color.WHITE);
                                    Toast.makeText(MainActivity.this, getString(R.string.deletedword), Toast.LENGTH_SHORT).show();
                                    if (rndGameType == 1) // если тип игры 1
                                    {
                                        // удаляем слово по переводу
                                        Data.words.remove(txt[1].getText());
                                        Data.ruswords.remove(word);
                                        Data.learnedLvl.remove(numWord);
                                        Menu.numberWordsInDict.setText(String.valueOf(Data.words.size()));
                                    }
                                    else {
                                        // удаляем слово по слову
                                        Data.words.remove(word);
                                        Data.ruswords.remove(txt[1].getText());
                                        Data.learnedLvl.remove(numWord);
                                        Menu.numberWordsInDict.setText(String.valueOf(Data.words.size()));
                                    }
                                }
                                else
                                {
                                    // при нажатии на кнопку "Изучать слово"  кнопка меняется на  "Не хочу учить это слово"
                                    btnStopLearning.setText(getString(R.string.notlearn));
                                    btnStopLearning.setBackground(getResources().getDrawable(R.drawable.dont_learn));
                                    btnStopLearning.setTextColor(Color.WHITE);
                                    Toast.makeText(MainActivity.this, getString(R.string.addedword), Toast.LENGTH_SHORT).show();
                                    if (rndGameType == 1) { // если тип игры 1
                                        // добавляем слово в словарь по слову
                                        Data.words.add(txt[1].getText().toString());
                                        Data.ruswords.add(word);
                                        Data.learnedLvl.add(numWord);
                                        Menu.numberWordsInDict.setText(String.valueOf(Data.words.size()));
                                    }
                                    else // если тип игры 0
                                    {
                                        // добавляем слово в словарь по переводу
                                        Data.words.add(word);
                                        Data.ruswords.add(txt[1].getText().toString());
                                        Data.learnedLvl.add(numWord);
                                        Menu.numberWordsInDict.setText(String.valueOf(Data.words.size()));
                                    }

                                }
                            }
                        });
                        // добавляем элементы на экран
                        StopLearningLayout.addView(btnStopLearning);
                        ButtonNextLayout.addView(btnNext);
                        ButtonOkayLayout.addView(btnOkay);
                    }
                }
            });
            WordLayout.addView(btnW[i]);
        }
    }
    public void startNewGame(){ // старт новой игры
        BackLogo.setEnabled(true);
        mgame.setBackground(fon); // ставим фон
        // обнуляем переменные
        setType = 0;
        numWord = 0;
        sCount=0;
        word = "";
        btnNum=0;
        // удаляем элементы с экрана
        tbl.removeAllViews();
        tbll.removeAllViews();
        BtnsLayout.removeAllViewsInLayout();
        WordLayout.removeAllViewsInLayout();
        ButtonNextLayout.removeAllViewsInLayout();
        ButtonOkayLayout.removeAllViewsInLayout();
        TxtLayout.removeAllViewsInLayout();
        StopLearningLayout.removeAllViewsInLayout();
        BackLogo.setBackgroundColor(Color.rgb(0, 137, 75));
        TranslateLayout.removeAllViewsInLayout();
        // подбираем новое слово
        do {
                numWord = rnd.nextInt(Data.words.size());
                word = Data.words.get(numWord);
        }while(usedwords.contains(word));
        rndGameType = rnd.nextInt(2);
        if (rndGameType == 1) // если тип игры 1
        {
            word = Data.ruswords.get(numWord); // загаданное слово равняется его переводу
        }
        wordLength = word.length(); // переменной присваеваем значение
        spawnButtons(); // генерируем кнопки
        spawnLetters(); // генерируем ячейки с буквами
        spawnText(); // генерируем текст
        BtnsLayout.addView(tbl);
        mgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // нажимая по пустому полю
                for (int k = 1; k < 17; k++) {
                    // все кнопки становятся не активными
                    btn[k].setTag("unpressed");
                    btn[k].setBackground(getResources().getDrawable(R.drawable.custom_button));
                }
            }
        });
    }
    public void openRes(){ // открываем результаты
        for (int i =0; i<Data.learnedLvl.size(); i++){ // цикл
            if (Data.learnedLvl.get(i) < 0) // если степень знания где-либо равняется отрицательному числу
            {
                Data.learnedLvl.set(i, 0); // то делаем это число нулем
            }
        }
        Intent intent = new Intent(this,results.class);
        startActivity(intent);
    }
    protected void onStop() { // закрывается программа
        super.onStop();
        Data.mSave();
    }
    @Override
    public void onBackPressed() { // при нажатии на кнопку назад
        for (int i =0; i<Data.learnedLvl.size(); i++){ // цикл
            if (Data.learnedLvl.get(i) == -1) // если степень знания где-либо равняется отрицательному числу
            {
                Data.learnedLvl.set(i, 0); // то делаем это число нулем
            }
        }
        finish(); // закрываем
        openRes(); // открываем результат
    }
}