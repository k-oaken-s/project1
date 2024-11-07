"use client";

import ItemForm from '@/app/admin/categories/components/ItemForm';
import Category from '@/components/Category';
import ItemList from '@/components/ItemList';
import { useFetchCategoryWithItems } from '@/hooks/useFetchCategoryWithItems';
import { Item } from '@/types/Category';
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
        <p>Loading...</p>
    ) : (
        <div className="p-5 max-w-4xl mx-auto">
            <button onClick={() => router.push('/admin')} className="bg-gray-700 text-white py-2 px-4 rounded hover:bg-gray-800 mb-4">
                管理者ダッシュボードに戻る
            </button>
            {category && (
                <div className="mb-6">
                    <Category category={category} />
                </div>
            )}
            <h2 className="text-xl font-semibold mb-4">カテゴリーのアイテム一覧</h2>
            <ItemList items={items} onEdit={startEditingItem} />
            <ItemForm onSubmit={editingItem ? editItem : addItem} />
        </div>
    );
};

export default CategoryDetailPage;
