package com.example.fragments_4_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.ListFragment
import androidx.fragment.app.commit
import com.example.fragments_4_2.fragments.ListUserFragment
import com.example.fragments_4_2.fragments.EditUserFragment
import androidx.fragment.app.add
import androidx.fragment.app.replace
import com.example.fragments_4_2.fragments.AddUserFragment


class MainActivity : AppCompatActivity(), ListUserFragment.OnListenerListFragment,
    EditUserFragment.OnListenerUserFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startListUserFragment()
    }

    fun startListUserFragment() {
        if (supportFragmentManager.findFragmentByTag(ListUserFragment.TAG) == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ListUserFragment>(R.id.container, ListUserFragment.TAG)
            }
        }
    }

    override fun onClickedUserFragment() {
        supportFragmentManager.commit {
            replace<EditUserFragment>(R.id.container, EditUserFragment.TAG)
            addToBackStack(EditUserFragment.TAG)
        }
    }

    override fun onAddClickedUserFragment() {
        supportFragmentManager.commit {
            replace<AddUserFragment>(R.id.container, AddUserFragment.TAG)
            addToBackStack(AddUserFragment.TAG)
        }
    }

    override fun onSaveClickedButton() {
        supportFragmentManager.popBackStack()
    }
}

