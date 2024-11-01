"use client";

import { useState } from 'react';
import axios from 'axios';

const AddCategoryForm = () => {
    const [name, setName] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            await axios.post(
                `${process.env.NEXT_PUBLIC_API_BASE_URL}/admin/categories`,
                { name },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            alert('カテゴリーが追加されました');
            setName('');
        } catch (error) {
            console.error(error);
            alert('カテゴリーの追加に失敗しました');
        }
    };

    return (
        <form onSubmit={handleSubmit} className="p-4 bg-white rounded shadow-md">
            <h2 className="text-lg font-semibold mb-4">カテゴリー追加</h2>
            <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="カテゴリー名"
                className="p-2 border rounded w-full mb-4"
                required
            />
            <button type="submit" className="bg-blue-500 text-white p-2 rounded">追加</button>
        </form>
    );
};

export default AddCategoryForm;

