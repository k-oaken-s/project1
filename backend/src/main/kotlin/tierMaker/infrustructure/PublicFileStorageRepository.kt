package tierMaker.infrustructure

import java.nio.file.Files
import java.nio.file.Paths
import java.time.Instant
import org.springframework.stereotype.Service

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
    // ディレクトリとファイル名の生成
    val directoryPath = Paths.get(baseDir, fileType)
    val fileName = "$identifier-${Instant.now().epochSecond}.$extension"
    val filePath = directoryPath.resolve(fileName)

    // ディレクトリが存在しない場合は作成
    Files.createDirectories(directoryPath)

    // ファイルの保存
    Files.write(filePath, fileData)

    // ファイルURLの生成
    return "/$fileType/$fileName"
  }
}
