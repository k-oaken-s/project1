"use client";

import { Category } from '@/types/Category';
import axios from 'axios';
import Link from 'next/link';
import { useEffect, useState } from 'react';

const HomePage = () => {
    const [categories, setCategories] = useState<Category[]>([]);

    useEffect(() => {
        axios.get<Category[]>('http://localhost:8080/categories')
            .then((res) => setCategories(res.data as Category[]))
            .catch((err) => console.error(err));
    }, []);

    return (
        <div>
            <h1>カテゴリーを選択してください</h1>
            <ul>
                {categories.map((category: Category) => (
                    <li key={category.id}>
                        <Link href={`/rankings?categoryId=${category.id}`}>{category.name}</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default HomePage;
