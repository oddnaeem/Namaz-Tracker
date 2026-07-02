package com.prospectbook.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prospectbook.app.data.Company
import com.prospectbook.app.data.Status
import com.prospectbook.app.ui.theme.FaintText
import com.prospectbook.app.ui.theme.MutedText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProspectBookScreen(vm: CompanyViewModel) {
    val companies by vm.companies.collectAsState()
    var query by remember { mutableStateOf("") }
    var statusFilter by remember { mutableStateOf<Status?>(null) }
    var showForm by remember { mutableStateOf(false) }
    var editingCompany by remember { mutableStateOf<Company?>(null) }

    val filtered = companies.filter { c ->
        (query.isBlank() || c.company.contains(query, true) || c.problem.contains(query, true)) &&
            (statusFilter == null || c.status == statusFilter)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { editingCompany = null; showForm = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "যোগ করুন", tint = Color.White)
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            Column(Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("প্রসপেক্ট খাতা", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Text("${companies.size}টি কোম্পানি", fontSize = 13.sp, color = FaintText)
                }
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("কোম্পানি বা সমস্যা খুঁজুন") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(Modifier.height(10.dp))
                Row(Modifier.horizontalScroll(rememberScrollState())) {
                    FilterChipItem("সব", statusFilter == null) { statusFilter = null }
                    Spacer(Modifier.width(8.dp))
                    Status.entries.forEach { s ->
                        FilterChipItem(s.label, statusFilter == s) { statusFilter = s }
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }

            if (filtered.isEmpty()) {
                Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                    Text(
                        if (companies.isEmpty()) "এখনো কোনো এন্ট্রি নেই — + বাটনে চাপ দিন" else "কিছু পাওয়া যায়নি",
                        color = FaintText
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filtered, key = { it.id }) { c ->
                        CompanyCard(
                            company = c,
                            onEdit = { editingCompany = c; showForm = true },
                            onDelete = { vm.delete(c) }
                        )
                    }
                    item { Spacer(Modifier.height(80.dp)) }
                }
            }
        }
    }

    if (showForm) {
        CompanyFormSheet(
            initial = editingCompany,
            onDismiss = { showForm = false },
            onSave = { c -> vm.save(c); showForm = false }
        )
    }
}

@Composable
fun FilterChipItem(label: String, selected: Boolean, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = { Text(label, fontSize = 12.5.sp) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (selected) MaterialTheme.colorScheme.secondaryContainer else Color.White,
            labelColor = if (selected) MaterialTheme.colorScheme.primary else Color(0xFF7A7168)
        )
    )
}

@Composable
fun CompanyCard(company: Company, onEdit: () -> Unit, onDelete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(company.company, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    if (company.problem.isNotBlank()) {
                        Text(company.problem, fontSize = 13.sp, color = MutedText, maxLines = 1)
                    }
                }
                Surface(shape = RoundedCornerShape(50), color = MaterialTheme.colorScheme.secondaryContainer) {
                    Text(
                        company.status.label,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
            if (expanded) {
                Spacer(Modifier.height(8.dp))
                if (company.contactName.isNotBlank()) DetailLine("যোগাযোগ ব্যক্তি", company.contactName)
                if (company.phone.isNotBlank()) DetailLine("ফোন", company.phone)
                if (company.email.isNotBlank()) DetailLine("ইমেইল", company.email)
                if (company.notes.isNotBlank()) DetailLine("নোট", company.notes)
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onEdit, modifier = Modifier.weight(1f)) { Text("এডিট করুন") }
                    IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, contentDescription = "মুছুন") }
                }
            }
        }
    }
}

@Composable
fun DetailLine(label: String, value: String) {
    Column(Modifier.padding(top = 6.dp)) {
        Text(label, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold, color = FaintText)
        Text(value, fontSize = 13.5.sp, color = Color(0xFF3A342E))
    }
}
