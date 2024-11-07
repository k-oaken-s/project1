"use client";

import UserCategoryList from '@/components/UserCategoryList';
import { Category as CategoryType } from '@/types/Category';
import axios from 'axios';
import { useEffect, useState } from 'react';

const TopPage = () => {
    const [categories, setCategories] = useState<CategoryType[]>([]);

    useEffect(() => {
        axios.get<CategoryType[]>(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories`)
            .then((res) => setCategories(res.data))
            .catch((err) => console.error("Failed to fetch categories:", err));
    }, []);

    return (
        <div className="p-8 max-w-6xl mx-auto">
            <h1 className="text-3xl font-bold text-gray-800 mb-8">カテゴリ一覧</h1>
            <UserCategoryList categories={categories} />
        </div>
    );
};

export default TopPage;
