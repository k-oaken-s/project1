"use client";

import UserCategoryList from '@/components/UserCategoryList';
import {Category as CategoryType} from '@/types/Category';
import {getApiBaseUrl} from '@/utils/getApiBaseUrl';
import axios from 'axios';
import {useEffect, useState} from 'react';
import 'tailwindcss/tailwind.css';

const TopPage = () => {
    const [categories, setCategories] = useState<CategoryType[]>([]);

    useEffect(() => {
        axios
            .get<CategoryType[]>(`${getApiBaseUrl()}/categories`)
            .then((res) => setCategories(res.data))
            .catch((err) => console.error("Failed to fetch categories:", err));
    }, []);

    return (
        <div className="container mx-auto px-4">
            <h1 className="text-4xl font-bold text-center mt-10 mb-12">カテゴリ一覧</h1>
            <UserCategoryList categories={categories}/>
        </div>
    );
};

export default TopPage;
