"use client";

import axios from 'axios';
import { useParams, useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react';

interface Item {
    id: string;
    name: string;
    image?: string; // Base64エンコードされた画像を格納
}

const CategoryPage = () => {
    const [items, setItems] = useState<Item[]>([]);
    const [newItemName, setNewItemName] = useState('');
    const [itemImage, setItemImage] = useState<File | null>(null);
    const [editingItem, setEditingItem] = useState<Item | null>(null);
    const [editedItemName, setEditedItemName] = useState('');
    const [editedItemImage, setEditedItemImage] = useState<File | null | "remove">(null);
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

    const handleEditImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files[0]) {
            setEditedItemImage(e.target.files[0]);
        } else {
            setEditedItemImage(null); // 未設定に戻すために null をセット
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

    const startEditingItem = (item: Item) => {
        setEditingItem(item);
        setEditedItemName(item.name);
        setEditedItemImage(null); // 初期状態は画像を変更しない
    };

    const updateItem = () => {
        if (!editingItem) return;

        const token = localStorage.getItem('token');
        if (!token) return;

        const formData = new FormData();
        formData.append('item', new Blob([JSON.stringify({ name: editedItemName })], { type: 'application/json' }));

        if (editedItemImage === "remove") {
            formData.append('removeImage', 'true');
        } else if (editedItemImage) {
            formData.append('file', editedItemImage);
        } else {
            // 画像を変更していないことを示すフラグを追加
            formData.append('keepCurrentImage', 'true');
        }

        axios.put(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories/${categoryId}/items/${editingItem.id}`, formData, {
            headers: {
                Authorization: `Bearer ${token}`
            },
        })
            .then((res) => {
                setItems((prevItems) =>
                    prevItems.map((item) => (item.id === editingItem.id ? res.data : item))
                );
                setEditingItem(null);
                setEditedItemName('');
                setEditedItemImage(null);
            })
            .catch((err) => console.error(err));
    };

    return (
        <div className="p-5 max-w-4xl mx-auto">
            <h1 className="text-2xl font-bold mb-6">カテゴリーのアイテム一覧</h1>
            <button
                onClick={() => router.push('/admin')}
                className="bg-gray-700 text-white py-2 px-4 rounded hover:bg-gray-800 mb-4"
            >
                管理者ダッシュボードに戻る
            </button>
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
                        <button
                            onClick={() => startEditingItem(item)}
                            className="bg-yellow-500 text-white py-1 px-3 rounded hover:bg-yellow-600 ml-2"
                        >
                            編集
                        </button>
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
            {editingItem && (
                <div className="mt-6 p-4 border rounded shadow-md bg-gray-50">
                    <h2 className="text-xl font-bold mb-4">アイテムの編集</h2>
                    <input
                        type="text"
                        placeholder="アイテム名を編集"
                        value={editedItemName}
                        onChange={(e) => setEditedItemName(e.target.value)}
                        className="border rounded p-2 w-full mb-2"
                    />
                    <input
                        type="file"
                        onChange={handleEditImageUpload}
                        className="mb-2"
                    />
                    <button
                        onClick={() => setEditedItemImage("remove")}
                        className="bg-gray-500 text-white py-1 px-4 rounded hover:bg-gray-600 mb-2"
                    >
                        画像を削除
                    </button>
                    <button
                        onClick={updateItem}
                        className="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600"
                    >
                        保存
                    </button>
                    <button
                        onClick={() => setEditingItem(null)}
                        className="bg-red-500 text-white py-2 px-4 rounded hover:bg-red-600 ml-2"
                    >
                        キャンセル
                    </button>
                </div>
            )}
        </div>
    );
};

export default CategoryPage;
