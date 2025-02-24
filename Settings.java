package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import java.util.Calendar;

public class Settings extends Fragment {

    private CalendarView calendarView; // Declare CalendarView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize the logout button
        Button logoutButton = view.findViewById(R.id.logout);

        // Initialize CalendarView correctly
        calendarView = view.findViewById(R.id.calendarView);

        // Get the current date
        Calendar calendar = Calendar.getInstance();
        long today = calendar.getTimeInMillis();

        // Set CalendarView to present date
        calendarView.setDate(today, true, true);

        // Set the click listener for the logout button
        logoutButton.setOnClickListener(v -> {
            // Navigate back to MainActivity
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish(); // Close the current activity
        });

        return view;
    }
}
