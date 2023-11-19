package com.example.fragments_4_2.data

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.serpro69.kfaker.Faker

class UsersViewModel : ViewModel() {


    private val _users = MutableLiveData<MutableList<User>>()
    val users: LiveData<MutableList<User>>
        get() = _users
    private val faker = Faker()
    private val pictures = mutableListOf("/dog", "/paris", "/brazil,rio")
    private var lastId = 0

    init {
        val users = mutableListOf<User>()
        for (i in 0..10) {
            users.add(
                User(
                    id = i,
                    imagePerson = "https://loremflickr.com/320/240" + pictures.random(),
                    name = faker.name.firstName(),
                    lastName = faker.name.lastName(),
                    phoneNumber = faker.phoneNumber.phoneNumber()
                )
            )
        }
        _users.postValue(users)

    }

    fun addUser(name: String, lastName: String, phoneNumber: String) {
        val list = _users.value!!
        val user = User(
            id = getId(),
            imagePerson = "https://loremflickr.com/320/240",
            name = name,
            lastName = lastName,
            phoneNumber = phoneNumber
        )
        list.add(user)
        _users.postValue(list)

    }

    fun getUser(id: Int): User {
        val list = _users.value!!
        var user : User? = null
        for (i in list) {
            if (i.id == id) {
                user = i
            }
        }
        return user!!
    }

    fun deleteUser(id: List<Int>) {
        val list = _users.value!!
        val newList = mutableListOf<User>()
        for (i in list) {
            if (i.id !in id) {
                newList.add(i)
            }
        }
        _users.postValue(newList)
    }

    fun editUser(user: User) {
        val list = _users.value!!
        val newList = mutableListOf<User>()

        for (i in list) {
            if (i.id == user.id) {
                newList.add(user)
            } else {
                newList.add(i)
            }
        }
        _users.postValue(newList)
    }

    private fun getId() : Int {
        val list = _users.value!!
        return if (list.isEmpty()){
            lastId + 1
        }else{
            _users.value?.last()?.id!! + 1
        }
    }
}