package com.jshvarts.motionlayoutcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.jshvarts.motionlayoutcomposedemo.ui.theme.MotionLayoutComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotionLayoutComposeDemoTheme {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    var progress by remember {
                        mutableStateOf(0f)
                    }
                    Logo(progress = progress)
                    Slider(
                        value = progress,
                        onValueChange = {
                            progress = it
                        },
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun Logo(progress: Float) {
    val context = LocalContext.current
    val motionSceneContent = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }
    MotionLayout(
        motionScene = MotionScene(motionSceneContent),
        progress = progress,
        modifier = Modifier
            .fillMaxWidth(),
        //debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL)
    ) {
        val properties = motionProperties(id = "my_text")

        Image(
            painter = painterResource(
                id = R.drawable.jetpack_compose_icon
            ),
            contentDescription = stringResource(R.string.compose_icon_description),
            modifier = Modifier
                .layoutId("my_image")
        )
        Divider(
            color = Color.Gray,
            thickness = 2.dp,
            modifier = Modifier
                .layoutId("my_divider")
        )
        Text(
            text = stringResource(R.string.text_compose),
            color = properties.value.color("textColor"),
            modifier = Modifier
                .layoutId("my_text")
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LogoPreview() {
    MotionLayoutComposeDemoTheme {
        Logo(1F)
    }
}