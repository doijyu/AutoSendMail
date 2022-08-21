package com.example.autosendmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncTask a=new asyncTask();
//                a.execute("Gmailのアカウント名（@gmail.comの前まで）",
//                        "Gmailのパスワード","テストタイトル","送信完了\n本文をここに記述する") ;
                a.execute("テスト","送信完了\n本文をここに記述する") ;
            }
        });
    }

    private class asyncTask extends android.os.AsyncTask{
        protected String account;
        protected String password;
        protected String title;
        protected String text;

        @Override
        protected Object doInBackground(Object... obj){

            EditText editText = findViewById(R.id.smtp_id);
            account = editText.getText().toString();
//          ハードコーディング①
            account = "doi";
            editText = findViewById(R.id.smtp_pw);
            password = editText.getText().toString();
//          ハードコーディング②
            password = "yng63dgs";
            title=(String)obj[0];
            text=(String)obj[1];

            java.util.Properties properties = new java.util.Properties();

            editText = findViewById(R.id.smtp_server);
            String server = editText.getText().toString();
//          ハードコーディング③
            server = "smtp.kiwi.ne.jp";
            properties.put("mail.smtp.host", server);
            properties.put("mail.smtp.auth", "true");

            editText = findViewById(R.id.smtp_port);
            String port = editText.getText().toString();
//          ハードコーディング④
            port = "587";
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.socketFactory.post", port);
//            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            final javax.mail.Message msg = new javax.mail.internet.MimeMessage(javax.mail.Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(account,password);
                }
            }));

            try {
                msg.setFrom(new javax.mail.internet.InternetAddress(account + "@kiwi.ne.jp"));

                editText = findViewById(R.id.send);
                String send = editText.getText().toString();
//          ハードコーディング⑤
                send = "doi@kiwi.ne.jp";
                msg.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(send));
                msg.setSubject(title);
                msg.setText(text);

                javax.mail.Transport.send(msg);

            } catch (Exception e) {
                return (Object)e.toString();
            }

            return (Object)"送信が完了しました";

        }
        @Override
        protected void onPostExecute(Object obj) {
            //画面にメッセージを表示する
            Toast.makeText(MainActivity.this,(String)obj,Toast.LENGTH_LONG).show();
            Log.d("ERROR", (String)obj);
        }
    }
}