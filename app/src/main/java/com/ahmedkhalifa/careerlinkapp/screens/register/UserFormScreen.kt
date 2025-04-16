package com.ahmedkhalifa.careerlinkapp.screens.register

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.CropImageDialog
import com.ahmedkhalifa.careerlinkapp.composable.CustomBtn
import com.ahmedkhalifa.careerlinkapp.composable.CustomTextField
import com.ahmedkhalifa.careerlinkapp.composable.ProfileAvatarView
import com.ahmedkhalifa.careerlinkapp.composable.TextButton
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.graphs.AuthScreen
import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.utils.EventObserver
import com.ahmedkhalifa.careerlinkapp.utils.Resource
import com.ahmedkhalifa.careerlinkapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.util.UUID

@Composable
fun UserFormScreen(
    navHostController: NavHostController,
    backStackEntry: NavBackStackEntry,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val registerState = authViewModel.registerState.collectAsState()
    val saveUserState = authViewModel.saveUserDataState.collectAsState()
    val context = LocalContext.current

    var isLoading by remember { mutableStateOf(false) }

    val userEmail = backStackEntry.arguments?.getString("userEmail")
    val userPassword = backStackEntry.arguments?.getString("userPassword")

    var userFormData by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(registerState.value) {
        registerState.value.getContentIfNotHandled()?.let { resource ->
            when (resource) {
                is Resource.Loading -> {
                    isLoading = true
                }
                is Resource.Success -> {
                    isLoading =false
                    Toast.makeText(context, "Register success", Toast.LENGTH_SHORT).show()
                    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                    userFormData?.let { user ->
                        val updatedUser = user.copy(userId = uid)
                        authViewModel.saveUserData(updatedUser)
                    }
                }
                is Resource.Error -> {
                    isLoading = false
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    val saveUserProfileEventObserver = EventObserver<Unit>(
        onLoading = {
            isLoading = true
        },
        onSuccess = {
            isLoading = false
            Toast.makeText(context, "User data saved successfully", Toast.LENGTH_SHORT).show()
            navHostController.navigate(AuthScreen.Login.route) {
                popUpTo(AuthScreen.UserForm.route) { inclusive = true }
            }
        },
        onError = { errorMessage ->
            isLoading = false
            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        }
    )

    LaunchedEffect(saveUserState.value) {
        saveUserProfileEventObserver.emit(saveUserState.value)
    }

    UserFormScreenContent(
        isLoading = isLoading,
        onSaveClick = { user ->
            userFormData = user
            authViewModel.register(email = user.email, password = userPassword ?: "")
        },
        userEmail = userEmail ?: "",
    )
}


@Composable
fun UserFormScreenContent(
    isLoading: Boolean,
    onSaveClick: (User) -> Unit,
    userEmail: String = "",
)  {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var profilePictureLink by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showCropper by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val localContext = LocalContext.current

    val firstNameFocusRequester = remember { FocusRequester() }
    val lastnameFocusRequester = remember { FocusRequester() }
    val phoneNumberFocusRequester = remember { FocusRequester() }
    val locationFocusRequester = remember { FocusRequester() }


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            showCropper = true // Show cropping UI
        } ?: run {
            Toast.makeText(localContext, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    // Request permission to access the gallery
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(localContext, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Cropping UI
    if (showCropper && selectedImageUri != null) {
        CropImageDialog(
            imageUri = selectedImageUri!!,
            onCrop = { croppedBitmap ->
                // Save cropped bitmap to file and update profilePictureLink
                val croppedFile = File(localContext.cacheDir, "cropped_image_${System.currentTimeMillis()}.jpg")
                croppedFile.outputStream().use { out ->
                    croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                }
                profilePictureLink = Uri.fromFile(croppedFile).toString()
                showCropper = false
                selectedImageUri = null
                Toast.makeText(localContext, "Image cropped successfully", Toast.LENGTH_SHORT).show()
            },
            onCancel = {
                showCropper = false
                selectedImageUri = null
                Toast.makeText(localContext, "Cropping cancelled", Toast.LENGTH_SHORT).show()
            }
        )
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        })

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(text = stringResource(R.string.upload_image)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        // First Name Text Field
        CustomTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = "First Name",
            placeholder = "Enter your first name",
            icon = Icons.Default.Person,
            focusRequester = firstNameFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    lastnameFocusRequester.requestFocus()
                }
            )
        )

        // Last Name Text Field
        CustomTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = "Last Name",
            placeholder = "Enter your last name",
            icon = Icons.Default.Person,
            focusRequester = lastnameFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    phoneNumberFocusRequester.requestFocus()
                }
            )
        )

        // Email Text Field
        CustomTextField(
            value = userEmail,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "Enter your email",
            icon = Icons.Default.Email,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                }
            )
        )

        // Phone Number Text Field
        CustomTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = "Phone Number",
            placeholder = "Enter your phone number",
            icon = Icons.Default.Phone,
            focusRequester = phoneNumberFocusRequester,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    locationFocusRequester.requestFocus()
                }
            )
        )

        CustomTextField(
            value = location,
            onValueChange = { location = it },
            label = "Location",
            placeholder = "Enter your location",
            icon = Icons.Default.LocationOn,
            focusRequester = locationFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        CustomBtn(
            text = stringResource(R.string.save),
            onClick = {
                if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || location.isEmpty()) {
                    Toast.makeText(localContext, "Please fill all fields", Toast.LENGTH_SHORT).show()
                } else {
                    onSaveClick(
                        User(
                            firstName = firstName,
                            lastName = lastName,
                            email = userEmail,
                            phoneNumber = phoneNumber,
                            location = location,
                            profilePictureLink = profilePictureLink,
                            userId = UUID.randomUUID().toString(),
                            joinedAt = System.currentTimeMillis()
                        )
                    )
                }
            }
        )
    }
}



