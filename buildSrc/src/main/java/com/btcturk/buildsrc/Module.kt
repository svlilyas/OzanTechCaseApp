import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Features Management Class
 * Reaching all feature dependencies from this class
 */
object Module {
    private const val PATH = "path"

    // Module Paths
    private const val APP = ":app"
    private const val CORE_DATA = ":core:data"
    private const val CORE_MODEL = ":core:model"
    private const val CORE_UICOMPONENTS = ":core:uicomponents"
    private const val CORE_NETWORK = ":core:network"
    private const val CORE_DATABASE = ":core:database"

    object Project {
        fun DependencyHandler.app(): Dependency = project(mapOf(PATH to APP))
        fun DependencyHandler.core_data(): Dependency = project(mapOf(PATH to CORE_DATA))
        fun DependencyHandler.core_model(): Dependency = project(mapOf(PATH to CORE_MODEL))
        fun DependencyHandler.core_uicomponents(): Dependency =
            project(mapOf(PATH to CORE_UICOMPONENTS))

        fun DependencyHandler.core_network(): Dependency = project(mapOf(PATH to CORE_NETWORK))
        fun DependencyHandler.core_database(): Dependency = project(mapOf(PATH to CORE_DATABASE))
    }
}
