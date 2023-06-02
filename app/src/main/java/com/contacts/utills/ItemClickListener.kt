package com.contacts.utills

import com.contacts.model.ContactsList

public interface OnContactsListItemClickListener {
    fun onItemClick(model: ContactsList,int: Int)
}