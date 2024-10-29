import axios from 'axios';
import Link from 'next/link';
import { useEffect, useState } from 'react';

const HomePage = () => {
  const [games, setGames] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/games')
      .then((res) => setGames(res.data))
      .catch((err) => console.error(err));
  }, []);

  return (
    <div>
      <h1>ゲームを選択してください</h1>
      <ul>
        {games.map((game) => (
          <li key={game.id}>
            <Link href={`/rankings?gameId=${game.id}`}>{game.name}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default HomePage;
