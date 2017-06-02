package demo.captain.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class LeftTvRightEtActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lefttvrightet);
        initView();
        initViewState();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
    }

    @Override
    public void onClick(View v) {

    }
}
