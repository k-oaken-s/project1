import { useDroppable } from '@dnd-kit/core';
import React from 'react';
import { Item } from '@/types/Item';
import DraggableItem from './DraggableItem';

type TierProps = {
    name: string;
    items: Item[];
};

const Tier: React.FC<TierProps> = ({ name, items }) => {
    const { setNodeRef } = useDroppable({
        id: name,
        data: { tierName: name }, // ドロップ先の名前を明示
    });

    return (
        <div
            ref={setNodeRef}
            style={{
                display: 'flex',
                flexWrap: 'wrap',
                width: '100%',
                minHeight: '150px',
                border: '1px solid #ccc',
                padding: '10px',
                gap: '10px',
                alignItems: 'center',
                background: '#f9f9f9',
            }}
        >
            {items.map((item) => (
                <DraggableItem key={item.id} item={item} tierName={name} />
            ))}
        </div>
    );
};

export default Tier;
