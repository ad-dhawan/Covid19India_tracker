package com.example.covid19tracker.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.covid19tracker.R
import com.example.covid19tracker.fragments.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)

        setUpToolbar()
        openCases()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null){
                previousMenuItem?.isChecked = false

                it.isCheckable = true
                it.isChecked = true
                previousMenuItem = it

            }
            when (it.itemId){
                R.id.cases -> {
                    openCases()
                    drawerLayout.closeDrawers()
                }

                R.id.news -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        NewsFragment()
                    )
                        .commit()

                    supportActionBar?.title="News"
                    drawerLayout.closeDrawers()
                }

                R.id.global -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        GlobalFragment()
                    )
                        .commit()

                    supportActionBar?.title="Global"
                    drawerLayout.closeDrawers()
                }

                R.id.preventions -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        PreventionsFragment()
                    )
                        .commit()

                    supportActionBar?.title="Preventions"
                    drawerLayout.closeDrawers()
                }

            }

            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "BookHub"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun openCases() {
        supportFragmentManager.beginTransaction().replace(
            R.id.frameLayout,
            CasesFragment()
        ).commit()

        supportActionBar?.title="Statistics"
        navigationView.setCheckedItem(R.id.cases)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)

        when(frag){
            !is CasesFragment -> openCases()

            else -> super.onBackPressed()
        }
    }
}
