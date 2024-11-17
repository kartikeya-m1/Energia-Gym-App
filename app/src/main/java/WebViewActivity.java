package com.project.intellifit_trainer;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient()); // Prevents opening in the default browser
        webView.getSettings().setJavaScriptEnabled(true); // Enables JavaScript if needed

        // Get the URL from the Intent
        String url = getIntent().getStringExtra("URL");
        if (url != null) {
            webView.loadUrl(url);
        } else {
            // Fallback URL if the intent is missing the URL
            webView.loadUrl("https://aiworkout.profematika.com");
        }
    }
}
