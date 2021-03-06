package com.pcp.composecomponent

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pcp.composecomponent.ui.theme.*

/*
  Author: Joey yang
  Create date: 22/05/01

  First screen:
    1. Column
    2. Text
    3. Button
    4. OutlinedTextField
    5. DropdownMenu
        5-1. DropdownMenuItem
    6. TextField
    7. OutlinedButton
    8. OutlinedTextField
    9. DropdownMenu
    10. ExposeDropdownMenuBox
    11. Badge  //https://www.youtube.com/watch?v=4xyRnIntwTo
    12. BottomNavigationBar
       ??????????????????,???A?????????B???,????????? BottomNavigationBar, ????????????,??????NavHost????????????
       Navigation?????????????????????????????????: https://developer.android.com/jetpack/compose/navigation
    13. Box
    14. Scaffold
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
                    composable("third_screen") {
                        ThirdScreen(navControllerFrom = navController)
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
                fontStyle = FontStyle.Italic,   //???????????????
                fontWeight = FontWeight.Bold,   //???????????????
                fontFamily = FontFamily.SansSerif,  //???????????????
                letterSpacing = 4.sp,
                style = TextStyle(textAlign = TextAlign.Center),
                textDecoration = TextDecoration.LineThrough,
                textAlign = TextAlign.Start,  //???????????????
                lineHeight = 30.sp,
                overflow = TextOverflow.Ellipsis, //???????????????,???????????????,??????????????????????????? ...
                softWrap = true, //??????????????????soft line breaks.??????false, text?????????(glyphs)???????????????????????????????????????,
                    // overflow???TextAlign??????????????????????????????.
                maxLines = 2,
                onTextLayout = { textLayout ->
                    Log.v("TAG","text width: ${textLayout.size.width}, text height: ${textLayout.size.height}")
                },
                // style????????????????????????????????????,????????????
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
        OutlinedButtonDemo()
        OutlinedTextFieldDropdownMenuDemo()
        TextFieldDemo()
        ExposeDropdownMenuBoxDemo()
        BoxDemo()
    }
}

@Composable
fun BoxDemo() {
    Box(modifier = Modifier.size(100.dp, 100.dp)
        .background(color = YellowFFEB3B),
      contentAlignment = Alignment.BottomEnd,
      propagateMinConstraints = false)
      //propagateMinConstraints : Box????????????????????????,???????????????????????????,????????????????????????????????????View,????????????, true, false????????????
    {
        Text("Test1")
        Box(modifier = Modifier.align(Alignment.TopCenter).
            fillMaxHeight().
            width(50.dp).
            background(color = PinkE91E63)
        )
        Text("Test2")
    }
}

@Composable
fun ThirdScreen(navControllerFrom: NavController) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))

    val visibleState:MutableState<Boolean> =
        remember { mutableStateOf(true) }
    val snackBarMessage:MutableState<String> =
        remember { mutableStateOf("This is a snackbar") }
    Log.v("TAG", "Third screen: ${scaffoldState.toString()}")
    Scaffold(
        modifier = Modifier.padding(5.dp),
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = { Text("TopAppBar")},
            backgroundColor = BlueFF9979)   //??????????????????
        }, //????????? @Composable () -> Unit = {} ?????????
        bottomBar = { BottomAppBar(backgroundColor = YellowEEF88B) {
            Text("BottomAppBar")
        }},
        snackbarHost = {
            if (visibleState.value){
                Snackbar(
                    action = {
                        TextButton(
                            onClick = { visibleState.value = false }
                        ) {
                            Text(text = "Hide")
                        }
                    },
                    content = {
                        Text(text = snackBarMessage.value)
                    },
                    backgroundColor = Color(0xFFFF9966)
                )
            }
        },
        floatingActionButton = { FloatingActionButton(onClick = {}) {
            Text("X")
        } },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,
        drawerContent = { Text(text = "drawerContent") },
        drawerGesturesEnabled = true,
        drawerShape = MaterialTheme.shapes.medium,
        drawerElevation = 10.dp,
        drawerBackgroundColor = Green1BFD02,
        drawerContentColor = GrayBBBABA,
        drawerScrimColor = YellowFFEB3B,
        backgroundColor = BlueFF9979,
        contentColor = Teal200,
        content = { Text("BodyContent") },

    )
}
/*
@Composable
fun ThirdScreen(navControllerFrom: NavController) {
    val navControllerV2 = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = "three_home_view",
                        icon = Icons.Default.Home
                    ),
                    BottomNavItem(
                        name = "Chat",
                        route = "three_chat_view",
                        icon = Icons.Default.Notifications
                    ),
                    BottomNavItem(
                        name = "Setting",
                        route = "three_setting_view",
                        icon = Icons.Default.Settings
                    ),
                ),
                navController = navControllerV2,
                onItemClick = {
                    navControllerV2.navigate(it.route)
                }
            )
        }
    ) {
        //Text("Hello Joey")
        NavigationThird(navController = navControllerV2)
    }
}

 */

