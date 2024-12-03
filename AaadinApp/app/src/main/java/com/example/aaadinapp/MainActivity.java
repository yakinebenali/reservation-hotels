package com.example.aaadinapp;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Trouver la WebView dans la mise en page
        WebView webView = findViewById(R.id.webview);

        // Activer JavaScript pour Vaadin
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Charger l'URL de l'application Vaadin
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://172.20.10.8:8080/hotel");
    }
}
