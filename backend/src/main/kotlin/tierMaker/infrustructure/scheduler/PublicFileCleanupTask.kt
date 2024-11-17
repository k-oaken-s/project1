import java.io.File
import java.time.Instant
import java.time.temporal.ChronoUnit
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * `PublicFileCleanupTask` は、指定されたディレクトリ内の不要な画像ファイルを定期的に削除するためのクラスです。
 * 画像の最大保持時間や最終アクセス時間に基づいて、削除対象を決定します。
 */
@Component
class PublicFileCleanupTask {

  /** 画像が保存されるディレクトリのパス */
  private val imageDirPath = "public/images"

  /** ファイルの最大保持時間（単位: 時間） */
  private val maxAgeInHours: Long = 72 // 最大保持時間を72時間と仮定

  /** ファイルの非アクティブ期間（単位: 時間） */
  private val maxInactiveHours: Long = 48 // 最後のアクセスから48時間以上経過したものを削除対象に

  /**
   * 画像ファイルを定期的に削除するスケジュールタスク。
   *
   * <p>
   * 毎時実行され、指定したディレクトリ内のファイルをチェックして削除します。 削除基準：
   * <ul>
   * <li>作成からの時間が {@code maxAgeInHours} を超えている。</li>
   * <li>最終更新（アクセス）からの時間が {@code maxInactiveHours} を超えている。</li>
   * </ul>
   */
  @Scheduled(cron = "0 0 * * * ?") // 毎時実行
  fun cleanUpUnusedImages() {
    val imageDir = File(imageDirPath)

    // ディレクトリが存在しない、または空の場合は終了
    val files = imageDir.listFiles() ?: return

    val now = Instant.now()

    files.forEach { file ->
      val lastModifiedTime = Instant.ofEpochMilli(file.lastModified())
      val hoursSinceLastModified = ChronoUnit.HOURS.between(lastModifiedTime, now)

      // 削除基準に基づいてファイルを削除
      if (hoursSinceLastModified > maxAgeInHours || hoursSinceLastModified > maxInactiveHours) {
        file.delete()
        println("削除されたファイル: ${file.name}")
      }
    }
  }
}
