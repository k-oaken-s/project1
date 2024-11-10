"use client";

import TierCreationScreen from '@/components/TierCreationScreen/TierCreationScreen';
import { TierItem } from '@/types/TierItem';
import React from 'react';

const App: React.FC = () => {
    const items: TierItem[] = [
        { id: 1, name: 'Item 1' },
        { id: 2, name: 'Item 2' },
    ];

    return (
        <div className="App">
            <TierCreationScreen items={items} />
        </div>
    );
};

export default App;
