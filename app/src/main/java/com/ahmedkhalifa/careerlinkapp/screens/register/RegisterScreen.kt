package com.ahmedkhalifa.careerlinkapp.screens.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.CustomBtn
import com.ahmedkhalifa.careerlinkapp.composable.CustomImageButton
import com.ahmedkhalifa.careerlinkapp.composable.CustomTextField
import com.ahmedkhalifa.careerlinkapp.composable.PasswordTextField
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.graphs.AuthScreen
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.ui.theme.FacebookIconColor
import com.ahmedkhalifa.careerlinkapp.ui.theme.GoogleIconColor
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal
import com.ahmedkhalifa.careerlinkapp.utils.Event
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.ahmedkhalifa.careerlinkapp.viewmodel.AuthViewModel


@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.registerState.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(state.value) {

        state.value.getContentIfNotHandled()?.let { resource ->
            when (resource) {
                is Resource.Success -> {
                    navController.navigate(AuthScreen.Login.route)
                    Toast.makeText(
                        context,
                        context.getString(R.string.successful_registration_please_verify_your_email_address),
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is Resource.Error -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    RegisterScreenContent(
        onRegisterClick = { email, password ->
            viewModel.register(email, password)
        },
        registerState = state.value,
        onClickLogin = {
            navController.navigate(AuthScreen.UserForm.route)
        }
    )
}

@Composable
fun RegisterScreenContent(
    onRegisterClick: (String, String) -> Unit,
    registerState: Event<Resource<Unit>>,
    onClickLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current


    val mainTextColor = getColor(AppColors.AppColorSet.AppMainTextColor)
    val secondTextColor = getColor(AppColors.AppColorSet.AppSecondTextColor)
    Column(
        Modifier
            .fillMaxSize()
            .background(getColor(AppColors.AppColorSet.AppScreenBackgroundColor))
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.register_account),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = Tajawal,
            color = mainTextColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(R.string.login_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 20.sp,
            color = secondTextColor,
            fontFamily = Tajawal,
            lineHeight = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(R.string.email_address),
            placeholder = stringResource(R.string.enter_your_email_address),
            icon = Icons.Default.Email,
            focusRequester = emailFocusRequester,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    passwordFocusRequester.requestFocus()
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            value = password,
            onValueChange = { password = it },
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.enter_your_password),
            icon = Icons.Default.Lock,
            isError = password.isNotEmpty() && password.length < 8,
            supportingText = {
                if (password.isNotEmpty() && password.length < 8) {
                    Text(
                        text = stringResource(R.string.password_must_be_at_least_8_characters_long),
                        color = Color.Red
                    )
                }
            },
            focusRequester = passwordFocusRequester,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        CustomBtn(text = stringResource(R.string.sign_up_c), icon = null) {
            onRegisterClick(email, password)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.or_continue_with),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 18.sp,
            color = secondTextColor,
            lineHeight = 18.sp,
            fontFamily = Tajawal,
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
                onClick = {

                }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = stringResource(R.string.already_have_account),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp,
                color = secondTextColor,
                lineHeight = 18.sp,
                fontFamily = Tajawal,
                modifier = Modifier

                    .padding(bottom = 16.dp)

            )
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp,
                color = mainTextColor,
                fontFamily = Tajawal,
                lineHeight = 18.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clickable {
                        onClickLogin()
                    }
            )
        }


        when (val resource = registerState.peekContent()) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is Resource.Error -> {
                Text(
                    text = resource.message ?: stringResource(R.string.an_error_occurred),
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            else -> {}
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewRegisterScreen() {
    RegisterScreenContent(
        onRegisterClick = { _, _ -> },
        registerState = Event(Resource.Init()),
        onClickLogin = {}
    )
}