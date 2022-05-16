package com.apsan.atlasgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val painter = painterResource(id = R.drawable.globe)
            val title = "This is the close button bro"
            val desc = "Close Button"
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(15.dp)
                ) {
                    ImageCard(painter_ = painter, contentDesc_ = desc, title_ = title)
                }
                TextStyling("Hello ", "Everyone ", "bye", "-", "bye")
            }

        }
    }
}

data class ImageCardParameters(
    val painter_: Painter,
    val contentDesc_: String,
    val title_: String,
    val modifier_: Modifier
)

//class ImageCardParamProvider : CollectionPreviewParameterProvider<ImageCardParameters>(
//    listOf(
//        ImageCardParameters(
//            painterResource(id = R.drawable.globe),
//            "Close Button",
//            "This is the close button bro",
//            Modifier
//        )
//    )
//)


//@Preview("My Image Card")
@Composable
fun ImageCard(
    painter_: Painter? = painterResource(id = R.drawable.globe),
    contentDesc_: String = "Globe image",
    title_: String = "This is a globe, bro",
    modifier_: Modifier = Modifier
) {
    Card(
        modifier = modifier_,
        shape = RoundedCornerShape(15.dp),
        elevation = 14.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painter_!!, contentDescription = contentDesc_,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black),
                            startY = 400f
                        )
                    )
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = title_,
                    style = TextStyle(color = Color.White, fontSize = 15.sp),
                    modifier = Modifier.padding(16.dp)
                )
            }

        }
    }
}

@Composable
fun TextStyling(vararg words : String) {
    Text(text = buildAnnotatedString {
        for(word in words){
            withStyle(style = SpanStyle(Color.Green, 26.sp, fontWeight = FontWeight.Bold)) {
                append(word[0])
            }
            append(word.substring(1))
        }

    }, color = Color.White,
        fontSize = 18.sp,
        fontStyle = FontStyle.Italic,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.padding(12.dp)
    )
}

