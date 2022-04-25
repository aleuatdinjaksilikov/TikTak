package com.example.tiktak

import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_play.*

class LoadTwoPlayer(private var activity: PlayActivity) {
    private var click = 1
    private var gamePanel = mutableListOf<MutableList<String>>()
    private var winner = ""

    fun start(){
        fillGamePanel()
        setOnClickArea()
    }
    private fun check() {
        checkWinner()
        Log.d("Clickable","$click")
        if(winner.length>0 && winner != "draw"){
            Toast.makeText(activity,winner+" victory",Toast.LENGTH_SHORT).show()
        }else if(click == 10){
            Toast.makeText(activity,"Draw !!",Toast.LENGTH_LONG).show()
        }
    }

    private fun checkWinner() {
        winner = if(checkColumn().winner == "X" || checkDiagonal().winner == "X" || checkRow().winner == "X"){
            "X"
        }else if(checkColumn().winner == "O" || checkDiagonal().winner == "O" || checkRow().winner == "O"){
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


    private fun viewFuncition(textView: TextView, row:Int, col:Int){
        if(textView.text.isEmpty()){
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