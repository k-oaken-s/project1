import { Category } from '@/types/Category';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

export const useFetchCategoryWithItems = (categoryId: string | undefined) => {
    const [category, setCategory] = useState<Category | null>(null);
    const [isLoading, setIsLoading] = useState(true);
    const router = useRouter();

    useEffect(() => {
        if (!categoryId) return;

        const token = localStorage.getItem('token');
        if (!token) {
            router.push('/admin/login');
            return;
        }

        setIsLoading(true);
        axios.get<Category>(`${process.env.NEXT_PUBLIC_API_BASE_URL}/categories/${categoryId}`, {
            withCredentials: true,
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        })
            .then((res) => {
                setCategory(res.data);
            })
            .catch((err) => {
                console.error(err);
                if (err.response && err.response.status === 401) {
                    router.push('/admin/login');
                }
            })
            .finally(() => setIsLoading(false));
    }, [categoryId, router]);

    return { category, isLoading };
};
