package com.example.contactsbook.ui.create

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.contactsbook.App
import com.example.contactsbook.R
import com.example.contactsbook.data.model.Contact
import com.example.contactsbook.databinding.FragmentCreateContactBinding
import com.example.contactsbook.loadImage

class CreateContactFragment : Fragment() {

    private var _binding: FragmentCreateContactBinding? = null
    private val binding get() = _binding!!
    private var selectedFileUri: Uri? = null
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                selectedFileUri = it.data?.data!!
                binding.imgContact.loadImage(selectedFileUri.toString())
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

    }

    private fun setListeners() {
        with(binding) {
            imgContact.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK).setType("image/*")
                galleryLauncher.launch(intent)
            }
            btnSaveContact.setOnClickListener {
                val contact = Contact(
                    photo = selectedFileUri.toString(),
                    name = etName.text.toString(),
                    phoneNumber = etPhoneNumber.text.toString()
                )
                App.db.contactDao().insert(contact)
                findNavController().navigate(R.id.navigation_contacts)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}