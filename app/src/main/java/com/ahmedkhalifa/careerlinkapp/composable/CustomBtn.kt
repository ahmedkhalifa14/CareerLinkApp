package com.ahmedkhalifa.careerlinkapp.composable


import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppMainColor

@Composable
fun CustomBtn(text: String,icon: ImageVector?=null, onClick: () -> Unit) {
    Button(

        onClick = onClick,
        shape= CutCornerShape(10),
        modifier = Modifier.fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(3.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppMainColor,
            contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            if (icon != null) {  // Check if icon is provided
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = "Button Icon",
                    tint = Color.White
                )
            }

        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewAuthButton() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CustomBtn("CLICK",Icons.Default.Home) {}
    }
}