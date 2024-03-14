package com.clementcoutarel.musicapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(){

    val viewModel:MainViewModel = viewModel()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope :CoroutineScope = rememberCoroutineScope()
    val controller:NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentScreen = remember {
        viewModel.currentScreen.value
    }

    val title = remember {
        mutableStateOf(currentScreen.title)
    }

    val dialogOpen = remember {
        mutableStateOf(false)
    }

    if (currentRoute != null) {
        Log.d("route",currentRoute)
    }

    val bottomBar : @Composable () -> Unit = {
        if(currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home){
            BottomNavigation(modifier = Modifier.wrapContentSize()) {
                screensInBottom.forEach {
                    item ->
                    BottomNavigationItem(
                        selected = currentRoute == item.bRoute,
                        onClick = {
                                  controller.navigate(item.bRoute)
                        },
                        icon = {
                            Icon(painter = painterResource(id = item.icon),
                                contentDescription = item.bTitle )
                        },
                        label = { Text(text = item.bTitle)},
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Black)
                }
            }
        }
    }

    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopAppBar(title = { Text(text = title.value) },
                navigationIcon = { IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Menu")
                }}
            )
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(screensInDrawer){
                    item ->
                    DrawerItem(selected = currentRoute == item.dRoute, item = item) {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        if(item.dRoute == "add_account"){
                            dialogOpen.value = true
                        } else {
                            controller.navigate(item.dRoute)
                            title.value = item.dTitle
                        }
                    }
                }
            }
        }
        
    ) {
        Navigation(
            navController = controller,
            viewModel = viewModel,
            pd = it )

        AccountDialog(dialogOpen = dialogOpen)
    }
}

@Composable
fun DrawerItem(
    selected:Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemSelected:() -> Unit
){
    val background = if(selected) Color.DarkGray else Color.White

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .background(background)
        .clickable { onDrawerItemSelected() }) {
        Icon(
            painter = painterResource(id = item.icon) ,
            contentDescription = item.dTitle,
            modifier = Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(text = item.dTitle,
            style = MaterialTheme.typography.h5)

    }
}