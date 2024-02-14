package com.example.homework4part2

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import com.example.homework4part2.databinding.ActivityMainBinding
import com.example.homework4part2.model.User
import com.example.homework4part2.navigator.Navigator
import com.example.homework4part2.navigator.ResultListener


class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, UsersFragment())
                .commit()
        }

        setContentView(binding.root)
    }

    override fun showEditUserFragment(user: User) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, EditUserFragment.newInstance(user))
            .commit()
    }

    override fun <T : Parcelable> setEditionResult(result: T) {
        supportFragmentManager.setFragmentResult(USER, bundleOf(USER to result))
    }

    override fun <T : Parcelable> listenEditionResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    ) {
        supportFragmentManager.setFragmentResultListener(USER, owner) { key, bundle ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                listener.invoke(bundle.getParcelable(key, clazz)!!)
            } else {
                listener.invoke(bundle.getParcelable(key)!!)
            }
        }
    }

}