import { useRouter } from 'next/router';
import LZString from 'lz-string';
import axios from 'axios';
import { useEffect, useState } from 'react';

const SharePage = () => {
  const router = useRouter();
  const { d } = router.query;
  const [rankingData, setRankingData] = useState(null);
  const [characters, setCharacters] = useState([]);

  useEffect(() => {
    if (d) {
      const jsonString = LZString.decompressFromEncodedURIComponent(d as string);
      const data = JSON.parse(jsonString);
      setRankingData(data);

      axios.get(`http://localhost:8080/api/games/${data.gameId}/characters`)
        .then((res) => setCharacters(res.data))
        .catch((err) => console.error(err));
    }
  }, [d]);

  return (
    <div>
      {/* ランク表を表示 */}
    </div>
  );
};

export default SharePage;
