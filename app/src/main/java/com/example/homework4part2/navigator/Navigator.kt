package com.example.homework4part2.navigator

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.homework4part2.model.User

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun showEditUserFragment(user: User)

    fun <T: Parcelable> setEditionResult(result: T)

    fun <T: Parcelable> listenEditionResult(clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)

}