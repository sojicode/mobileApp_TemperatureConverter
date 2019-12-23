package com.example.temperatureconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTag";

    private TextView history;
    private EditText numText;

    public TextView choice1, choice2;

    private RadioButton option1, option2;
    private SharedPreferences conversionPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        history = findViewById(R.id.outputHistory);
        numText = findViewById(R.id.inputDegree);


        option1 = findViewById(R.id.FtoC);
        option2 = findViewById(R.id.CtoF);

        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);

        conversionPref = getSharedPreferences("CONVERSION PREF", Context.MODE_PRIVATE);
        String convData = conversionPref.getString("CONVERSION PREF", "ONE");
//        ((TextView) findViewById(R.id.textView)).setText(convData);

        if(convData.equals("ONE")){
            option1.setChecked(true);
            Log.d(TAG, "onCreate: convData: "+convData);

            choice1.setText("Fahrenheit Degrees:");
            choice2.setText("Celsius Degrees:");

        } else if (convData.equals("TWO")){
            option2.setChecked(true);
            Log.d(TAG, "onCreate: convData: "+convData);

            choice1.setText("Celsius Degrees:");
            choice2.setText("Fahrenheit Degrees:");

        }

    }

    public void radioClick(View v){

        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);

        Log.d(TAG, "radioClick: choce1: "+ choice1);
        Log.d(TAG, "radioClick: choce2: "+ choice2);
        Log.d(TAG, "radioClick: choce1: "+ choice1.getText().toString());
        Log.d(TAG, "radioClick: choce2: "+ choice2.getText().toString());

        String newText = numText.getText().toString();
        if(newText.trim().isEmpty())

        numText.setText("");

        SharedPreferences.Editor editor = conversionPref.edit();

        switch (v.getId()){

            case R.id.FtoC:
                Log.d(TAG, "radioClick: Fahrenheit to Celsius Selected"+ R.id.FtoC);
                Log.d(TAG, "radioClick: v.getId: "+v.getId());
                Toast.makeText(this, "Fahrenheit to Celsius Selected", Toast.LENGTH_SHORT).show();

                choice1.setText("Fahrenheit Degrees:");
                choice2.setText("Celsius Degrees:");

                Log.d(TAG, "radioClick: setText1: fahren");
                Log.d(TAG, "radioClick: setText2: cel");

                editor.putString("CONVERSION PREF","ONE");
                editor.apply();

                break;

            case R.id.CtoF:
                Log.d(TAG, "radioClick: Celsius to Fahrenheit Selected"+ R.id.CtoF);
                Log.d(TAG, "radioClick: v.getId: "+v.getId());
                Toast.makeText(this,"Celsius to Fahrenheit"+R.id.CtoF, Toast.LENGTH_SHORT).show();

                choice1.setText("Celsius Degrees:");
                choice2.setText("Fahrenheit Degrees:");

                Log.d(TAG, "radioClick: setText1: cel");
                Log.d(TAG, "radioClick: setText2: fah");

                editor.putString("CONVERSION PREF","TWO");
                Log.d(TAG, "radioClick: choice1 "+ choice1.getText().toString());
                Log.d(TAG, "radioClick: choice2 "+ choice2.getText().toString());
                editor.apply();

                break;

            default:
                Log.d(TAG, "radioClicked: No View Selected");
                break;
        }

    }

    public void doConvert(View v) {

        String cho = choice1.getText().toString();
        Log.d(TAG, "doConvert: cho: "+cho);

        String f = "Fahrenheit Degrees:";
        String c = "Celsius Degrees:";

        String name = ((Button) v).getText().toString();
        Log.d(TAG, "doConvert: "+ name);

        EditText input = findViewById(R.id.inputDegree);
        TextView outputText = findViewById(R.id.outDegree);
        TextView outputHistory = findViewById(R.id.outputHistory);

        outputHistory.setMovementMethod(new ScrollingMovementMethod());

        String inputString = input.getText().toString();
        Log.d(TAG, "doConvert: A: "+ inputString);

        double inputValue = Double.parseDouble(inputString);

        if(cho.equals(f)){

            double outputValue = (inputValue - 32.0) / 1.8;

            Log.d(TAG, "doConvert: outputValue: "+outputValue+" fahrenheit: ");

            String output = String.format("∙ F to C: %.1f ==> %.1f\n", inputValue, outputValue);

            String orig = outputHistory.getText().toString();

            String out = String.format("%.1f", outputValue);

            Log.d(TAG, "doConvert: "+ output);

            outputText.setText(out);

            outputHistory.setText(output + orig);



        } else if(cho.equals(c)) {

            double outputValue = (inputValue * 1.8) + 32;
            Log.d(TAG, "doConvert: outputValue: " + outputValue + " celsius: ");

            String output = String.format("∙ C to F: %.1f ==> %.1f\n",inputValue, outputValue);

            String orig = outputHistory.getText().toString();

            String out = String.format("%.1f", outputValue);

            Log.d(TAG, "doConvert: "+ output);

            outputText.setText(out);

            outputHistory.setText(output + orig);

        } else {

            double outputValue = (inputValue - 32.0) / 1.8;
            Log.d(TAG, "doConvert: outputValue: " + outputValue + " celsius: ");
            String output = String.format("∙ F to C: %.1f ==> %.1f\n", inputValue, outputValue);

            String orig = outputHistory.getText().toString();

            String out = String.format("%.1f", outputValue);

            Log.d(TAG, "doConvert: "+ output);

            outputText.setText(out);

            outputHistory.setText(output + orig);

        }

    }

    public void clearScreen(View v){

//        String name = ((Button) v).getText().toString();
//        Log.d(TAG, "clearScreen: " + name);

        TextView outputHistory = findViewById(R.id.outputHistory);
        Log.d(TAG, "clearScreen: outputHistory "+ outputHistory);

        if(outputHistory != null){
            outputHistory.setText("");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("HISTORY", history.getText().toString());
        outState.putString("CHOICEHISTORY1", choice1.getText().toString());
        outState.putString("CHOICEHISTORY2", choice2.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        history.setText(savedInstanceState.getString("HISTORY"));
        choice1.setText(savedInstanceState.getString("CHOICEHISTORY1"));
        choice2.setText(savedInstanceState.getString("CHOICEHISTORY2"));
    }
}
