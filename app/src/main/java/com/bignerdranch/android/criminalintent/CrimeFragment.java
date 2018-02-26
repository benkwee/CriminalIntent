package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * Created by ben on 2/21/18.
 */

public class CrimeFragment extends Fragment {

  private static final String ARG_CRIME_ID = "crime_id";
  private static final String DIALOG_DATE = "DialogDate";
  private static final String DIALOG_TIME = "DialogTime";

  private static final int REQUEST_DATE = 0;
  private static final int REQUEST_TIME = 1;

  private Crime mCrime;
  private EditText mTitleField;
  private Button mDateButton;
  private Button mTimeButton;
  private CheckBox mSolvedCheckBox;

  public static CrimeFragment newInstance(UUID crimeId) {
    Bundle bundle = new Bundle();
    bundle.putSerializable(ARG_CRIME_ID, crimeId);

    CrimeFragment crimeFragment = new CrimeFragment();
    crimeFragment.setArguments(bundle);
    return crimeFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
    mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_crime, container, false);

    mTitleField = (EditText) view.findViewById(R.id.crime_title);
    mTitleField.setText(mCrime.getTitle());
    mTitleField.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mCrime.setTitle(charSequence.toString());
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });


    mDateButton = (Button) view.findViewById(R.id.crime_date);
    updateDate();
    mDateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FragmentManager manager = getFragmentManager();
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mCrime.getDate());
        datePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
        datePickerFragment.show(manager, DIALOG_DATE);
      }
    });

    mTimeButton = (Button) view.findViewById(R.id.crime_time);
    updateTime();
    mTimeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FragmentManager manager = getFragmentManager();
        TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mCrime.getDate());
        timePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
        timePickerFragment.show(manager, DIALOG_TIME);
      }
    });

    mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
    mSolvedCheckBox.setChecked(mCrime.getSolved());
    mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        mCrime.setSolved(isChecked);
      }
    });

    return view;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != Activity.RESULT_OK) {
      return;
    }

    if (requestCode == REQUEST_DATE) {
      Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
      mCrime.setDate(date);
      updateDate();
    }

    if (requestCode == REQUEST_TIME) {
      Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
      mCrime.setDate(date);
      updateTime();
    }
  }

  private void updateDate() {
    DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
    mDateButton.setText(dateFormat.format(mCrime.getDate()));
  }

  private void updateTime() {
    DateFormat dateFormat = new SimpleDateFormat("h:mm a");
    mTimeButton.setText(dateFormat.format(mCrime.getDate()));
  }
}
