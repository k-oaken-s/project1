"use client";

import UserCategoryList from '@/components/UserCategoryList';
import { Category as CategoryType } from '@/types/Category';
import { getApiBaseUrl } from '@/utils/getApiBaseUrl';
import axios from 'axios';
import { useEffect, useState } from 'react';
const TopPage = () => {
    const [categories, setCategories] = useState<CategoryType[]>([]);

    useEffect(() => {
        axios
            .get<CategoryType[]>(`${getApiBaseUrl()}/categories`)
            .then((res) => setCategories(res.data))
            .catch((err) => console.error("Failed to fetch categories:", err));
    }, []);

    return (
        <>
            <h1 className="text-3xl font-bold text-gray-800 mb-8">カテゴリ一覧</h1>
            <UserCategoryList categories={categories} />
        </>
    );
};

export default TopPage;
