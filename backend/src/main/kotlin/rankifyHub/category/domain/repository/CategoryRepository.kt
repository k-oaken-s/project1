package rankifyHub.category.domain.repository

import rankifyHub.category.domain.model.Category
import java.util.*

/**
 * ドメイン層のCategoryリポジトリインターフェース
 */
interface CategoryRepository {
    /**
     * すべてのカテゴリーを取得します。
     *
     * @return 登録されているカテゴリーのリスト
     */
    fun findAll(): List<Category>

    /**
     * 指定されたIDのカテゴリーを取得します。
     *
     * @param id カテゴリーのID
     * @return 指定されたIDのカテゴリー（Optional）
     */
    fun findById(id: String): Optional<Category>

    /**
     * カテゴリーを保存します。
     *
     * @param category 保存するカテゴリー
     * @return 保存されたカテゴリー
     */
    fun save(category: Category): Category

    /**
     * 指定されたIDのカテゴリーを削除します。
     *
     * @param id 削除するカテゴリーのID
     */
    fun deleteById(id: String)
}
