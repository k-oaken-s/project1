package rankifyHub.admin.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

/**
 * 管理者エンティティ
 *
 * 管理者の認証情報を表すデータクラスで、データベース内の `administrator` テーブルにマッピングされています。
 */
@Entity
@Table(name = "administrator")
data class Administrator(
  @Id val id: String = UUID.randomUUID().toString(),
  @Column(nullable = false, unique = true) val username: String,
  @Column(nullable = false) val passwordHash: String
)
