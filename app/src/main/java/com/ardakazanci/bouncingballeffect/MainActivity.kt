package com.ardakazanci.bouncingballeffect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ardakazanci.bouncingballeffect.ui.theme.BouncingBallEffectTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BouncingBallEffectTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0XFFFCFCFC),
                                    Color(0xffFCFCFC).copy(alpha = 0.5f)
                                )
                            )
                        )
                ) { innerPadding ->
                    BouncingBallScreen()
                }
            }
        }
    }
}

@Composable
fun BouncingBallScreen() {
    val density = LocalDensity.current
    val position by remember { mutableFloatStateOf(200f) }
    val animatableObj = remember { Animatable(position) }
    var rectDeflection by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(key1 = true) {
        while (true) {
            animatableObj.animateTo(
                targetValue = with(density) { 610.dp.toPx() },
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
            rectDeflection = 60f
            delay(100)
            rectDeflection = 0f
            animatableObj.animateTo(
                targetValue = 500f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val rectHeight = with(density) { 200.dp.toPx() }
            val radius = with(density) { 40.dp.toPx() }

            val rectPath = Path().apply {
                moveTo(0f, size.height - rectHeight)
                lineTo(size.width * 0.1f, size.height - rectHeight)
                quadraticBezierTo(
                    size.width * 0.5f, size.height - rectHeight + rectDeflection,
                    size.width * 0.9f, size.height - rectHeight
                )
                lineTo(size.width, size.height - rectHeight)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            drawPath(
                path = rectPath,
                brush = Brush.linearGradient(listOf(Color(0XFF160C28), Color(0xffEFCB68)))
            )

            drawCircle(
                brush = Brush.linearGradient(listOf(Color(0XFFE1EFE6), Color(0XFFAEB7B3))),
                center = Offset(x = size.width / 2, y = animatableObj.value - radius),
                radius = radius
            )
        }
    }
}