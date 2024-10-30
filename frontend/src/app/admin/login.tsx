// pages/admin/login.tsx

import axios from 'axios';
import { useRouter } from 'next/router';
import { useState } from 'react';

const AdminLoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const router = useRouter();

    const login = () => {
        axios.post('http://localhost:8080/api/admin/login', {}, {
            auth: {
                username,
                password
            }
        }).then(() => {
            router.push('/admin');
        }).catch((err) => {
            console.error(err);
            alert('ログインに失敗しました');
        });
    };

    return (
        <div>
            <h1>管理者ログイン</h1>
            <input type="text" placeholder="ユーザー名" value={username} onChange={(e) => setUsername(e.target.value)} />
            <input type="password" placeholder="パスワード" value={password} onChange={(e) => setPassword(e.target.value)} />
            <button onClick={login}>ログイン</button>
        </div>
    );
};

export default AdminLoginPage;
