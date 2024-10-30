"use client";

import { Game } from '@/types/Game';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

const AdminDashboard = () => {
    const [games, setGames] = useState<Game[]>([]);
    const router = useRouter();

    useEffect(() => {
        // トークンがない場合はログインページへリダイレクト
        const token = localStorage.getItem('token');
        if (!token) {
            router.push('/admin/login');
            return;
        }

        // トークンを使用してゲームデータを取得
        axios.get<Game[]>(`${process.env.NEXT_PUBLIC_API_BASE_URL}/api/games`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((res) => setGames(res.data))
            .catch((err) => console.error(err));
    }, []);

    const addGame = () => {
        // ゲーム追加の処理
    };

    const deleteGame = (gameId: string) => {
        // ゲーム削除の処理
    };

    return (
        <div>
            <h1>管理者ダッシュボード</h1>
            <button onClick={addGame}>ゲームを追加</button>
            <ul>
                {games.map((game) => (
                    <li key={game.id}>
                        {game.name}
                        <button onClick={() => deleteGame(game.id)}>削除</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default AdminDashboard;
