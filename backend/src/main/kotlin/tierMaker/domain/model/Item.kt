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
) {
    constructor() : this(UUID.randomUUID().toString(), "", null, Category())

    companion object {
        fun create(name: String, category: Category, image: ByteArray?): Item {
            return Item(
                name = name,
                image = image,
                category = category
            )
        }
    }
}
