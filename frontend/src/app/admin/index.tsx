// pages/admin/index.tsx

import { useEffect, useState } from 'react';
import axios from 'axios';

const AdminDashboard = () => {
  const [games, setGames] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/games')
      .then((res) => setGames(res.data))
      .catch((err) => console.error(err));
  }, []);

  const addGame = () => {
    // ゲーム追加の処理
  };

  const deleteGame = (gameId) => {
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
            {/* キャラクターの管理ページへのリンク */}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AdminDashboard;
