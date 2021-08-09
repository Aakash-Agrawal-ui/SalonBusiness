package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.salononline.salonbusiness.R;


public class PdfView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        WebView termsPdf=findViewById(R.id.termsPdf);
        final ProgressBar progressBar=findViewById(R.id.progressBarPdf);

        progressBar.setVisibility(View.VISIBLE);
        String finalUrl="https://www.salonqs.com/T&C";

        termsPdf.setWebViewClient(new MyWebViewTerms());
        termsPdf.getSettings().setJavaScriptEnabled(true);

        termsPdf.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        termsPdf.loadUrl(finalUrl);

    }

    private static class MyWebViewTerms extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
