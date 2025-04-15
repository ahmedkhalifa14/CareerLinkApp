package com.ahmedkhalifa.careerlinkapp.screens.register

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.CustomBtn
import com.ahmedkhalifa.careerlinkapp.composable.CustomTextField
import com.ahmedkhalifa.careerlinkapp.composable.ProfileAvatarView
import com.ahmedkhalifa.careerlinkapp.composable.TextButton
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.graphs.AuthScreen
import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.utils.EventObserver
import com.ahmedkhalifa.careerlinkapp.viewmodel.AuthViewModel
import java.util.UUID

@Composable
fun UserFormScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val userState = authViewModel.saveUserDataState.collectAsState()
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    val eventObserver = EventObserver<Unit>(
        onLoading = {
            isLoading = true
        },
        onSuccess = {
            isLoading = false
            Toast.makeText(context, "User data saved successfully", Toast.LENGTH_SHORT).show()
            navHostController.navigate(AuthScreen.Login.route)
        },
        onError = { errorMessage ->
            isLoading = false
            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        }
    )

    LaunchedEffect(userState) {
        eventObserver.emit(userState.value)
    }

    UserFormScreenContent(
        isLoading = isLoading,
        onSaveClick = { user ->
            authViewModel.saveUserData(user)
        }
    )
}

@Composable
fun UserFormScreenContent(
    isLoading: Boolean,
    onSaveClick: (User) -> Unit,
) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var profilePictureLink by rememberSaveable { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }
    val focusRequester5 = remember { FocusRequester() }
    val localContext = LocalContext.current

    // State to handle if the image picker is triggered
    val shouldLaunchImagePicker = remember { mutableStateOf(false) }

    // Gallery launcher for picking image
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Update profile picture link with the URI of the selected image
            profilePictureLink = uri.toString()
            Toast.makeText(localContext, "Image selected successfully", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(localContext, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    // Request permission to access the gallery before launching it
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(localContext, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Handle permission request for READ_EXTERNAL_STORAGE
    LaunchedEffect(shouldLaunchImagePicker.value) {
        if (shouldLaunchImagePicker.value) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                // Android 13 and above, use READ_MEDIA_IMAGES
                requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                // For Android versions below 13, use READ_EXTERNAL_STORAGE
                requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            shouldLaunchImagePicker.value = false // Reset the state after launching the permission request
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(getColor(AppColors.AppColorSet.AppScreenBackgroundColor))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.complete_your_profile),
            style = MaterialTheme.typography.headlineSmall
        )

        // Profile Picture view
        ProfileAvatarView(imageUrl = profilePictureLink, onClick = {
            // Trigger the gallery launcher on click
            shouldLaunchImagePicker.value = true // Set the state to launch the permission request
        })

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(text = stringResource(R.string.upload_image)) {
            // Trigger the gallery launcher on upload image button click
            shouldLaunchImagePicker.value = true // Set the state to launch the permission request
        }

        // First Name Text Field
        CustomTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = "First Name",
            placeholder = "Enter your first name",
            icon = Icons.Default.Person,
            focusRequester = focusRequester1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusRequester2.requestFocus() }
            )
        )

        // Last Name Text Field
        CustomTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = "Last Name",
            placeholder = "Enter your last name",
            icon = Icons.Default.Person,
            focusRequester = focusRequester2,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusRequester3.requestFocus() }
            )
        )

        // Email Text Field
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "Enter your email",
            icon = Icons.Default.Email,
            focusRequester = focusRequester3,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequester4.requestFocus() }
            )
        )

        // Phone Number Text Field
        CustomTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = "Phone Number",
            placeholder = "Enter your phone number",
            icon = Icons.Default.Phone,
            focusRequester = focusRequester4,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequester5.requestFocus() }
            )
        )

        // Location Text Field
        CustomTextField(
            value = location,
            onValueChange = { location = it },
            label = "Location",
            placeholder = "Enter your location",
            icon = Icons.Default.LocationOn,
            focusRequester = focusRequester5,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )

        // Show a loading spinner if `isLoading` is true
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Save Button
        CustomBtn(
            text = stringResource(R.string.save),
            onClick = {
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || location.isEmpty()) {
                    Toast.makeText(
                        localContext,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    onSaveClick(
                        User(
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            phoneNumber = phoneNumber,
                            location = location,
                            profilePictureLink = profilePictureLink,
                            userId = UUID.randomUUID().toString(),
                            joinedAt = System.currentTimeMillis()
                        )
                    )
                }
            },
        )
    }
}

