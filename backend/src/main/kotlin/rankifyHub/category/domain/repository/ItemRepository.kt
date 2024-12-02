package rankifyHub.category.domain.repository

import rankifyHub.category.domain.model.Item

/**
 * ドメイン層のItemリポジトリインターフェース
 */
interface ItemRepository {
    /**
     * 最近追加されたアイテムを取得
     * @param limit 取得する件数
     * @return 最近追加されたアイテムのリスト
     */
    fun findLatest(limit: Int): List<Item>

    /**
     * 特定の条件に基づいたアイテムを取得
     * @param condition 条件を表す文字列
     * @return 条件に合致するアイテムのリスト
     */
    fun findByCustomCondition(condition: String): List<Item>
}
