package com.quickstart.utils

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * Created at 29.11.16 11:34
 * @author Alexey_Ivanov
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class FragmentScoped
