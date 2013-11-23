package com.ktarasenko.minesweeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import com.ktarasenko.minesweeper.model.GameTable;
import com.ktarasenko.minesweeper.view.GameView;

public class GameActivity extends Activity {
    private static final int DLG_CONFIRM = 1001;
    private static final int DLG_NEW_GAME = 1002;
    private GameView mGameView;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.newgame:
                    if (mGameView.isGameStarted()){
                        showDialog(DLG_CONFIRM);
                    } else {
                        showDialog(DLG_NEW_GAME);
                    }
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
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case DLG_CONFIRM:
                return new AlertDialog.Builder(this)
                        .setTitle(R.string.new_game)
                        .setMessage(R.string.start_new_game)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showDialog(DLG_NEW_GAME);
                                dismissDialog(DLG_CONFIRM);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismissDialog(DLG_CONFIRM);
                            }
                        }).show();
            case DLG_NEW_GAME:
                View v = View.inflate(this, R.layout.dlg_new_game, null);
                final TextView heightText = (TextView) v.findViewById(R.id.height_value);
                final TextView widthText = (TextView) v.findViewById(R.id.width_value);
                final TextView minesText = (TextView) v.findViewById(R.id.mines_value);
                final SeekBar height = (SeekBar) v.findViewById(R.id.height);
                final SeekBar width = (SeekBar) v.findViewById(R.id.width);
                final SeekBar mines = (SeekBar) v.findViewById(R.id.mines);
                SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                       int minesMax = (height.getProgress()+1)* (width.getProgress()+1)-1;
                       mines.setMax(minesMax);
                       mines.setProgress(Math.min(minesMax, mines.getProgress()));
                       heightText.setText(getString(R.string.height_caption, height.getProgress()+1));
                       widthText.setText(getString(R.string.width_caption, width.getProgress()+1));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                };
                SeekBar.OnSeekBarChangeListener minesSeekListener = new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        minesText.setText(getString(R.string.mines_caption, mines.getProgress()+1));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                };

                height.setOnSeekBarChangeListener(seekListener);
                width.setOnSeekBarChangeListener(seekListener);
                mines.setOnSeekBarChangeListener(minesSeekListener);
                height.setProgress(GameTable.DEFAULT_HEIGHT-1);
                width.setProgress(GameTable.DEFAULT_WIDTH-1);
                mines.setProgress(GameTable.DEFAULT_MINES-1);
                return new AlertDialog.Builder(this).setView(v)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mGameView.startNewGame(height.getProgress()+1, width.getProgress()+1, mines.getProgress()+1);
                                dismissDialog(DLG_NEW_GAME);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismissDialog(DLG_NEW_GAME);
                            }
                        }).show();
        }
        return  super.onCreateDialog(id);
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
