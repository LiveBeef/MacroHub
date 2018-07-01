package com.ajszoke.macrohub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ManualMacroInputActivity extends AppCompatActivity {

    int workPro, workCarbs, workFat, workTot, restPro, restCarbs, restFat, restTot;
    EditText workProInput, workCarbsInput, workFatInput, restProInput, restCarbsInput, restFatInput;
    TextView workTotCals, restTotCals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_macro_input);

        final Button continueBtn = findViewById(R.id.ManualMacroInputContinueBtn);
        workProInput = findViewById(R.id.workProInput);
        workCarbsInput = findViewById(R.id.workCarbsInput);
        workFatInput = findViewById(R.id.workFatInput);
        restProInput = findViewById(R.id.restProInput);
        restCarbsInput = findViewById(R.id.restCarbsInput);
        restFatInput = findViewById(R.id.restFatInput);
        workTotCals = findViewById(R.id.workTotCals);
        restTotCals = findViewById(R.id.restTotCals);

        workPro = workCarbs = workFat = restPro = restCarbs = restFat = workTot = restTot = 0;

        workProInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    try {
                        workPro = Integer.parseInt(workProInput.getText().toString());
                        recalcTotals();
                        workTotCals.setText(Integer.toString(workTot));
                    } catch (NumberFormatException nfe){}
                }
            }
        });
        workCarbsInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    try {
                        workCarbs = Integer.parseInt(workCarbsInput.getText().toString());
                        recalcTotals();
                        workTotCals.setText(Integer.toString(workTot));
                    } catch (NumberFormatException nfe){}
                }
            }
        });
        workFatInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    try {
                        workFat = Integer.parseInt(workFatInput.getText().toString());
                        recalcTotals();
                        workTotCals.setText(Integer.toString(workTot));
                    } catch (NumberFormatException nfe){}
                }
            }
        });
        restProInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    try {
                        restPro = Integer.parseInt(restProInput.getText().toString());
                        recalcTotals();
                        restTotCals.setText(Integer.toString(restTot));
                    } catch (NumberFormatException nfe){}
                }
            }
        });
        restCarbsInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    try {
                        restCarbs = Integer.parseInt(restCarbsInput.getText().toString());
                        recalcTotals();
                        restTotCals.setText(Integer.toString(restTot));
                    } catch (NumberFormatException nfe){}
                }
            }
        });
        restFatInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    try {
                        restFat = Integer.parseInt(restFatInput.getText().toString());
                        recalcTotals();
                        restTotCals.setText(Integer.toString(restTot));
                    } catch (NumberFormatException nfe){}
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            // TODO: Input validation
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("workPro", workPro);
                editor.putInt("workCarbs", Integer.parseInt(workCarbsInput.toString()));
                editor.putInt("workFat", Integer.parseInt(workFatInput.toString()));
                editor.putInt("restPro", Integer.parseInt(restProInput.toString()));
                editor.putInt("restCarbs", Integer.parseInt(restCarbsInput.toString()));
                editor.putInt("restFat", Integer.parseInt(restFatInput.toString()));
                editor.commit();

                Intent launchSetDaysIntent = new Intent(ManualMacroInputActivity.this,
                        SetDaysActivity.class);
                startActivity(launchSetDaysIntent);
            }
        });
    }

    void recalcTotals() {
        workTot = 4*workPro + 4*workCarbs + 9*workFat;
        restTot = 4*restPro + 4*restCarbs + 9*restFat;
    }
}
