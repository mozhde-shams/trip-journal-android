package com.example.tripjournal

import android.content.Context
import androidx.startup.Initializer
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TripsStartupInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context,
            TripsStartupEntryPoint::class.java,
        )

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            entryPoint.populateInitialTripsIfEmptyUseCase().invoke()
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> = emptyList()
}
