"use client";

import UserCategoryList from '@/components/UserCategoryList';
import UserTierList from '@/components/UserTierList';
import {Category as CategoryType} from '@/types/Category';
import {Tier as TierType} from '@/types/Tier';
import {getApiBaseUrl} from '@/utils/getApiBaseUrl';
import axios from 'axios';
import {useEffect, useState} from 'react';
import 'tailwindcss/tailwind.css';

const TopPage = () => {
    const [categories, setCategories] = useState<CategoryType[]>([]);
    const [tiers, setTiers] = useState<TierType[]>([]);
    const [lastUpdated, setLastUpdated] = useState<number>(Date.now());

    useEffect(() => {
        // カテゴリの取得（最近追加された順にN件）
        axios
            .get<CategoryType[]>(`${getApiBaseUrl()}/categories?limit=10&sort=createdAt_desc`)
            .then((res) => setCategories(res.data))
            .catch((err) => console.error("Failed to fetch categories:", err));

        // Tierの取得（新着N件）
        axios
            .get<TierType[]>(`${getApiBaseUrl()}/user-tiers/latest?limit=10`)
            .then((res) => {
                setTiers(res.data);
                if (res.data.length > 0) {
                    const latestTimestamp = new Date(res.data[0].createdAt).getTime();
                    setLastUpdated(latestTimestamp);
                }
            })
            .catch((err) => console.error("Failed to fetch tiers:", err));
    }, []);

    useEffect(() => {
        // ロングポーリングでTierの更新を監視
        let isMounted = true;

        const longPolling = () => {
            axios
                .get<TierType[]>(`${getApiBaseUrl()}/user-tiers/since?since=${lastUpdated}`)
                .then((res) => {
                    if (isMounted && res.data.length > 0) {
                        setTiers((prevTiers) => {
                            const newTiers = [...res.data, ...prevTiers];
                            return newTiers.slice(0, 10); // 最新10件を保持
                        });
                        // 最新の更新日時を更新
                        const latestTimestamp = new Date(res.data[0].createdAt).getTime();
                        setLastUpdated(latestTimestamp);
                    }
                    // 再度ポーリング
                    longPolling();
                })
                .catch((err) => {
                    console.error("Failed during long polling:", err);
                    setTimeout(() => {
                        if (isMounted) {
                            longPolling();
                        }
                    }, 5000); // 5秒後に再試行
                });
        };

        longPolling();

        return () => {
            isMounted = false;
        };
    }, [lastUpdated]);

    return (
        <div className="container mx-auto px-4">
            <h1 className="text-4xl font-bold text-center mt-10 mb-12">新着Tier一覧</h1>
            <UserTierList tiers={tiers}/>

            <h1 className="text-4xl font-bold text-center mt-10 mb-12">カテゴリ一覧</h1>
            <UserCategoryList categories={categories}/>
        </div>
    );
};

export default TopPage;
