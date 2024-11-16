'use client';

import TierCreationScreen from '@/app/categories/[id]/TierCreationScreen/TierCreationScreen';
import { Category } from '@/types/Category';
import { Item } from '@/types/Item';
import { getApiBaseUrl } from '@/utils/getApiBaseUrl';
import { useParams } from 'next/navigation';
import { useEffect, useState } from 'react';

const HomePage = () => {
    const params = useParams();
    const categoryId = Array.isArray(params?.id) ? params.id[0] : params?.id;
    const [items, setItems] = useState<Item[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // アイテムをバックエンドから取得
        const fetchItems = async () => {
            try {
                const response = await fetch(`${getApiBaseUrl()}/categories/${categoryId}`); // APIエンドポイントに合わせてURLを変更
                if (!response.ok) {
                    throw new Error('データの取得に失敗しました');
                }
                const category: Category = await response.json();
                setItems(category.items);
            } catch (error) {
                console.error('エラー:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchItems();
    }, []);

    return (
        <div>
            {loading ? (
                <p>データを読み込み中...</p>
            ) : (
                <TierCreationScreen items={items} />
            )}
        </div>
    );
};

export default HomePage;
