package com.alive.myapplication.view

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.alive.myapplication.R
import com.alive.myapplication.databinding.FragmentFirstBinding
import com.alive.myapplication.utils.DateUtils.getAgeNumber
import com.alive.myapplication.viewmodel.FirstFragmentViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var viewModel: FirstFragmentViewModel

    private val firstFragmentDisposables = CompositeDisposable()

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(FirstFragmentViewModel::class.java)
        }!!
        binding.firstName.editText?.addTextChangedListener(loginTextWatcher)
        binding.secondName.editText?.addTextChangedListener(loginTextWatcher)
        // DOB Picker
        val builder = MaterialDatePicker.Builder.datePicker()
        val pick = builder.build()
        binding.calendarButton.setOnClickListener{
            pick.show(childFragmentManager,pick.toString())
        }
        var selectedDate = "default"

        pick.addOnPositiveButtonClickListener {
            Log.d("vs",
                "Date String = ${pick.headerText}:: Date epoch value = ${it}")
            selectedDate = pick.headerText

            binding.calendarTextView.text =
                String.format(resources.getString(R.string.dob_text),selectedDate)

            binding.calendarTextView.isVisible = true

            val simpleDateFormat = SimpleDateFormat("MMMM d, yyyy",Locale.US)
            val date = simpleDateFormat.parse(selectedDate)
            Log.d("vs","Selected DOB ${date}")
            val dob:Long = date.time
            val getCurrentDay: String? = context?.let { it1 -> getAgeNumber(it1,dob) }
            if (getCurrentDay != null) {
                selectedDate = getCurrentDay
            }
            Log.d("vs","Todays Date ${getCurrentDay}")

               // pick.headerText
        }
        binding.nextButton.setOnClickListener {
            binding.calendarTextView.let {
                if(!(it.isVisible)) {
                    Toast.makeText(context," Please Select DOB", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.data.value = Triple(binding.firstNameText.text.toString(),
                        binding.secondNameText.text.toString(),
                        selectedDate)
                    Log.d("vs","onNext First ${viewModel?.data?.value?.first}")
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        firstFragmentDisposables.clear()
    }

    private val loginTextWatcher:TextWatcher = object :TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val firstName:String = binding.firstNameText.text.toString()
            val lastName: String = binding.secondNameText.text.toString()
            binding.nextButton.isEnabled = !(firstName.isNullOrEmpty() || lastName.isNullOrEmpty())
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }
}