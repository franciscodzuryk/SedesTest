package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DisplayActivity : ComponentActivity() {

    companion object {
        private const val EXTRA_TEXT = "extra_text"

        fun newIntent(context: Context, text: String): Intent {
            return Intent(context, DisplayActivity::class.java).apply {
                putExtra(EXTRA_TEXT, text)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val text = intent.getStringExtra(EXTRA_TEXT) ?: ""
        setContent {
            DisplayText(text = text)
        }
    }
}

@Composable
fun DisplayText(text: String) {
    val context = LocalContext.current as? ComponentActivity
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Texto ingresado: $text", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { context?.finish() }) {
            Text("Volver")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayTextPreview() {
    DisplayText("Text")
}