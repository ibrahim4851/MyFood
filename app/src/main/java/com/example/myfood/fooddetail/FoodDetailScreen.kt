package com.example.myfood.fooddetail

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.myfood.Food
import com.example.myfood.R
import com.example.myfood.commonui.RatingBar
import com.example.myfood.myFoodList

@Composable
fun FoodDetailScreen(
    navController: NavController,
    food: Food
) {
    Surface {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Notifications, null)
                    }
                }
                Text(text = food.foodMeal, color = Color(0xFF128FAE), fontSize = 16.sp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = food.foodTitle,
                        Modifier.align(Alignment.CenterVertically),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Favorite, null)
                    }
                }
                RatingBar(rating = 4.5f, Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .height(150.dp)
                            .weight(1f)
                    ) {
                        Row(Modifier.padding(top = 20.dp)) {
                            Icon(painter = painterResource(id = R.drawable.timer), null)
                            Text("10 minutes", Modifier.align(Alignment.CenterVertically).padding(start = 6.dp))
                        }
                        Row {
                            Icon(painter = painterResource(id = R.drawable.serving), null)
                            Text("2 servings", Modifier.align(Alignment.CenterVertically).padding(start = 6.dp))
                        }
                    }
                    Image(
                        painter = painterResource(food.foodImage),
                        null,
                        modifier = Modifier
                            .weight(1f)
                            .size(170.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )

                }
                LazyRow(
                    content = {
                        items(food.foodIngredientImages) { image ->
                            Image(
                                painter = painterResource(id = image),
                                null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(25.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                        }
                    },
                    modifier = Modifier
                        .padding()
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(50.dp))
                YouTubeVideoButton(
                    youtubeLink = food.foodYoutubeLink,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                )
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    Text("Recipe")
                }

            }
        }
    }
}

@Composable
fun YouTubeVideoButton(
    modifier: Modifier = Modifier,
    youtubeLink: String) {

    val context = LocalContext.current
    var launcher: ActivityResultLauncher<Intent>? = null

    Button(
        modifier = modifier,
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(context, intent, null)
        }) {
        Text("Tutorial")
    }
}