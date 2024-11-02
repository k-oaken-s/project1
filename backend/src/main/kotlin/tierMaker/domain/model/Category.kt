package tierMaker.domain.model

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "category")
data class Category(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val name: String = "",

    val description: String? = null,

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    val image: ByteArray? = null, // 画像データ用のフィールド

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL])
    val items: List<Item> = emptyList()
)
