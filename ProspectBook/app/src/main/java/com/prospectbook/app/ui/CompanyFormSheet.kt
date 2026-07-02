package com.prospectbook.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prospectbook.app.data.Company
import com.prospectbook.app.data.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyFormSheet(initial: Company?, onDismiss: () -> Unit, onSave: (Company) -> Unit) {
    var company by remember { mutableStateOf(initial?.company ?: "") }
    var problem by remember { mutableStateOf(initial?.problem ?: "") }
    var contactName by remember { mutableStateOf(initial?.contactName ?: "") }
    var phone by remember { mutableStateOf(initial?.phone ?: "") }
    var email by remember { mutableStateOf(initial?.email ?: "") }
    var status by remember { mutableStateOf(initial?.status ?: Status.WATCHING) }
    var notes by remember { mutableStateOf(initial?.notes ?: "") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                if (initial == null) "নতুন কোম্পানি যোগ করুন" else "এন্ট্রি এডিট করুন",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(14.dp))

            OutlinedTextField(company, { company = it }, label = { Text("কোম্পানির নাম *") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(problem, { problem = it }, label = { Text("সমস্যা / সুযোগ") }, modifier = Modifier.fillMaxWidth(), minLines = 2)
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(contactName, { contactName = it }, label = { Text("যোগাযোগ ব্যক্তি") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(phone, { phone = it }, label = { Text("ফোন") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(email, { email = it }, label = { Text("ইমেইল") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(14.dp))

            Text("স্ট্যাটাস", style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Status.entries.forEach { s ->
                    FilterChip(
                        selected = status == s,
                        onClick = { status = s },
                        label = { Text(s.label) }
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            OutlinedTextField(notes, { notes = it }, label = { Text("অতিরিক্ত নোট") }, modifier = Modifier.fillMaxWidth(), minLines = 2)
            Spacer(Modifier.height(18.dp))

            Button(
                onClick = {
                    if (company.isNotBlank()) {
                        onSave(
                            Company(
                                id = initial?.id ?: 0,
                                company = company,
                                problem = problem,
                                contactName = contactName,
                                phone = phone,
                                email = email,
                                status = status,
                                notes = notes,
                                createdAt = initial?.createdAt ?: System.currentTimeMillis()
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(if (initial == null) "যোগ করুন" else "পরিবর্তন সেভ করুন")
            }
        }
    }
}
