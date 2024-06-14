package com.example.quotify


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quotify.ui.theme.QuotifyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = ViewModelProvider(this,MainViewModelFactory(application))[MainViewModel::class.java]
            QuotifyTheme {
                App(mainViewModel,this)
            }
        }
    }
}


@Composable
fun Top(modifier: Modifier = Modifier) {
    Text(
        text = "Quotify",
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        textAlign = TextAlign.Center,
        fontSize = 40.sp,
        fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
    )
}


@Composable
fun Middle(quote: Quote, context: Context, modifier: Modifier = Modifier){
    Box (
        modifier = modifier
    ){
        Box(
            modifier = modifier
                .fillMaxWidth()
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(16.dp))
        ){
            Column(
                modifier = modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(12.dp)
                    .padding(bottom = 25.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_quote),
                    contentDescription = "quote"
                )
                Text(
                    text = quote.text,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier.padding(bottom = 0.dp, start = 10.dp),
                    fontFamily = FontFamily.Cursive,
                    letterSpacing = (1.7).sp,
                    lineHeight = 30.sp,
                )
                Text(
                    text = quote.author,
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                    modifier = Modifier.padding(start = 10.dp, bottom = 7.dp),
                )
            }
        }
        Button(
            onClick = { onShare(quote.text,context) },
            contentPadding = ButtonDefaults.TextButtonContentPadding,
            modifier = modifier
                .align(Alignment.BottomEnd)
                .offset((-35).dp,35.dp)
                .background(Color(0xFF454545), CircleShape)
            ,
            colors = ButtonDefaults.textButtonColors(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = "Share",
                modifier = Modifier
            )
        }
    }
}

fun onShare(quote: String,context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = ("text/plain")
        putExtra(Intent.EXTRA_TEXT,quote)
    }
    val intentChose = Intent.createChooser(intent, "Share via")
    try {
        startActivity(context,intentChose,null)
    }catch (_:Exception){}
}


@Composable
fun Bottom(model: MainViewModel, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { model.previousQuote() },
            modifier = modifier,
            contentPadding = ButtonDefaults.TextButtonWithIconContentPadding,
            colors = ButtonDefaults.textButtonColors()
        ) {
            Text(
                text = "Previous",
                modifier = Modifier,
                color = Color.White,
                fontSize = 27.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semibold))
            )
        }
        Button(
            onClick = { model.nextQuote() },
            modifier = modifier,
            contentPadding = ButtonDefaults.TextButtonWithIconContentPadding,
            colors = ButtonDefaults.textButtonColors()
        ) {
            Text(
                text = "Next",
                modifier = Modifier,
                color = Color.White,
                fontSize = 27.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semibold))
            )
        }
    }
}


@Composable
fun App(model: MainViewModel,context: Context,modifier: Modifier = Modifier){
    Box(modifier = modifier
        .background(
            brush = Brush
                .linearGradient(
                    listOf(Color(0xFF010207),Color(0xFF102671),Color(0xFFC33764))
                            //listOf(Color(0xFF102671), Color(0xFFC33764))
                )
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp, 25.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Top()
            Middle(model.currentQuote,context)
            Bottom(model)
        }
    }
}


/*
@Preview()
@Composable
fun TopPreview() {
    QoutifyTheme {
        Top()
    }
}

@Preview()
@Composable
fun MiddlePreview() {
    QuotifyTheme {
        Middle(c,MainActivity())
    }
}

@Preview()
@Composable
fun BottomPreview() {
    QoutifyTheme {
        Bottom()
    }
}
*/
/*
@Preview()
@Composable
fun AppPreview() {
    QuotifyTheme {
        val mainViewModel: MainViewModel = ViewModelProvider(MainActivity(),MainViewModelFactory(MainActivity()))[MainViewModel::class.java]
        App(mainViewModel,MainActivity())
    }
}
*/