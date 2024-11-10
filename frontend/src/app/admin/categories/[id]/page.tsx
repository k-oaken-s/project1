"use client";

import ItemForm from '@/app/admin/categories/components/ItemForm';
import ItemList from '@/components/ItemList';
import { useFetchCategoryWithItems } from '@/hooks/useFetchCategoryWithItems';
import { Item } from '@/types/Item';
import axios from 'axios';
import { useParams, useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

const CategoryDetailPage = () => {
    const params = useParams();
    const router = useRouter();
    const categoryId = Array.isArray(params?.id) ? params.id[0] : params?.id;
    const { category, isLoading } = useFetchCategoryWithItems(categoryId);
    const [items, setItems] = useState<Item[]>([]);
    const [editingItem, setEditingItem] = useState<Item | null>(null);

    useEffect(() => {
        if (category) setItems(category.items);
    }, [category]);

    const addItem = (name: string, image: File | null | "remove") => {
        const token = localStorage.getItem('token');
        if (!token) {
            router.push('/admin/login');
            return;
        }
        const formData = new FormData();
        formData.append('item', new Blob([JSON.stringify({ name })], { type: 'application/json' }));
        if (image && image !== "remove") formData.append('file', image);

        axios.post<Item>(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories/${categoryId}/items`, formData, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then((res) => {
                setItems((prevItems) => [...prevItems, res.data]);
            })
            .catch((err) => console.error("Failed to add item:", err));
    };

    const editItem = (name: string, image: File | null | "remove") => {
        if (!editingItem) return;
        const token = localStorage.getItem('token');
        if (!token) {
            router.push('/admin/login');
            return;
        }

        const formData = new FormData();
        formData.append('item', new Blob([JSON.stringify({ name })], { type: 'application/json' }));

        if (image === "remove") {
            formData.append('removeImage', 'true');
        } else if (image) {
            formData.append('file', image);
        } else {
            formData.append('keepCurrentImage', 'true');
        }

        axios.put<Item>(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories/${categoryId}/items/${editingItem.id}`, formData, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then((res) => {
                setItems((prevItems) =>
                    prevItems.map((item) => (item.id === editingItem.id ? res.data : item))
                );
                setEditingItem(null);
            })
            .catch((err) => console.error("Failed to update item:", err));
    };

    const startEditingItem = (item: Item) => {
        setEditingItem(item);
    };

    return isLoading ? (
        <p className="text-center text-gray-500 text-lg mt-10">Loading...</p>
    ) : (
        <div className="p-8 max-w-4xl mx-auto bg-white rounded-lg shadow-lg">
            <button
                onClick={() => router.push('/admin')}
                className="bg-gradient-to-r from-blue-500 to-purple-600 text-white py-2 px-6 rounded-full hover:shadow-lg transition-all duration-300 mb-6"
            >
                管理者ダッシュボードに戻る
            </button>

            {category && (
                <div className="mb-8">
                    <h1 className="text-3xl font-bold text-gray-800 mb-2">{category.name}</h1>
                    {category.description && <p className="text-gray-600 mb-4">{category.description}</p>}
                    {category.image && (
                        <img
                            src={`data:image/jpeg;base64,${category.image}`}
                            alt={`${category.name} image`}
                            className="mt-4 w-full max-h-72 object-cover rounded-lg shadow-lg"
                        />
                    )}
                </div>
            )}

            <h2 className="text-2xl font-semibold text-gray-700 mb-6">カテゴリーのアイテム一覧</h2>

            <div className="mb-6">
                <ItemList items={items} onEdit={startEditingItem} />
            </div>

            <div className="p-6 bg-gray-50 rounded-lg shadow-inner">
                <ItemForm onSubmit={editingItem ? editItem : addItem} />
            </div>
        </div>
    );
};

export default CategoryDetailPage;
