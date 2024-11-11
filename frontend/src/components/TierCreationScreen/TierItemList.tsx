// ItemList.tsx

import { TierItem } from '@/types/TierItem';
import React from 'react';
import DraggableItem from './DraggableItem';

type TierItemListProps = {
    items: TierItem[];
};

const TierItemList: React.FC<TierItemListProps> = ({ items }) => {
    return (
        <div className="item-list">
            {items.map((item) => (
                <DraggableItem key={item.id} item={item} tierName="unassigned" />
            ))}
        </div>
    );
};

export default TierItemList;
