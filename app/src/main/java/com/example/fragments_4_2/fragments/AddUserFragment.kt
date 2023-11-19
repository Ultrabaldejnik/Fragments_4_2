package com.example.fragments_4_2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.example.fragments_4_2.databinding.FragmentAddUserBinding


class AddUserFragment : Fragment() {

    private var _binding: FragmentAddUserBinding? = null
    private val binding: FragmentAddUserBinding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            listenUserFragment()
        }
    }

    private fun listenUserFragment() {
        setFragmentResult("userAdd", Bundle().apply {
            with(binding) {
                putString("lastName", etLastName.text.toString())
                putString("name", etName.text.toString())
                putString("phoneNumber", etPhone.text.toString())
            }
        })
        (requireActivity() as EditUserFragment.OnListenerUserFragment).onSaveClickedButton()
    }

    companion object {
        const val TAG = "add_user_fragment"
    }
}