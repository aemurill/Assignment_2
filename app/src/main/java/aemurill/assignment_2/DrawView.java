package aemurill.assignment_2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static junit.framework.Assert.fail;

/**
 * Created by aemurill on 11/8/2017.
 */

public class DrawView extends View {
    String state = DataModel.def_state;
    int turn = 1;
    boolean win = false;
    Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint chipPaint_Empty = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint chipPaint_Red = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint chipPaint_Yellow = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int strokeWidth = 24;

    private void setPaint() {
        linePaint.setAntiAlias(true);
        linePaint.setDither(false);
        linePaint.setColor(0xFF0000FF);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setStrokeCap(Paint.Cap.BUTT);
        linePaint.setStrokeWidth(strokeWidth);
        chipPaint_Empty.setAntiAlias(true);
        chipPaint_Empty.setDither(false);
        chipPaint_Empty.setColor(0xFFFAFAFA);
        chipPaint_Empty.setStyle(Paint.Style.FILL);
        chipPaint_Empty.setStrokeJoin(Paint.Join.ROUND);
        chipPaint_Empty.setStrokeCap(Paint.Cap.BUTT);
        chipPaint_Red.setAntiAlias(true);
        chipPaint_Red.setDither(false);
        chipPaint_Red.setColor(0xFFFF0000);
        chipPaint_Red.setStyle(Paint.Style.FILL);
        chipPaint_Red.setStrokeJoin(Paint.Join.ROUND);
        chipPaint_Red.setStrokeCap(Paint.Cap.BUTT);
        chipPaint_Yellow.setAntiAlias(true);
        chipPaint_Yellow.setDither(false);
        chipPaint_Yellow.setColor(0xFFFFFF00);
        chipPaint_Yellow.setStyle(Paint.Style.FILL);
        chipPaint_Yellow.setStrokeJoin(Paint.Join.ROUND);
        chipPaint_Yellow.setStrokeCap(Paint.Cap.BUTT);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(100);

    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPaint();

    }

    private float convert(float n) {
        return n * -1 + getMeasuredHeight();
    }

    private RectF circlePos(float x, float y, float d) {
        return new RectF(x, convert(y + d), x + d, convert(y));
    }

    private RectF rectPos(float x1, float y1, float x2, float y2) {
        return new RectF(x1, convert(y2), x2, convert(y1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint chipPaint = chipPaint_Empty;
        float w = getMeasuredWidth();
        float h = w * ((float) 6 / 7);

        RectF backGround = rectPos(0, 0, w, getMeasuredHeight());

        if(win) {
            if(turn == 2) canvas.drawRect(backGround, chipPaint_Yellow);
            else canvas.drawRect(backGround, chipPaint_Red);
        }else{
            if(turn == 1) canvas.drawRect(backGround, chipPaint_Yellow);
            else canvas.drawRect(backGround, chipPaint_Red);
        }

        RectF grid = rectPos(strokeWidth, strokeWidth, w - strokeWidth, h - strokeWidth);
        canvas.drawRect(grid, linePaint);

        float gap = (w - strokeWidth) / 7;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                int k = i * 6 + j;
                float iX = gap * i;
                float jY = gap * j;
                switch (state.charAt(k)) {
                    case '0':
                        chipPaint = chipPaint_Empty;
                        break;
                    case '1':
                        chipPaint = chipPaint_Red;
                        break;
                    case '2':
                        chipPaint = chipPaint_Yellow;
                        break;
                    default:
                        fail("Invalid State encountered");
                }
                RectF mBounds = circlePos(
                        strokeWidth + iX,
                        strokeWidth + jY,
                        gap - strokeWidth);
                canvas.drawArc(
                        mBounds,
                        360,
                        360,
                        false, chipPaint);
            }
            if(win){
                String player = "PLAYER_COLOR";
                if(turn == 1) player = "Red";
                else player = "Yellow";
                String congrats =  player + " wins!";
                int yPos = (int) ((getMeasuredHeight() + h)/2 - 50);
                int xPos = (int) ((w - 100 * Math.abs(congrats.length() / 2)) / 2);
                canvas.drawText(congrats, xPos, convert(yPos), textPaint);
            }
        }

            /*
             * Grid ratio tests
            drawLine(canvas, strokeWidth/2, strokeWidth/2,
             w-strokeWidth/2, w-strokeWidth/2, linePaint);
            drawLine(canvas,w-strokeWidth/2, strokeWidth/2,
             strokeWidth/2, w-strokeWidth/2, linePaint);
             */
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth, parentHeight);
    }

    public void update(String new_state, int new_turn, boolean check){
        state = new_state;
        turn = new_turn;
        win = check;
        invalidate();
    }
}
