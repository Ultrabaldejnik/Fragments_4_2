package com.example.fragments_4_2.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fragments_4_2.UserAdapter
import com.example.fragments_4_2.data.User
import com.example.fragments_4_2.data.UsersViewModel
import com.example.fragments_4_2.databinding.FragmentListUserBinding


class ListUserFragment : Fragment() {

    private val binding: FragmentListUserBinding
        get() = _binding!!
    private var _binding: FragmentListUserBinding? = null

    private lateinit var vm: UsersViewModel
    private lateinit var usersListAdapter: UserAdapter
    private lateinit var listContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
        initUI()
        initRV()
    }

    override fun onStart() {
        super.onStart()
        getInfoFromEditUserFragment()
        getInfoFromAddUserFragment()
    }

    private fun getInfoFromAddUserFragment() {
        setFragmentResultListener("userAdd") { _, bundle ->
            with(bundle) {
                vm.addUser(
                    name = getString("name")!!,
                    lastName = getString("lastName")!!,
                    phoneNumber = getString("phoneNumber")!!
                )
            }
        }
    }

    private fun getInfoFromEditUserFragment() {
        setFragmentResultListener("userBack") { _, bundle ->
            val user = User(
                id = bundle.getInt("userId"),
                name = bundle.getString("userName")!!,
                lastName = bundle.getString("userLastName")!!,
                imagePerson = bundle.getString("userImage")!!,
                phoneNumber = bundle.getString("userPhone")!!,
            )
            vm.editUser(user)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    interface OnListenerListFragment {
        fun onClickedUserFragment()
        fun onAddClickedUserFragment()
    }

    fun initVM() {
        vm = ViewModelProvider(this)[UsersViewModel::class.java]
        vm.users.observe(this) {
            usersListAdapter.submitList(it)
        }
    }

    fun initUI() {
        with(binding) {
            btnAdd.setOnClickListener {
                (requireActivity() as OnListenerListFragment).onAddClickedUserFragment()
            }
            btnDelete.setOnClickListener {
                groupDeleteContacts.visibility = View.VISIBLE
                btnAdd.visibility = View.GONE
                btnDelete.visibility = View.GONE
                usersListAdapter.mode = false
                rvUsers.adapter?.notifyDataSetChanged()
            }
            btnClose.setOnClickListener {
                groupDeleteContacts.visibility = View.GONE
                btnAdd.visibility = View.VISIBLE
                btnDelete.visibility = View.VISIBLE
                usersListAdapter.mode = true
                rvUsers.adapter?.notifyDataSetChanged()
            }
            btnDeleteSelected.setOnClickListener {
                groupDeleteContacts.visibility = View.GONE
                btnAdd.visibility = View.VISIBLE
                btnDelete.visibility = View.VISIBLE
                vm.deleteUser(usersListAdapter.getListForDelete())
                usersListAdapter.mode = true
                rvUsers.adapter?.notifyDataSetChanged()
            }
        }
    }

    fun initRV() {
        usersListAdapter = UserAdapter(listContext)
        usersListAdapter.onUserClicked = { id ->
            val user = vm.getUser(id)
            setFragmentResult("userFrom", Bundle().apply {
                putInt("userId", user.id)
                putString("userName", user.name)
                putString("userLastName", user.lastName)
                putString("userImage", user.imagePerson)
                putString("userPhone", user.phoneNumber)
            })
            (requireActivity() as OnListenerListFragment).onClickedUserFragment()
        }
        binding.rvUsers.adapter = usersListAdapter
        binding.rvUsers.layoutManager =
            LinearLayoutManager(listContext, LinearLayoutManager.VERTICAL, false)
    }

    companion object {
        const val TAG = "list_user_fragment"
    }
}