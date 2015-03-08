package com.example.zy.twentyfourgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.zy.twentyfourgame.alog.AlogMethod;

import java.util.Arrays;


public class MainActivity extends Activity {
    private static final int COUNTIME=61000;
    private static final int COUNTINTERVAL=1000;
    private int score=0;
    private int highscore=0;
    //get the value in the picture
    private int [] cardValueInPic=new int[4];

    private TextSwitcher textSwitcher ;
    private TextView textView_timer;
    private TextView textView_score;
    private TextView textView_highscore;
    private EditText editText;
    private String inputString=null;
    //button
    private Button button_add;
    private Button button_sub;
    private Button button_multiple;
    private Button button_divide;
    private Button button_left;
    private Button button_right;
    private Button button_delete;
    private Button button_refresh;
    private Button button_verify;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    private DeckOfCards deckOfCards;
    //计时器
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_layout);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putInt("HighestScores",highscore);
        editor.commit();
    }

    private void init(){
        //读取最高分，使用sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        highscore = sharedPreferences.getInt("HighestScores",0);

        imageView1 = (ImageView)findViewById(R.id.firstImage);
        imageView2 = (ImageView)findViewById(R.id.secondImage);
        imageView3 = (ImageView)findViewById(R.id.thirdImage);
        imageView4 = (ImageView)findViewById(R.id.fourthImage);
        ImageviewListener imageviewListener = new ImageviewListener();

        imageView2.setOnClickListener(imageviewListener);
        imageView1.setOnClickListener(imageviewListener);
        imageView3.setOnClickListener(imageviewListener);
        imageView4.setOnClickListener(imageviewListener);
        deckOfCards = new DeckOfCards();
        Buttonlistener buttonlistener = new Buttonlistener();
        button_verify  = (Button)findViewById(R.id.verify);
        button_refresh = (Button)findViewById(R.id.button_refresh);
        button_refresh.setOnClickListener(new refreshListener());
        button_add=(Button)findViewById(R.id.button_add);
        button_add.setOnClickListener(buttonlistener);
        button_delete=(Button)findViewById(R.id.button_delete);
        button_delete.setOnClickListener(buttonlistener);
        button_divide=(Button)findViewById(R.id.button_divide);
        button_divide.setOnClickListener(buttonlistener);
        button_sub=(Button)findViewById(R.id.button_sub);
        button_sub.setOnClickListener(buttonlistener);
        button_multiple=(Button)findViewById(R.id.button_multiple);
        button_multiple.setOnClickListener(buttonlistener);
        button_left=(Button)findViewById(R.id.button_left);
        button_left.setOnClickListener(buttonlistener);
        button_right=(Button)findViewById(R.id.button_right);
        button_right.setOnClickListener(buttonlistener);

        button_verify.setOnClickListener(new verifybuttonListener());

        textView_score =(TextView)findViewById(R.id.score);
        textView_highscore = (TextView)findViewById(R.id.highscore);
        textView_highscore.setText(""+highscore);

        textView_timer =(TextView)findViewById(R.id.timer);
        textSwitcher =(TextSwitcher)findViewById(R.id.textswitcher);
        editText = (EditText)findViewById(R.id.inputText);
        editText.setInputType(InputType.TYPE_NULL);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView  textView= new TextView(MainActivity.this);
                //textView.setSingleLine();
               // textView.setEllipsize(TextUtils.TruncateAt.END);
                //textSwitcher 是 frameLayout类型的
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams
                        (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.CENTER;
                textView.setLayoutParams(lp);
                textView.setTextSize(30);
                return textView;
            }
        });
        //设置入场动画
        textSwitcher.setInAnimation
                (MainActivity.this,android.R.anim.slide_in_left);
        textSwitcher.setOutAnimation
                (MainActivity.this,android.R.anim.slide_out_right);
        textSwitcher.setText("Game");

    }
    /**
     *This private class is a timer class
     * */
    private class Timer extends CountDownTimer{
        public Timer(long millisToCount,long countDownInterval){
            super(millisToCount,countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            textView_timer.setText(millisUntilFinished/1000+"s");
            if(millisUntilFinished/1000==30){
                Toast.makeText(MainActivity.this,"抓紧时间哦,时间过去一半了",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish() {
            textView_timer.setText(0+"s");
            button_verify.setEnabled(false);
        }
    }

    class Buttonlistener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_add:{
                    editText.setText(editText.getText()+"+");
                    break;
                }
                case R.id.button_sub:{
                    editText.setText(editText.getText()+"-");
                    break;
                } case R.id.button_multiple:{
                    editText.setText(editText.getText()+"*");
                    break;
                } case R.id.button_divide:{
                    editText.setText(editText.getText()+"/");
                    break;
                } case R.id.button_left:{
                    editText.setText(editText.getText()+"(");
                    break;
                } case R.id.button_right:{
                    editText.setText(editText.getText()+")");
                    break;
                } case R.id.button_delete:{
                    switch (editText.length()){
                        case 0:break;
                        case 1:editText.setText("");break;
                        default:editText.setText(editText.getText().subSequence(0,editText.length()-1));
                    }
                    break;
                }
            }
        }
    }

    private boolean compare(){
        int [] array =AlogMethod.returnArray();
        Arrays.sort(cardValueInPic);
        Arrays.sort(array);

        for(int i=0;i<4;i++){
            if(array[i]!=cardValueInPic[i])
                return  false;
        }
        return true;
    }
    /**
     * This is a listener to listen on the imageview
     * */
    private class ImageviewListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.firstImage:
                    editText.append(""+((Card)deckOfCards.getRandomCards().get(0)).getNumber());
                    break;
                case R.id.secondImage:
                    editText.append(""+((Card)deckOfCards.getRandomCards().get(1)).getNumber());
                    break;
                case R.id.thirdImage:
                    editText.append(""+((Card)deckOfCards.getRandomCards().get(2)).getNumber());
                    break;
                case R.id.fourthImage:
                    editText.append(""+((Card)deckOfCards.getRandomCards().get(3)).getNumber());
                    break;
            }
        }
    }
    /**
     * refresh button
     * */
    private class refreshListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            button_verify.setEnabled(true);
            if(timer!=null){
                timer.cancel();
            }
            timer = new Timer(COUNTIME,COUNTINTERVAL);
            timer.start();
            textSwitcher.setText("游戏开始!!");
            deckOfCards.reRandomSelect();
            imageView1.setImageResource(((Card)deckOfCards.getRandomCards().get(0)).returnImage());
            imageView2.setImageResource(((Card)deckOfCards.getRandomCards().get(1)).returnImage());
            imageView3.setImageResource(((Card)deckOfCards.getRandomCards().get(2)).returnImage());
            imageView4.setImageResource(((Card)deckOfCards.getRandomCards().get(3)).returnImage());
            for(int i=0;i<4;i++){
                cardValueInPic[i]=((Card)deckOfCards.getRandomCards().get(i)).getNumber();
            }
        }
    }
    /**
     *
     * */
    private class verifybuttonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String s=null;
            //if the string is not valid
            if(!AlogMethod.stringValid(s=editText.getText().toString())){
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Invalid!!");
                textSwitcher.setText("输入不合法");
                Log.e("nullReturn", "Invalid");
                Log.e("nullReturn",s);
                dialog.setMessage("The String you input is illegal");
                dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
            else{
                if(AlogMethod.result(s)==24){
                    if(compare()){
                        textSwitcher.setText("答案正确");
                        button_verify.setEnabled(false);
                        //停止计时器
                        if(timer!=null)
                            timer.cancel();
                        score+=10;
                        textView_score.setText(""+score);

                        if(score>highscore) {
                            highscore = score;
                        }
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("Correct");
                        dialog.setMessage("Correct answer");
                        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                    }
                    else{
                        textSwitcher.setText("不匹配!");
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("Not Match");
                        dialog.setMessage("Your input is not match with the given numbers");
                        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                    }
                }
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Wrong");
                    dialog.setMessage("Wrong answer");
                    dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                    }
                    });
                    dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }
            }
        }
    }
       }
