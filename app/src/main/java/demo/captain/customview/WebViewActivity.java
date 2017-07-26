package demo.captain.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_webview);
        initView();
        initViewState();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webview = (WebView) findViewById(R.id.webview);
    }

    private void initViewState() {
        toolbar.setTitle("WebView交互");
        toolbar.setNavigationIcon(R.drawable.vector_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);                   //设置JavaScript可用
        webview.addJavascriptInterface(new JsInterface(), "localInterface");  // 给webview添加JavaScript接口。相当于把本地的JsInterface设置了个别名（这个别名是用在javascript中的）
        String htmlCode = "<html>"
                + "<head>"
                + "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>"
                + "<script type='text/javascript'>" + " function callLocalMethod() { javascript:localInterface.showToast();}"
                + "</script> "
                + "</head>"
                + "<body>"
                + "<input type='button' value='调用本地方法' onclick=\"callLocalMethod()\">"
                + "</body>"
                + "</html>";
        webview.loadDataWithBaseURL("about:blank", htmlCode, "text/html", "utf-8", null);
    }

    @Override
    public void onClick(View v) {

    }

    public class JsInterface {          //webview中调用该本地方法
        @JavascriptInterface
        public void showToast() {
            Toast.makeText(WebViewActivity.this, "本地方法已被调用！", Toast.LENGTH_SHORT).show();
        }
    }
}
