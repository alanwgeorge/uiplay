package com.example.android.uiplay.v5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.uiplay.App;
import com.example.android.uiplay.R;

public class OfferFragment extends Fragment {
    private static final String ARG_MESSAGE_MAJOR = "messageMajor";
    private static final String ARG_MESSAGE_MINOR = "messageMinor";

    private String messageMajor;
    private String messageMinor;

    public static OfferFragment newInstance(String messageMajor, String messageMinor) {
        OfferFragment fragment = new OfferFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE_MAJOR, messageMajor);
        args.putString(ARG_MESSAGE_MINOR, messageMinor);
        fragment.setArguments(args);
        return fragment;
    }

    public OfferFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            messageMajor = getArguments().getString(ARG_MESSAGE_MAJOR);
            messageMinor = getArguments().getString(ARG_MESSAGE_MINOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_view_page, container, false);

        TextView messageMajorTextView = (TextView) view.findViewById(R.id.message_major);
        TextView messageMinorTextView = (TextView) view.findViewById(R.id.message_minor);
        Button offerButton = (Button) view.findViewById(R.id.offer_button);


        if (messageMajor != null) {
            messageMajorTextView.setText(messageMajor);
        } else {
            messageMajorTextView.setText("No message found");
        }

        if (messageMinor != null) {
            messageMinorTextView.setText(messageMinor);
        } else {
            messageMinorTextView.setText("No message found");
        }

        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity(), "offer accepted", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, App.dpToPx(100));
                toast.show();
            }
        });

        return view;
    }
}
