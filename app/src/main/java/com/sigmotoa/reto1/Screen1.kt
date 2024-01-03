package com.sigmotoa.reto1

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 *
 * Created by sigmotoa on 19/12/23.
 * @author sigmotoa
 *
 * www.sigmotoa.com
 */

@Composable
fun ScreenReto() {
var msg by remember {
    mutableStateOf("")
}
    var localContext = LocalContext.current
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
        Text(text = "Insert a text")
        TextField(value = msg, onValueChange = {msg = it})
        Button(onClick = { showMsg(localContext, msg) }) {
            Text(text = "Show message")

        }
    }
}

fun showMsg(localContext: Context, msg: String){
    Toast.makeText(localContext, msg,Toast.LENGTH_SHORT).show()
}