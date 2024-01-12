package com.example.myfood.filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfood.Food
import com.example.myfood.R
import com.example.myfood.commonui.RatingBar
import com.example.myfood.myFoodList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodCard(
    food: Food,
    onRecipeItemClick: (Food) -> Unit
) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.DarkGray)
        .padding(4.dp),
        onClick = {
            onRecipeItemClick(food)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { addFavorite(food.foodId.toString()) }) {
                    Icon(Icons.Outlined.Favorite, contentDescription = null)
                }

                Image(
                    painter = painterResource(id = food.foodImage),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(130.dp)
                )
            }

            Text(text = food.foodCourse, color = Color.Blue)
            Text(text = food.foodTitle)
            Spacer(Modifier.padding(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Icon(painter = painterResource(id = R.drawable.timer), null)
                Spacer(Modifier.padding(2.dp))
                Text(text = food.foodTime.toString(), Modifier.align(Alignment.CenterVertically))
            }
            if (food.foodTitle.length > 11) {
                RatingBar(4.5f, Modifier.height(15.dp))
            } else {
                RatingBar(5.0f, Modifier.height(15.dp))
            }
            Spacer(Modifier.padding(bottom = 8.dp))
        }
    }
}

fun addFavorite(foodId: String) {

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFoodCard() {
    FoodCard(food = myFoodList[3], onRecipeItemClick = {})
}