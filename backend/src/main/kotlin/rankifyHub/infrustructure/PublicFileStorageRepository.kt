package rankifyHub.infrustructure

import java.nio.file.Files
import java.nio.file.Paths
import org.springframework.stereotype.Service

/**
 * 公開用ファイルの生成を行うリポジトリ
 *
 * アプリケーション内で扱うファイル（例: 画像やドキュメント）を保存し、 公開URLを返す機能を提供します。
 */
@Service
class PublicFileStorageRepository {

  private val baseDir = "public"

  /**
   * ファイルを指定のディレクトリに保存し、公開URLを返す
   *
   * @param fileType ファイルの種類（例: "images", "documents"など）
   * @param identifier ファイルに関連付けるID（例: カテゴリIDやアイテムID）
   * @param fileData 保存するファイルのデータ
   * @param extension ファイルの拡張子（例: "jpg", "pdf"）
   * @return 保存先の公開URL
   */
  fun saveFile(
    fileType: String,
    identifier: String,
    fileData: ByteArray,
    extension: String
  ): String {
    val directoryPath = Paths.get(baseDir, fileType)
    val fileName = "$identifier.$extension"
    val filePath = directoryPath.resolve(fileName)
    Files.createDirectories(directoryPath)
    if (Files.exists(filePath)) {
      Files.delete(filePath)
    }
    Files.write(filePath, fileData)
    return "/$fileType/$fileName"
  }
}
