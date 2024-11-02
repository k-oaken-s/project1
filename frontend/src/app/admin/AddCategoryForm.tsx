"use client";

import { useState } from 'react';
import axios from 'axios';

const AddCategoryForm = () => {
    const [name, setName] = useState('');

      const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files[0]) {
          setImage(e.target.files[0]);
        }
      };

    const handleSubmit = async (e: { preventDefault: () => void; }) => {
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
    <div>
      <input
        type="text"
        placeholder="カテゴリー名を入力"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <input type="file" onChange={handleImageUpload} />
      <button onClick={handleSubmit}>カテゴリーを追加</button>
    </div>
  );
};

export default AddCategoryForm;

