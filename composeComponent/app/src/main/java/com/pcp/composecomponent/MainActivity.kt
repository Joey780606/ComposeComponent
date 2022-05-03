package com.pcp.composecomponent

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pcp.composecomponent.ui.theme.*

/*
  Author: Joey yang
  Create date: 22/05/01

  First screen:
    1. Column
    2. Text
    3. Button

 */
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
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Row() {
            Text(text = "First screen, click me to Second Screen",
                modifier = Modifier.clickable(onClick = {
                    navController.navigate("second_screen")
                }),
                color = Purple200,
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,   //可看原始碼
                fontWeight = FontWeight.Bold,   //可看原始碼
                fontFamily = FontFamily.SansSerif,  //可看原始碼
                letterSpacing = 4.sp,
                style = TextStyle(textAlign = TextAlign.Center),
                textDecoration = TextDecoration.LineThrough,
                textAlign = TextAlign.Start,  //可看原始碼
                lineHeight = 30.sp,
                overflow = TextOverflow.Ellipsis, //可看原始碼,若字太長時,超過螢幕就只會顯示 ...
                softWrap = true, //文字是否應在soft line breaks.若為false, text的字型(glyphs)將如同未限制的水平空間放置,
                    // overflow和TextAlign可能會有非預期的效果.
                maxLines = 2,
                onTextLayout = { textLayout ->
                    Log.v("TAG","text width: ${textLayout.size.width}, text height: ${textLayout.size.height}")
                },
                // style目前怎麼設定都是有問題的,要找原因
                //style = LocalTextStyle.current,
//                style = TextStyle(
//                    color = Color.Green,
//                    fontSize = 24.sp,
//                    fontFamily = FontFamily.Monospace,
//                    letterSpacing = 4.sp,
//                    textAlign = TextAlign.Center,
//                    shadow = Shadow(
//                        color = Color.Black,
//                        offset = Offset(8f, 8f),
//                        blurRadius = 4f
//                    ),
//                    textGeometricTransform = TextGeometricTransform(
//                        scaleX = 2.5f,
//                        skewX = 1f
//                    )
//                ),
            )
        }
        ButtonDemo()
    }
}

@Composable
fun ButtonDemo() {
    // 重要,下面三行是 interactionSource 的使用
    val interactionSourceTest = remember { MutableInteractionSource() }
    val pressState = interactionSourceTest.collectIsPressedAsState()
    val borderColor = if (pressState.value) YellowFFEB3B else Green4CAF50 //Import com.pcp.composecomponent.ui.theme.YellowFFEB3B

    Button( //Button只是一個容器,裡面要放文字,就是要再加一個Text
        modifier = Modifier.wrapContentWidth(),
        //enabled = false,
        enabled = true, //如果 enabled 設為false, border, interactionSource就不會有變化
        interactionSource = interactionSourceTest,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
            disabledElevation = 2.dp
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(5.dp, color = borderColor),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Red),
        contentPadding = PaddingValues(4.dp, 3.dp, 2.dp, 1.dp),
        onClick = { Log.v("Test", "${pressState.value}")}) {
        Text(text = "Hello joey 12121212")
    }
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