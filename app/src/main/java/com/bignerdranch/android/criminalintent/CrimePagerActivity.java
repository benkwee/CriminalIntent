package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

/**
 * Created by ben on 2/25/18.
 */

public class CrimePagerActivity extends AppCompatActivity {

  private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

  private ViewPager mViewPager;
  private Button mFirstButton;
  private Button mLastButton;
  private List<Crime> mCrimes;

  public static Intent newIntent(Context packageContext, UUID crimeId) {
    Intent intent = new Intent(packageContext, CrimePagerActivity.class);
    intent.putExtra(EXTRA_CRIME_ID, crimeId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_crime_pager);

    UUID crimeId = (UUID) getIntent()
        .getSerializableExtra(EXTRA_CRIME_ID);

    mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);


    mCrimes = CrimeLab.get(this).getCrimes();
    FragmentManager fragmentManager = getSupportFragmentManager();
    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
      @Override
      public Fragment getItem(int position) {
        Crime crime = mCrimes.get(position);
        return CrimeFragment.newInstance(crime.getID());
      }

      @Override
      public int getCount() {
        return mCrimes.size();
      }
    });

    for (int i = 0; i < mCrimes.size(); i++) {
      if (mCrimes.get(i).getID().equals(crimeId)) {
        mViewPager.setCurrentItem(i);
        break;
      }
    }

    mFirstButton = (Button) findViewById(R.id.jump_first_button);
    mFirstButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mViewPager.setCurrentItem(0);
      }
    });

    mLastButton = (Button) findViewById(R.id.jump_last_button);
    mLastButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1);
      }
    });

    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mViewPager.getCurrentItem() == 0) {
          mFirstButton.setVisibility(View.INVISIBLE);
        } else {
          mFirstButton.setVisibility(View.VISIBLE);
        }

        if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
          mLastButton.setVisibility(View.INVISIBLE);
        } else {
          mLastButton.setVisibility(View.VISIBLE);
        }
      }

      @Override
      public void onPageSelected(int position) {

      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }
}
