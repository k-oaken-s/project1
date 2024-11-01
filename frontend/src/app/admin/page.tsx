"use client";

import { Category } from '@/types/Category';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

const AdminDashboard = () => {
    const [categories, setCategories] = useState<Category[]>([]);
    const [newCategoryName, setNewCategoryName] = useState('');
    const router = useRouter();

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            router.push('/admin/login');
            return;
        }

        axios.get<Category[]>(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
        .then((res) => setCategories(res.data))
        .catch((err) => console.error(err));
    }, []);

    const addCategory = () => {
        const token = localStorage.getItem('token');
        if (!token) return;

        axios.post(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories`, { name: newCategoryName }, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
        .then((res) => {
            setCategories([...categories, res.data]);
            setNewCategoryName(''); // フィールドをクリア
        })
        .catch((err) => console.error(err));
    };

    const deleteCategory = (categoryId: string) => {
        const token = localStorage.getItem('token');
        if (!token) return;

        axios.delete(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories/${categoryId}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
        .then(() => {
            setCategories(categories.filter(category => category.id !== categoryId));
        })
        .catch((err) => console.error(err));
    };

    return (
        <div>
            <h1>管理者ダッシュボード</h1>
            <div>
                <input
                    type="text"
                    placeholder="カテゴリー名を入力"
                    value={newCategoryName}
                    onChange={(e) => setNewCategoryName(e.target.value)}
                />
                <button onClick={addCategory}>カテゴリーを追加</button>
            </div>
            <ul>
                {categories.map((category) => (
                    <li key={category.id}>
                        {category.name}
                        <button onClick={() => deleteCategory(category.id)}>削除</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default AdminDashboard;
