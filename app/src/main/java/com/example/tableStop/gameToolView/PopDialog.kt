package com.example.tableStop.gameToolView

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.tableStop.R

class PopDialog: DialogFragment() {
    companion object {
        const val TAG = "PopDialog"
        private const val DICE_NUM = "DICE_NUM"     // define DICE_NUM

        fun newInstance(diceNum:Int): PopDialog {
            val args = Bundle()
            args.putInt(DICE_NUM, diceNum)      // pass diceNum to popup window
            val fragment = PopDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_popup_window, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultTv = view.findViewById<TextView>(R.id.popup_window_text)
        val buttonPopup = view.findViewById<Button>(R.id.popup_window_button)

        // to generate dice animation
            val targetDice = arguments?.getInt(DICE_NUM)     //get target dice num
            val result = targetDice?.let { rollDice(it) }
        if (result != null){
            val animator = ValueAnimator.ofInt(1, targetDice, result)
            if(targetDice == 10){       // change duration for different limit
                animator.duration = 1200
            }
            if(targetDice == 12){
                animator.duration = 1300
            }
            if(targetDice == 20){
                animator.duration = 1600
            }
            animator.duration = 1000
            animator.addUpdateListener { animation -> resultTv.text = animation.animatedValue.toString() }
            animator.start()
            // set button to control Popup window
            buttonPopup.setOnClickListener{
                dismiss()
            }

        }


    }

    override fun onStart() {
        super.onStart()
        // to change the popup window size
//        dialog?.window?.setLayout(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
    }
    private fun rollDice(limit: Int): Int {
        // Toast.makeText(this, "button clicked",
        //  Toast.LENGTH_SHORT).show()

        val randomInt = (1..limit).random()
//        val resultText: TextView = roll_result
//        resultText.text = randomInt.toString()
        return randomInt
    }
}