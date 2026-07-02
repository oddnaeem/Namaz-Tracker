package com.prospectbook.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prospectbook.app.data.AppDatabase
import com.prospectbook.app.data.Company
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CompanyViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).companyDao()

    private val _companies = MutableStateFlow<List<Company>>(emptyList())
    val companies: StateFlow<List<Company>> = _companies

    init {
        viewModelScope.launch {
            dao.getAll().collectLatest { _companies.value = it }
        }
    }

    fun save(company: Company) = viewModelScope.launch {
        if (company.id == 0L) {
            dao.insert(company)
        } else {
            dao.update(company.copy(updatedAt = System.currentTimeMillis()))
        }
    }

    fun delete(company: Company) = viewModelScope.launch {
        dao.delete(company)
    }
}
