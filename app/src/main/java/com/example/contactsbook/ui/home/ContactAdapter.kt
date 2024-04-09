package com.example.contactsbook.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.contactsbook.App
import com.example.contactsbook.R
import com.example.contactsbook.data.model.Contact
import com.example.contactsbook.databinding.ItemContactBinding
import com.example.contactsbook.loadImage

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private val list = arrayListOf<Contact>()

    fun addContacts(contacts: List<Contact>) {
        list.clear()
        list.addAll(contacts)
        notifyDataSetChanged()
    }

    fun removeContact(position: Int) {
        val contact = list[position]
        App.db.contactDao().delete(contact)
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun showPopupMenu(context: Context, anchorView: View, name: String, phoneNumber: String) {
        val popupMenu = PopupMenu(context, anchorView)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_message, null)
        val editMessage = dialogView.findViewById<EditText>(R.id.editMessage)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.call -> {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$phoneNumber")
                    context.startActivity(intent)
                    true
                }
                R.id.message -> {
//                    // Создаем интент для отправки сообщения
//                    val intent = Intent(Intent.ACTION_SENDTO)
//                    intent.data = Uri.parse("smsto:$phoneNumber")
//                    intent.putExtra("sms_body", name)
//                    context.startActivity(intent)
                    val alertDialogBuilder = AlertDialog.Builder(context)
                        .setView(dialogView)
                        .setPositiveButton("Send") { dialog, _ ->
                            val message = editMessage.text.toString()
                            if (message.isNotEmpty()) {
                                val intent = Intent(Intent.ACTION_SENDTO)
                                intent.data = Uri.parse("smsto:$phoneNumber")
                                intent.putExtra("sms_body", "$name: $message")
                                context.startActivity(intent)
                            } else {
                                // Сообщение не может быть пустым
                                Toast.makeText(context, "Сообщение не может быть пустым", Toast.LENGTH_SHORT).show()
                            }
                            dialog.dismiss()
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = list[position]
        holder.onBind(contact)
        holder.binding.btnAction.setOnClickListener {
            showPopupMenu(it.context, holder.binding.root, contact.name, contact.phoneNumber)
        }
    }

    class ContactViewHolder(val binding: ItemContactBinding) : ViewHolder(binding.root) {
        fun onBind(model: Contact) {
            with(binding) {
                imgContact.loadImage(model.photo.toString())
                tvName.text = model.name
                tvPhoneNumber.text = model.phoneNumber
            }
        }
    }
}
