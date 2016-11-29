package com.quickstart.utils

import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created at 29.11.16 13:02
 * @author Alexey_Ivanov
 */
fun addFragmentToActivity(@NonNull fm : FragmentManager, @NonNull fr : Fragment, frameId : Int){
    checkNotNull(fm)
    checkNotNull(fr)
    val transaction = fm.beginTransaction()
    transaction.add(frameId, fr)
    transaction.commit()
}

