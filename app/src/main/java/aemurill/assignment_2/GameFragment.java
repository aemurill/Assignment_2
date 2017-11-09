package aemurill.assignment_2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static junit.framework.Assert.fail;

/**
 * Created by Beeooow on 11/4/2017.
 */

public class GameFragment extends Fragment implements View.OnClickListener {
    final static String ARG_LOAD = "LoadState";
    boolean loadState = false;
    int col = 7, row = 6;
    String state = "000000000000000000000000000000000000000000"; //defaulted to empty grid
//    ClickInterface Interface;
//
//    // Container Activity must implement this interface
//    public interface ClickInterface {
//        public void drawingClicked(int id);
//    }
//
//    @Override
//    public void onAttach(Activity activity){
//        super.onAttach(activity);
//        try {
//            Interface = (ClickInterface) activity;
//        }catch (ClassCastException e){
//            throw new ClassCastException(activity.toString() +
//                    "must implement ClickInterface");
//        }
//
//    }


    private class DrawView extends View {
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint chipPaint_Empty = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint chipPaint_Red = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint chipPaint_Yellow = new Paint(Paint.ANTI_ALIAS_FLAG);
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
        }

        public DrawView(Context context) {
            super(context);
            setPaint();
        }

        private float convert (float n){
            return n * -1 + getMeasuredHeight();
        }

        private RectF circlePos (float x, float y, float d){
            return new RectF(x, convert(y+d), x+d, convert(y));
        }

        private RectF rectPos (float x1, float y1, float x2, float y2){
            return new RectF(x1, convert(y2), x2, convert(y1));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint chipPaint = chipPaint_Empty;
            float w = getMeasuredWidth();
            float h = w * ((float) 6 / 7);

            RectF grid = rectPos(strokeWidth, strokeWidth, w-strokeWidth, h-strokeWidth);
            canvas.drawRect(grid, linePaint);

            float gap = (w - strokeWidth) / 7;
            for(int i = 0; i < col; i++){
                for(int j = 0; j < row; j++){
                    int k = i * 6 + j;
                    float iX = gap * i;
                    float jY = gap * j;
                    switch (state.charAt(k)){
                        case '0': chipPaint = chipPaint_Empty; break;
                        case '1': chipPaint = chipPaint_Red; break;
                        case '2': chipPaint = chipPaint_Yellow; break;
                        default: fail("Invalid State encountered");
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
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            this.setMeasuredDimension(parentWidth, parentHeight);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);

        // If activity recreated (such as from screen rotate), restore
        // the previous state set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        //SHOULD BE REDUNDANT!!!
        if (savedInstanceState != null) {
            loadState = savedInstanceState.getBoolean(ARG_LOAD);
        }
        return new DrawView(getActivity());

//        View rootView = inflater.inflate(R.layout.game_view, container, false);
//        DrawView drawing = (DrawView) rootView.findViewById(R.id.drawView);
//        drawing.setOnClickListener(this);
//        return rootView;

//        View rootView = inflater.inflate(R.layout.game_view, container, false);
//        DrawView drawView = rootView.findViewById(R.id.drawView);
//        new DrawView(getContext());
//        drawView.setOnClickListener(this);
//        return drawView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the updates the drawing?
        Bundle args = getArguments();
        loadState = args.getBoolean(ARG_LOAD);
        if (loadState) {
            // Set article based on saved instance state defined during onCreateView
            Toast.makeText(getContext(),
                    "Load? - " + String.valueOf(loadState), Toast.LENGTH_SHORT).show();
            //UPDATE DRAWING
        }else Toast.makeText(getContext(),
                "Load? - " + String.valueOf(loadState), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view){
        Toast.makeText(getContext(), String.valueOf(loadState), Toast.LENGTH_SHORT).show();
    }
}
