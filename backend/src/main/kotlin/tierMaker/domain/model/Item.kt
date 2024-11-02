package tierMaker.domain.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "item")
data class Item(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val name: String = "",

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    val image: ByteArray? = null,

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category
)
