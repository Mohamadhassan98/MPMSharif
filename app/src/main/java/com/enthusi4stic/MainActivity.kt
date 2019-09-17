package com.enthusi4stic

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private fun Fragment.transaction() =
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_main_frame_layout, this)
            .commit()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity_main_navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_items_network_item -> NetworkFragment().transaction()
                R.id.navigation_items_recycler_item -> RecyclerFragment().transaction()
            }
            activity_main_drawer_layout.closeDrawers()
            true
        }
        NetworkFragment().transaction()
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                if(activity_main_drawer_layout.isDrawerOpen(activity_main_navigation_view))
                    activity_main_drawer_layout.closeDrawer(activity_main_navigation_view)
                else
                    activity_main_drawer_layout.openDrawer(activity_main_navigation_view)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

