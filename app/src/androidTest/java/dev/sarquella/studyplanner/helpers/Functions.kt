package dev.sarquella.studyplanner.helpers

import java.lang.Thread.sleep


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

/**
 * Makes the test to wait for 1 second.
 * This is used while `verify` does not wait for a LiveData.postValue()
 * to finish.
 *
 * Once `verify` can be used after LiveData.postValue() waiting for it to finish
 * this function and all its calls should be removed.
 */
fun makeVerifyWaitForLiveData() {
    sleep(1000)
}