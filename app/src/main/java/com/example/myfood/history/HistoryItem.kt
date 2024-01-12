package com.example.myfood.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfood.Food
import com.example.myfood.R
import com.example.myfood.commonui.RatingBar
import com.example.myfood.myFoodList

@Composable
fun HistoryItem(food: Food) {
    ElevatedCard {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Image(
                    painter = painterResource(id = food.foodImage),
                    null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(Modifier.padding(end = 8.dp))
            Column(modifier = Modifier.weight(3f)) {
                Text(text = food.foodCourse, color = Color(0xFF128FAE))
                Text(text = food.foodTitle, fontSize = 22.sp)
                RatingBar(rating = 4.6f, modifier = Modifier.height(14.dp))
                Row(Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(painter = painterResource(id = R.drawable.timer), null)
                    Text(
                        text = food.foodTime.toString() + " mins",
                        Modifier.align(Alignment.CenterVertically),
                        color = Color(0xFF7B7B7B)
                    )
                    Spacer(Modifier.padding(end = 8.dp))
                    Icon(painter = painterResource(id = R.drawable.serving), null)
                    Text(
                        text = food.foodServings.toString() + " servings",
                        Modifier.align(Alignment.CenterVertically),
                        color = Color(0xFF7B7B7B)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HistoryItemPreview() {
    HistoryItem(food = myFoodList[12])
}