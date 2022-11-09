package com.appdexon.jettriviaapp.component


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.appdexon.jettriviaapp.data.ApiResponse
import com.appdexon.jettriviaapp.model.QuestionItem
import com.appdexon.jettriviaapp.screens.QuestionViewModel
import com.appdexon.jettriviaapp.util.AppColors



@Composable
fun Questions(
    viewModel: QuestionViewModel, navController: NavHostController

) {
    when (val responseState: ApiResponse = viewModel.responseMutableState.value) {
        is ApiResponse.Loading -> Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(color = AppColors.mTeal), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = AppColors.mYellow)
        }

        is ApiResponse.Error -> Box(
            //TODO Error Screen Design
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) { Text("Error: ${responseState.getMessage()}") }

        is ApiResponse.Questions -> {
            val questionIndex = viewModel.questionIndex.value
            val questionItem = try {
                responseState.list[questionIndex]
            } catch (exc: Exception) {
                null
            }
            if (questionItem != null) {
                QuestionDisplay(
                    question = questionItem,
                    numOfQuestions = responseState.list.size,
                    viewModel = viewModel,
                )
            }
        }
    }
}

//val questionItem = QuestionItem(
//    question = "What is your name? Tell me otherwise I am going to complain. Ok. What is your name? Tell me otherwise I am going to complain. Ok. What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok. What is your name? Tell me otherwise I am going to complain. Ok.",
//    answer = "Sourav",
//    choices = listOf("Anisha What is your name? Tell me otherwise I am going to complain. Ok. What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.What is your name? Tell me otherwise I am going to complain. Ok.", "Sourav", "Hemant", "Rakesh"),
//    category = "animals"
//)

@Composable
fun QuestionDisplay(
    question: QuestionItem,
    numOfQuestions: Int,
    viewModel: QuestionViewModel,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val choicesState = remember(question) {
        question.choices.toMutableList()
    }
    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }
    val correctAnswerState = remember {
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }

    fun getBGColor(index: Int, border: Boolean): Color {
        if (question.answer == choicesState[index] && answerState.value != null) {
            return AppColors.mTeal
        } else if (answerState.value == index && correctAnswerState.value == false) {
            return AppColors.mRed
        }
        if (border)
            return AppColors.mLightTeal
        return Color.Transparent
    }

    fun getTextColor(index: Int): Color {
        if (answerState.value == null) {
            return AppColors.mDarkTeal
        }
        if (answerState.value == index || question.answer == choicesState[index]) {
            return Color.White
        }
        return AppColors.mDarkTeal
    }


//    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), phase = 0f)
    Scaffold(
        topBar = {
            TopAppBar(title = {Text(viewModel.getCategoryTitle())},
        )
                 },

        floatingActionButton = {
            if (answerState.value != null) {
                ExtendedFloatingActionButton(
                    text = { Text(text = "Next Question") },
                    onClick = {
                        viewModel.nextQuestion()
                        answerState.value = null
                        correctAnswerState.value = null
                    })
            }
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            QuestionCard(
                heightOfQCard = screenHeight * 0.38f,
                heightOfChipCard = 40.dp,
                question = question.question,
                count = viewModel.questionIndex.value + 1,
                numOfQuestions = numOfQuestions
            )
            choicesState.forEachIndexed { index, choice ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .border(
                            width = 2.dp, color = getBGColor(index, true),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(
                            getBGColor(index, false)
                        )
                        .clickable(
                            enabled = answerState.value == null
                        ) {
                            if (answerState.value == null) {
                                updateAnswer(index)
                            }
                        }
                ) {
                    Text(
                        text = choice,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center),
                        color = getTextColor(index),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    }
}


@Composable
fun ShowProgress(score: Int = 200) {
    val gradient = Brush.linearGradient(
        listOf(
            Color(0XFFF95075), Color(0XFFBE6BE5)
        )
    )
    val progressFactor = remember(score) {
        mutableStateOf(score * 0.005f)
    }
    Row(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(45.dp)
            .border(
                width = 4.dp, brush = Brush.linearGradient(
                    colors = listOf(
                        AppColors.mLightPurple, AppColors.mLightPurple
                    )
                ), shape = RoundedCornerShape(34.dp)
            )
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomStartPercent = 50,
                    bottomEndPercent = 50
                )
            )
            .background(Color.Transparent), verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(progressFactor.value)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent,
            )
        ) {
            Text(
                text = (score * 10).toString(),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun QuestionTracker(counter: Int = 10, outOf: Int = 100) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGray, fontWeight = FontWeight.Bold, fontSize = 27.sp
                )
            ) {
                append("Question $counter/")
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp
                    ),
                ) {
                    append("$outOf")
                }
            }
        }
    }, modifier = Modifier.padding(20.dp))
}

