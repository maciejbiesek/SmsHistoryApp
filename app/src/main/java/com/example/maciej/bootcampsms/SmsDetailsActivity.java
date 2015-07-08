package com.example.maciej.bootcampsms;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class SmsDetailsActivity extends ActionBarActivity {

    public static final String SMS_KEY = "sms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_details);

        Intent i = getIntent();
        Sms sms = (Sms) i.getExtras().getSerializable(SMS_KEY);

        showSms(sms);
    }

    private void showSms(Sms sms) {
        TextView smsDetailsBody = (TextView) findViewById(R.id.sms_details_body);
        smsDetailsBody.setText(sms.getBody());

        TextView smsDetailsNumber = (TextView) findViewById(R.id.sms_details_number);
        smsDetailsNumber.setText(sms.getNumber());

        TextView smsDetailsDate = (TextView) findViewById(R.id.sms_details_date);
        smsDetailsDate.setText(sms.getDate());

    }
}
