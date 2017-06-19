package demo.captain.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import demo.captain.customview.bulletinbarview.Bulletinbar;

public class BulletinbarActivity extends AppCompatActivity implements View.OnClickListener {

    List<String> list = new ArrayList<String>() {{
        add("区块链领域领先企业-PDX");
        add("什么是区块链？");
        add("区块链在商业保理中如何应用？");
    }};
    Toolbar toolbar;
    Bulletinbar bulletinbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bulletinbar);
        initView();
        initViewState();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bulletinbar = (Bulletinbar) findViewById(R.id.bulletinbar_bb);
    }

    private void initViewState() {
        toolbar.setTitle("公告栏");
        toolbar.setNavigationIcon(R.drawable.vector_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //公告栏加载数据 List<String>
        bulletinbar.setData(list);
        //公告栏设置监听事件
        bulletinbar.setOnClickListener(new Bulletinbar.OnClickListener() {
            @Override
            public void onClick(Bulletinbar bulletinbar, int position) {
                //TODO 你的逻辑
                Toast.makeText(BulletinbarActivity.this, "当前点击第" + position + "条公告", Toast.LENGTH_SHORT).show();
                startActivity(new Intent().setClass(BulletinbarActivity.this, LeftTvRightEtActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}