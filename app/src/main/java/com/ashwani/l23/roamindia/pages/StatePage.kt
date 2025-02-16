package com.ashwani.l23.roamindia.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashwani.l23.roamindia.AppDimensions
import com.ashwani.l23.roamindia.AppStyles
import com.ashwani.l23.roamindia.R
import com.ashwani.l23.roamindia.ui.theme.RoamIndiaTheme
import com.ashwani.l23.roamindia.ui.theme.parrot

@Composable
fun StateScreen(modifier: Modifier = Modifier, onCheckButtonClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF303030))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StateImage()
        StateName()
        Spacer(modifier = Modifier.height(8.dp))
        StateOverview()
        StateRecommendations(onCheckButtonClicked = onCheckButtonClicked)

    }
}

@Composable
private fun StateRecommendations(onCheckButtonClicked: () -> Unit) {
    Column {
        Text(
            text = "Top Recommendations",
            style = TextStyle(
                fontSize = 18.25.sp,
                fontWeight = FontWeight(600),
                color = parrot,
            ),
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .width(217.dp)
                .height(27.dp)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .width(AppDimensions.stateTileWidth)
                .height(AppDimensions.stateTileHeight)
                .background(
                    color = Color(0xFFFFFDFD),
                    shape = RoundedCornerShape(AppDimensions.cornerRadius)
                )
        ) {
            PlaceTileContent(onCheckButtonClicked = onCheckButtonClicked)
        }
    }
}
@Composable
private fun PlaceTileContent(onCheckButtonClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(13.18.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlaceImage()
        PlaceInfo()
        CheckButton(onCheckButtonClicked = onCheckButtonClicked)
    }
}

@Composable
private fun PlaceImage() {
    Image(
        painter = painterResource(R.drawable.cafe),
        contentDescription = "image of andhra pradesh",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(13.18.dp))
            .width(75.0175.dp)
            .height(55.75625.dp)
    )
}

@Composable
private fun PlaceInfo() {
    Column(modifier = Modifier.padding(8.dp)) {
        androidx.compose.material3.Text(
            text = "Andhra Pradesh",
            style = AppStyles.stateNameStyle,
            modifier = Modifier
                .width(109.dp)
                .height(20.dp)
        )
        LocationInfo()
    }
}
@Composable
private fun LocationInfo() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            modifier = Modifier
                .width(9.70415.dp)
                .height(12.35072.dp),
            tint = parrot
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "India",
            style = TextStyle(
                fontWeight = FontWeight(600),
                color = Color(0xFF89807A)
            ),
            modifier = Modifier
                .width(109.dp)
                .height(20.dp)
        )
    }
}
@Composable
private fun CheckButton(onCheckButtonClicked: () -> Unit) {
    Button(
        onClick = {onCheckButtonClicked()},
        colors = ButtonDefaults.buttonColors(parrot)
    ) {
        androidx.compose.material3.Text(text = "Check")
    }
}

@Composable
private fun StateOverview() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Overview",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB5E848),
            ),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            AreaInfo()
            Spacer(modifier = Modifier.width(8.dp))
            RatingInfo()
        }
        Spacer(modifier = Modifier.height(8.dp))
        StateDes()
    }
}

@Composable
private fun StateDes() {
    Text(
        text = "Andhra Pradesh offers rich culture, historic temples, scenic beaches, and great hospitality. Visitors enjoy delicious cuisine, excellent services, and comfortable stays. Many hotels provide flexible refund policies, ensuring a hassle-free experience. Explore Andhra’s beauty and warm local traditions.",
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xCCFFFFFF),
        ),
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
    )
}

@Composable
private fun RatingInfo() {
    InfoBox(iconRes = R.drawable.star, label = "RATING", value = "4.8 out of 5")
}

@Composable
private fun AreaInfo() {
    InfoBox(iconRes = R.drawable.area_chart, label = "Area", value = "162.97 Km²")
}

@Composable
private fun InfoBox(iconRes: Int, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color = Color(0xFFF0F0F0), shape = RoundedCornerShape(5.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
            )
            Text(
                text = value,
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0x99FEFEFE))
            )
        }
    }
}

@Composable
private fun StateName() {
    Text(
        text = "Andhra Pradesh",
        style = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    )
}

@Composable
private fun StateImage() {
    Image(
        painter = painterResource(R.drawable.andhra),
        contentDescription = null,
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .fillMaxWidth()
            .height(364.95001.dp)
    )
}
@Preview
@Composable
private fun StateScreenPreview() {
    RoamIndiaTheme {
        StateScreen(onCheckButtonClicked = {})
    }
}
