"use client";

import { Game } from '@/types/Game';
import axios from 'axios';
import { useEffect, useState } from 'react';

const AdminDashboard = () => {
    const [games, setGames] = useState<Game[]>([]);

    useEffect(() => {
        axios.get<Game[]>('http://localhost:8080/api/games')
            .then((res) => setGames(res.data as Game[]))
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
                {games.map((game: Game) => (
                    <li key={game.id}>
                        {game.name}
                        <button onClick={() => deleteGame(game.id)}>削除</button>
                        {/* キャラクターの管理ページへのリンク */}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default AdminDashboard;
