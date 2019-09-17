package com.enthusi4stic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Navigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.networkItem -> supportFragmentManager.beginTransaction().replace(
                    R.id.frameLayout,
                    Fragment()
                ).commit()
                R.id.recyclerItem -> supportFragmentManager.beginTransaction().replace(
                    R.id.frameLayout,
                    Fragment()
                ).commit()
            }
            drawer.closeDrawers()
            true
        }
    }
}