@Composable
fun NavigationThird(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("three_home_view") {
            ThreeHomeView(navController = navController)
        }
        composable("three_chat_view") {
            ThreeChatView(navController = navController)
        }
        composable("three_setting_view") {
            ThreeSettingView(navController = navController)
        }
    }
}

@Composable
fun OutlinedTextFieldDropdownMenuDemo() {
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Item1", "Item2", "Item3",)
    var selectedText by remember { mutableStateOf("") } //??????: ???????????? import library,??????AS????????? import androidx.compose.runtime.*
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val keyboardOption = KeyboardOptions(autoCorrect = true)   //??????,????????????,???????????????
    val keyboardAction = KeyboardActions(onDone = {})

    val icon2 = if (expanded) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward
    val icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    // ??????,??????????????? interactionSource ?????????
    val interactionSourceTest = remember { MutableInteractionSource() }
    val pressState = interactionSourceTest.collectIsPressedAsState()
    val borderColor = if (pressState.value) YellowFFEB3B else Green4CAF50 //Import com.pcp.composecomponent.ui.theme.YellowFFEB3B

    OutlinedTextField(
        value = selectedText,
        onValueChange = { selectedText = it },
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->  //??????
                textfieldSize = coordinates.size.toSize()
            },
        enabled = true,
        readOnly = true,
        textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
        label = { Text("OutlinedTextField & DropdownMenu")},
        placeholder = { Text("Enter info") },
        leadingIcon = {    //??????
            Icon(icon2, "contentDescription",
                Modifier.clickable { expanded = !expanded })
        },
        trailingIcon = {    //??????
            Icon(icon, "contentDescription",
                Modifier.clickable { expanded = !expanded })
        },
        isError = false,    //????????????text fields????????????????????????,???true, label, bottom indicator??? trailingIcon ??????????????????????????????
        visualTransformation = PasswordVisualTransformation(), //???????????????
        keyboardOptions = keyboardOption,
        keyboardActions = keyboardAction,
        singleLine = false,
        maxLines = 2,
        interactionSource = interactionSourceTest,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = PinkE91E63),
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .width(with(LocalDensity.current) {
                textfieldSize.width.toDp() }),
        offset = DpOffset(10.dp, 10.dp),
        properties = PopupProperties(focusable = true, dismissOnClickOutside = false, securePolicy = SecureFlagPolicy.SecureOn), // ??????
    ) {
        suggestions.forEach { label ->
            DropdownMenuItem(onClick = {
                selectedText = label
                expanded = false },
                modifier = Modifier.background(Teal200),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
                interactionSource = interactionSourceTest) {    //?????????????????????????????????
                Text(text = label)
            }
        }
    }
}

@Composable
fun ButtonDemo() {
    // ??????,??????????????? interactionSource ?????????
    val interactionSourceTest = remember { MutableInteractionSource() }
    val pressState = interactionSourceTest.collectIsPressedAsState()
    val borderColor = if (pressState.value) YellowFFEB3B else Green4CAF50 //Import com.pcp.composecomponent.ui.theme.YellowFFEB3B

    Button( //Button??????????????????,??????????????????,?????????????????????Text
        modifier = Modifier.wrapContentWidth(),
        //enabled = false,
        enabled = true, //?????? enabled ??????false, border, interactionSource??????????????????
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
        Text(text = "Button") }
}

