package com.example.fragments_4_2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.fragments_4_2.data.User
import com.example.fragments_4_2.databinding.FragmentUserBinding


class EditUserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding: FragmentUserBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenUserFragment()
    }

    private fun listenUserFragment() {
        setFragmentResultListener("userFrom") { _, bundle ->
            val userId = bundle.getInt("userId")
            val userName = bundle.getString("userName")!!
            val userLastName = bundle.getString("userLastName")!!
            val userImage = bundle.getString("userImage")!!
            val userPhone = bundle.getString("userPhone")!!
            with(binding) {
                etLastName.setText(userLastName)
                etName.setText(userName)
                etPhone.setText(userPhone)
                btnSave.setOnClickListener {
                    val user = User(
                        id = userId,
                        imagePerson = userImage,
                        lastName = etLastName.text.toString(),
                        name = etName.text.toString(),
                        phoneNumber = etPhone.text.toString()
                    )
                    getBackEditInfoUser(user)
                    (requireActivity() as OnListenerUserFragment).onSaveClickedButton()
                }
            }
        }
    }

    private fun getBackEditInfoUser(user: User) {
        setFragmentResult("userBack", Bundle().apply {
            putInt("userId", user.id)
            putString("userName", user.name)
            putString("userLastName", user.lastName)
            putString("userImage", user.imagePerson)
            putString("userPhone", user.phoneNumber)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    interface OnListenerUserFragment {
        fun onSaveClickedButton()
    }

    companion object {
        const val TAG = "user_fragment"
    }
}