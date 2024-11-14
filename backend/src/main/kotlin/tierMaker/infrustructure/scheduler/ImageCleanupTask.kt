import java.io.File
import java.time.Instant
import java.time.temporal.ChronoUnit
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ImageCleanupTask {

    private val imageDirPath = "public/images"
    private val maxAgeInHours: Long = 72 // 最大保持時間を72時間と仮定
    private val maxInactiveHours: Long = 48 // 最後のアクセスから48時間以上経過したものを削除対象に

    @Scheduled(cron = "0 0 * * * ?") // 毎時実行
    fun cleanUpUnusedImages() {
        val imageDir = File(imageDirPath)
        val files = imageDir.listFiles() ?: return

        val now = Instant.now()

        files.forEach { file ->
            val lastModifiedTime = Instant.ofEpochMilli(file.lastModified())
            val hoursSinceLastModified = ChronoUnit.HOURS.between(lastModifiedTime, now)

            // 作成から最大保持時間を超えたか、最終更新からの非アクティブ期間が基準を超えた場合に削除
            if (hoursSinceLastModified > maxAgeInHours || hoursSinceLastModified > maxInactiveHours
            ) {
                file.delete()
                println("削除されたファイル: ${file.name}")
            }
        }
    }
}
