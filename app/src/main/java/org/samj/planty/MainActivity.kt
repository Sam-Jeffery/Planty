package org.samj.planty

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.samj.planty.ui.theme.PlantyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.statusBarsPadding()) {
                        PlantCard(
                            plant = Plant("Bonsai", 5)
                        )
                        PlantCard(
                            plant = Plant("Leafy", 2)
                        )
                    }
                }
            }
        }
    }
}

data class Plant(val name: String, val nextWater: Int)

@Composable
fun PlantCard(plant: Plant){
    var expanded by remember { mutableStateOf(true) }
    var cardHeightPx by remember { mutableIntStateOf(250) }
    var animatedHeightPx by remember { mutableIntStateOf(75) }

    val density = LocalDensity.current

    val totalHeight by animateDpAsState(
        targetValue = with(density) { (cardHeightPx + if (expanded) animatedHeightPx else 0).toDp()},
        label = "SurfaceHeightAnimation"
    )

    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .clickable { expanded = !expanded }
            .animateContentSize()
            .height(totalHeight)
    ){
        Box{
            AnimatedVisibility(
                visible = expanded,
                enter = slideInVertically { -it },
                exit = slideOutVertically { -it },
                modifier = Modifier
                    .offset {
                        val yOffset = with(density) { cardHeightPx.toDp().roundToPx() }
                        IntOffset(0, yOffset)
                    }
                    .onGloballyPositioned { coordinates ->
                        animatedHeightPx = coordinates.size.height
                    }

            ) {
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(IntrinsicSize.Max)
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onPrimaryContainer, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.watering_can_icon),
                            contentDescription = "Mark as watered",
                            //tint = MaterialTheme.colorScheme.onPrimary
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                        )
                    }

                    Spacer(Modifier.width(4.dp))

                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onPrimaryContainer, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Spacer(Modifier.width(4.dp))

                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onPrimaryContainer, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .onGloballyPositioned { coordinates ->
                        cardHeightPx = coordinates.size.height
                    }
            ){
                Column{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = "Plant icon",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text(
                            text = plant.name,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelMedium
                        )

                        Spacer(
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = "2d",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )
                    }

                    LinearProgressIndicator(
                        progress = { 0.5f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 4.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        trackColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

// @Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PlantCardPreview() {
    PlantyTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                PlantCard(
                    plant = Plant("Bonsai", 5)
                )
                PlantCard(
                    plant = Plant("Leafy", 2)
                )
            }
        }
    }
}