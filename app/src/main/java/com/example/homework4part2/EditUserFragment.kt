package com.example.homework4part2

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.homework4part2.databinding.FragmentEditUserBinding
import com.example.homework4part2.model.User
import com.example.homework4part2.navigator.navigator


const val USER = "user"

class EditUserFragment : Fragment() {

    private var user: User? = null

    private lateinit var binding: FragmentEditUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(USER, User::class.java)
            } else {
                it.getParcelable(USER)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditUserBinding.inflate(inflater,container,false)
        with (binding) {
            nameEt.setText(user?.name)
            surnameEt.setText(user?.surname)
            numberEt.setText(user?.number)
            Glide.with(editAvatarIv.context)
                .load(user!!.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_avatar_error)
                .into(editAvatarIv)
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()

        with(binding) {
            navigator().setEditionResult(
                User(
                    user!!.id,
                    nameEt.text.toString(),
                    surnameEt.text.toString(),
                    numberEt.text.toString(),
                    user!!.avatarUrl
                )
            )
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(user: User) =
            EditUserFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER, user)
                }
            }
    }
}