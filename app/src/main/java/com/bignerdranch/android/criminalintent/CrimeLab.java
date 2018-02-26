package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ben on 2/21/18.
 */

public class CrimeLab {

  private static CrimeLab sCrimeLab;

  private Map<UUID,Crime> mCrimes;

  public static CrimeLab get(Context context) {
    if (sCrimeLab == null) {
      sCrimeLab = new CrimeLab(context);
    }
    return sCrimeLab;
  }

  private CrimeLab(Context context) {
    mCrimes = new LinkedHashMap<>();
  }

  public void addCrime(Crime c) {
    mCrimes.put(c.getID(), c);
  }

  public List<Crime> getCrimes() {
    return new ArrayList<>(mCrimes.values());
  }

  public Crime getCrime(UUID id) {
    return mCrimes.get(id);
  }

  public void deleteCrime(UUID id) {
    mCrimes.remove(id);
  }
}
