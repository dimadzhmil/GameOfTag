package com.example.android.gameoftag;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.*;

import java.text.NumberFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private String headerMessage = null;
    private int widthFree; // width current possition free cell
    private int heightFree; // height current possition free cell
    private int scaleOfTag = 5;
    private int arrayOfNumbers[] = new int[(scaleOfTag - 1) * (scaleOfTag - 1) + 1]; // array of numbers from 1 to 16
    private int arrayOfTag[][] = new int[scaleOfTag][scaleOfTag]; // main array
    private int iterations;

    private TextView timerText;
    private long startTime;
    private Timer timer;
    private TimerTask task;
    private long pauseTime;
    private boolean isTimeRunning = false;
    private boolean isTimePaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerText = (TextView) findViewById(R.id.timer);
        if (savedInstanceState != null) {
            headerMessage = savedInstanceState.getString("HeaderMessage");
            iterations = savedInstanceState.getInt("Iterations");
            startTime = savedInstanceState.getLong("StartTime");
            pauseTime = savedInstanceState.getLong("PauseTime");
            arrayOfNumbers = savedInstanceState.getIntArray("ArrayOfNumbers");
            isTimeRunning = savedInstanceState.getBoolean("IsTimeRunning");
            isTimePaused = savedInstanceState.getBoolean("IsTimePaused");
            if (savedInstanceState.getString("TimerMessage") != null) {
                timerText.setText(savedInstanceState.getString("TimerMessage"));
            }
            if (headerMessage.equals(getString(R.string.header_wellcome))) {
                Toast.makeText(this, "header_wellcome", Toast.LENGTH_SHORT).show();
            }
            if (headerMessage.equals(getString(R.string.header_new_game))) {
                displaySortArray();
            }
            if (headerMessage.equals(getString(R.string.header_start))) {
                displayShuffleArray(arrayOfNumbers, arrayOfTag);
                changeTextButton(R.id.start, R.string.pause);
                Toast.makeText(this, "onStartBundle: Running->" + isTimeRunning + " Pause->" + isTimePaused, Toast.LENGTH_SHORT).show();
            }
            if (headerMessage.equals(getString(R.string.header_pause))) {
                displayShuffleArray(arrayOfNumbers, arrayOfTag);
                changeTextButton(R.id.start, R.string.pause);
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.array_tag);
                mainLayout.setVisibility(View.INVISIBLE);
                changeTextHeader(R.id.main_text, R.string.header_pause);
                Toast.makeText(this, "onPauseBundle: Running->" + isTimeRunning + " Pause->" + isTimePaused, Toast.LENGTH_SHORT).show();
            }
            if (headerMessage.equals(getString(R.string.header_win))) {
                displaySortArray();
                changeTextHeader(R.id.main_text, R.string.header_win);
                changeTextButton(R.id.start, R.string.pause);
                displayIteratoins(getString(R.string.steps) + iterations);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView textView = (TextView) findViewById(R.id.main_text);
        outState.putString("HeaderMessage", textView.getText().toString());
        TextView textTime = (TextView) findViewById(R.id.timer);
        outState.putString("TimerMessage", textTime.getText().toString());
        outState.putInt("Iterations", iterations);
        outState.putLong("StartTime", startTime);
        outState.putLong("PauseTime", pauseTime);
        outState.putIntArray("ArrayOfNumbers", arrayOfNumbers);
        outState.putBoolean("IsTimeRunning", isTimeRunning);
        outState.putBoolean("IsTimePaused", isTimePaused);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Toast.makeText(this, "onPause: Running->" + isTimeRunning + " Pause->" + isTimePaused, Toast.LENGTH_SHORT).show();
        if (startTime != 0) {
            if (isTimeRunning) {
                timer.cancel();
                isTimeRunning = false;
                pauseTime = System.currentTimeMillis() - startTime;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(this, "onResume: Running->" + isTimeRunning + " Pause->" + isTimePaused, Toast.LENGTH_SHORT).show();
        if ((startTime != 0) && (!isTimePaused) && (!isTimeRunning)) {
            startTime = System.currentTimeMillis() - pauseTime;
            createTimer();
        }
        if ((startTime != 0) && (!isTimePaused) && (isTimeRunning)) {
            startTime = System.currentTimeMillis() - pauseTime;
        }
    }

    private void createTimer() {
        if (!isTimePaused && !isTimeRunning) {
            isTimeRunning = true;
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    updateTimerView();
                }
            };
            timer.schedule(task, 0, 1000);
        }
    }

    private void updateTimerView() {
        long currentTime = System.currentTimeMillis();
        final long milliseconds = currentTime - startTime;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int seconds = (int) (milliseconds / 1000) % 60;
                int minutes = (int) (milliseconds / (1000 * 60)) % 60;
//                int hours = (int) (milliseconds / (1000 * 60 * 60)) % 24;

                timerText.setText(getString(R.string.time) + String.format("%02d:%02d", minutes, seconds));

//                timerText.setText(getString(R.string.time) +
//                        String.valueOf(hours) + ":" +
//                        String.valueOf(minutes) + ":" +
//                        String.valueOf(seconds));
            }
        });
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        createTimer();
    }

    /*
    *  Shows the winning option.
    */
    public void resetOfTag(View view) {
        Button startButton = (Button) findViewById(R.id.start);
        if (startButton.getText().equals(getString(R.string.start))) {
            displaySortArray();
        } else {
            changeVisibilityLayout(startButton);
        }
    }

    /*
    *  Display sorted array.
    */
    public void displaySortArray() {
        int countNumber = 0;
        for (int heightOfTag = 1; heightOfTag < scaleOfTag; heightOfTag++) {
            for (int widthOfTag = 1; widthOfTag < scaleOfTag; widthOfTag++) {
                countNumber++;
                arrayOfTag[heightOfTag][widthOfTag] = countNumber;
                arrayOfNumbers[countNumber] = countNumber;
                String blockName = "block" + heightOfTag + widthOfTag;
                if (heightOfTag != 4 || widthOfTag != 4) {
                    displayInBlock(blockName, countNumber);
                }
            }
        }
        heightFree = 4;
        widthFree = 4;
        displayInBlockFree("block44");
        changeEnabledButton(R.id.new_game);
        changeEnabledButton(R.id.start);
        changeTextButton(R.id.start, R.string.pause);
        changeTextHeader(R.id.main_text, R.string.header_new_game);
    }

    /*
    * Change visibility Main Linear Layout
     */

    public void changeVisibilityLayout(Button nameButton) {
        if (nameButton.getText().equals(getString(R.string.pause))) {
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.array_tag);
            if (mainLayout.getVisibility() == View.VISIBLE) {
                mainLayout.setVisibility(View.INVISIBLE);
                changeTextHeader(R.id.main_text, R.string.header_pause);
                timer.cancel();
                isTimeRunning = false;
                isTimePaused = true;
                pauseTime = System.currentTimeMillis() - startTime;
            } else {
                mainLayout.setVisibility(View.VISIBLE);
                changeTextHeader(R.id.main_text, R.string.header_start);
                isTimePaused = false;
                startTime = System.currentTimeMillis() - pauseTime;
                createTimer();
            }
        }
    }

    /*
    *   Change status of Button.
    */
    public void changeEnabledButton(int resId) {
        Button dynamicButton = (Button) findViewById(resId);
        if (dynamicButton.isEnabled()) {
            dynamicButton.setEnabled(false);
        } else {
            dynamicButton.setEnabled(true);
        }
    }

    /*
    *   Change text of Button.
    */
    public void changeTextButton(int resId, int resString) {
        Button dynamicButton = (Button) findViewById(resId);
        dynamicButton.setText(getString(resString));
    }

    /*
   *   Change text of TextView.
   */
    public void changeTextHeader(int resId, int resString) {
        TextView dynamicButton = (TextView) findViewById(resId);
        dynamicButton.setText(getString(resString));
    }

    /*
    * Shuffle array of numbers from 1 to 16, and write to main array.
    */
    public void shuffleOfTag(View view) {
        iterations = 0;
        shuffleArray(arrayOfNumbers);
        displayShuffleArray(arrayOfNumbers, arrayOfTag);
        changeEnabledButton(R.id.new_game);
        changeEnabledButton(R.id.start);
    }

    public void displayShuffleArray(int[] currentArray, int[][] currentDoubleArray) {
        startTimer();
        int number = 0;
        for (int heightOfTag = 1; heightOfTag < scaleOfTag; heightOfTag++) {
            for (int widthOfTag = 1; widthOfTag < scaleOfTag; widthOfTag++) {
                number++;
                currentDoubleArray[heightOfTag][widthOfTag] = currentArray[number];
                String blockName = "block" + heightOfTag + widthOfTag;
                if (currentArray[number] != 16) {
                    displayInBlock(blockName, currentArray[number]);
                } else {
                    heightFree = heightOfTag;
                    widthFree = widthOfTag;
                    displayInBlockFree("block" + heightFree + widthFree);
                }
            }
        }
        changeTextHeader(R.id.main_text, R.string.header_start);
        displayIteratoins(getString(R.string.steps) + iterations);
    }

    public int heightNumber(int number) {
        return (number - 1) / 4;
    }

    public int weigthNumber(int number) {
        if (number < 4) {
            return number;
        } else {
            return number % 4;
        }
    }

    /*
    * Shuffle array of numbers from 1 to 16.
    */
    public void shuffleArray(int[] currentArray) {
        int n = currentArray.length;
        Random rnd = new Random();
        rnd.nextInt();
        for (int i = 1; i < n - 2; i++) {
            int change = i + rnd.nextInt(n - 2 - i);
            swapArray(currentArray, i, change);
        }
        int countDisorder = 0;
        for (int i = 1; i < n - 2; i++) {
            countDisorder += numbersOfDisorders(i);
        }
//        Toast.makeText(this, "Total of Disorders:" + countDisorder, Toast.LENGTH_SHORT).show();
        if ((countDisorder % 2) == 1) {
            swapArray(currentArray, 14, 15);
        }
    }

    /*
    * Count desorders betewen numbers in array
     */
    public int numbersOfDisorders(int currentNumber) {
        int currentDisorders = 0;
        for (int i = currentNumber; i < 15; i++) {
            if (arrayOfNumbers[i] < arrayOfNumbers[currentNumber]) {
                currentDisorders++;
            }
        }
        return currentDisorders;
    }

    /*
    * Swap two elements from array of numbers.
    */
    public void swapArray(int[] currentArray, int changeOne, int changeTwo) {
        int tmpVariable = currentArray[changeOne];
        currentArray[changeOne] = currentArray[changeTwo];
        currentArray[changeTwo] = tmpVariable;
    }

    /*
    * Change cells possition in the main array.
    */
    public void moveTextView(View view) {
        Button buttonNewGame = (Button) findViewById(R.id.new_game);
        if (buttonNewGame.isEnabled() == false) {
            TextView srsOnClick = (TextView) view;
            String idString = srsOnClick.getResources().getResourceEntryName(srsOnClick.getId());
            int heightTmp = Integer.parseInt(idString.substring(5, 6));
            int widthTmp = Integer.parseInt(idString.substring(6, 7));
            if (widthFree == widthTmp) {
                if (abs(heightFree - heightTmp) == 1) {
                    swapDoubleArray(heightFree, widthFree, heightTmp, widthTmp);
                    swapArray(arrayOfNumbers, 4 * (heightFree - 1) + widthFree, 4 * (heightTmp - 1) + widthTmp);
                    heightFree = heightTmp;
                    widthFree = widthTmp;
                    iterations++;
                    displayIteratoins(getString(R.string.steps) + String.valueOf(iterations));
                }
            }
            if (heightFree == heightTmp) {
                if (abs(widthFree - widthTmp) == 1) {
                    swapDoubleArray(heightFree, widthFree, heightTmp, widthTmp);
                    swapArray(arrayOfNumbers, 4 * (heightFree - 1) + widthFree, 4 * (heightTmp - 1) + widthTmp);
                    heightFree = heightTmp;
                    widthFree = widthTmp;
                    iterations++;
                    displayIteratoins(getString(R.string.steps) + String.valueOf(iterations));
                }
            }
            checkWinPossition();
        }
    }

    public void checkWinPossition() {
        int number = 0;
        boolean isWinPossition = true;
        for (int heightOfTag = 1; heightOfTag < scaleOfTag; heightOfTag++) {
            for (int widthOfTag = 1; widthOfTag < scaleOfTag; widthOfTag++) {
                number++;
                if (arrayOfTag[heightOfTag][widthOfTag] != number) {
                    isWinPossition = false;
                }
            }
        }
        if (isWinPossition) {
            Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show();
            timer.cancel();
            isTimeRunning = false;
            startTime = 0;
            changeEnabledButton(R.id.new_game);
            changeEnabledButton(R.id.start);
            changeTextHeader(R.id.main_text, R.string.header_win);
        }
    }

    /*
       * Swap two elements from main array.
        */
    public void swapDoubleArray(int h1, int w1, int h2, int w2) {
        int tmpVariable = arrayOfTag[h1][w1];
        arrayOfTag[h1][w1] = arrayOfTag[h2][w2];
        displayInBlock("block" + h1 + w1, arrayOfTag[h1][w1]);
        arrayOfTag[h2][w2] = tmpVariable;
        displayInBlockFree("block" + h2 + w2);
    }

    /*
    * Set message in main header
    */
    public void displayIteratoins(String putText) {
        TextView blockTextView = (TextView) findViewById(R.id.iterations);
        blockTextView.setText(putText);
    }

    /*
    * Set colos and number in non free cell
    */
    private void displayInBlock(String setNameTextView, int setNumberTextView) {
        int resId = getResources().getIdentifier(setNameTextView, "id", getPackageName());
        TextView dynamicTextView = (TextView) findViewById(resId);
        dynamicTextView.setText(NumberFormat.getIntegerInstance().format(setNumberTextView));
        dynamicTextView.setBackgroundColor(Color.parseColor("#00ddff"));
    }

    /*
     * Set colos and text in free cell
     */
    private void displayInBlockFree(String setNameFree) {
        int resId = getResources().getIdentifier(setNameFree, "id", getPackageName());
        TextView dynamicTextView = (TextView) findViewById(resId);
        dynamicTextView.setText("#");
        dynamicTextView.setBackgroundColor(Color.parseColor("#ffbb33"));
    }

    /*
    * method ror Button INFO
     */
    public void onClickInfo(View v) {
        Toast.makeText(this, "Some informatoins of the game", Toast.LENGTH_SHORT).show();
    }
}