//
//@Composable
//fun QuestionDisplay(
//    question: QuestionItem,
//    questionIndex: Int,
//    numOfQuestions: Int,
//    viewModel: QuestionViewModel,
//    onNextClick: (Int) -> Unit = {},
//) {
//    val choicesState = remember(question) {
//        question.choices.toMutableList()
//
//    }
//
//    val answerState = remember(question) {
//        mutableStateOf<Int?>(null)
//    }
//    val correctAnswerState = remember {
//        mutableStateOf<Boolean?>(null)
//    }
//    val updateAnswer: (Int) -> Unit = remember(question) {
//        {
//            answerState.value = it
//            correctAnswerState.value = choicesState[it] == question.answer
//        }
//    }
//    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), phase = 0f)
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight(), color = AppColors.mDarkPurple
//    ) {
//        Column(
//            modifier = Modifier.padding(12.dp),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.Start
//        ) {
//            if (questionIndex >= 3) ShowProgress(score = questionIndex)
//            QuestionTracker(counter = questionIndex + 1, outOf = numOfQuestions)
//            DrawDottedLine(pathEffect = pathEffect)
//            Column {
//                Text(
//                    text = question.question,
//                    modifier = Modifier
//                        .padding(6.dp)
//                        .align(alignment = Alignment.Start)
//                        .fillMaxHeight(0.3f)
//                        .verticalScroll(rememberScrollState(0)),
//                    fontSize = 17.sp,
//                    color = AppColors.mOffWhite,
//                    fontWeight = FontWeight.Bold,
//                    lineHeight = 22.sp
//                )
//                choicesState.forEachIndexed { index, answerText ->
//                    Row(
//                        modifier = Modifier
//                            .padding(3.dp)
//                            .fillMaxWidth()
//                            .height(45.dp)
//                            .border(
//                                width = 4.dp, brush = Brush.linearGradient(
//                                    colors = listOf(
//                                        AppColors.mOffDarkPurple, AppColors.mOffDarkPurple
//                                    )
//                                ), shape = RoundedCornerShape(
//                                    15.dp
//                                )
//                            )
//                            .clip(
//                                shape = RoundedCornerShape(
//                                    topStartPercent = 50,
//                                    topEndPercent = 50,
//                                    bottomEndPercent = 50,
//                                    bottomStartPercent = 50
//                                )
//                            )
//                            .background(Color.Transparent),
//                        verticalAlignment = Alignment.CenterVertically
//
//                    ) {
//                        RadioButton(
//                            selected = (answerState.value == index),
//                            onClick = {
//                                updateAnswer(index)
//                            },
//                            modifier = Modifier.padding(start = 16.dp),
//                            colors = RadioButtonDefaults.colors(
//                                selectedColor = if (correctAnswerState.value == true && index == answerState.value) {
//                                    Color.Green.copy(alpha = 0.2f)
//                                } else {
//                                    Color.Red.copy(alpha = 0.2f)
//
//                                }
//                            )
//                        )
//                        val annotatedAnswerString = buildAnnotatedString {
//                            withStyle(
//                                style = SpanStyle(
//                                    fontWeight = FontWeight.Light,
//                                    color = if (correctAnswerState.value == true && index == answerState.value) {
//                                        Color.Green
//                                    } else if (correctAnswerState.value == false && index == answerState.value) {
//                                        Color.Red
//                                    } else {
//                                        AppColors.mOffWhite
//                                    },
//                                    fontSize = 17.sp
//                                )
//                            ) {
//                                append(answerText)
//                            }
//                        }
//                        Text(text = annotatedAnswerString, modifier = Modifier.padding(6.dp))
//                    }
//                }
//                Button(
//                    onClick = { onNextClick(questionIndex) },
//                    modifier = Modifier
//                        .padding(3.dp)
//                        .align(alignment = Alignment.CenterHorizontally),
//                    shape = RoundedCornerShape(34.dp),
//                    colors = buttonColors(
//                        backgroundColor = AppColors.mLightBlue
//                    )
//                ) {
//                    Text(
//                        text = "Next",
//                        modifier = Modifier.padding(4.dp),
//                        color = AppColors.mOffWhite,
//                        fontSize = 17.sp
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun DrawDottedLine(pathEffect: PathEffect) {
//    Canvas(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(1.dp)
//    ) {
//        drawLine(
//            color = AppColors.mLightGray,
//            start = Offset(x = 0f, y = 0f),
//            end = Offset(x = size.width, y = 0f),
//            pathEffect = pathEffect
//        )
//    }
//}
//
//@Preview
//@Composable
//fun ShowProgress(score: Int = 200) {
//    val gradient = Brush.linearGradient(
//        listOf(
//            Color(0XFFF95075), Color(0XFFBE6BE5)
//        )
//    )
//    val progressFactor = remember(score) {
//        mutableStateOf(score * 0.005f)
//    }
//    Row(
//        modifier = Modifier
//            .padding(3.dp)
//            .fillMaxWidth()
//            .height(45.dp)
//            .border(
//                width = 4.dp, brush = Brush.linearGradient(
//                    colors = listOf(
//                        AppColors.mLightPurple, AppColors.mLightPurple
//                    )
//                ), shape = RoundedCornerShape(34.dp)
//            )
//            .clip(
//                RoundedCornerShape(
//                    topStartPercent = 50,
//                    topEndPercent = 50,
//                    bottomStartPercent = 50,
//                    bottomEndPercent = 50
//                )
//            )
//            .background(Color.Transparent), verticalAlignment = Alignment.CenterVertically
//    ) {
//        Button(
//            contentPadding = PaddingValues(1.dp),
//            onClick = {},
//            modifier = Modifier
//                .fillMaxWidth(progressFactor.value)
//                .background(brush = gradient),
//            enabled = false,
//            elevation = null,
//            colors = buttonColors(
//                backgroundColor = Color.Transparent,
//                disabledBackgroundColor = Color.Transparent,
//            )
//        ) {
//            Text(
//                text = (score * 10).toString(),
//                modifier = Modifier
//                    .clip(shape = RoundedCornerShape(23.dp))
//                    .fillMaxHeight(0.87f)
//                    .fillMaxWidth()
//                    .padding(6.dp),
//                color = AppColors.mOffWhite,
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}
//
//
//@Composable
//fun QuestionTracker(counter: Int = 10, outOf: Int = 100) {
//    Text(text = buildAnnotatedString {
//        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
//            withStyle(
//                style = SpanStyle(
//                    color = AppColors.mLightGray, fontWeight = FontWeight.Bold, fontSize = 27.sp
//                )
//            ) {
//                append("Question $counter/")
//                withStyle(
//                    style = SpanStyle(
//                        color = AppColors.mLightGray,
//                        fontWeight = FontWeight.Light,
//                        fontSize = 14.sp
//                    ),
//                ) {
//                    append("$outOf")
//                }
//            }
//        }
//    }, modifier = Modifier.padding(20.dp))
//}
