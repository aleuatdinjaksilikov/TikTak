package com.example.tiktak

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_single_player.*

class LoadSinglePlayerEasy(private val activitySingle: SinglePlayer) {
    private var click = 1
    private var gamePanel = mutableListOf<MutableList<String>>()
    private var set = mutableSetOf(1,2,3,4,5,6,7,8,9)
    private var winner = ""
    private var isPlayerSet = false
    private var xWinCount = 0
    private var oWinCount = 0
    private var drawCount = 0
    private var isShowDialog = false


    @RequiresApi(Build.VERSION_CODES.O)
    fun start(){
        fillGamePanel()
        setOnClickArea()
    }

    private fun restart(){
        set = mutableSetOf(1,2,3,4,5,6,7,8,9)
        gamePanel.clear()
        winner=""
        fillGamePanel()
        click=1
        isPlayerSet = false
        setTextOnTextViews()
        Log.d("tekseriw",gamePanel.toString())
        Log.d("tekseriw",set.toString())
    }

    private fun setTextOnTextViews(){
        activitySingle.area11.text = ""
        activitySingle.area12.text = ""
        activitySingle.area13.text = ""
        activitySingle.area21.text = ""
        activitySingle.area22.text = ""
        activitySingle.area23.text = ""
        activitySingle.area31.text = ""
        activitySingle.area32.text = ""
        activitySingle.area33.text = ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setOnClickArea() {
        activitySingle.area11.setOnClickListener {
            clickView(it as TextView,0,0,1)
        }
        activitySingle.area12.setOnClickListener {
            clickView(it as TextView,0,1,2)
        }
        activitySingle.area13.setOnClickListener {
            clickView(it as TextView,0,2,3)
        }
        activitySingle.area21.setOnClickListener {
            clickView(it as TextView,1,0,4)
        }
        activitySingle.area22.setOnClickListener {
            clickView(it as TextView,1,1,5)
        }
        activitySingle.area23.setOnClickListener {
            clickView(it as TextView,1,2,6)
        }
        activitySingle.area31.setOnClickListener {
            clickView(it as TextView,2,0,7)
        }
        activitySingle.area32.setOnClickListener {
            clickView(it as TextView,2,1,8)
        }
        activitySingle.area33.setOnClickListener {
            clickView(it as TextView,2,2,9)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickView(view: TextView, x:Int, y:Int, number:Int) {
        if(!isPlayerSet){

            if(view.text.toString().isEmpty()){

                val vibration = activitySingle.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibration.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))

                if(click%2==0){
                    view.text = "O"
                }else{
                    view.text = "X"
                }
                gamePanel[x][y] = view.text.toString()
                click++
                check()
                set.remove(number)
                isPlayerSet = true
            }
        }
        if(isPlayerSet){
            Handler().postDelayed({
                randomComputer()
            }, 1000)
        }
    }

    private fun check() {
        checkWinner()
        if(winner.isNotEmpty() && winner != "draw"){
            showDialog(winner)
        }else if(click == 10){
            showDialog("draw")
            drawCount++
            activitySingle.draw.text = "Draws: $drawCount"
        }
    }

    private fun checkWinner() {
        winner = ""
        if(checkColumn().winner == "X" || checkDiagonal().winner == "X" || checkRow().winner == "X"){
            winner = "X"
            xWinCount++
        }else if(checkColumn().winner == "O" || checkDiagonal().winner == "O" || checkRow().winner == "O"){
            winner = "O"
            oWinCount++
        }else if(checkColumn().winner == "draw" || checkDiagonal().winner == "draw" || checkRow().winner == "draw"){
            winner = "draw"
            drawCount++
        }else{
            winner = ""
        }
    }

    private fun showDialog(winnerin:String) {
        var str = winnerin
        str = if(str.equals("draw")){
            "Draw !!"
        }else {
            "$str Victory!"
        }
        isShowDialog = true

        var dialog = AlertDialog.Builder(activitySingle)
            .setIcon(R.drawable.ic_group_2)
            .setTitle("Result")
            .setMessage(str)
            .setPositiveButton("Reset", null)
            .show()
        dialog.setCancelable(false)
        var button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

        button.setOnClickListener {
            dialog.dismiss()
            isShowDialog = false
            restart()
        }
        activitySingle.playerx.text = "X: $xWinCount"
        activitySingle.playero.text = "O: $oWinCount"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun randomComputer() {
        if(set.isNotEmpty() && !isShowDialog){
            
            when(set.random()){
                1 -> {
                    setRandomNumber(activitySingle.area11,0,0,1)
                }
                2 -> {
                    setRandomNumber(activitySingle.area12,0,1,2)
                }
                3 -> {
                    setRandomNumber(activitySingle.area13,0,2,3)
                }
                4 -> {
                    setRandomNumber(activitySingle.area21,1,0,4)
                }
                5 -> {
                    setRandomNumber(activitySingle.area22,1,1,5)
                }
                6 -> {
                    setRandomNumber(activitySingle.area23,1,2,6)
                }
                7 -> {
                    setRandomNumber(activitySingle.area31,2,0,7)
                }
                8 -> {
                    setRandomNumber(activitySingle.area32,2,1,8)
                }
                9 -> {
                    setRandomNumber(activitySingle.area33,2,2,9)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRandomNumber(view:TextView, x:Int, y:Int, number:Int) {

        val vibration = activitySingle.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibration.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))

        if(click%2==0){
            view.text = "O"
        }else{
            view.text = "X"
        }
        isPlayerSet = false
        click++
        gamePanel[x][y] = view.text.toString()
        set.remove(number)
        check()

    }

    private fun checkRow(): Winner {
    for (i in 0..2){
        val row = gamePanel[i][0]+gamePanel[i][1]+gamePanel[i][2]+""
        if(row.lowercase() == "xxx"){
            return Winner("X")
        }
        if(row.lowercase() == "ooo"){
            return Winner("O")
        }
    }
    return Winner("")
}

    private fun checkColumn(): Winner {
    for (i in 0..2){
        val row = gamePanel[0][i]+gamePanel[1][i]+gamePanel[2][i]+""
        if(row.lowercase() == "xxx"){
            return Winner("X")
        }
        if(row.lowercase() == "ooo"){
            return Winner("O")
        }
    }
    if(set.isEmpty()){
        return Winner("")
    }else{
        return Winner("")
    }

}

    private fun checkDiagonal(): Winner {
    val dl = gamePanel[0][0]+gamePanel[1][1]+gamePanel[2][2]
    if(dl.lowercase() == "xxx"){
        return Winner("X")
    }else if(dl.lowercase() == "ooo"){
        return Winner("O")
    }
    val dl1 = gamePanel[2][0]+gamePanel[1][1]+gamePanel[0][2]
    if(dl1.lowercase() == "xxx"){
        return Winner("X")
    }
    if(dl1.lowercase() == "ooo"){
        return Winner("O")
    }
    return Winner("")
}

    private fun fillGamePanel() {
        for (i in 0..2){
            val list = mutableListOf<String>()
            for (j in 0..2){
                list.add(" ")
            }
            gamePanel.add(list)
        }
    }
}