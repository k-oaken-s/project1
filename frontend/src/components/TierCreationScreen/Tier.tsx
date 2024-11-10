import { TierItem } from '@/types/TierItem';
import { SortableContext, verticalListSortingStrategy } from '@dnd-kit/sortable';
import React from 'react';
import DraggableItem from './DraggableItem';

type TierProps = {
    name: string;
    items: TierItem[];
};

const Tier: React.FC<TierProps> = ({ name, items }) => {
    const tierColors: { [key: string]: string } = {
        Tier1: 'red',
        Tier2: 'yellow',
        Tier3: 'green',
        Tier4: 'blue',
    };

    return (
        <div className="tier" style={{ backgroundColor: tierColors[name] }}>
            <h3>{name}</h3>
            <SortableContext items={items} strategy={verticalListSortingStrategy}>
                {items.map((item) => (
                    <DraggableItem key={item.id} item={item} />
                ))}
            </SortableContext>
        </div>
    );
};

export default Tier;
