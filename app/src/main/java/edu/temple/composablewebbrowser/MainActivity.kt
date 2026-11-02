package edu.temple.composablewebbrowser

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import edu.temple.composablewebbrowser.ui.theme.ComposableWebBrowserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposableWebBrowserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Browser()
                }
            }
        }
    }
}

@Composable
fun Browser() {
    var tempUrl by rememberSaveable() { mutableStateOf("https://google.com") }
    var url by rememberSaveable() { mutableStateOf("https://google.com") }

    Column() {
        Spacer(Modifier.height(64.dp))

        TextField(
            value = tempUrl,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Enter URL here...")
            },
            onValueChange = {
                tempUrl = it
            }
        )

        Button(
            onClick = {
                tempUrl = fixUrl(tempUrl)
                url = tempUrl
            }
        ) {
            Text("Visit Website")
        }

        Web(url = url)
    }
}

@Composable
fun Web(
    modifier: Modifier = Modifier,
    url: String = ""
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        },
        update = { view ->
            view.loadUrl(url)
        }
    )
}

fun fixUrl(url: String) =
    if (url.startsWith("https://")) {
        url
    } else {
        "https://$url"
    }