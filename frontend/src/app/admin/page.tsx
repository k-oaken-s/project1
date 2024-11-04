"use client";

import { Category } from '@/types/Category';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

const AdminDashboard = () => {
    const [categories, setCategories] = useState<Category[]>([]);
    const [newCategoryName, setNewCategoryName] = useState('');
    const [categoryImage, setCategoryImage] = useState<File | null>(null); // カテゴリー用の画像
    const router = useRouter();

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            router.push('/admin/login');
            return;
        }

        axios.get<Category[]>(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories`, {
            withCredentials: true,
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        })
            .then((res) => {
                if (res.data && Array.isArray(res.data)) {
                    setCategories(res.data);
                }
            })
            .catch((err) => {
                console.error(err);
                if (err.response && err.response.status === 401) {
                    router.push('/admin/login');
                }
            });
    }, []);

    const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>, setImage: (file: File | null) => void) => {
        if (e.target.files && e.target.files[0]) {
            setImage(e.target.files[0]);
        }
    };

    const addCategory = () => {
        const token = localStorage.getItem('token');
        if (!token) return;

        const formData = new FormData();
        formData.append('category', new Blob([JSON.stringify({ name: newCategoryName, description: "" })], { type: 'application/json' }));
        if (categoryImage) formData.append('file', categoryImage);

        axios.post(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories`, formData, {
            headers: {
                Authorization: `Bearer ${token}`
            },
        })
        .then((res) => {
            setCategories((prevCategories) => [...prevCategories, res.data]);
            setNewCategoryName('');
            setCategoryImage(null);
        })
        .catch((err) => console.error(err));
    };

    const handleCategoryClick = (category: Category) => {
        router.push(`/admin/categories/${category.id}`);
    };

    return (
        <div className="p-5 max-w-4xl mx-auto">
            <h1 className="text-2xl font-bold mb-6">管理者ダッシュボード</h1>
            <div className="mb-6">
                <input
                    type="text"
                    placeholder="カテゴリー名を入力"
                    value={newCategoryName}
                    onChange={(e) => setNewCategoryName(e.target.value)}
                    className="border rounded p-2 w-full mb-2"
                />
                <input
                    type="file"
                    onChange={(e) => handleImageUpload(e, setCategoryImage)}
                    className="mb-2"
                />
                <button
                    onClick={addCategory}
                    className="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600"
                >
                    カテゴリーを追加
                </button>
            </div>
            <ul className="space-y-4">
                {categories.length > 0 ? categories.map((category) => (
                    <li
                        key={category.id}
                        className="border rounded p-4 shadow-md flex items-center cursor-pointer hover:bg-gray-100"
                        onClick={() => handleCategoryClick(category)}
                    >
                        {category.image && (
                            <img
                                src={`data:image/png;base64,${category.image}`} // Base64エンコードされた画像を使用
                                alt={`${category.name}の画像`}
                                className="w-16 h-16 object-cover mr-4"
                                loading="lazy"
                            />
                        )}
                        <div className="flex-1">
                            <span className="font-semibold text-lg">{category.name}</span>
                        </div>
                    </li>
                )) : <li className="text-gray-500">カテゴリーがありません</li>}
            </ul>
        </div>
    );
};

export default AdminDashboard;
