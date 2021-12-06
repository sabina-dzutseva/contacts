package com.gmail.dzutsevasabina.repository

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import com.gmail.dzutsevasabina.model.BriefContact
import com.gmail.dzutsevasabina.model.DetailedContact
import io.reactivex.rxjava3.core.Single

class ContactRepositoryImpl : ContactRepository {
    private lateinit var contentResolver: ContentResolver

    override fun getContact(id: String, context: Context): Single<DetailedContact> {
        return Single.fromCallable { getDetails(id, context) }
    }

    override fun getContactList(context: Context, query: String): Single<List<BriefContact>> {
        return Single.fromCallable { getList(context, query) }
    }

    private fun getDetails(id: String, context: Context): DetailedContact {

        var contact = DetailedContact(id, "", "", "", "", "", "", "", "")

        val contactsUri = ContactsContract.Contacts.CONTENT_URI

        contentResolver = context.contentResolver

        contentResolver.query(
            contactsUri,
            null,
            ContactsContract.Contacts._ID + " = " + id,
            null,
            null
        ).use { contactsCursor ->
            if (contactsCursor != null) {
                if (contactsCursor.moveToNext()) {

                    val image = contactsCursor.getString(
                        contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI)
                    )
                    val name = contactsCursor.getString(
                        contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)
                    )

                    val phones: Array<String> = getPhones(id)
                    val emails: Array<String> = getEmails(id)
                    val birthday = getBirthday(id)
                    val description = getDescription(id)

                    contact = DetailedContact(
                        id,
                        image,
                        name,
                        phones[0],
                        phones[1],
                        emails[0],
                        emails[1],
                        birthday,
                        description
                    )
                }
            }
        }
        return contact
    }

    private fun getList(context: Context, query: String): List<BriefContact> {
        val contacts = ArrayList<BriefContact>()
        val contactsUri = ContactsContract.Contacts.CONTENT_URI

        contentResolver = context.contentResolver

        contentResolver.query(contactsUri, null, null, null, null).use { contactsCursor ->
            if (contactsCursor != null) {
                while (contactsCursor.moveToNext()) {
                    val id = contactsCursor.getString(
                        contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)
                    )
                    val image = contactsCursor.getString(
                        contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI)
                    )
                    val name = contactsCursor.getString(
                        contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)
                    )
                    val phone = getPhones(id)

                    if (name.contains(query)) {
                        contacts.add(BriefContact(id, image, name, phone[0]))
                    }
                }
            }
        }
        return contacts
    }

    private fun getPhones(id: String): Array<String> {
        var phone1 = ""
        var phone2 = ""
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        contentResolver.query(
            phoneUri,
            null,
            ContactsContract.Data.CONTACT_ID + " = " + id,
            null,
            null
        ).use { phoneCursor ->
            if (phoneCursor != null) {
                if (phoneCursor.moveToNext()) {
                    phone1 = phoneCursor.getString(
                        phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
                }

                if (phoneCursor.moveToNext()) {
                    phone2 = phoneCursor.getString(
                        phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
                }
            }
        }
        return arrayOf(phone1, phone2)
    }

    private fun getEmails(id: String): Array<String> {
        var email1 = ""
        var email2 = ""
        val emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI
        contentResolver.query(
            emailUri,
            null,
            ContactsContract.Data.CONTACT_ID + " = " + id,
            null,
            null
        ).use { emailCursor ->
            if (emailCursor != null) {
                if (emailCursor.moveToNext()) {
                    email1 = emailCursor.getString(
                        emailCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS)
                    )
                }

                if (emailCursor.moveToNext()) {
                    email2 = emailCursor.getString(
                        emailCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS)
                    )
                }
            }
        }
        return arrayOf(email1, email2)
    }

    private fun getBirthday(id: String): String {
        var birthday = ""

        contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Event.START_DATE),
            ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?",
            arrayOf(id, ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE),
            null
        ).use { birthdayCursor ->
            if (birthdayCursor != null) {
                if (birthdayCursor.moveToNext()) {
                    birthday = birthdayCursor.getString(0).drop(2).replace('-', '.')
                }
            }
        }

        return birthday
    }

    private fun getDescription(id: String): String {
        var description = ""

        contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Note.NOTE),
            ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?",
            arrayOf(id, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE),
            null
        ).use { noteCursor ->
            if (noteCursor != null) {
                if (noteCursor.moveToNext()) {
                    description = noteCursor.getString(0)
                }
            }
        }
        return description
    }
}
