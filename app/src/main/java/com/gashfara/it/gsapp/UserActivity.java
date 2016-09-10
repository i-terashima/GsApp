package com.gashfara.it.gsapp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiUserCallBack;
import com.kii.cloud.storage.exception.CloudExecutionException;


public class UserActivity extends ActionBarActivity {
    //蜈･蜉帙☆繧九ン繝･繝ｼ縺ｧ縺吶��
    private EditText mUsernameField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //閾ｪ蜍輔Ο繧ｰ繧､繝ｳ縺ｮ縺溘ａ菫晏ｭ倥＆繧後※縺�繧蟻ccess token繧定ｪｭ縺ｿ蜃ｺ縺吶�Ｕoken縺後≠繧後�ｰ繝ｭ繧ｰ繧､繝ｳ縺ｧ縺阪ｋ
        SharedPreferences pref = getSharedPreferences(getString(R.string.save_data_name), Context.MODE_PRIVATE);
        String token = pref.getString(getString(R.string.save_token), "");//菫晏ｭ倥＆繧後※縺�縺ｪ縺�譎ゅ�ｯ""
        //token縺後↑縺�縺ｨ縺阪��
        if(token == "") {
            //逕ｻ髱｢繧剃ｽ懊ｋ
            CreateMyView(savedInstanceState);
        }else {
            //閾ｪ蜍輔Ο繧ｰ繧､繝ｳ繧偵☆繧九��
            try {
                //KiiCloud縺ｮAccessToken縺ｫ繧医ｋ繝ｭ繧ｰ繧､繝ｳ蜃ｦ逅�縲ょｮ御ｺ�縺吶ｋ縺ｨ邨先棡縺慶allback髢｢謨ｰ縺ｨ縺励※螳溯｡後＆繧後ｋ縲�
                KiiUser.loginWithToken(callback, token);
            } catch (Exception e) {
                //繝�繧､繧｢繝ｭ繧ｰ繧定｡ｨ遉ｺ
                showAlert(R.string.operation_failed, e.getLocalizedMessage(), null);
                //逕ｻ髱｢繧剃ｽ懊ｋ
                CreateMyView(savedInstanceState);
            }
        }

    }
    //View繧剃ｽ懊ｋ縲ゅ＞縺､繧ＰnCreate縺ｧ繧�縺｣縺ｦ縺�繧九％縺ｨ
    protected void CreateMyView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
        //EditText縺ｮ繝薙Η繝ｼ繧呈爾縺励∪縺�
        mUsernameField = (EditText) findViewById(R.id.username_field);
        mPasswordField = (EditText) findViewById(R.id.password_field);
        //繝代せ繝ｯ繝ｼ繝峨ｒ髫�縺呵ｨｭ螳�
        mPasswordField.setTransformationMethod(new PasswordTransformationMethod());
        //繝代せ繝ｯ繝ｼ繝峨�ｮ蜈･蜉帶枚蟄励ｒ蛻ｶ髯舌☆繧九�ょ盾閠�ｼ喇ttp://techbooster.jpn.org/andriod/ui/3857/
        mPasswordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //逋ｻ骭ｲ繝懊ち繝ｳ
        Button signupBtn = (Button) findViewById(R.id.signup_button);
        //繝ｭ繧ｰ繧､繝ｳ繝懊ち繝ｳ
        Button loginBtn = (Button) findViewById(R.id.login_button);
        //繝ｭ繧ｰ繧､繝ｳ繝懊ち繝ｳ繧偵け繝ｪ繝�繧ｯ縺励◆譎ゅ�ｮ蜃ｦ逅�繧定ｨｭ螳�
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //繝ｭ繧ｰ繧､繝ｳ蜃ｦ逅�
                onLoginButtonClicked(v);
            }
        });
        //逋ｻ骭ｲ繝懊ち繝ｳ繧偵け繝ｪ繝�繧ｯ縺励◆譎ゅ�ｮ蜃ｦ逅�繧定ｨｭ螳�
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //逋ｻ骭ｲ蜃ｦ逅�
                onSignupButtonClicked(v);
            }
        });
    }
    //繝ｭ繧ｰ繧､繝ｳ蜃ｦ逅��ｼ壼盾閠�縲�http://documentation.kii.com/ja/guides/android/managing-users/sign-in/
    public void onLoginButtonClicked(View v) {
        //IME繧帝哩縺倥ｋ
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        //蜈･蜉帶枚蟄励ｒ蠕励ｋ
        String username = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();
        try {
            //KiiCloud縺ｮ繝ｭ繧ｰ繧､繝ｳ蜃ｦ逅�縲ょｮ御ｺ�縺吶ｋ縺ｨ邨先棡縺慶allback髢｢謨ｰ縺ｨ縺励※螳溯｡後＆繧後ｋ縲�
            KiiUser.logIn(callback, username, password);
        } catch (Exception e) {
            //繝�繧､繧｢繝ｭ繧ｰ繧定｡ｨ遉ｺ
            showAlert(R.string.operation_failed, e.getLocalizedMessage(), null);
        }
    }
    //繝�繧､繧｢繝ｭ繧ｰ繧定｡ｨ遉ｺ縺吶ｋ
    void showAlert(int titleId, String message, AlertDialogFragment.AlertDialogListener listener ) {
        DialogFragment newFragment = AlertDialogFragment.newInstance(titleId, message, listener);
        newFragment.show(getFragmentManager(), "dialog");
    }
    //逋ｻ骭ｲ蜃ｦ逅�
    public void onSignupButtonClicked(View v) {
        //IME繧帝哩縺倥ｋ
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        //蜈･蜉帶枚蟄励ｒ蠕励ｋ
        String username = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();
        try {
            //KiiCloud縺ｮ繝ｦ繝ｼ繧ｶ逋ｻ骭ｲ蜃ｦ逅�
            KiiUser user = KiiUser.createWithUsername(username);
            user.register(callback, password);
        } catch (Exception e) {
            showAlert(R.string.operation_failed, e.getLocalizedMessage(), null);
        }
    }
    //譁ｰ隕冗匳骭ｲ縲√Ο繧ｰ繧､繝ｳ縺ｮ譎ゅ↓蜻ｼ縺ｳ蜃ｺ縺輔ｌ繧九さ繝ｼ繝ｫ繝舌ャ繧ｯ髢｢謨ｰ
    KiiUserCallBack callback = new KiiUserCallBack() {
        //繝ｭ繧ｰ繧､繝ｳ縺悟ｮ御ｺ�縺励◆譎ゅ↓閾ｪ蜍慕噪縺ｫ蜻ｼ縺ｳ蜃ｺ縺輔ｌ繧九�り�ｪ蜍輔Ο繧ｰ繧､繝ｳ縺ｮ譎ゅｂ蜻ｼ縺ｳ蜃ｺ縺輔ｌ繧�
        @Override
        public void onLoginCompleted(int token, KiiUser user, Exception e) {
            // setFragmentProgress(View.INVISIBLE);
            if (e == null) {
                //閾ｪ蜍輔Ο繧ｰ繧､繝ｳ縺ｮ縺溘ａ縺ｫSharedPreference縺ｫ菫晏ｭ倥�ゅい繝励Μ縺ｮ繧ｹ繝医Ξ繝ｼ繧ｸ縲ょ盾閠�ｼ喇ttp://qiita.com/Yuki_Yamada/items/f8ea90a7538234add288
                SharedPreferences pref = getSharedPreferences(getString(R.string.save_data_name), Context.MODE_PRIVATE);
                pref.edit().putString(getString(R.string.save_token), user.getAccessToken()).apply();

                // Intent 縺ｮ繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ繧貞叙蠕励☆繧九�ＨetApplicationContext()縺ｧ閾ｪ蛻�縺ｮ繧ｳ繝ｳ繝�繧ｭ繧ｹ繝医ｒ蜿門ｾ励�る�ｷ遘ｻ蜈医�ｮ繧｢繧ｯ繝�繧｣繝薙ユ繧｣繝ｼ繧�.class縺ｧ謖�螳�
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // 驕ｷ遘ｻ蜈医�ｮ逕ｻ髱｢繧貞他縺ｳ蜃ｺ縺�
                startActivity(intent);
                //謌ｻ繧後↑縺�繧医≧縺ｫActivity繧堤ｵゆｺ�縺励∪縺吶��
                finish();
            } else {
                //e縺桑iiCloud迚ｹ譛峨�ｮ繧ｯ繝ｩ繧ｹ繧堤ｶ呎価縺励※縺�繧区凾
                if (e instanceof CloudExecutionException)
                    //KiiCloud迚ｹ譛峨�ｮ繧ｨ繝ｩ繝ｼ繝｡繝�繧ｻ繝ｼ繧ｸ繧定｡ｨ遉ｺ縲ゅヵ繧ｩ繝ｼ繝槭ャ繝医′驕輔≧
                    showAlert(R.string.operation_failed, Util.generateAlertMessage((CloudExecutionException) e), null);
                else
                    //荳�闊ｬ逧�縺ｪ繧ｨ繝ｩ繝ｼ繧定｡ｨ遉ｺ
                    showAlert(R.string.operation_failed, e.getLocalizedMessage(), null);
            }
        }
        //譁ｰ隕冗匳骭ｲ縺ｮ譎ゅ↓閾ｪ蜍慕噪縺ｫ蜻ｼ縺ｳ蜃ｺ縺輔ｌ繧�
        @Override
        public void onRegisterCompleted(int token, KiiUser user, Exception e) {
            if (e == null) {
                //閾ｪ蜍輔Ο繧ｰ繧､繝ｳ縺ｮ縺溘ａ縺ｫSharedPreference縺ｫ菫晏ｭ倥�ゅい繝励Μ縺ｮ繧ｹ繝医Ξ繝ｼ繧ｸ縲ょ盾閠�ｼ喇ttp://qiita.com/Yuki_Yamada/items/f8ea90a7538234add288
                SharedPreferences pref = getSharedPreferences(getString(R.string.save_data_name), Context.MODE_PRIVATE);
                pref.edit().putString(getString(R.string.save_token), user.getAccessToken()).apply();

                // Intent 縺ｮ繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ繧貞叙蠕励☆繧九�ＨetApplicationContext()縺ｧ閾ｪ蛻�縺ｮ繧ｳ繝ｳ繝�繧ｭ繧ｹ繝医ｒ蜿門ｾ励�る�ｷ遘ｻ蜈医�ｮ繧｢繧ｯ繝�繧｣繝薙ユ繧｣繝ｼ繧�.class縺ｧ謖�螳�
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // 驕ｷ遘ｻ蜈医�ｮ逕ｻ髱｢繧貞他縺ｳ蜃ｺ縺�
                startActivity(intent);
                //謌ｻ繧後↑縺�繧医≧縺ｫActivity繧堤ｵゆｺ�縺励∪縺吶��
                finish();
            } else {
                //e縺桑iiCloud迚ｹ譛峨�ｮ繧ｯ繝ｩ繧ｹ繧堤ｶ呎価縺励※縺�繧区凾
                if (e instanceof CloudExecutionException)
                    //KiiCloud迚ｹ譛峨�ｮ繧ｨ繝ｩ繝ｼ繝｡繝�繧ｻ繝ｼ繧ｸ繧定｡ｨ遉ｺ
                    showAlert(R.string.operation_failed, Util.generateAlertMessage((CloudExecutionException) e), null);
                else
                    //荳�闊ｬ逧�縺ｪ繧ｨ繝ｩ繝ｼ繧定｡ｨ遉ｺ
                    showAlert(R.string.operation_failed, e.getLocalizedMessage(), null);
            }
        }
    };

    //繝｡繝九Η繝ｼ髢｢菫ゑｼ壽悴菴ｿ逕ｨ
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
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
