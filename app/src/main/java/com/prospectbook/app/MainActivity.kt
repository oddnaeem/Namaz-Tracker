package com.prospectbook.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prospectbook.app.ui.CompanyViewModel
import com.prospectbook.app.ui.ProspectBookScreen
import com.prospectbook.app.ui.theme.ProspectBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProspectBookTheme {
                val vm: CompanyViewModel = viewModel()
                ProspectBookScreen(vm)
            }
        }
    }
}
