package com.example.homework4part2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework4part2.databinding.FragmentUsersBinding
import com.example.homework4part2.model.User
import com.example.homework4part2.model.UserListener
import com.example.homework4part2.model.UserService
import com.example.homework4part2.navigator.navigator


class UsersFragment : Fragment() {

    private lateinit var adapter: UsersAdapter
    private lateinit var binding: FragmentUsersBinding
    private lateinit var userService: UserService
    private val userListener: UserListener = {
        adapter.users = it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userService = (requireActivity().applicationContext as App).userService
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater,container,false)
        adapter = UsersAdapter { user: User -> navigator().showEditUserFragment(user) }
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        userService.addListener(userListener)
        navigator().listenEditionResult(User::class.java, viewLifecycleOwner) {user -> userService.editUser(user)}
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        userService.removeListener(userListener)
    }
}