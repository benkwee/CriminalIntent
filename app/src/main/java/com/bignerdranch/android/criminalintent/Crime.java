package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ben on 2/21/18.
 */

public class Crime {

  private UUID mID;
  private String mTitle;
  private Date mDate;
  private Boolean mSolved;
  private Boolean mRequiresPolice;

  public Crime() {
    mID = UUID.randomUUID();
    mDate = new Date();
    mSolved = false;
    mRequiresPolice = false;
  }

  public UUID getID() {
    return mID;
  }

  public void setID(UUID ID) {
    mID = ID;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public Date getDate() {
    return mDate;
  }

  public void setDate(Date date) {
    mDate = date;
  }

  public Boolean getSolved() {
    return mSolved;
  }

  public void setSolved(Boolean solved) {
    mSolved = solved;
  }

  public Boolean getRequiresPolice() {
    return mRequiresPolice;
  }

  public void setRequiresPolice(Boolean requiresPolice) {
    mRequiresPolice = requiresPolice;
  }
}
