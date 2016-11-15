package com.isanechek.pilotproject.utils.schedulers;

import android.support.annotation.NonNull;

import rx.Scheduler;

/**
 * Created by isanechek on 11/15/16.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
