package com.appdexon.jettriviaapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdexon.jettriviaapp.util.AppColors


@Composable
fun QuestionCard(
    heightOfQCard: Dp,
    heightOfChipCard: Dp,
    question: String,
    count: Int,
    numOfQuestions: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .height(heightOfQCard)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(heightOfQCard - heightOfChipCard)
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState())
                .background(color = AppColors.mYellow, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center,

            ) {
            Text(
                text = question,
                modifier = Modifier.padding(heightOfChipCard / 2),
                color = AppColors.mDarkYellow,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                style = MaterialTheme.typography.subtitle1
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(heightOfChipCard)
                .background(color = AppColors.mWhite, shape = RoundedCornerShape(50)),
            contentAlignment = Alignment.Center,

            ) {
            Text(
                text = "${count}/${numOfQuestions}",
                modifier = Modifier.padding(horizontal = heightOfChipCard / 2),
                color = AppColors.mDarkTeal,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(heightOfChipCard)
                .background(color = AppColors.mWhite, shape = RoundedCornerShape(50)),
            contentAlignment = Alignment.Center,


            ) {
            Text(
                text = "?",
                modifier = Modifier.padding(horizontal = heightOfChipCard / 2),
                color = AppColors.mDarkTeal,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
        }

    }
}