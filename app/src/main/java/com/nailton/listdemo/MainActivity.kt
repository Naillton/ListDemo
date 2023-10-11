package com.nailton.listdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nailton.listdemo.ui.theme.ListDemoTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
private fun MainScreen() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        ColumnList()
    }
}

/**
 * Definindo Column list onde teremos uam lista em coluna, essa lista em coluna
 * nao possui um scroll por padrao entao definimos o scrolll para podermos interagir
 * com a lista.
 */

@Composable
private fun ColumnList() {
    // definindo scroll state e adcionando scroll  na lista de items na coluna
    val scrollState = rememberScrollState()
    // usnado coroutineScope para conseguirmos interagir com a lista acessando qualuer valor da mesma
    // de forma manual
    val coroutineScope = rememberCoroutineScope()

    // funcao que executara um scopo de corrotinas acessando a parte da lista desejada
    val changeFirstEnd = {it: Int ->
        coroutineScope.launch {
            scrollState.animateScrollTo(it)
        }
    }
    Column( horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Button(
                onClick = { changeFirstEnd(0) },
                modifier = Modifier.weight(0.5f).padding(2.dp))
                {
                    Text("Top")
            }

            Button(onClick = { changeFirstEnd(scrollState.maxValue )},
                modifier = Modifier.weight(0.5f)
                    .padding(2.dp)) {
                Text("End")
            }
        }
        Column(
            Modifier.verticalScroll(scrollState)
        ) {
            // usando o repeat para criar 500 elementosdo tipo text
            repeat(500) {
                Text(
                    "List item: $it",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(5.dp))
            }
        }
    }
}

// definir o showSystemUi nos da uma visualizacao mais legivel para testes
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ListDemoTheme {
        MainScreen()
    }
}