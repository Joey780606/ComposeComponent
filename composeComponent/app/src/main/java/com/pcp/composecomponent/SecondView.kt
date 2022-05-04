package com.pcp.composecomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.pcp.composecomponent.ui.theme.Blue8898F3
import com.pcp.composecomponent.ui.theme.Purple200
import com.pcp.composecomponent.ui.theme.YellowEEF88B
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
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SecondScreen(navController: NavController) {
    val viewModel: SecondViewModel = viewModel()
    val pagerState = rememberPagerState(pageCount = 2)
    Column(
        modifier = Modifier.background(YellowEEF88B, shape = RoundedCornerShape(8.dp))
            .fillMaxSize()
    ) {
        Text(
            text = "Second screen, click me to First Screen",
            color = Purple200,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.clickable(onClick = {
                navController.navigate("first_screen")
            })
        )
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, viewModel, navController)
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
            1 -> TabScreenTwo(data = "Tab Screen 2", viewModel, navController)
        }
    }
}

@Composable
fun TabScreenOne(data: String, viewModel: SecondViewModel, navController: NavController) {
    Text(text = data)
}

@Composable
fun TabScreenTwo(data: String, viewModel: SecondViewModel, navController: NavController) {
    Text(text = data)
}

