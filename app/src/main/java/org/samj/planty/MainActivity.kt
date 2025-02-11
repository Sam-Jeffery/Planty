package org.samj.planty

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.samj.planty.ui.theme.PlantyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MessageCard(
                        msg = Message("Sam", "Jetpack compose fucking sucks")
                    )
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message){
    Column {
        Text(
            text = msg.author,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(4.dp))
        Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp){
            Text(
                text = msg.body,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

data class Plant(val name: String, val nextWater: Int)

@Composable
fun PlantCard(plant: Plant){
    Column{
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Plant icon",
                modifier = Modifier.size(40.dp).clip(CircleShape)
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = plant.name,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "2d",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )
        }

        LinearProgressIndicator(
            progress = { 0.5f },
            modifier = Modifier.fillMaxWidth(),
        )
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
            }
        }
    }
}