package com.logiclabs.core.data.session

import com.logiclabs.core.data.persistence.DesktopAppPaths
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

/** What kind of thing was on the bench when the user last left it. */
enum class SessionKind { LAB, PROJECT, SANDBOX }

/**
 * The pointer behind the Home screen's CONTINUE card.
 */
data class Session(
    val kind: SessionKind,
    val id: String?,
    val savedAtMillis: Long
)

@Serializable
private data class SessionDto(
    val kind: String,
    val id: String? = null,
    val savedAtMillis: Long = 0L
)

/**
 * Records what the user was last doing into `<dataDir>/session.json`.
 */
class SessionRepository(
    dataDir: File = DesktopAppPaths.resolveDataDir()
) {

    private val file = File(dataDir, "session.json")
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val mutex = Mutex()
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    private val _lastSession = MutableStateFlow(loadSync())
    val lastSession: Flow<Session?> = _lastSession.asStateFlow()

    suspend fun current(): Session? = _lastSession.value

    fun recordLab(labId: String) = record(SessionKind.LAB, labId)

    fun recordProject(projectId: String) = record(SessionKind.PROJECT, projectId)

    fun recordSandbox() = record(SessionKind.SANDBOX, null)

    private fun record(kind: SessionKind, id: String?) {
        val next = Session(kind = kind, id = id, savedAtMillis = System.currentTimeMillis())
        _lastSession.value = next
        scope.launch {
            mutex.withLock {
                runCatching {
                    val dto = SessionDto(kind = next.kind.name, id = next.id, savedAtMillis = next.savedAtMillis)
                    DesktopAppPaths.atomicWriteText(file, json.encodeToString(SessionDto.serializer(), dto))
                }
            }
        }
    }

    private fun loadSync(): Session? {
        if (!file.isFile) return null
        return runCatching {
            val dto = json.decodeFromString<SessionDto>(file.readText(Charsets.UTF_8))
            val kind = SessionKind.valueOf(dto.kind)
            Session(kind = kind, id = dto.id, savedAtMillis = dto.savedAtMillis)
        }.getOrNull()
    }
}
