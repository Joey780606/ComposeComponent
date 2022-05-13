package com.pcp.composecomponent

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.pcp.composecomponent.ui.theme.*
import kotlinx.coroutines.launch

/*
  Author: Joey yang
  Create date: 22/05/04

  1. Tab會使用到的:
    a. rememberPagerState
      需要 implementation 'com.google.accompanist:accompanist-pager:0.12.0'
    b. TabRow(
      Tab(
    c. HorizontalPager
  2. shape 有 RectangleShape, CircleShape, RoundedCornerShape, CutCornerShape
  3. Brush.horizontalGradient 的功能學習, 下有範例
  4. Image 的 colorFilter 有點難(有些轉換的函式),有空再來學,參:
    https://stackoverflow.com/questions/4354939/understanding-the-use-of-colormatrix-and-colormatrixcolorfilter-to-modify-a-draw
  5. Card
  6. Checkbox
  7. Divider
  8. AlertDialog
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SecondScreen(navController: NavController) {
    val viewModel: SecondViewModel = viewModel()
    val pagerState = rememberPagerState(pageCount = 2)
    Column(
        modifier = Modifier
            .background(YellowEEF88B, shape = RoundedCornerShape(8.dp))
            .fillMaxSize()
    ) {
        Text(
            text = "Second screen, click me to Third Screen",
            color = Purple200,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.clickable(onClick = {
                navController.navigate("third_screen")
            })
        )
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, viewModel, navController)
    }
}

@Composable
fun ThreeHomeView(navController: NavController) {
    val viewModel: SecondViewModel = viewModel()
    Column(
        modifier = Modifier
            .background(YellowEEF88B, shape = RoundedCornerShape(8.dp))
            .fillMaxSize()
    ) {
        Text(
            text = "Three home view, click me to First Screen",
            color = Purple200,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.clickable(onClick = {
                navController.navigate("first_screen")
            })
        )
    }
}

@Composable
fun ThreeChatView(navController: NavController) {
    val viewModel: SecondViewModel = viewModel()
    Column(
        modifier = Modifier
            .background(YellowEEF88B, shape = RoundedCornerShape(8.dp))
            .fillMaxSize()
    ) {
        Text(
            text = "Three chat view, click me to First Screen",
            color = Purple200,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.clickable(onClick = {
                navController.navigate("first_screen")
            })
        )
    }
}

@Composable
fun ThreeSettingView(navController: NavController) {
    val viewModel: SecondViewModel = viewModel()
    Column(
        modifier = Modifier
            .background(YellowEEF88B, shape = RoundedCornerShape(8.dp))
            .fillMaxSize()
    ) {
        Text(
            text = "Three setting view, click me to First Screen",
            color = Purple200,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.clickable(onClick = {
                navController.navigate("first_screen")
            })
        )
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val tabTitleList = listOf("Tab 1", "Tab 2")
    val scope = rememberCoroutineScope() //可學: 寫法

    TabRow( //可學: TabRow.kt
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Blue8898F3,
        contentColor = Color.White,
        divider = {
            TabRowDefaults.Divider(
                thickness = 2.dp,
                color = Color.Green
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                //Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),   // Already no this function.
                height = 2.dp,
                color = Color.White
            )
        }
    ) {
        tabTitleList.forEachIndexed { index, _->    //可學: _Collections.kt
            Tab(    //可學: Tab.kt
                text = {
                    Text(
                        tabTitleList[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi   //因為 PagerState 需要
@Composable
fun TabsContent(pagerState: PagerState,
                viewModel : SecondViewModel,
                navController: NavController) {
    HorizontalPager(state = pagerState) { page ->   //Pager.kt
        when(page) {
            0 -> TabScreenOne(data = "Tab Screen 1", viewModel, navController)
            1 -> TabScreenTwo(data = "Tab Screen 3", viewModel, navController)
        }
    }
}

@Composable
fun TabScreenOne(data: String, viewModel: SecondViewModel, navController: NavController) {
    val openDialog = remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(BlueFF9979, shape = CutCornerShape(5, 10, 15, 20))) {
        Text(text = data)
        Card_ImageDemo()
        CheckboxDemo()
        DividerDemo()
        AlertDialogDemo() { status -> openDialog.value = status } //State Hoist, 這是若只有一個 Lambda function,就這樣用
        //AlertDialogDemo({status -> openDialog.value = status}) {status -> openDialog.value = status}//重要: State Hoist, 這是若只有一個 Lambda function,就這樣用
            //重要,要顯示dialog,必須要用 mutableStateOf 變數,而不能用 Modifier.clickable(onClick = { 然後放 Composable function的方式,會有問題
    }

    if(openDialog.value)
        AlertDialogShow(openDialog) { status -> openDialog.value = status } //State Hoist
}

@Composable
fun AlertDialogDemo(infoUpdate: (newInfo: Boolean) -> Unit) {
    Text(text = "AlertDialog show up.  Please click me!",
        modifier = Modifier.clickable(onClick = {
            infoUpdate(true)
        })
    )
}

@Composable
fun AlertDialogShow(dialogStatus: MutableState<Boolean>, infoUpdate: (newInfo: Boolean) -> Unit) {
    if(dialogStatus.value) {
        AlertDialog(
            onDismissRequest = {
                infoUpdate(false)
            },
            title = {
                //Log.v("Test", "rssiFilter 000: ${testRSSI.value} ${viewModel.rssiFilter}")
                Text(modifier = Modifier
                    .fillMaxWidth(),
                    text = "My testing")
            },
            text = {
                Row() {
                    Text(modifier = Modifier
                        .weight(0.05f)
                        .align(Alignment.CenterVertically),
                        text = "-")
                    Text(modifier = Modifier
                        .weight(0.1f)
                        .align(Alignment.CenterVertically),
                        text = " dBm")
                }
            },
            buttons = {
                Row() {
                    Button(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(2.dp),
                        onClick = {
                            infoUpdate(false)
                        })
                    {
                        Text("OK")
                    }

                    Button(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(2.dp),
                        onClick = {
                            infoUpdate(false)
                        })
                    {
                        Text("Cancel")
                    }
                }
            }
        )
    }
}

@Composable
fun DividerDemo() {
    Divider(
        //modifier = Modifier.border(border = BorderStroke(5.dp, YellowFFEB3B), shape = CircleShape),
           //實測上方,線的顏色會改,但會影響 startIndent 的效果,所以先不放
        color = Color.White,
        thickness = 1.dp,
        startIndent = 50.dp)
}

@Composable
fun CheckboxDemo() {
    val isChecked = remember { mutableStateOf(false) }

    val interactionSourceTest = remember { MutableInteractionSource() }
    val pressState = interactionSourceTest.collectIsPressedAsState()
    val borderColor = if (pressState.value) YellowFFEB3B else Green4CAF50 //Import com.pcp.composecomponent.ui.theme.YellowFFEB3B


    Row(
        modifier = Modifier
            .clickable(onClick = {
                isChecked.value = !isChecked.value
            })
            .padding(16.dp)
    ) {
        Checkbox(   //只有純box,需額外加文字
            checked = isChecked.value,
            onCheckedChange = { checked ->
                isChecked.value = checked
            },
            modifier = Modifier.background(color = borderColor),
            enabled = true,
            interactionSource = interactionSourceTest,
            colors = CheckboxDefaults.colors(
                checkedColor = Purple700,
                uncheckedColor = YellowFFEB3B,
                checkmarkColor = PinkE91E63,
                disabledColor = Blue8898F3,
                disabledIndeterminateColor = BlueFF9979
            )
        )
        Text(text = "CheckBox text",
          color = borderColor,
          modifier = Modifier
              .padding(start = 8.dp)
              .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun Card_ImageDemo() {
    Card(
        modifier = Modifier.size(56.dp),
        shape = CircleShape,
        backgroundColor = Purple500,
        contentColor = contentColorFor(backgroundColor = Teal200),
        border = BorderStroke(2.dp, brush = Brush.horizontalGradient(listOf(Purple700, PinkE91E63))),
        elevation = 2.dp
    ) {
        Image(
            painterResource(id = R.drawable.lanlancat01),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.Crop,   //不變形的放大到整螢幕,過長,寬的部分會截掉
            alpha = 50.0f,
            //colorFilter = ColorFilter.colorMatrix(ColorMatrix.val)
        )
    }
}

@Composable
fun TabScreenTwo(data: String, viewModel: SecondViewModel, navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.horizontalGradient(listOf(YellowFFEB3B, Green4CAF50)),
            shape = CutCornerShape(5, 10, 15, 20)
        )) {
        Text(text = data)
    }
}

