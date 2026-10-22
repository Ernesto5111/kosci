package com.example.koci;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLiczbaKostek;
    private Button buttonLosuj;
    private GridLayout gridKostki;
    private TextView textViewWyniki;
    private Button buttonRosnaco;
    private Button buttonMalejaco;

    private List<Integer> wynikiLosowania = new ArrayList<>();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLiczbaKostek = findViewById(R.id.editTextLiczbaKostek);
        buttonLosuj = findViewById(R.id.buttonLosuj);
        gridKostki = findViewById(R.id.gridKostki);
        textViewWyniki = findViewById(R.id.textViewWyniki);
        buttonRosnaco = findViewById(R.id.buttonRosnaco);
        buttonMalejaco = findViewById(R.id.buttonMalejaco);
        buttonLosuj.setOnClickListener(v -> losujKostki());

        buttonRosnaco.setOnClickListener(v -> {
            if (wynikiLosowania.isEmpty()) return;
            Collections.sort(wynikiLosowania);
            aktualizujWidok();
        });

        buttonMalejaco.setOnClickListener(v -> {
            if (wynikiLosowania.isEmpty()) return;
            Collections.sort(wynikiLosowania, Collections.reverseOrder());
            aktualizujWidok();
        });
    }

    private void losujKostki() {
        String inputText = editTextLiczbaKostek.getText().toString().trim();

        if (TextUtils.isEmpty(inputText)) {
            Toast.makeText(this, "Wprowadź liczbę kostek", Toast.LENGTH_SHORT).show();
            return;
        }

        int liczbaKostek = Integer.parseInt(inputText);
        if (liczbaKostek <= 0) {
            Toast.makeText(this, "Liczba kostek musi być większa od 0", Toast.LENGTH_SHORT).show();
            return;
        }

        wynikiLosowania.clear();
        for (int i = 0; i < liczbaKostek; i++) {
            wynikiLosowania.add(random.nextInt(6) + 1);
        }

        aktualizujWidok();
    }

    private void aktualizujWidok() {
        gridKostki.removeAllViews();

        int suma = 0;
        StringBuilder wynikiTekst = new StringBuilder();

        for (int i = 0; i < wynikiLosowania.size(); i++) {
            int wartosc = wynikiLosowania.get(i);
            suma += wartosc;
            TextView tvKostka = new TextView(this);
            tvKostka.setText("Kostka " + (i + 1) + ": " + wartosc);
            tvKostka.setTextSize(16);
            gridKostki.addView(tvKostka);
            wynikiTekst.append(wartosc);
            if (i < wynikiLosowania.size() - 1) {
                wynikiTekst.append(", ");
            }
        }

        textViewWyniki.setText("Wylosowane wartości: " + wynikiTekst.toString() + "\nSuma: " + suma);
    }
}