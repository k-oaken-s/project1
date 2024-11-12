"use client";

import axios from 'axios';
import { useRouter } from 'next/navigation';
import { useState } from 'react';

const AdminLogin = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const router = useRouter();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const response = await axios.post(`${process.env.NEXT_PUBLIC_API_BASE_URL}/admin/login`, {
                username,
                password,
            });

            // ログイン成功時にトークンをlocalStorageに保存し、ダッシュボードにリダイレクト
            if (response.data.token) {
                localStorage.setItem('token', response.data.token);
                router.push('/admin');
            } else {
                alert('ログインに失敗しました');
            }
        } catch (error) {
            console.error(error);
            alert('ログインに失敗しました');
        }
    };

    return (
        <form onSubmit={handleLogin}>
            <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
            <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
            <button type="submit">Login</button>
        </form>
    );
};

export default AdminLogin;
