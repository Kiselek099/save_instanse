package com.example.users_directory

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    lateinit var toolbarTB: Toolbar
    lateinit var usersLV: ListView
    lateinit var nameET: EditText
    lateinit var ageET: EditText
    lateinit var saveBTN: Button
    lateinit var userViewModel: UserViewModel

    @SuppressLint("MissingInflatedId")
    val users: MutableList<User> = mutableListOf()
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exitApp -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toolbarTB = findViewById(R.id.toolbarTB)
        usersLV = findViewById(R.id.usersLV)
        nameET = findViewById(R.id.nameET)
        ageET = findViewById(R.id.ageET)
        saveBTN = findViewById(R.id.saveBTN)
        setSupportActionBar(toolbarTB)
        title = "Каталог пользователей"
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf<User>())
        usersLV.adapter = adapter
        userViewModel.userList.observe(this) { users ->
            adapter.clear()
            adapter.addAll(users)
            adapter.notifyDataSetChanged()
        }
        saveBTN.setOnClickListener {
            if (nameET.text.isEmpty() || ageET.text.isEmpty()) return@setOnClickListener
            val name = nameET.text.toString()
            val age = ageET.text.toString().toInt()
            val updatedList = userViewModel.userList.value ?: mutableListOf()
            updatedList.add(User(name, age))
            userViewModel.userList.value = updatedList
            nameET.text.clear()
            ageET.text.clear()
        }
        usersLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val user = adapter.getItem(position)
                user?.let {
                    val updatedList = userViewModel.userList.value ?: mutableListOf()
                    updatedList.remove(it)
                    userViewModel.userList.value = updatedList
                    Toast.makeText(this, "Пользователь $it удален", Toast.LENGTH_LONG).show()
                }
            }

    }
}