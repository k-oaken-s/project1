"use client";

import { Category } from '@/types/Category';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

const AdminDashboard = () => {
    const [categories, setCategories] = useState<Category[]>([]);
    const [newCategoryName, setNewCategoryName] = useState('');
    const [selectedCategoryId, setSelectedCategoryId] = useState<string | null>(null); // 選択されたカテゴリーID
    const [newItemName, setNewItemName] = useState(''); // 新規Itemの名前
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
                    // 未認証時のリダイレクト
                    router.push('/admin/login');
                }
            });
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
                setCategories((prevCategories) => [...prevCategories, res.data]);
                setNewCategoryName('');
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
                setCategories((prevCategories) => prevCategories.filter(category => category.id !== categoryId));
            })
            .catch((err) => console.error(err));
    };

    // 選択されたカテゴリーに紐づくItemを追加する
    const addItemToCategory = () => {
        const token = localStorage.getItem('token');
        if (!token || !selectedCategoryId) return;

        axios.post(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories/${selectedCategoryId}/items`, { name: newItemName }, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then(() => {
                alert('Itemが追加されました');
                setNewItemName('');
                setSelectedCategoryId(null); // フォームを閉じる
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
                        <span onClick={() => setSelectedCategoryId(category.id)} style={{ cursor: 'pointer', color: 'blue' }}>
                            {category.name}
                        </span>
                        <button onClick={() => deleteCategory(category.id)}>削除</button>

                        {/* カテゴリーが選択されたらItem追加フォームを表示 */}
                        {selectedCategoryId === category.id && (
                            <div style={{ marginTop: '10px' }}>
                                <input
                                    type="text"
                                    placeholder="Item名を入力"
                                    value={newItemName}
                                    onChange={(e) => setNewItemName(e.target.value)}
                                />
                                <button onClick={addItemToCategory}>Itemを追加</button>
                            </div>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default AdminDashboard;
