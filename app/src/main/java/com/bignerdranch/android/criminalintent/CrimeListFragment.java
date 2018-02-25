package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ben on 2/21/18.
 */

public class CrimeListFragment extends Fragment {

  private RecyclerView mCrimeRecyclerView;
  private CrimeAdapter mAdapter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

    mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
    mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    updateUI();

    return view;
  }

  private void updateUI() {
    CrimeLab crimeLab = CrimeLab.get(getActivity());
    List<Crime> crimes = crimeLab.getCrimes();

    mAdapter = new CrimeAdapter(crimes);
    mCrimeRecyclerView.setAdapter(mAdapter);
  }

  private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Crime mCrime;
    private TextView mTitleTextView;
    private TextView mDateTextView;
    private Button mPoliceButton;
    private ImageView mSolvedImageView;

    public CrimeHolder(LayoutInflater inflator, ViewGroup parent, int viewType) {
      super(inflator.inflate(viewType, parent, false));
      itemView.setOnClickListener(this);

      mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
      mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
      mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
      mPoliceButton = (Button) itemView.findViewById(R.id.crime_contact_police);
    }

    public void bind(Crime crime) {
      mCrime = crime;
      mTitleTextView.setText(crime.getTitle());

      DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
      mDateTextView.setText(dateFormat.format(crime.getDate()));

      if (mSolvedImageView != null) {
        mSolvedImageView.setVisibility(crime.getSolved() ? View.VISIBLE : View.GONE);
      }
      if (mPoliceButton != null && crime.getRequiresPolice() && !crime.getSolved()) {
        mPoliceButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Toast.makeText(getActivity(), "Contacting Police...", Toast.LENGTH_LONG).show();
          }
        });
      }
    }

    @Override
    public void onClick(View view) {
      Toast.makeText(getActivity(),
          mCrime.getTitle() + " clicked!",
          Toast.LENGTH_SHORT)
          .show();
    }
  }

  private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
    private List<Crime> mCrimes;
    private int mNormalCrime = R.layout.list_item_crime;
    private int mPoliceCrime = R.layout.list_item_crime_requires_police;

    public CrimeAdapter(List<Crime> crimes) {
      mCrimes = crimes;
    }

    @Override
    public int getItemViewType(int position) {
      if (mCrimes.get(position).getRequiresPolice() && !mCrimes.get(position).getSolved()) {
        return mPoliceCrime;
      }
      return mNormalCrime;
    }

    @Override
    public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

      return new CrimeHolder(layoutInflater, parent, viewType);
    }

    @Override
    public void onBindViewHolder(CrimeHolder holder, int position) {
      Crime crime = mCrimes.get(position);
      holder.bind(crime);
    }

    @Override
    public int getItemCount() {
      return mCrimes.size();
    }
  }
}
