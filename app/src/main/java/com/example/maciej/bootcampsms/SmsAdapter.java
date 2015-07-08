package com.example.maciej.bootcampsms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ViewAnimator;

import java.util.ArrayList;
import java.util.List;


public class SmsAdapter extends BaseAdapter {

    private List<Sms> smsList = new ArrayList<Sms>();
    private Context context;


    public SmsAdapter(Context context, List<Sms> list) {
        this.context = context;
        this.smsList = list;
    }

    @Override
    public int getCount() {
        return smsList.size();
    }

    @Override
    public Sms getItem(int position) {
        return smsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View smsView;

        if (convertView == null) {
            smsView = LayoutInflater.from(context).inflate(getLayoutId(position), parent, false);
        } else {
            smsView = convertView;
        }

        bindSmsToView(getItem(position), smsView);

        return smsView;
    }

    private int getLayoutId(int position) {
        if (getItemViewType(position) == ViewType.INBOX.ordinal()) {
            return R.layout.inbox_row;
        } else {
            return R.layout.sent_row;
        }
    }

    enum ViewType {
        INBOX, SENT;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType() == "inbox" ? ViewType.INBOX.ordinal() : ViewType.SENT.ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return ViewType.values().length;
    }

    private void bindSmsToView(Sms sms, View smsView) {
        TextView smsBody = (TextView) smsView.findViewById(R.id.sms_body);
        smsBody.setText(sms.getBody());

        TextView smsNumber = (TextView) smsView.findViewById(R.id.sms_number);
        smsNumber.setText(sms.getNumber());

        TextView smsDate = (TextView) smsView.findViewById(R.id.sms_date);
        smsDate.setText(sms.getDate());
    }

}
