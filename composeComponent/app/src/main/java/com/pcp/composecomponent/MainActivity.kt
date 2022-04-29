package com.pcp.composecomponent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pcp.composecomponent.ui.theme.ComposeComponentTheme
import com.pcp.composecomponent.ui.theme.Purple200

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeComponentTheme {
                val navController = rememberNavController() //Navigation Step 2

                NavHost( //Navigation Step 2
                    navController = navController,
                    startDestination = "first_screen"
                ) {
                    composable("first_screen") {
                        //first screen
                        FirstScreen(navController = navController)
                    }
                    composable("second_screen") {
                        //second screen
                        SecondScreen(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun FirstScreen(navController: NavController) {
    Text(text = "First screen, click me to Second Screen",
        color = Purple200,
        style = TextStyle(textAlign = TextAlign.Center),
        modifier = Modifier.clickable(onClick = {
            navController.navigate("second_screen")
        })
    )
}

@Composable
fun SecondScreen(navController: NavController) {
    Text(text = "Second screen, click me to First Screen",
        color = Purple200,
        style = TextStyle(textAlign = TextAlign.Center),
        modifier = Modifier.clickable(onClick = {
            navController.navigate("first_screen")
        })
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeComponentTheme {
        FirstScreen(rememberNavController())    //重要,這樣就可以Preview了
    }
}