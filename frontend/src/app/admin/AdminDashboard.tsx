"use client";

import CategoryForm from '@/app/admin/categories/components/CategoryForm';
import CategoryList from '@/components/CategoryList';
import {Category} from '@/types/Category';
import {getApiBaseUrl} from '@/utils/getApiBaseUrl';
import axios from 'axios';
import {useRouter} from 'next/navigation';
import {useEffect, useState} from 'react';
import 'tailwindcss/tailwind.css';

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
            headers: {Authorization: `Bearer ${token}`},
        })
            .then((res) => setCategories(res.data))
            .catch((err) => {
                if (err.response?.status === 401) router.push('/admin/login');
            });
    }, [router]);

    const addCategory = (name: string, image: File | null) => {
        const token = localStorage.getItem('token');
        if (!token) {
            router.push('/admin/login');
            return;
        }

        const formData = new FormData();
        formData.append('category', new Blob([JSON.stringify({name})], {type: 'application/json'}));
        if (image) formData.append('file', image);

        axios.post<Category>(`${getApiBaseUrl()}/categories`, formData, {
            headers: {Authorization: `Bearer ${token}`},
        })
            .then((res) => {
                setCategories((prevCategories) => [...prevCategories, res.data]);
            })
            .catch((err) => console.error("Failed to add category:", err));
    };

    return (
        <div className="p-8 max-w-4xl mx-auto bg-gray-100 rounded-lg shadow-lg">
            <h1 className="text-3xl font-bold text-center text-gray-900 mb-6">カテゴリーリスト</h1>

            <div
                className="mb-8 bg-white px-8 py-6 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300">
                <CategoryForm onAddCategory={addCategory}/>
                <CategoryList categories={categories}/>
            </div>
        </div>
    );
};

export default AdminDashboard;
