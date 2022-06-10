package com.example.tujutuju.custom.edit_text

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.tujutuju.R


class MyEtPassword : AppCompatEditText {
    private lateinit var passwordButtonImage: Drawable
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init(){
        passwordButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_lock_24) as Drawable
        setButtonDrawables(startOfTheText = passwordButtonImage)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(s.toString().isNotEmpty()){
                    error = if(passwordValidation(s.toString())) null else context.getString(
                        R.string.invalid_password)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = "Password"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
    private fun passwordValidation(password:String):Boolean{
        return password.length >=6
    }
    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText:Drawable? = null,
        endOfTheText:Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }
}