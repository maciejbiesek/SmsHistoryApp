package com.example.maciej.bootcampsms;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewAnimator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class SmsListActivity extends ActionBarActivity {

    private ListView listView;
    private SmsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_list);

        initializeList();
    }

    private void initializeList() {
        listView = (ListView) findViewById(R.id.sms_list);
        adapter = new SmsAdapter(this, getSms());

        ViewAnimator animator = (ViewAnimator) findViewById(R.id.animator);
        animator.setDisplayedChild(1);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sms sms = adapter.getItem(position);
                showSms(sms);
            }
        });

    }

    private List<Sms> getSms() {
        List<Sms> lstSms = new ArrayList<Sms>();
        ContentResolver contentResolver = getContentResolver();

        Cursor cursorInbox = contentResolver.query(Uri.parse("content://sms/inbox"),
                new String[] { "body", "address", "date" },
                null,
                null,
                null);

        int totalInbox = cursorInbox.getCount();

        if (cursorInbox.moveToFirst()) {
            for (int i = 0; i < totalInbox; i++) {
                Sms sms = new Sms(cursorInbox.getString(0), cursorInbox.getString(1), timestampToDateString(cursorInbox.getString(2)), "inbox");
                lstSms.add(sms);

                cursorInbox.moveToNext();
            }
        } else {
            Log.i("DEBUG", "No SMS in Inbox");
        }
        cursorInbox.close();

        Cursor cursorSent = contentResolver.query(Uri.parse("content://sms/sent"),
                new String[] { "body", "address", "date" },
                null,
                null,
                null);

        int totalSent = cursorSent.getCount();

        if (cursorSent.moveToFirst()) {
            for (int i = 0; i < totalSent; i++) {
                Sms sms = new Sms(cursorSent.getString(0), cursorSent.getString(1), timestampToDateString(cursorSent.getString(2)), "sent");
                lstSms.add(sms);

                cursorSent.moveToNext();
            }
        } else {
            Log.i("DEBUG", "No SMS in Sent");
        }
        cursorSent.close();

        Collections.sort(lstSms, new Comparator<Sms>() {
            public int compare(Sms s1, Sms s2) {
                return s2.getDate().compareTo(s1.getDate());
            }
        });

        return lstSms;

    }

    private String timestampToDateString(String timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(timestamp));
        Date date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return dateFormat.format(date);
    }

    private void showSms(Sms sms) {
        Intent i = new Intent(this, SmsDetailsActivity.class);
        i.putExtra(SmsDetailsActivity.SMS_KEY, sms);
        startActivity(i);
    }


}
