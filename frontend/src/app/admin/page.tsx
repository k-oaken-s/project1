"use client";

import CategoryForm from '@/app/admin/categories/components/CategoryForm';
import CategoryList from '@/components/CategoryList';
import { Category } from '@/types/Category';
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

        axios.get<Category[]>(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories`, {
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

        axios.post<Category>(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories`, formData, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then((res) => {
                setCategories((prevCategories) => [...prevCategories, res.data]);
            })
            .catch((err) => console.error("Failed to add category:", err));
    };

    return (
        <div className="p-5 max-w-4xl mx-auto">
            <h1 className="text-2xl font-bold mb-6">管理者ダッシュボード</h1>
            <CategoryForm onAddCategory={addCategory} />
            <CategoryList categories={categories} />
        </div>
    );
};

export default AdminDashboard;
