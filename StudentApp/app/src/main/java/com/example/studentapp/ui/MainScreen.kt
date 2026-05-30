package com.example.studentapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentapp.data.Siswa
import com.example.studentapp.viewmodel.StudentViewModel

@Composable
fun MainScreen(
    viewModel: StudentViewModel
) {

    var nama by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var siswaBeingEdited by remember { mutableStateOf<Siswa?>(null) }

    val siswaList by viewModel.siswaList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Registrasi Siswa",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Kelola data siswa",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        FormInput(
            nama = nama,
            email = email,
            onNamaChange = {
                nama = it
            },
            onEmailChange = {
                email = it
            },
            buttonText = "+ Tambah Siswa",
            onTambahClick = {

                if (nama.isBlank()) {
                    return@FormInput
                }

                if (email.isBlank()) {
                    return@FormInput
                }

                if (!email.contains("@")) {
                    return@FormInput
                }

                viewModel.tambahSiswa(nama, email)

                nama = ""
                email = ""
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Daftar Siswa",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (siswaList.isEmpty()) {

            Text(
                text = "Belum ada data siswa",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        LazyColumn {

            items(siswaList) { siswa ->

                StudentItem(
                    siswa = siswa,
                    onDelete = {
                        viewModel.hapusSiswa(siswa)
                    },
                    onEdit = {
                        siswaBeingEdited = siswa
                    }
                )
            }
        }
    }

    // Popup Edit Data Siswa
    siswaBeingEdited?.let { siswaData ->
        var editNama by remember(siswaData) { mutableStateOf(siswaData.nama) }
        var editEmail by remember(siswaData) { mutableStateOf(siswaData.email) }

        AlertDialog(
            onDismissRequest = { siswaBeingEdited = null },
            title = {
                Text(text = "Edit Data Siswa")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = editNama,
                        onValueChange = { editNama = it },
                        label = { Text("Nama") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = editEmail,
                        onValueChange = { editEmail = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (editNama.isNotBlank() && editEmail.isNotBlank() && editEmail.contains("@")) {
                            viewModel.editSiswa(siswaData.copy(nama = editNama, email = editEmail))
                            siswaBeingEdited = null
                        }
                    }
                ) {
                    Text("Simpan Perubahan")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { siswaBeingEdited = null }
                ) {
                    Text("Batal")
                }
            }
        )
    }
}