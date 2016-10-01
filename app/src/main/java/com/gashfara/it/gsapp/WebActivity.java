package com.gashfara.it.gsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //���C�A�E�g���Z�b�g���܂�
        setContentView(R.layout.activity_web);

        // Intent ���擾�B
        //Intent�ŃA�N�e�B�r�e�B�[�Ԃ̃f�[�^���󂯓n�����܂��BIntent�̒l���󂯎�邽�߂ɍ쐬�B
        Intent intent = getIntent();
        // �L�[�����ɃC���e���g�f�[�^���擾����
        String url  = intent.getStringExtra("url");

        //WebView��T��
        WebView webView = (WebView) findViewById(R.id.webView1);
        //�f�o�b�O���O
        Log.d("get myurl", url);
        //�u���E�U�̋@�\���Z�b�g���܂��B���񑩁B
        webView.setWebViewClient(new WebViewClient());
        //URL��\�����܂��B
        webView.loadUrl(url);
    }
    //�f�t�H���g�ō쐬���ꂽ���j���[�̊֐��ł��B���g�p�B
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}