@Composable
fun TextFieldDemo() {
    val interactionSourceTest = remember { MutableInteractionSource() }
    val pressState = interactionSourceTest.collectIsPressedAsState()

    var textInfo by remember { mutableStateOf("TextField") }
    val icon2 = Icons.Filled.ArrowUpward
    val icon = Icons.Filled.ArrowDropUp
    val keyboardOption = KeyboardOptions(autoCorrect = true)   //??????,????????????,???????????????
    val keyboardAction = KeyboardActions(onDone = {})

    TextField(
        value = textInfo,
        onValueChange = { textInfo = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        enabled = true,
        //readOnly = true,
        readOnly = false,
        textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
        label = { Text("TextField Label")},
        placeholder = { Text("Enter info") },
        leadingIcon = {    //??????
            Icon(icon2, "contentDescription",
                Modifier.clickable { textInfo = "TextField leading icon click" })
        },
        trailingIcon = {    //??????
            Icon(icon, "contentDescription",
                Modifier.clickable { textInfo = "TextField trailing icon click" })
        },
        isError = false,    //????????????text fields????????????????????????,???true, label, bottom indicator??? trailingIcon ??????????????????????????????
        //visualTransformation = PasswordVisualTransformation(), //???????????????
        visualTransformation = VisualTransformation.None,
        keyboardOptions = keyboardOption,
        keyboardActions = keyboardAction,
        singleLine = false,
        maxLines = 2,
        interactionSource = interactionSourceTest,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = YellowEEF88B),
    )
}

@Composable
fun OutlinedButtonDemo() {
    val interactionSourceTest = remember { MutableInteractionSource() }
    val pressState = interactionSourceTest.collectIsPressedAsState()
    val colorMy = if (pressState.value) YellowFFEB3B else Green4CAF50 //Import com.pcp.composecomponent.ui.theme.YellowFFEB3B

    OutlinedButton(
        onClick = {},
        modifier = Modifier.fillMaxWidth(1f),
        enabled = true,
        interactionSource = interactionSourceTest,
        elevation = ButtonDefaults.elevation(defaultElevation = 6.dp, pressedElevation = 8.dp),     //??????,???????????????????????????????????????,???????????????????????????,???????????????????????????????????????
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(width = 10.dp, brush = Brush.horizontalGradient(listOf(Purple700, PinkE91E63))),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = colorMy, contentColor = Teal200),
    ) {
        Text( color = Purple500,
            text ="OutlinedButtonDemo")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposeDropdownMenuBoxDemo() {
    val options = listOf("ExposeDropdownMenuBox 1", "ExposeDropdownMenuBox 2", "ExposeDropdownMenuBox 3", "ExposeDropdownMenuBox 4", "ExposeDropdownMenuBox 5")
    var expandedValue by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expandedValue,
        onExpandedChange = {
            expandedValue = !expandedValue
        },
        modifier = Modifier.padding(top = 5.dp)
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Label")},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expandedValue
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = Blue8898F3)  //??????????????????
        )
        ExposedDropdownMenu(
            expanded = expandedValue,
            onDismissRequest = {
                expandedValue = false
            },
            modifier = Modifier.background(Teal200)
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(   // DropdownMenuItem ??????project?????????
                    onClick = {
                        selectedOptionText = selectionOption
                        expandedValue = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

//Badge Step 01: Create BottomNavigationBar
// Reference : https://www.youtube.com/watch?v=4xyRnIntwTo
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,    //??????????????????,????????????????????????????????????
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()   //??????,?????? backStack??? Entry
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selectedRoute = item.route == backStackEntry.value?.destination?.route  //??????,??????????????????????????????
            BottomNavigationItem(
                //selected = item.route == navController.currentDestination?.route,   //??????,??????????????????????????????,?????????????????????
                selected = selectedRoute,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if(item.badgeCount > 0) {
                            BadgedBox(  //??????,????????? BadgedBox,?????? BadgeBox
                                badge = {
                                    Text(text = item.badgeCount.toString())
                                }
                            ) {
                                Icon(imageVector = item.icon,
                                    contentDescription = item.name)
                            }
                        } else {
                            Icon(imageVector = item.icon,
                                contentDescription = item.name)
                        }
                        if(selectedRoute) { //??????,???Youtube???????????????
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeComponentTheme {
        FirstScreen(rememberNavController())    //??????,???????????????Preview???
    }
}