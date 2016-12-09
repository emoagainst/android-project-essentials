package com.quickstart.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View

/**
 * Created at 09.12.16 13:16
 * @author Alexey_Ivanov
 */
fun Context.layoutInflater(): LayoutInflater = LayoutInflater.from(this)
fun View.layoutInflater():LayoutInflater = LayoutInflater.from(this.context)
