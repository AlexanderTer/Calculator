package com.example.admn.univarsalcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView textViev_input;
    TextView textViev_result;
    Button button_zero;
    Button button_1;
    Button button_2;
    Button button_3;
    Button button_4;
    Button button_5;
    Button button_6;
    Button button_7;
    Button button_8;
    Button button_9;
    Button button_plus;
    Button button_minus;
    Button button_miltiply;
    Button button_split;
    Button button_equall;
    Button button_remove;
    Button button_point;
    Button button_leftBracket;
    Button button_rightBracket;
    Button button_degree;
    Button button_sqrt;
    Button button_imaginary_unit;
    Button button_PI;
    Button button_e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViev_input = (TextView)findViewById(R.id.textViev_input);
        textViev_result = (TextView)findViewById(R.id.textViev_result);
        button_remove = (Button)findViewById(R.id.button_remove);
        button_equall = (Button)findViewById(R.id.button_equally);
        button_plus   = (Button)findViewById(R.id.button_plus) ;
        button_minus  = (Button)findViewById(R.id.button_minus) ;
        button_miltiply = (Button)findViewById(R.id.button_multiply);
        button_split  = (Button)findViewById(R.id.button_split);
        button_point  = (Button)findViewById(R.id.button_point);
       // button_imaginary_unit = (Button)findViewById(R.id.button_j) ;
       // button_PI = (Button) findViewById(R.id.button_PI);
        //button_e = (Button) findViewById(R.id.button_e);
        button_leftBracket = (Button)findViewById(R.id.button_leftBracket);
        button_rightBracket = (Button)findViewById(R.id.button_rightBracket);
       // button_degree = (Button) findViewById(R.id.button_degree);
      //  button_sqrt = (Button) findViewById(R.id.button_sqrt);
        button_zero   = (Button)findViewById(R.id.button_zero);
        button_1      = (Button)findViewById(R.id.button_1);
        button_2      = (Button)findViewById(R.id.button_2);
        button_3      = (Button)findViewById(R.id.button_3);
        button_4      = (Button)findViewById(R.id.button_4);
        button_5      = (Button)findViewById(R.id.button_5);
        button_6      = (Button)findViewById(R.id.button_6);
        button_7      = (Button)findViewById(R.id.button_7);
        button_8      = (Button)findViewById(R.id.button_8);
        button_9      = (Button)findViewById(R.id.button_9);



        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              switch (v.getId()){
                  case R.id.button_zero:
                      textViev_input.setText(  textViev_input.getText()+"0");
                      break;
                  case R.id.button_1:
                      textViev_input.setText( textViev_input.getText()+"1");
                      break;
                  case R.id.button_2:
                      textViev_input.setText( textViev_input.getText()+"2");
                      break;
                  case R.id.button_3:
                      textViev_input.setText( textViev_input.getText()+"3");
                      break;
                  case R.id.button_4:
                      textViev_input.setText( textViev_input.getText()+"4");
                      break;
                  case R.id.button_5:
                      textViev_input.setText( textViev_input.getText()+"5");
                      break;
                  case R.id.button_6:
                      textViev_input.setText( textViev_input.getText()+"6");
                      break;
                  case R.id.button_7:
                      textViev_input.setText( textViev_input.getText()+"7");
                      break;
                  case R.id.button_8:
                      textViev_input.setText( textViev_input.getText()+"8");
                      break;
                  case R.id.button_9:
                      textViev_input.setText( textViev_input.getText()+"9");
                      break;
                  case R.id.button_plus:
                      textViev_input.setText( textViev_input.getText()+" + ");
                      break;
                  case R.id.button_minus:
                      textViev_input.setText( textViev_input.getText()+" - ");
                      break;
                  case R.id.button_multiply:
                      textViev_input.setText( textViev_input.getText()+" * ");
                      break;
                  case R.id.button_split:
                      textViev_input.setText( textViev_input.getText()+" / ");
                      break;
                  case R.id.button_point:
                      textViev_input.setText( textViev_input.getText()+".");
                      break;
                  case R.id.button_leftBracket:
                      textViev_input.setText( textViev_input.getText()+"(");
                     break;
                  case R.id.button_rightBracket:
                      textViev_input.setText( textViev_input.getText()+")");
                      break;
//                  case R.id.button_degree:
//                      result.setText( result.getText()+"^");
//                      break;
//                  case R.id.button_sqrt:
//                      result.setText( result.getText()+"√");
//                      break;
//                  case R.id.button_j:
//                      result.setText( result.getText()+" i ");
//                      break;
//                  case R.id.button_PI:
//                      result.setText( result.getText()+"3.14");
//                      break;
//                  case R.id.button_e:
//                      result.setText( result.getText()+"2.718");
//                      break;

                  case R.id.button_remove:
                      textViev_input.setText("");
                      textViev_result.setText("");
                      break;
                  case R.id.button_equally:
                      PolishRecord match_strok = new PolishRecord();
                      textViev_result.setText("");
                      textViev_result.setText(match_strok.stringToResult(textViev_input.getText().toString()));

                      break;


              }//switch
            }//OnClick
        };//method OnClickListener обработчик нескольких кнопок

        button_plus.setOnClickListener(onClickListener);
        button_minus.setOnClickListener(onClickListener);
        button_miltiply.setOnClickListener(onClickListener);
        button_split.setOnClickListener(onClickListener);
        button_equall.setOnClickListener(onClickListener);
        button_remove.setOnClickListener(onClickListener);
        button_leftBracket.setOnClickListener(onClickListener);
        button_rightBracket.setOnClickListener(onClickListener);
       // button_degree.setOnClickListener(onClickListener);
        //button_sqrt.setOnClickListener(onClickListener);
        button_point.setOnClickListener(onClickListener);
       // button_imaginary_unit.setOnClickListener(onClickListener);
       // button_PI.setOnClickListener(onClickListener);
       // button_e.setOnClickListener(onClickListener);
        button_zero.setOnClickListener(onClickListener);
        button_1.setOnClickListener(onClickListener);
        button_2.setOnClickListener(onClickListener);
        button_3.setOnClickListener(onClickListener);
        button_4.setOnClickListener(onClickListener);
        button_5.setOnClickListener(onClickListener);
        button_6.setOnClickListener(onClickListener);
        button_7.setOnClickListener(onClickListener);
        button_8.setOnClickListener(onClickListener);
        button_9.setOnClickListener(onClickListener);





    }//Method onCreate
}//Class MainActivity
