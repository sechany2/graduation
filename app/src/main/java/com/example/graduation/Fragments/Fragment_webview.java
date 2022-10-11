package com.example.graduation.Fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.graduation.R;

public class Fragment_webview extends Fragment {
    public static Fragment_webview newInstance() {
        return new Fragment_webview();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adminstatistics, container, false);

        WebView webview = view.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://analytics.google.com/analytics/web/?authuser=1&hl=ko#/p304547395/reports/dashboard?r=firebase-overview");
        webview.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keycode, KeyEvent event){
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    if(keycode == KeyEvent.KEYCODE_BACK){
                        if(webview!=null){
                            if (webview.canGoBack()){
                                webview.goBack();
                            }else{
                                getActivity().onBackPressed();
                            }
                        }
                    }
                }return true;
            }
        });


        return view;
    }
}
