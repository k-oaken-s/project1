import axios from 'axios';
import LZString from 'lz-string';
import { useRouter } from 'next/router';
import { useEffect, useState } from 'react';
import { DropResult } from 'react-beautiful-dnd';

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

    const onDragEnd = (result: DropResult) => {
        const { source, destination } = result;

        // ドロップ先がない場合は、並べ替えを実行せず終了
        if (!destination) return;

        // 移動元と移動先が同じであれば何もしない
        if (source.droppableId === destination.droppableId && source.index === destination.index) {
            return;
        }

        // キャラクターリストを更新するロジックを記述
        // ここでは例として、`characters` ステートを更新する処理を記述します
        const updatedCharacters = Array.from(characters);
        const [movedCharacter] = updatedCharacters.splice(source.index, 1); // 移動する要素を削除
        updatedCharacters.splice(destination.index, 0, movedCharacter); // 新しい位置に要素を追加

        // 更新されたリストをステートに設定
        setCharacters(updatedCharacters);
    };

    const generateShareUrl = () => {
        const compressedData = LZString.compress(JSON.stringify(rankings));
        const url = `${window.location.origin}${window.location.pathname}?data=${encodeURIComponent(compressedData)}`;
        console.log('共有URL:', url);
        return url;
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
