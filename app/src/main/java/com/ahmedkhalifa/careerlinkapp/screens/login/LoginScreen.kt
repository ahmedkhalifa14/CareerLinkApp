package com.ahmedkhalifa.careerlinkapp.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.MaterialTheme
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.CustomBtn
import com.ahmedkhalifa.careerlinkapp.composable.CustomImageButton
import com.ahmedkhalifa.careerlinkapp.composable.CustomTextField
import com.ahmedkhalifa.careerlinkapp.ui.theme.FacebookIconColor
import com.ahmedkhalifa.careerlinkapp.ui.theme.GoogleIconColor

@Composable
fun LoginScreen(
    navController: NavController
) {
    LoginScreenContent(
        onForgetClick = { },
        onClickGoogleAuth = {},
        onClickFacebookAuth = {},
        onClickLogin = {}
    )

}

@Composable
fun LoginScreenContent(
    onForgetClick: () -> Unit, onClickGoogleAuth: () -> Unit,
    onClickFacebookAuth: () -> Unit, onClickLogin: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)

    ) {

        Text(
            text = stringResource(R.string.login_welcome_back),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(R.string.login_subtitle),
            style = MaterialTheme.typography.body1,
            fontSize = 20.sp,
            color = Color.Gray,
            lineHeight = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        CustomTextField(value = "", onValueChange = {}, hint =  stringResource(R.string.email_address), icon = Icons.Default.Email)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(value = "", onValueChange = {}, hint =  stringResource(R.string.password), icon = Icons.Default.Lock)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.forget_password),
            style = MaterialTheme.typography.body1,
            fontSize = 18.sp,
            color = Color.Gray,
            lineHeight = 18.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomBtn(text = stringResource(R.string.log_in_c), icon = null) {
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.or_continue_with),
            style = MaterialTheme.typography.body1,
            fontSize = 18.sp,
            color = Color.Gray,
            lineHeight = 18.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            CustomImageButton(
                image = painterResource(id = R.drawable.google),
                backgroundColor = GoogleIconColor,
                onClick = { /* Handle click */ }
            )
            Spacer(modifier = Modifier.width(16.dp))

            CustomImageButton(

                image = painterResource(id = R.drawable.facebook),
                backgroundColor = FacebookIconColor,
                onClick = { /* Handle click */ }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = stringResource(R.string.new_user),
                style = MaterialTheme.typography.body1,
                fontSize = 18.sp,
                color = Color.Gray,
                lineHeight = 18.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)

            )
            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.body1,
                fontSize = 18.sp,
                color = Color.Black,
                lineHeight = 18.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
        }

    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewLoginScreen() {
LoginScreen(navController = rememberNavController())
}


