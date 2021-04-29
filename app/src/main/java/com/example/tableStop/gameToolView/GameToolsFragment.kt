package com.example.tableStop.gameToolView

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.tableStop.R
import kotlinx.android.synthetic.main.fragment_gametools.*


class GameToolsFragment : Fragment(){
    var navc: NavController?= null
  
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gametools, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var warnningTV = roll_warning
        warnningTV.visibility = View.GONE; // set warning TV
        val rollButton: Button = btn_roll
            d4.setOnClickListener{
                d4.isSelected = !d4.isSelected
                if(d6.isSelected){ d6.isSelected = !d6.isSelected}
                if(d8.isSelected){ d8.isSelected = !d8.isSelected}
                if(d10.isSelected){ d10.isSelected = !d10.isSelected}
                if(d12.isSelected){ d12.isSelected = !d12.isSelected}
                if(d20.isSelected){ d20.isSelected = !d20.isSelected}
                rollButton.setOnClickListener {
                    if(!d4.isSelected){
                        warnningTV.visibility = View.VISIBLE
                    }else{
                        warnningTV.visibility = View.GONE
                        val result = rollDice(4)
                        CreatePopupWindow(result)
                    }
// --------------------------------------- use intent to create popup window ---------------------//
//                val intent = Intent(this.activity, PopupWindow::class.java)
//                intent.putExtra("popuptitle", "you got")
//                intent.putExtra("popuptext", "12")
//                intent.putExtra("popupbtn", "OK")
//                intent.putExtra("darkstatusbar", false)
//                startActivity(intent)
//************************************************************************************************//
                }
            }

            d6.setOnClickListener{
                d6.isSelected = !d6.isSelected
                if(d4.isSelected){ d4.isSelected = !d4.isSelected}
                if(d8.isSelected){ d8.isSelected = !d8.isSelected}
                if(d10.isSelected){ d10.isSelected = !d10.isSelected}
                if(d12.isSelected){ d12.isSelected = !d12.isSelected}
                if(d20.isSelected){ d20.isSelected = !d20.isSelected}
                rollButton.setOnClickListener {
                    if(!d6.isSelected){
                        warnningTV.visibility = View.VISIBLE
                    }else{
                        warnningTV.visibility = View.GONE
                        val result = rollDice(6)
                        CreatePopupWindow(result)
                    }
                }}
            d8.setOnClickListener{
                d8.isSelected = !d8.isSelected
                if(d4.isSelected){ d4.isSelected = !d4.isSelected}
                if(d6.isSelected){ d6.isSelected = !d6.isSelected}
                if(d10.isSelected){ d10.isSelected = !d10.isSelected}
                if(d12.isSelected){ d12.isSelected = !d12.isSelected}
                if(d20.isSelected){ d20.isSelected = !d20.isSelected}
                rollButton.setOnClickListener {
                    if(!d8.isSelected){
                        warnningTV.visibility = View.VISIBLE
                    }else {
                        warnningTV.visibility = View.GONE
                        val result = rollDice(8)
                        CreatePopupWindow(result)
                    }
                }}
            d10.setOnClickListener{
                d10.isSelected = !d10.isSelected
                if(d4.isSelected){ d4.isSelected = !d4.isSelected}
                if(d8.isSelected){ d8.isSelected = !d8.isSelected}
                if(d6.isSelected){ d6.isSelected = !d6.isSelected}
                if(d12.isSelected){ d12.isSelected = !d12.isSelected}
                if(d20.isSelected){ d20.isSelected = !d20.isSelected}
                rollButton.setOnClickListener {
                    if(!d10.isSelected){
                        warnningTV.visibility = View.VISIBLE
                    }else {
                        warnningTV.visibility = View.GONE
                        val result = rollDice(10)
                        CreatePopupWindow(result)
                    }
                } }
            d12.setOnClickListener{
                d12.isSelected = !d12.isSelected
                if(d4.isSelected){ d4.isSelected = !d4.isSelected}
                if(d8.isSelected){ d8.isSelected = !d8.isSelected}
                if(d10.isSelected){ d10.isSelected = !d10.isSelected}
                if(d6.isSelected){ d6.isSelected = !d6.isSelected}
                if(d20.isSelected){ d20.isSelected = !d20.isSelected}
                rollButton.setOnClickListener {
                    if(!d12.isSelected){
                        warnningTV.visibility = View.VISIBLE
                    }else {
                        warnningTV.visibility = View.GONE
                        val result = rollDice(12)
                        CreatePopupWindow(result)
                    }
                }}
            d20.setOnClickListener{
                d20.isSelected = !d20.isSelected
                if(d4.isSelected){ d4.isSelected = !d4.isSelected}
                if(d8.isSelected){ d8.isSelected = !d8.isSelected}
                if(d10.isSelected){ d10.isSelected = !d10.isSelected}
                if(d12.isSelected){ d12.isSelected = !d12.isSelected}
                if(d6.isSelected){ d6.isSelected = !d6.isSelected}
//            d20.setTextColor(Color.parseColor("#FF00AA"))
                rollButton.setOnClickListener {
                    if(!d20.isSelected){
                        warnningTV.visibility = View.VISIBLE
                    }else {
                        warnningTV.visibility = View.GONE
                        val result = rollDice(20)
                        CreatePopupWindow(result)
                    }
                }}
            btn_shop.setOnClickListener{
                val url = "https://www.ebay.com/b/Dungeons-Dragons-Accessories-Dice/44112/bn_1913850"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        //}


//        val mainHandler = Handler(Looper.getMainLooper())
//
//        mainHandler.post(object : Runnable {
//            override fun run() {
//                rollDice(2)
//                mainHandler.postDelayed(this, 1000)
//            }
//        })

    }
//    override fun onClick(v: View){
//        if(v.id == R.id.d4){
//            d4.isSelected = !d4.isSelected
//            if(d6.isSelected){ d6.isSelected = !d6.isSelected}
//            if(d8.isSelected){ d8.isSelected = !d8.isSelected}
//            if(d10.isSelected){ d10.isSelected = !d10.isSelected}
//            if(d12.isSelected){ d12.isSelected = !d12.isSelected}
//            if(d20.isSelected){ d20.isSelected = !d20.isSelected}
//            R.id.popup_window_button.setOnClickListener {
//
//                val result = rollDice(4)
//                CreatePopupWindow(result)
//        }
//    }


