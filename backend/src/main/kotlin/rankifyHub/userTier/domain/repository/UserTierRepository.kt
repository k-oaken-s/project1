package rankifyHub.userTier.domain.repository

import rankifyHub.userTier.domain.model.UserTier
import java.time.Instant

/**
 * UserTierリポジトリのドメイン層インターフェース
 */
interface UserTierRepository {

    /**
     * UserTierを保存します。
     *
     * @param userTier 保存するUserTier
     * @return 保存されたUserTier
     */
    fun save(userTier: UserTier): UserTier

    /**
     * 公開されているUserTierを取得します。
     *
     * @return 公開されているUserTierのリスト
     */
    fun findByIsPublicTrueOrderByCreatedAtDesc(): List<UserTier>

    /**
     * 指定した件数の最新のUserTierを取得します。
     *
     * @param limit 取得する件数
     * @return 最新のUserTierリスト
     */
    fun findLatest(limit: Int): List<UserTier>

    /**
     * 指定した時刻以降のUserTierを取得します。
     *
     * @param timestamp 開始時刻
     * @return 指定時刻以降のUserTierリスト
     */
    fun findSince(timestamp: Instant): List<UserTier>
}
