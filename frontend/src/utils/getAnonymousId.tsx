export const getAnonymousId = (): string => {
    const key = "anonymousId";

    // 既存の anonymousId があれば取得
    let anonymousId = localStorage.getItem(key);

    // 既存の anonymousId がなければ新規作成
    if (!anonymousId) {
        anonymousId = generateAnonymousId();
        localStorage.setItem(key, anonymousId);
    }

    return anonymousId;
};

const generateAnonymousId = (): string => {
    return crypto.randomUUID(); // UUID を生成
};
