package demo.captain.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import demo.captain.customview.lefttextrighteditview.LeftTextViewRightEditText1;

/**
 * Created by guoqiang on 2017/6/1.
 */

public class LeftTvRightEtActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    LeftTextViewRightEditText1 lefttvrightetv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lefttvrightet);
        initView();
        initViewState();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lefttvrightetv1 = (LeftTextViewRightEditText1) findViewById(R.id.lefttvrightetv1);
    }

    private void initViewState() {
        toolbar.setTitle("左TextView右EditText");
        toolbar.setNavigationIcon(R.drawable.vector_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lefttvrightetv1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lefttvrightetv1:
                break;
        }
    }
}
