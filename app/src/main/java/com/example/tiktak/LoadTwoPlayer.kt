package com.example.tiktak

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.dialog_message.*
import kotlinx.android.synthetic.main.dialog_message.view.*

class LoadTwoPlayer(private var activity: PlayActivity) {
    private var click = 1
    private var gamePanel = mutableListOf<MutableList<String>>()
    private var winner = ""
    private var xWinCount = 0
    private var oWinCount = 0
    private var draw = 0

    fun start(){
        fillGamePanel()
        setOnClickArea()
    }

    private fun restart() {
        gamePanel.clear()
        winner=""
        fillGamePanel()
        click=1
        setTextOnTextViews()
    }
    private fun setTextOnTextViews(){
        activity.area11.text = ""
        activity.area12.text = ""
        activity.area13.text = ""
        activity.area21.text = ""
        activity.area22.text = ""
        activity.area23.text = ""
        activity.area31.text = ""
        activity.area32.text = ""
        activity.area33.text = ""
    }

    @SuppressLint("SetTextI18n")
    private fun check() {
        checkWinner()
        Log.d("Clickable","$click")
        activity.playerx.text = "X: $xWinCount"
        activity.playero.text = "O: $oWinCount"
        if(winner.length>0 && winner != "draw"){
            val view = LayoutInflater.from(activity).inflate(R.layout.dialog_message,null)
            val dialog = AlertDialog.Builder(activity).setView(view).show()
            dialog.setCancelable(false)
            view.tv_whoiswinner.text = "$winner victory ! !"
            view.iv_winner.setImageResource(R.drawable.winner)
            dialog.btn_restart.setOnClickListener {
                dialog.dismiss()
                restart()
            }
        }else if(click == 10){
            draw++
            activity.draw.text = "Draws: $draw"
            val view = LayoutInflater.from(activity).inflate(R.layout.dialog_message,null)
            val dialog = AlertDialog.Builder(activity).setView(view).show()
            dialog.setCancelable(false)
            view.tv_whoiswinner.text = "Draw ! !"
            view.iv_winner.setImageResource(R.drawable.draw)
            dialog.btn_restart.setOnClickListener {
                dialog.dismiss()
                restart()
            }
        }
    }

    private fun checkWinner() {
        winner = if(checkColumn().winner == "X" || checkDiagonal().winner == "X" || checkRow().winner == "X"){
            xWinCount++
            "X"
        }else if(checkColumn().winner == "O" || checkDiagonal().winner == "O" || checkRow().winner == "O"){
            oWinCount++
            "O"
        }else if(checkColumn().winner == "draw" || checkDiagonal().winner == "draw" || checkRow().winner == "draw"){
            "draw"
        }else{
            ""
        }
    }

    private fun checkRow():Winner{
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

    private fun checkColumn():Winner{
        for (i in 0..2){
            val row = gamePanel[0][i]+gamePanel[1][i]+gamePanel[2][i]+""
            if(row.lowercase() == "xxx"){
                return Winner("X")
            }
            if(row.lowercase() == "ooo"){
                return Winner("O")
            }
        }
        return Winner("")
    }

    private fun checkDiagonal():Winner{
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

    @SuppressLint("NewApi")
    private fun viewFuncition(textView: TextView, row:Int, col:Int){
        if(textView.text.isEmpty()){

            val vibration = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibration.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))

            if(click%2==0){
                textView.text = "O"
            }else{
                textView.text = "X"
            }
            gamePanel[row][col] = textView.text.toString()
            click++
            check()
        }
    }

    private fun setOnClickArea() {
        activity.area11.setOnClickListener {
            viewFuncition(it as TextView,0,0)
        }
        activity.area12.setOnClickListener {
            viewFuncition(it as TextView,0,1)
        }
        activity.area13.setOnClickListener {
            viewFuncition(it as TextView,0,2)
        }
        activity.area21.setOnClickListener {
            viewFuncition(it as TextView,1,0)
        }
        activity.area22.setOnClickListener {
            viewFuncition(it as TextView,1,1)
        }
        activity.area23.setOnClickListener {
            viewFuncition(it as TextView,1,2)
        }
        activity.area31.setOnClickListener {
            viewFuncition(it as TextView,2,0)
        }
        activity.area32.setOnClickListener {
            viewFuncition(it as TextView,2,1)
        }
        activity.area33.setOnClickListener {
            viewFuncition(it as TextView,2,2)
        }
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