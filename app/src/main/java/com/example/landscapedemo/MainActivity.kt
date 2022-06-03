package com.example.landscapedemo

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.example.landscapedemo.database.DarkModeUtil
import com.example.landscapedemo.databinding.ActivityMainBinding
import com.example.landscapedemo.databinding.DialogChangePasswordBinding
import com.example.landscapedemo.databinding.DialogProfileBinding
import com.example.landscapedemo.model.Profile
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.*
class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{
//    private lateinit var database: DatabaseReference
//    private val currentUser = Firebase.auth.currentUser!!
    private val TAG = "12345"
    override fun onCreate(savedInstanceState: Bundle?) {
        loadLocate()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        database = Firebase.database.reference
        setContentView(binding.root)

        val hView = binding.navigationView.getHeaderView(0)
//        val ivAvatar = hView.findViewById<ImageView>(R.id.ivAvatar)
        val tvProfilename = hView.findViewById<TextView>(R.id.tvName)
        val ivLanguage = hView.findViewById<ImageView>(R.id.ivLanguage)
        val ivThememode = hView.findViewById<ImageView>(R.id.ivThememode)



        if(DarkModeUtil.isDarkMode) ivThememode.setImageResource(R.drawable.light)
        else ivThememode.setImageResource(R.drawable.dark)

        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if(language.equals("vi")){
            ivLanguage.setImageResource(R.drawable.english)
        } else {
            ivLanguage.setImageResource(R.drawable.vietnamese)
        }


//        database.child("profile").child(currentUser.uid).addValueEventListener(object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val profile = snapshot.getValue(Profile::class.java)
//                tvProfilename.text = profile!!.name
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d(TAG, "onCancelled: ")
//            }
//
//        })


        binding.navigationView.setNavigationItemSelectedListener(this)
        ivLanguage.setOnClickListener {


            if(language.equals("vi")){
                setLocate("en")
                ivLanguage.setImageResource(R.drawable.english)
                recreate()
            } else {
                setLocate("vi")
                ivLanguage.setImageResource(R.drawable.vietnamese)
                recreate()
            }
        }

        ivThememode.setOnClickListener {
            if(DarkModeUtil.isDarkMode){
                DarkModeUtil.isDarkMode = false
                ivThememode.setImageResource(R.drawable.light)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            } else {
                DarkModeUtil.isDarkMode = true
                ivThememode.setImageResource(R.drawable.dark)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }//onCreate

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navHome -> {

            }
            R.id.navProfile -> {
//                database.child("profile")
//                    .child(currentUser.uid).addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            var profile = snapshot.getValue(Profile::class.java)!!
//                            openDialogProfile(profile)
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            Log.d(TAG, "onCancelled: $error")
//
//                        }
//                    })
            }
            R.id.navChangepassword -> {
                openDialogChangePassword()
            }
            R.id.navLogout -> {
                Firebase.auth.signOut()
                super.onBackPressed()
            }
            else -> {

            }
        }
        return false
    }


    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
//            finishAffinity()
            super.onBackPressed()
        }

    }

    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        var language = sharedPreferences.getString("My_Lang","")
        if (language.isNullOrEmpty()) language = "en"
        setLocate(language)
    }

    fun openDialogChangePassword(){
        val binding = DialogChangePasswordBinding.inflate(LayoutInflater.from(this))
        val builder = Dialog(this, R.style.Theme_LandscapeDemo)
        builder.setContentView(binding.root)
        builder.show()

        binding.ivBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun openDialogProfile(profile: Profile) {
        val binding = DialogProfileBinding.inflate(LayoutInflater.from(this))
        val builder = Dialog(this, R.style.Theme_LandscapeDemo)
        builder.setContentView(binding.root)
        builder.show()


        binding.tvUsername.text = profile.username
        binding.etPhone.text = Editable.Factory.getInstance().newEditable(profile.mobile)
        binding.etEmail.text = Editable.Factory.getInstance().newEditable(profile.username)
        binding.etName.text = Editable.Factory.getInstance().newEditable(profile.name)
        binding.etDOB.text = Editable.Factory.getInstance().newEditable(profile.dob)

        binding.etDOB.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                binding.etDOB.setText("$dayOfMonth/$monthOfYear/$year")
            }, year, month, day)
            dpd.show()
        }

        binding.ivBack.setOnClickListener {
            builder.cancel()
        }
        binding.ivCheck.setOnClickListener {
            profile.name = binding.etName.text.toString()
            profile.email = binding.etEmail.text.toString()
            profile.dob = binding.etDOB.text.toString()
            profile.mobile = binding.etPhone.text.toString()
//            database.child("profile").child(currentUser.uid).setValue(profile)
            builder.cancel()
        }
    }

    companion object{
        private lateinit var binding: ActivityMainBinding
    }

}