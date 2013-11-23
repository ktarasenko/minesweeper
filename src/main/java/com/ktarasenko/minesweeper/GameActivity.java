package com.ktarasenko.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.ktarasenko.minesweeper.view.GameView;

public class GameActivity extends Activity {
    private GameView mGameView;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.newgame:
                    mGameView.startNewGame();
                    break;
                case R.id.cheat:
                    mGameView.cheat();
                    break;
                case R.id.check:
                    mGameView.check();
                    break;
            }
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mGameView = (GameView)findViewById(R.id.game_view);
        findViewById(R.id.check).setOnClickListener(mClickListener);
        findViewById(R.id.cheat).setOnClickListener(mClickListener);
        findViewById(R.id.newgame).setOnClickListener(mClickListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
