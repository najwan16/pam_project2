package com.example.pam_project2.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import kotlin.time.Duration.Companion.seconds
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookScreen() {
    var nama by remember { mutableStateOf("") }
    var ticketCount by remember { androidx.compose.runtime.mutableIntStateOf(0) }
    var textInput by remember { mutableStateOf("0") }
    var isLoading by remember { mutableStateOf(false) }
    var statusState by remember { androidx.compose.runtime.mutableIntStateOf(0) }

    LaunchedEffect(ticketCount) {
        if (textInput.toIntOrNull() != ticketCount) {
            textInput = ticketCount.toString()
        }
        if (!isLoading) {
            statusState = 0
        }
    }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            statusState = 1
            kotlinx.coroutines.delay(2.seconds)
            isLoading = false
            statusState = 2
        }
    }

    // Background
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E88E5)),
    ) {
        // Header
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Pemesanan Tiket",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp),
        )

        // Container
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            color = Color(0xFFF4F6F9),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Card 1: Nama
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Nama",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedTextField(
                            value = nama,
                            onValueChange = {
                                nama = it
                                Log.d("TEST", "Text berubah : $it")
                            },
                            label = { Text("Masukkan Nama Anda") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }

                // Card 2: Jumlah Tiket
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Jumlah Tiket",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(Color(0xFFE9F0F7), shape = RoundedCornerShape(12.dp))
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { if (ticketCount > 0) ticketCount-- },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Decrease",
                                    tint = Color.DarkGray
                                )
                            }

                            OutlinedTextField(
                                value = textInput,
                                onValueChange = { newValue ->
                                    textInput = newValue
                                    val parsed = newValue.toIntOrNull()
                                    if (parsed != null) {
                                        ticketCount = parsed.coerceAtLeast(0)
                                    } else if (newValue.isEmpty()) {
                                        ticketCount = 0
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 16.dp)
                                    .height(56.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF1F3F5),
                                    unfocusedContainerColor = Color(0xFFF1F3F5),
                                    disabledContainerColor = Color(0xFFF1F3F5),
                                    focusedBorderColor = Color(0xFF1E88E5),
                                    unfocusedBorderColor = Color.Transparent
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                textStyle = LocalTextStyle.current.copy(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray,
                                    textAlign = TextAlign.Center
                                )
                            )

                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(Color(0xFFE9F0F7), shape = RoundedCornerShape(12.dp))
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { ticketCount++ },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Increase",
                                    tint = Color.DarkGray
                                )
                            }
                        }
                    }
                }

                // Card 3: Tombol
                Button(
                    onClick = {
                        if (!isLoading) {
                            if (nama.trim().isEmpty()) {
                                statusState = 3
                            } else if (ticketCount > 0) {
                                isLoading = true
                            }
                        }
                    },
                    enabled = !isLoading && ticketCount > 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E88E5),
                        disabledContainerColor = Color.LightGray
                    )
                ) {
                    Text(
                        text = "PESAN TIKET",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Card 4: Status
                val backgroundColor = when (statusState) {
                    1 -> Color(0xFFE9F0F7)
                    2 -> Color(0xFFE8F5E9)
                    3 -> Color(0xFFFFEBEE)
                    else -> Color(0xFFF1F3F5)
                }

                val textColor = when (statusState) {
                    1 -> Color(0xFF1E88E5)
                    2 -> Color(0xFF2E7D32)
                    3 -> Color(0xFFC62828)
                    else -> Color(0xFF7F8C8D)
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = backgroundColor
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        when (statusState) {
                            1 -> {
                                CircularProgressIndicator(
                                    color = Color(0xFF1E88E5),
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                            2 -> {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Success",
                                    tint = Color(0xFF4CAF50),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            3 -> {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = "Error",
                                    tint = Color(0xFFE53935),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Status: ",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2C3E50),
                                fontSize = 14.sp
                            )
                            Text(
                                text = when (statusState) {
                                    1 -> "Memproses pesanan..."
                                    2 -> "Tiket berhasil dipesan!"
                                    3 -> "Nama harus diisi"
                                    else -> if (ticketCount > 0) "Siap untuk dipesan" else "Silakan pesan tiket"
                                },
                                color = textColor,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookScreen()
}
