package demo.captain.customview.bulletinbarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import demo.captain.customview.R;


public class Bulletinbar extends RelativeLayout {
    static final int upward = 0;
    static final int downward = 1;
    CharSequence barDefaultText;    //无公告时，默认文字
    int barTextColor;               //公告文字颜色
    int barTextSize;                //公告文字大小
    int barAutoPlayTime;            //公告切换频率（默认5000毫秒/次,5秒后切换销一条公告栏）
    static int barInDuration;       //公告进入耗时（默认1500，公告进入到中间展示位置耗时1.5秒）
    static int barOutDuration;      //公告退出耗时（默认750，公告退出展示位置耗时0.75秒）
    int barChangeOrientation;        //公告切换方向（从下往上，或从下往上）
    Drawable barBackground;          //公告栏背景颜色


    AutoPlayTask autoPlayTask;

    static List<String> bulletins;    //公告内容
    boolean isOnlyOneBullentin = false;  //是否只有一条公告（一条公告时，关闭自动播放）
    boolean isAutoPlay = true;           //当前是否自动播放公告
    boolean isAutoPlaying = false;        //是否正在播放
    static int currentBulletinIndex = -1;    //当前播放公告位置


    LinearLayout bulletinBarLayout;   //公告栏整个空间
    static TextView bulletinBarTv;    //公告栏中文本控件
    OnClickListener onClickListener;


    public Bulletinbar(Context context) {
        this(context, null);
    }

    public Bulletinbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bulletinbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultAttrs(context);
        initCustomAttrs(context, attrs);
        initView(context);
    }

    private void initDefaultAttrs(Context context) {
        autoPlayTask = new AutoPlayTask(this);
        barDefaultText = "暂无公告...";
        barTextColor = Color.BLACK;
        barTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics());
        barAutoPlayTime = 5000;
        barInDuration = 1000;
        barOutDuration = 750;
        barChangeOrientation = upward;
        barBackground = new ColorDrawable(Color.parseColor("#FFFFFFFF"));
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Bulletinbar);
        if (typedArray != null) {
            barDefaultText = typedArray.getText(R.styleable.Bulletinbar_barDefaultText) == null ? barDefaultText : typedArray.getText(R.styleable.Bulletinbar_barDefaultText);
            barTextColor = typedArray.getColor(R.styleable.Bulletinbar_barTextColor, barTextColor);
            barTextSize = typedArray.getDimensionPixelSize(R.styleable.Bulletinbar_barTextSize, barTextSize);
            barAutoPlayTime = typedArray.getInteger(R.styleable.Bulletinbar_barAutoPlayTime, barAutoPlayTime);
            barInDuration = typedArray.getInteger(R.styleable.Bulletinbar_barInDuration, barInDuration);
            barOutDuration = typedArray.getInteger(R.styleable.Bulletinbar_barOutDuration, barOutDuration);
            barChangeOrientation = typedArray.getInt(R.styleable.Bulletinbar_barChangeOrientation, barChangeOrientation);
            barBackground = typedArray.getDrawable(R.styleable.Bulletinbar_barBackground) == null ? barBackground : typedArray.getDrawable(R.styleable.Bulletinbar_barBackground);
            typedArray.recycle();
        }
    }

    private void initView(Context context) {
        bulletinBarLayout = new LinearLayout(context);
        if (Build.VERSION.SDK_INT >= 16) {
            bulletinBarLayout.setBackground(barBackground);
        } else {
            bulletinBarLayout.setBackgroundDrawable(barBackground);
        }
        bulletinBarLayout.setPadding(16, 0, 8, 0);
        bulletinBarLayout.setGravity(CENTER_VERTICAL);
        addView(bulletinBarLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        bulletinBarTv = new TextView(context);
        bulletinBarTv.setGravity(Gravity.CENTER_VERTICAL);
        bulletinBarTv.setSingleLine(true);
        bulletinBarTv.setEllipsize(TextUtils.TruncateAt.END);
        bulletinBarTv.setTextColor(barTextColor);
        bulletinBarTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, barTextSize);
        bulletinBarLayout.addView(bulletinBarTv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoPlay && !isOnlyOneBullentin) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    stopAutoPlay();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    startAutoPlay();
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (View.VISIBLE == visibility) {
            startAutoPlay();
        } else if (View.INVISIBLE == visibility) {
            stopAutoPlay();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoPlay();
    }

    private void preparPlay() {
        currentBulletinIndex = 0;
        changeBulletinText();
        if (!isOnlyOneBullentin && isAutoPlay) {
            startAutoPlay();
        }
    }

    private void startAutoPlay() {
        if (isAutoPlay && !isAutoPlaying) {
            isAutoPlaying = true;
            postDelayed(autoPlayTask, barAutoPlayTime);
        }
    }

    private void stopAutoPlay() {
        if (isAutoPlay && isAutoPlaying) {
            isAutoPlaying = false;
            removeCallbacks(autoPlayTask);
        }
    }

    private static class AutoPlayTask implements Runnable {
        private final WeakReference<Bulletinbar> mBulletinbarWeakReference;

        private AutoPlayTask(Bulletinbar mBulletinbarWeakReference) {
            this.mBulletinbarWeakReference = new WeakReference<>(mBulletinbarWeakReference);
        }

        @Override
        public void run() {
            currentBulletinIndex++;
            Bulletinbar bulletinbar = mBulletinbarWeakReference.get();
            if (bulletinbar != null) {
                changeBulletinText();
                bulletinbar.postDelayed(bulletinbar.autoPlayTask, bulletinbar.barAutoPlayTime);
            }
        }
    }

    private static void changeBulletinText() {
        TranslateAnimation out = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.7f);
        out.setDuration(barOutDuration);
        bulletinBarTv.setAnimation(out);    //配置动画
        bulletinBarTv.startAnimation(out);   //启动动画

        out.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (currentBulletinIndex >= bulletins.size()) {
                    currentBulletinIndex = 0;
                }
                if (bulletins == null || bulletins.size() == 0) {
                    return;
                }
                currentBulletinIndex = currentBulletinIndex % bulletins.size();
                bulletinBarTv.setText(bulletins.get(currentBulletinIndex).trim());

                TranslateAnimation in = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0);
                in.setDuration(barInDuration);
                bulletinBarTv.setAnimation(in);   //配置动画(会替换之前的退出动画)
            }
        });
    }


    /********************对外暴露的方法、接口****************************/

    /**
     * 给公告栏装载据数
     *
     * @param bulletins 公告栏展示内容
     */
    public void setData(List<String> bulletins) {
        this.bulletins = bulletins;
        if (bulletins == null || bulletins.size() == 0) {
            bulletinBarTv.setText(barDefaultText);
            isAutoPlay = false;
            return;
        }

        if (isAutoPlay && bulletins.size() <= 1) {
            isAutoPlay = false;
        }
        if (bulletins.size() <= 1) {
            isOnlyOneBullentin = true;
        } else {
            isOnlyOneBullentin = false;
        }
        preparPlay();
    }

    /**
     * 给公告栏设置监听时间
     *
     * @param listener
     */
    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
        if (onClickListener != null) {
            bulletinBarLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(Bulletinbar.this, currentBulletinIndex);
                }
            });
        }
    }

    /**
     * 获取当前公告栏播放位置
     */
    public int getCurrentIndex() {
        return currentBulletinIndex;
    }

    public interface OnClickListener {
        void onClick(Bulletinbar bulletinbar, int position);
    }

}
