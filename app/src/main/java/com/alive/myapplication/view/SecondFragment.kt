package com.alive.myapplication.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.alive.myapplication.R
import com.alive.myapplication.databinding.FragmentSecondBinding
import com.alive.myapplication.viewmodel.FirstFragmentViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstFragmentViewModel =  activity?.run {
            ViewModelProviders.of(this).get(FirstFragmentViewModel::class.java)
        }
        Log.d("vs","SecondFragment onViewCreated ${firstFragmentViewModel?.data?.value?.first}")
        val firstName = firstFragmentViewModel?.data?.value?.let {
            Log.d("vs",
                "First Name ${it.first}")
            String.format(resources.getString(R.string.first_name_text),
            it.first)
        }
        val secondName = firstFragmentViewModel?.data?.value?.let {
            String.format(resources.getString(R.string.second_name_text),
                it.second)
        }
        val dob = firstFragmentViewModel?.data?.value?.let {
            String.format(resources.getString(R.string.dob_text),
                it.third,it.third,it.third)
        }
        binding.firstNameText.setText(firstName)
        binding.lastNameText.setText(secondName)
        binding.dobText.setText(dob)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        compositeDisposable.clear()
    }
}