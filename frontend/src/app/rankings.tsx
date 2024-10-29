import { useState, useEffect } from 'react';
import axios from 'axios';
import { useRouter } from 'next/router';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';

const RankingsPage = () => {
  const router = useRouter();
  const { gameId } = router.query;
  const [characters, setCharacters] = useState([]);
  const [unranked, setUnranked] = useState([]);
  const [rankings, setRankings] = useState({});
  const [rankPattern, setRankPattern] = useState(['S', 'A', 'B', 'C', 'D']);

  useEffect(() => {
    if (gameId) {
      axios.get(`http://localhost:8080/api/games/${gameId}/characters`)
        .then((res) => {
          setCharacters(res.data);
          setUnranked(res.data);
        })
        .catch((err) => console.error(err));
    }
  }, [gameId]);

  const onDragEnd = (result) => {
    // ドラッグアンドドロップの処理を実装
  };

  const generateShareUrl = () => {
    // ランク表データを圧縮・エンコードして共有URLを生成
  };

  return (
    <div>
      <h1>ランク表作成</h1>
      {/* ランクパターンのプルダウン */}
      {/* ドラッグアンドドロップエリア */}
      {/* 共有URLの生成ボタン */}
      {/* 画像出力のボタン */}
    </div>
  );
};

export default RankingsPage;
