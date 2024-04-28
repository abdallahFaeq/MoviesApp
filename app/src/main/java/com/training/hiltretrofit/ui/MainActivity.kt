package com.training.hiltretrofit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.training.hiltretrofit.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

// api key -> d9b5f12122396ecf4f14d929e8ecb6c1

/* in this app will show list of popular movies and when click item of
    this list will show details related to this item which clicked it

    i used retrofit and DI(Hilt) and image loader library and repository pattern

 */

/*
    response
    api
    utils
    di
    repository
    adapter
    ui
 */


