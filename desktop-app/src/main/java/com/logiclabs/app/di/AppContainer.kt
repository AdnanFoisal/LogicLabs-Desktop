package com.logiclabs.app.di

import com.logiclabs.core.data.persistence.DesktopAppPaths
import com.logiclabs.core.data.persistence.ProjectsRepository
import com.logiclabs.core.data.progress.ProgressRepository
import com.logiclabs.core.data.session.SessionRepository
import com.logiclabs.core.data.settings.SettingsRepository
import java.io.File

/**
 * Desktop dependency graph. Every repository persists into [dataDir] (`%APPDATA%\LogicLabs`
 * on Windows, or `<appDir>\data` when `portable.txt` is present beside `LogicLabs.exe`).
 */
class AppContainer(
    val dataDir: File = DesktopAppPaths.resolveDataDir()
) {
    val isPortableMode: Boolean = DesktopAppPaths.isPortableMode()
    val settings: SettingsRepository = SettingsRepository(dataDir)
    val session: SessionRepository = SessionRepository(dataDir)
    val progress: ProgressRepository = ProgressRepository(dataDir)
    val projects: ProjectsRepository = ProjectsRepository(dataDir)
}
