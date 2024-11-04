"use client";

import axios from 'axios';
import { useParams, useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react';

interface Item {
    id: string;
    name: string;
    image?: string;
}

const CategoryPage = () => {
    const [items, setItems] = useState<Item[]>([]);
    const [newItemName, setNewItemName] = useState('');
    const [itemImage, setItemImage] = useState<File | null>(null);
    const router = useRouter();
    const params = useParams();
    const categoryId = params?.id;

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            router.push('/admin/login');
            return;
        }

        axios.get<Item[]>(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories/${categoryId}/items`, {
            withCredentials: true,
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        })
            .then((res) => {
                if (res.data && Array.isArray(res.data)) {
                    setItems(res.data);
                }
            })
            .catch((err) => {
                console.error(err);
                if (err.response && err.response.status === 401) {
                    router.push('/admin/login');
                }
            });
    }, [categoryId, router]);

    const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files[0]) {
            setItemImage(e.target.files[0]);
        }
    };

    const addItemToCategory = () => {
        const token = localStorage.getItem('token');
        if (!token) return;

        const formData = new FormData();
        formData.append('item', new Blob([JSON.stringify({ name: newItemName })], { type: 'application/json' }));
        if (itemImage) formData.append('file', itemImage);

        axios.post(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories/${categoryId}/items`, formData, {
            headers: {
                Authorization: `Bearer ${token}`
            },
        })
            .then((res) => {
                setItems((prevItems) => [...prevItems, res.data]);
                setNewItemName('');
                setItemImage(null);
            })
            .catch((err) => console.error(err));
    };

    return (
        <div className="p-5 max-w-4xl mx-auto">
            <h1 className="text-2xl font-bold mb-6">カテゴリーのアイテム一覧</h1>
            <ul className="space-y-4">
                {items.length > 0 ? items.map((item) => (
                    <li key={item.id} className="border rounded p-4 shadow-md flex items-center">
                        {item.image && (
                            <img
                                src={`data:image/png;base64,${item.image}`} // Base64エンコードされた画像を使用
                                alt={`${item.name}の画像`}
                                className="w-16 h-16 object-cover mr-4"
                                loading="lazy"
                            />
                        )}
                        <span className="font-semibold text-lg flex-1">{item.name}</span>
                    </li>
                )) : <li className="text-gray-500">アイテムがありません</li>}
            </ul>
            <div className="mt-6">
                <input
                    type="text"
                    placeholder="アイテム名を入力"
                    value={newItemName}
                    onChange={(e) => setNewItemName(e.target.value)}
                    className="border rounded p-2 w-full mb-2"
                />
                <input
                    type="file"
                    onChange={handleImageUpload}
                    className="mb-2"
                />
                <button
                    onClick={addItemToCategory}
                    className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600"
                >
                    アイテムを追加
                </button>
            </div>
        </div>
    );
};

export default CategoryPage;
