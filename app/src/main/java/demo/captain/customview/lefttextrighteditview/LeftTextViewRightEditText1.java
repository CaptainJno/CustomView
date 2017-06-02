package demo.captain.customview.lefttextrighteditview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import demo.captain.customview.R;

/**
 * 左TextView、右EditText控件
 */

public class LeftTextViewRightEditText1 extends LinearLayout {
    TextView left_tv;
    EditText right_et;

    CharSequence leftText;
    ColorStateList leftTextColor = null;
    int leftTextSize = 14;
    CharSequence rightHint;
    CharSequence rightText;
    ColorStateList rightTextColor = null;
    int rightTextSize = 14;
    int rightInputType = EditorInfo.TYPE_NULL;
    boolean editabled = true;


    public TextView getLeftTv() {
        return left_tv;
    }

    public EditText getRightEt() {
        return right_et;
    }

    public LeftTextViewRightEditText1(Context context) {
        this(context, null);
    }

    public LeftTextViewRightEditText1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftTextViewRightEditText1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.include_lefttv_rightet, this);
        left_tv = (TextView) findViewById(R.id.left_tv);
        right_et = (EditText) findViewById(R.id.right_et);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LeftTextRightEditView, defStyle, 0);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.LeftTextRightEditView_android_text:
                    leftText = ta.getText(attr);
                    break;
                case R.styleable.LeftTextRightEditView_android_textColor:
                    leftTextColor = ta.getColorStateList(attr);
                    break;
                case R.styleable.LeftTextRightEditView_android_textSize:
                    leftTextSize = ta.getDimensionPixelSize(attr, leftTextSize);
                    break;
                case R.styleable.LeftTextRightEditView_rightHint:
                    rightHint = ta.getText(attr);
                    break;
                case R.styleable.LeftTextRightEditView_rightText:
                    rightText = ta.getText(attr);
                    break;
                case R.styleable.LeftTextRightEditView_rightTextColor:
                    rightTextColor = ta.getColorStateList(attr);
                    break;
                case R.styleable.LeftTextRightEditView_rightTextSize:
                    rightTextSize = ta.getDimensionPixelSize(attr, rightTextSize);
                    break;
                case R.styleable.LeftTextRightEditView_rightEnabled:
                    editabled = ta.getBoolean(attr, isEnabled());
                    break;
                case R.styleable.LeftTextRightEditView_rightInputType:
                    rightInputType = ta.getInt(attr, EditorInfo.TYPE_NULL);
                    break;
            }
        }
        ta.recycle();

        left_tv.setText(leftText);
        left_tv.setTextColor(leftTextColor != null ? leftTextColor : ColorStateList.valueOf(0xFF212121));
        left_tv.setTextSize(leftTextSize);
        right_et.setHint(rightHint);
        right_et.setText(rightText);
        right_et.setTextColor(leftTextColor != null ? leftTextColor : ColorStateList.valueOf(0xFF212121));
        right_et.setTextSize(rightTextSize);
        right_et.setEnabled(editabled);
        if (rightInputType != EditorInfo.TYPE_NULL) {
            right_et.setInputType(rightInputType);
        }
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//    }

}