    private fun rollDice(limit: Int): Int {
        // Toast.makeText(this, "button clicked",
        //  Toast.LENGTH_SHORT).show()

//        val randomInt = (1..limit).random()
        //val resultText: TextView = roll_result
        val randomInt = (1..limit).random()
        //resultText.text = randomInt.toString()
        return randomInt
    }

    fun PopupWindow.dimBehind() {
        val container = contentView.rootView
        val context = contentView.context
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = p.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.3f
        wm.updateViewLayout(container, p)
    }

    fun CreatePopupWindow(dice_result: Int){
        // using activity because it is fragment class in kotlin
        val inflater:LayoutInflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_popup_window, null)

        //Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )
        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }
        // If API level 23 or higher
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Create a new slide animation for popup window enter transition
//            val slideIn = Slide()
//            slideIn.slideEdge = Gravity.TOP
//            popupWindow.enterTransition = slideIn
//
//            // Slide animation for popup window exit transition
//            val slideOut = Slide()
//            slideOut.slideEdge = Gravity.RIGHT
//            popupWindow.exitTransition = slideOut
        }
        // Get the widgets reference from custom view
        val resultTv = view.findViewById<TextView>(R.id.popup_window_text)
        val buttonPopup = view.findViewById<Button>(R.id.popup_window_button)
        resultTv.text = dice_result.toString()
        setButtonClickable(false)  // when the popup window active, set button unclickable

        //popupWindow.dimBehind()
        // set button to control Popupwindow
        buttonPopup.setOnClickListener{
            setButtonClickable(true)
            popupWindow.dismiss()
        }
        
        TransitionManager.beginDelayedTransition(root_page)
        popupWindow.showAtLocation(
            root_page, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
    }

    fun setButtonClickable(b: Boolean) {
        d4.isClickable = b
        d6.isClickable = b
        d8.isClickable = b
        d10.isClickable = b
        d12.isClickable = b
        d20.isClickable = b
        btn_roll.isClickable = b
        btn_shop.isClickable = b
    }

}

//private fun Button.setOnClickListener(gameToolsFragment: GameToolsFragment) {
//
//}

