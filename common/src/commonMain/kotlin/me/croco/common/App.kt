package me.croco.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File

@Composable
fun App() {

    var pathText by remember { mutableStateOf("C://path/to/files") }
    var extensionText by remember { mutableStateOf("kt") }
    var linesResult by remember { mutableStateOf("0") }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Line counter", fontSize = 30.sp)

            Spacer(Modifier.height(12.dp))
            Text("Enter root path", fontSize = 20.sp)
            TextField(
                value = pathText,
                onValueChange = { pathText = it },
                label = { Text("Root path") }
            )

            Spacer(Modifier.height(12.dp))
            TextField(
                value = extensionText,
                onValueChange = { extensionText = it },
                label = { Text("Files extension") }
            )

            Spacer(Modifier.height(12.dp))
            Button(onClick = {
                val file = File(pathText)
                if (file.exists()) {
                    val lines = calcLines(pathText, extensionText)
                    linesResult = lines.toString()
                }
            }) {
                Text("Calculate")
            }

            Spacer(Modifier.height(12.dp))
            Text(linesResult, fontSize = 20.sp)

        //    Button(onClick = {
        //        text = "Hello, ${getPlatformName()}"
        //    }) {
        //        Text(text)
        //    }
        }
    }
}

private fun calcLines(pathText: String, extensionText: String): Long {
    val file = File(pathText)
    if (!file.exists()) {
        return 0L
    }
    if (file.isDirectory) {
        var cnt = 0L
        file.listFiles()?.forEach {
            cnt += calcLines(it.absolutePath, extensionText)
        }
        return cnt
    } else {
        if (file.extension.contains(extensionText)) {
            var cnt = 0L
            file.forEachLine { cnt++ }
            return cnt
        }
    }
    return 0L
}

