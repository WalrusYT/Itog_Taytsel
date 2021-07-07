package com.example.taytsel_app2;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

public class Data {
    static ArrayList<String> letters = new ArrayList<>(); // список для английского алфавита
    static ArrayList<String> rusletters = new ArrayList<>();// список для русского алфавита
    static ArrayList<String> words = new ArrayList<String>(Arrays.asList("dog","cat","boat")); // список начальных английских слов
    static ArrayList<String> ruswords = new ArrayList<String>(Arrays.asList("собака","кошка","лодка")); // список переводов начальных английских слов
    static ArrayList<Integer> learnedLvl = new ArrayList<Integer> (Arrays.asList(0,0,0)); // список степени изученности слов
    static ArrayList<String> dict = new ArrayList<String>(Arrays.asList("dog","cat","boat","hat","bag","apple","water","phone","pen","pencil","paper","book","table","day", "thing","tell","down","back","child","here","work","old","part","leave","life","great","case","woman","seem","system","group","company","problem","against","never","call","school","different","country","week","member","always","next","follow","bring","local", "family", "rather", "fact", "social", "write", "percent", "both", "run", "long", "right", "help", "every", "month", "side", "night", "important", "question", "business", "play", "power", "money", "change", "interest", "order", "often", "young", "hear", "perhaps", "level", "include", "already", "possible", "nothing", "line", "allow", "effect", "lead", "idea", "study", "live", "job", "name", "result", "body", "happen", "friend", "almost", "carry", "early", "together", "report", "appear", "continue")); // список слов в общем словаре
    static ArrayList<String> dictrus = new ArrayList<String>(Arrays.asList("собака","кошка","лодка","шляпа","сумка","яблоко","вода","телефон","ручка","карандаш","бумага","книга","стол", "день", "вещь","говорить","вниз","назад","ребенок","здесь","работать","старый","часть","покидать","жизнь","прекрасный","случай","женщина","казаться","система","группа","компания","проблема","против","никогда","звонить","школа","разный","страна","неделя","член","всегда","дальше","следовать","приносить","местный", "семья", "скорее", "факт", "соцаильный", "писать", "процент", "оба", "бежать", "длинный", "правильный", "помощь", "каждый", "месяц", "сторона", "ночь", "важный", "вопрос", "дело", "играть", "сила", "деньги", "менять", "интерес", "приказ", "часто", "молодой", "слышать", "возможно", "уровень", "включать", "уже", "возможный", "ничего", "линия", "разрешать", "эффект", "вести", "идея", "учить", "жить", "работа", "имя", "результат", "тело", "происходить", "друг", "почти", "нести", "рано", "вместе", "доклад", "появляться", "продолжать")); // список переводов слов из общего словаре
    static int countRightWords = 0; // счетчик верно угаданных слов за игру
    static int countWrongWords = 0; // счетчик неверно угаданных слов за игру
    static int countLearnedWords = 0; // счетчик выученных слов за игру
    static boolean isOpened; // проверка открыта ли
    static int ClickedWord = 0; // id кликнутого слова из словаря для передачи информации о слове на другой Activity
    static int learnedWords = 0;// количество выученных слов за все время
    Data() {// конструктор
        for (int i = 97; i <123; i++){
            letters.add(Character.toString((char)i)); // цикл заполняющий список английского алфавита английскими символами
        }
        for (int i = 1072; i <1104; i++){
            rusletters.add(Character.toString((char)i)); // цикл заполняющий список русского алфавита русскими символами
        }
    }
    public static void mSave() { // функция для сохранения данных через SharedPreferences
        final String KEY_DICTWORDS = "dictwords", KEY_MYWORDS = "mywords", KEY_PROGRESS = "progress", KEY_LEARNED = "learned"; // ключи для сохранения данных
        String Dictwords, Mywords, wordProgress; // переменные откуда берутся данные и куда возвращаются при запуске
        int learned = 0; // переменная куда записывается количество выученных слов для сохранения
        SharedPreferences.Editor myeditor = Menu.linkIlya.mSPref.edit(); // создание едитора и связка его с меню
        learned = Data.learnedWords; // инициализация переменной количеством общих выученных слов
        myeditor.putString(KEY_DICTWORDS, ""); // очищаем файл со словами из общего словаря
        myeditor.putString(KEY_MYWORDS, ""); // очищаем файл со словами из личного словаря
        myeditor.putString(KEY_PROGRESS, ""); // очищаем файл со степенью знания слов
        myeditor.putInt(KEY_LEARNED, 0); // очищаем файл с общим количеством выученных слов
        Mywords = ""; // обнуление переменной
        Dictwords = ""; // обнуление переменной
        wordProgress = ""; // обнуление переменной
        learned=0; // обнуление переменной
        for (int i = 0; i <dict.size(); i++){
            Dictwords = Dictwords + dict.get(i) + "-" +dictrus.get(i) + "!"; // цикл создания текста для удобного считывания и записи слов и его переводом в общем словаре. Например: слово: Apple , перевод: Яблоко, текстовая переменна будет выглядеть как "apple-яблоко!"
        }
        for (int i = 0; i < words.size(); i++){
            Mywords = Mywords + words.get(i) + "-" +ruswords.get(i) + "!"; // цикл создания текста для удобного считывания и записи слов и его переводом в личном словаре. Например: слово: Apple , перевод: Яблоко, текстовая переменна будет выглядеть как "apple-яблоко!"
        }
        for (int i =0;i<learnedLvl.size();i++)
        {
            wordProgress=wordProgress+learnedLvl.get(i).toString(); // цикл создания текста для удобного считывания и записи степеней знания слов. Например: слово яблоко (под номером 0) выучено на 25%, слово лодка (под номером 1) на 50%. Исход: "12"
            // "0" - 0%
            // "1" - 25%
            // "2" - 50%
            // "3" - 75%
            // "4" - 100%
        }
        myeditor.putString(KEY_DICTWORDS, Dictwords); // сохраняем файл со словами из общего словаря
        myeditor.putString(KEY_MYWORDS, Mywords); // сохраняем файл со словами из личного словаря
        myeditor.putString(KEY_PROGRESS, wordProgress); // сохраняем файл со степенью знания слов
        myeditor.putInt(KEY_LEARNED, learned); // сохраняем файл с общим количеством выученных слов
        myeditor.apply(); // приминяем изменения
    }
}

