package com.jm.newvista.enterprise.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jm.newvista.enterprise.R;
import com.jm.newvista.enterprise.bean.CheckinEntity;
import com.jm.newvista.enterprise.util.NetworkUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TicketDialogFragment extends DialogFragment {
    private TicketDialogListener mListener;

    private CheckinEntity checkinEntity;

    private ImageView avatar;
    private TextView username;
    private TextView email;
    private TextView movieTitle;
    private TextView showtime;
    private TextView auditoriumName;
    private TextView seatLocation;
    private TextView isUsed;
    private TextView welcome;

    public void show(FragmentManager manager) {
        show(manager, "DescriptionDialogFragment");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_ticket_alert, null);
        initView(view);
        updateView();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setPositiveButton(R.string.back, null);
        return builder.create();
    }

    private void initView(View view) {
        avatar = view.findViewById(R.id.avatar);
        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        movieTitle = view.findViewById(R.id.movieTitle);
        showtime = view.findViewById(R.id.showtime);
        auditoriumName = view.findViewById(R.id.auditoriumName);
        seatLocation = view.findViewById(R.id.seatLocation);
        isUsed = view.findViewById(R.id.isUsed);
        welcome = view.findViewById(R.id.welcome);
    }

    private void updateView() {
        Glide.with(getContext()).load(NetworkUtil.GET_AVATAR_URL + checkinEntity.getCustomer().getId()).into(avatar);
        username.setText(checkinEntity.getCustomer().getUsername());
        email.setText(checkinEntity.getCustomer().getEmail());
        movieTitle.setText(checkinEntity.getOrder().getMovieTitle());

        Date date = checkinEntity.getOrder().getShowtime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm:ss aa MMM d, yyyy", Locale.ENGLISH);
        String dateStr = simpleDateFormat.format(date);
        showtime.setText(dateStr);

        auditoriumName.setText(checkinEntity.getOrder().getAuditoriumName());
        seatLocation.setText(checkinEntity.getOrder().getSeatLocation());

        if (checkinEntity.getOrder().getIsUsed()) isUsed.setText("Yes");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TicketDialogListener) {
            mListener = (TicketDialogListener) context;
            checkinEntity = mListener.onGetTicketDialogListener();
        } else {
            throw new RuntimeException(context.toString() + " must implement TicketDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface TicketDialogListener {
        CheckinEntity onGetTicketDialogListener();
    }
}
