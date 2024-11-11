'use client';

import TierCreationScreen from '@/components/TierCreationScreen/TierCreationScreen';
import { Item } from '@/types/Item';

const items: Item[] = [
    { id: '1', name: 'アイテム1' },
    { id: '2', name: 'アイテム2' },
    { id: '3', name: 'アイテム3' },
    // その他のアイテム
];

const HomePage = () => {
    return (
        <div>
            <TierCreationScreen items={items} />
        </div>
    );
};

export default HomePage;
