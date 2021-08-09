package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.salononline.salonbusiness.R;


public class PolicyView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_view);
        String finalUrl="https://www.salonqs.com/privacy-policy";
        WebView policyPdf=findViewById(R.id.policyPdf);
        final ProgressBar progressBar=findViewById(R.id.progressBarPolicyPdf);
        progressBar.setVisibility(View.VISIBLE);
        policyPdf.setWebViewClient(new MyWebViewPolicy());
        policyPdf.getSettings().setJavaScriptEnabled(true);

        policyPdf.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        policyPdf.loadUrl(finalUrl);

    }
    private static class MyWebViewPolicy extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
