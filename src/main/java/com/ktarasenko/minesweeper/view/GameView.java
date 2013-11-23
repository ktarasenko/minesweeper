package com.ktarasenko.minesweeper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.ktarasenko.minesweeper.R;
import com.ktarasenko.minesweeper.model.GameTable;

public class GameView extends View {


    private float mCellSize;
    private int mHeight;
    private int mWidth;
    private float[] mTable;
    private int mCellSizeInt;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setTableSize(int width, int height){
        mWidth = width;
        mHeight = height;
        requestLayout();
    }

    private void init() {
       mCellSizeInt = getResources().getDimensionPixelSize(R.dimen.cell_size);
       mCellSize = getResources().getDimension(R.dimen.cell_size);
       mHeight = GameTable.DEFAULT_HEIGHT;
       mWidth = GameTable.DEFAULT_WIDTH;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       setMeasuredDimension(Math.max(mWidth * mCellSizeInt + 1, getSuggestedMinimumWidth()),
               Math.max(mHeight * mCellSizeInt + 1, getSuggestedMinimumHeight()));
        mTable = new float[(mWidth + mHeight + 2) * 4];

        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        for (int i = 0; i < mWidth+1; i++){
            mTable[i*4] = 0;
            mTable[i*4+1]=  i*mCellSize;
            mTable[i*4+2]=  height;
            mTable[i*4+3]=  i*mCellSize;
        }
        final int offset = (mWidth +1) * 4;
        for (int i = 0; i < mHeight+1; i++){
            mTable[offset+ i*4] = i*mCellSize;
            mTable[offset + i*4+1]=  0;
            mTable[offset+ i*4+2]=  i*mCellSize;
            mTable[offset+ i*4+3]=  width;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        canvas.drawLines(mTable, p);
    }
}
