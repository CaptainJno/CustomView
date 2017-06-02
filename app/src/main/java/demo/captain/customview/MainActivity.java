package demo.captain.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        initView();
        initViewState();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv1 = (TextView) findViewById(R.id.tv1);
    }

    private void initViewState() {
        toolbar.setTitle("自定义View");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                startActivity(new Intent().setClass(this, LeftTvRightEtActivity.class));
                break;
        }
    }
}
