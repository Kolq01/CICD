@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mvvmlogin.ui.theme.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvvmlogin.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel){
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Login(Modifier.align(Alignment.Center), viewModel)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {

    val email : String by viewModel.email.observeAsState("")
    val password : String by viewModel.password.observeAsState("")
    val loginEnable : Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading : Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if(isLoading){
        Box(modifier = Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{
        Column(modifier = Modifier) {
            Spacer(modifier = Modifier.padding(16.dp))
            HeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            EmailField(email, {viewModel.onLoginChanged(it, password)})
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password, {viewModel.onLoginChanged(email, it)})
            Spacer(modifier = Modifier.padding(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnable) {
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }
        }
    }
}

@Composable
fun LoginButton(loginEnable: Boolean,onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            Color(0xFF03A9F4),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ), enabled = loginEnable
    ) {
        Text(text = "Iniciar Sesión")
    }
}

@Composable
fun ForgotPassword(align: Modifier) {
    Text(text = "olvidaste la contraseña pelo de peruano?",
        modifier = Modifier.clickable {  },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color= Color(0xFFF44336)
    )
}

@Composable
fun PasswordField(password:String, onTextFieldChanged: (String) -> Unit) {
    TextField(value = password, onValueChange = {onTextFieldChanged(it)},
        placeholder = { Text(text = "Password")},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun EmailField(email:String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = email, onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email")},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun HeaderImage(modifier : Modifier) {
    Image(
        painter = painterResource(id = R.drawable.login),
        contentDescription = "Header",
        modifier = modifier
    )
}