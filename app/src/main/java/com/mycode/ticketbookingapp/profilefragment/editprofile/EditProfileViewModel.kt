package com.mycode.ticketbookingapp.profilefragment.editprofile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.DatePickerDialog
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycode.ticketbookingapp.database.AuthRepository
import com.mycode.ticketbookingapp.model.TicketBookingApp
import kotlinx.android.synthetic.main.activity_editprofile.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class EditProfileViewModel(application: Application, activity: Activity): ViewModel() {
    var _image= MutableLiveData<Boolean>()
    val image: LiveData<Boolean>
        get()=_image

    private var _spinner= MutableLiveData<Boolean>()
        val spinner: LiveData<Boolean>
            get()=_spinner

    private var _gender= MutableLiveData<String>()
    val gender: LiveData<String>
        get()=_gender

    private var _birthday= MutableLiveData<String>()
    val birthday: LiveData<String>
        get()=_birthday



        private var authRepository: AuthRepository

        val getData: LiveData<TicketBookingApp?>
            get()=authRepository.getUserDataMutableLiveData()

    val setData: LiveData<Boolean?>
        get()=authRepository.setUserDataMutableLiveData()

    val setImage:LiveData<String?>
     get()=authRepository.uploadedDataMutuableLiveData()

@SuppressLint("StaticFieldLeak")
       var act:Activity

        init {
            authRepository = AuthRepository(application)
            authRepository.getUserData()
             act = activity

        }




    fun Calendar(){
        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month =today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog= DatePickerDialog(act, { view, y, monthOfYear, dayOfMonth ->

            _birthday.value="$dayOfMonth/"+(monthOfYear+1)+"/$y"
        }, year, month, day)
        datePickerDialog.datePicker.maxDate= Date().time
        datePickerDialog.show()
    }


    fun imageFormating() {
        _image.value=true
    }
    var str:String?=null
    var str1:String?=null
    var str2:String?=null


    fun gender(){
        alert0.setTitle("Choose a language")
        val  options = arrayOf("தமிழ்","English","हिन्दी")
        alert0.setItems(options) { dialog, which ->
            dialog.dismiss()

            when (which) {
                /* execute here your actions */
                0 -> {
//                setLocale("ta")
                    updateLanguage("ta_IN")
//                _language.value="ta"
                    languageInitial("ta_IN")

                }
                1 -> {
//                setLocale("en")
                    updateLanguage("en_US")
//                _language.value="en"
                    languageInitial("en_US")
                }
                2 -> {
//                setLocale("hi")
                    updateLanguage("hi_IN")
//                _language.value="hi"
                    languageInitial("hi_IN")
                }

            }


        }

        alert0.show()

    }else{
        _snackbar.value=false
    }
}

}

    fun updateData(username:String,email:String,password:String,location:String,mobileNumber:String){
           if(setImage.value!=null) {
             str=setImage.value.toString()
           }else{
               str= getData.value?.profilePicture
           }

        if(getData.value?.birthday!=null){
            str1=getData.value?.birthday
        }else{
            str1=_birthday.value
        }

        if(getData.value?.gender!=null) {
            str2 = getData.value?.gender
        }else{
              str2=_gender.value
        }



        val ticketBookingApp=TicketBookingApp(username,email,password,str!!,location,mobileNumber,str1!!,str2!!)
            authRepository.setUserData(ticketBookingApp)
            _spinner.value=true
        }

    fun function(){
        authRepository.getUserData()
        _spinner.value=false
    }


    fun imageFormatingDone(dp: Uri){
        authRepository.uploadImageToFirebaseStorage(dp)
        _image.value=false
    }





}
