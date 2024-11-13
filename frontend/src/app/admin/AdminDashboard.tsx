"use client";

import CategoryForm from '@/app/admin/categories/components/CategoryForm';
import CategoryList from '@/components/CategoryList';
import { Category } from '@/types/Category';
import { getApiBaseUrl } from '@/utils/getApiBaseUrl';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

const AdminDashboard = () => {
    const [categories, setCategories] = useState<Category[]>([]);
    const router = useRouter();

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            router.push('/admin/login');
            return;
        }

        axios.get<Category[]>(`${getApiBaseUrl()}/categories`, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then((res) => setCategories(res.data))
            .catch((err) => {
                if (err.response?.status === 401) router.push('/admin/login');
            });
    }, []);

    const addCategory = (name: string, image: File | null) => {
        const token = localStorage.getItem('token');
        if (!token) {
            router.push('/admin/login');
            return;
        }

        const formData = new FormData();
        formData.append('category', new Blob([JSON.stringify({ name })], { type: 'application/json' }));
        if (image) formData.append('file', image);

        axios.post<Category>(`${getApiBaseUrl()}/categories`, formData, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then((res) => {
                setCategories((prevCategories) => [...prevCategories, res.data]);
            })
            .catch((err) => console.error("Failed to add category:", err));
    };

    return (
        <div className="p-8 max-w-4xl mx-auto bg-white rounded-lg shadow-lg">
            <h1 className="text-3xl font-bold text-gray-800 mb-6">管理者ダッシュボード</h1>

            <div className="mb-8 p-6 bg-gray-50 rounded-lg shadow-inner">
                <h2 className="text-xl font-semibold text-gray-700 mb-4">新しいカテゴリを追加</h2>
                <CategoryForm onAddCategory={addCategory} />
            </div>

            <h2 className="text-2xl font-semibold text-gray-700 mb-6">カテゴリー一覧</h2>
            <CategoryList categories={categories} />
        </div>
    );
};

export default AdminDashboard;
