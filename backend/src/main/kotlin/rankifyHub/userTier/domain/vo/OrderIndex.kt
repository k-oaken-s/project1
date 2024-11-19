@Embeddable
data class OderIndex(
    @Column(name = "order_index", nullable = false)
    val value: Int
) {
    init {
        require(value > 0) { "Order Index must be greater than 0." }
    }
}
