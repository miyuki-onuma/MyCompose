/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.NavigationViewModel
import com.example.androiddevchallenge.ui.Screen
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PuppyApp(navigationViewModel)
        }
    }

    override fun onBackPressed() {
        if (!navigationViewModel.onBack()) {
            super.onBackPressed()
        }
    }
}

@Composable
fun PuppyApp(
    navigationViewModel: NavigationViewModel
) {
    MyTheme {
        AppContent(
            navigationViewModel = navigationViewModel
        )
    }
}

@Composable
private fun AppContent(
    navigationViewModel: NavigationViewModel
) {
    Crossfade(navigationViewModel.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> HomeScreen(
                    navigateTo = navigationViewModel::navigateTo
                )
                is Screen.Detail -> DetailScreen(id = screen.dogId)
            }
        }
    }
}

val dogs = listOf(
    Dog(
        id = "1",
        name = "Amazon",
        subtitle = "cute",
        imageThumbId = R.drawable.dog1
    ), Dog(
        id = "2",
        name = "Scala",
        subtitle = "cute",
        imageThumbId = R.drawable.dog2
    ), Dog(
        id = "3",
        name = "Go",
        subtitle = "cute",
        imageThumbId = R.drawable.dog3
    ), Dog(
        id = "4",
        name = "Java",
        subtitle = "cute",
        imageThumbId = R.drawable.dog4
    ), Dog(
        id = "5",
        name = "Django",
        subtitle = "cute",
        imageThumbId = R.drawable.dog5
    )
)

@Composable
fun HomeScreen(navigateTo: (Screen) -> Unit) {

    Column {
        dogs.forEach { dog ->
            PostList(
                dog = dog,
                navigateTo = navigateTo
            )
            PostListDivider()
        }
    }
}

@Composable
fun DetailScreen(
    id: String
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val dog = dogs.find {
            it.id == id
        }

        Image(
            painter = painterResource(dog!!.imageThumbId),
            contentDescription = null,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(16.dp))

        Text(dog.name)
        Text("Davenport, California")
        Text("December 2018")
    }
}

@Composable
fun PostList(
    dog: Dog,
    navigateTo: (Screen) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = { navigateTo(Screen.Detail(dog.id)) })
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        DogImage(
            dog = dog,
            modifier = Modifier.clickable(onClick = { navigateTo(Screen.Detail(dog.id)) })
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            text = dog.name,
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun DogImage(dog: Dog, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(dog.imageThumbId),
        contentDescription = null, // decorative
        modifier = modifier
            .size(100.dp, 40.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

/**
 * Full-width divider with padding for [PostList]
 */
@Composable
private fun PostListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}
