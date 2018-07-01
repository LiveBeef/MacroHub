package com.ajszoke.macrohub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CalculateMacro1Activity extends AppCompatActivity {

    boolean isMale, isMetric;
    int age; // TODO: replace with Date birthdate
    RadioButton isMaleButton, isFemaleButton, isMetricButton, isImperialButton;
    RadioGroup sexGroup, unitsGroup;
    EditText ageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_macro1);

        final Button continueBtn = findViewById(R.id.CalculateMacro1ContinueBtn);
        sexGroup = findViewById(R.id.calculateMacro1SexGroup);
        unitsGroup = findViewById(R.id.calculateMacro1UnitsGroup);
        isMaleButton = findViewById(R.id.calculateMacro1IsMaleRadioButton);
        isFemaleButton = findViewById(R.id.calculateMacro1IsFemaleRadioButton);
        isMetricButton = findViewById(R.id.calculateMacro1IsMetricRadioButton);
        isImperialButton = findViewById(R.id.calculateMacro1IsImperialRadioButton);
        ageInput = findViewById(R.id.calculateMacroAgeInput);

        age = 0;
        isMale = isMetric = false;

        ageInput.addTextChangedListener(new TextValidator(ageInput) {
            @Override public void validate(TextView textView, String text) {
                try { age = Integer.parseInt(text); }
                catch (NumberFormatException nfe) {
                    displayError("You must enter a valid age.");
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sexGroup.getCheckedRadioButtonId() == -1) {
                    displayError("You must select a gender.");
                } else if (age == 0) {
                    displayError("You must select a valid age.");
                } else if (unitsGroup.getCheckedRadioButtonId() == -1) {
                    displayError("You must select a unit system.");
                } else {
                    SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("age", age);
                    editor.putBoolean("isMale", isMale);
                    editor.putBoolean("isMetric", isMetric);
                    editor.commit();

                    Intent calculateMacros2Intent = new Intent(CalculateMacro1Activity.this,
                            CalculateMacro2Activity.class);
                    startActivity(calculateMacros2Intent);
                }
            }
        });
    }

    // TODO: replace with EditText.setError()
    void displayError(String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(CalculateMacro1Activity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(CalculateMacro1Activity.this);
        }
        builder.setTitle("Error")
                .setMessage(message)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close message
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
