package com.ahmedkhalifa.careerlinkapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsAndToolsCard(
    skills: List<String>,
    onSkillClick: (String) -> Unit,
) {
    val cardBackgroundColor = getColor(AppColors.AppColorSet.AppCardBackgroundColor)
    val backgroundColor = getColor(AppColors.AppColorSet.AppScreenBackgroundColor)
    val textColor = getColor(AppColors.AppColorSet.AppMainTextColor)

    Card {
        Column(modifier = Modifier.background(backgroundColor)) {
            Text(
                text = "Skills and Tools:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 20.sp,
                color = textColor
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                skills.forEach { skill ->
                    Card(
                        modifier = Modifier
                            .background(cardBackgroundColor)
                            .clickable { onSkillClick(skill) },
                        shape = RoundedCornerShape(16.dp),

                        ) {
                        Text(
                            text = skill,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            fontWeight = FontWeight.SemiBold,
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}