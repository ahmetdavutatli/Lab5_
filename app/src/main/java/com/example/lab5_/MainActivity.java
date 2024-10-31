package com.example.lab5_;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.restart_game){
            finish();
            startActivity(getIntent());
        }
        return super.onOptionsItemSelected(item);
    }

    static final String PLAYER_1 = "X";
    static final String PLAYER_2 = "O";

    boolean player1Turn = true;

    byte [][] board = new byte[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table = findViewById(R.id.board);
        for(int i=0;i<3;i++){
            TableRow row = (TableRow) table.getChildAt(i);
            for(int j=0;j<3;j++){
                Button btn = (Button) row.getChildAt(j);
                btn.setOnClickListener(new CellListener(i,j));
            }
        }
    }

    class CellListener implements View.OnClickListener{
        int row,col;
        public CellListener(int row, int col){
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {
            if (!isValid(row,col)) {
                Toast.makeText(MainActivity.this, "Invalid move!", Toast.LENGTH_SHORT).show();
            };

            if(player1Turn){
                ((Button)v).setText(PLAYER_1);
                board[row][col] = 1;
            }else{
                ((Button)v).setText(PLAYER_2);
                board[row][col] = 2;
            }
            if (gameEnded(row,col) != -1) {
                endGame(gameEnded(row,col));
            } else {
                player1Turn = !player1Turn;
            }
        }
    }

    public boolean isValid (int row, int col){
        return board[row][col] == 0;
    }

    public int gameEnded (int row, int col){
        int symbol = board[row][col];
        boolean win = true;

        for (int i = 0; i < 3; i++) {
            if (board[row][i] != symbol) {
                win = false;
                break;
            }
        }

        if (win) {
            return symbol;
        }

        win = true;

        for (int i = 0; i < 3; i++) {
            if (board[i][col] != symbol) {
                win = false;
                break;
            }
        }

        if (win) {
            return symbol;
        }

        if (row == col) {
            win = true;
            for (int i = 0; i < 3; i++) {
                if (board[i][i] != symbol) {
                    win = false;
                    break;
                }
            }

            if (win) {
                return symbol;
            }
        }

        if (row + col == 2) {
            win = true;
            for (int i = 0; i < 3; i++) {
                if (board[i][2 - i] != symbol) {
                    win = false;
                    break;
                }
            }

            if (win) {
                return symbol;
            }
        }

        return -1;
    }

    public void endGame(int result){
        TableLayout table = findViewById(R.id.board);
        for(int i=0;i<3;i++){
            TableRow row = (TableRow) table.getChildAt(i);
            for(int j=0;j<3;j++){
                Button btn = (Button) row.getChildAt(j);
                btn.setEnabled(false);
            }
        }
        String message;
        if(result == 1) {
            message = "Player 1 wins!";
        } else if(result == 2) {
            message = "Player 2 wins!";
        } else {
            message = "Draw!";
        }
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }




}