package com.example.taytsel_app2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class About extends AppCompatActivity {
Button btnAbout, btnMenu;
TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // экран без верхней строки
        // инициализация
        btnAbout = findViewById(R.id.btnAboutAuthor);
        txt = findViewById(R.id.author);
        txt.setVisibility(View.INVISIBLE);
        btnMenu = findViewById(R.id.btnMenu);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // при нажатии на кнопку "Об авторе"..
                btnAbout.setVisibility(View.INVISIBLE); // кнопка изчезает
                txt.setVisibility(View.VISIBLE); // показывается текст с информацией об авторе
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }); // при нажатии на кнопку "в меню" возвращаемся в меню
    }
    protected void onStop() {
        super.onStop();
        Data.mSave();
    }
}
