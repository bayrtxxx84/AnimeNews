package com.example.animenews.utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.animenews.R


class FunctionsFragments(val manager: FragmentManager) {

    private val SHIMMER_TAG = "shimmer"


    fun ReplaceFragment(
        newFragment: Fragment, idFrameLayout: Int,
        args: List<Bundle>?, tag: String?
    ) {
        with(manager.beginTransaction()) {
            args?.forEach() { bundle ->
                newFragment.arguments = bundle
            }

            this.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            this.replace(idFrameLayout, newFragment, tag)

            if (!tag.isNullOrEmpty()) {
                this.addToBackStack(null)
            }

            this.commit()
        }
    }

    fun ReplaceShimmerFragment(newFragment: Fragment, idFrameLayout: Int) {
        with(manager.beginTransaction()) {
            this.replace(idFrameLayout, newFragment, SHIMMER_TAG)
            this.commit()
        }
    }

    fun RemoveShimmerFragment() {
        RemoveFragment(SHIMMER_TAG)
    }

    fun RemoveFragment(tag: String) {
        val fragment = manager.findFragmentByTag(tag)
        if (fragment != null) {
            manager.beginTransaction().remove(fragment).commit()
        }
    }


    fun AddFragment(
        newFragment: Fragment, idFrameLayout: Int,
        args: List<Bundle>?, tag: String?
    ) {
        args?.forEach() { bundle ->
            newFragment.arguments = bundle
        }
        with(manager.beginTransaction()) {
            this.add(idFrameLayout, newFragment, tag)
            this.addToBackStack(null)
            this.commit()
        }
    }
}