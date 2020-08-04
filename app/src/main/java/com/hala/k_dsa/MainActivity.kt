package com.hala.k_dsa

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.hala.k_dsa.ui.optionlist.OptionItemModel


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    val mFunctions: FirebaseFunctions = FirebaseFunctions.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        initFunctions();

    }


    private fun initFunctions() {

        getDataStructures()
            .addOnCompleteListener(OnCompleteListener<Map<*, *>?> { task ->
                if (!task.isSuccessful) {
                    val e = task.exception
                    if (e is FirebaseFunctionsException) {
                        val ffe = e as FirebaseFunctionsException
                        val code = ffe.code
                        val details = ffe.details
                    }
                    // ...
                }
                else {

                    Toast.makeText(this,task.result.toString(),Toast.LENGTH_SHORT).show()
                    renderLandingScreen(task.result  as HashMap<String, Any>)
                }
                // ...
            })
    }

    private fun renderLandingScreen(result: HashMap<String, Any>) {

        if(result!=null && result.size>0){
            var optionsList = ArrayList<OptionItemModel>()
            for ((key, value) in result) {
               optionsList.add(OptionItemModel(key,value))
            }
            
            openListofOptionsFragment(optionsList)
        }
        else
            Toast.makeText(this,result.toString(),Toast.LENGTH_SHORT).show()

    }

    private fun openListofOptionsFragment(optionsList: ArrayList<OptionItemModel>) {


        val outState = Bundle()
        outState.putParcelableArrayList("listOptions",optionsList)

      //    val bundle = ("listOptions" to optionsList)
//

//        val map = LinkedHashMap<String, Any>()
//       val wrap = ParcelableMap<String,Any>(result)
//        val bundle = bundleOf("listOptions" to wrap)
//        val bundle= Bundle().putParcelable("listOptions", wrap)

       findNavController(R.id.nav_host_fragment).navigate(R.id.nav_gallery,outState)

    }

    private fun getDataStructures(): Task<Map<*, *>> {

        return mFunctions
            .getHttpsCallable("getUrlStoreValueMap")
            .call()
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data as Map<*, *>
                result
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
