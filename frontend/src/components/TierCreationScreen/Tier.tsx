import { TierItem } from '@/types/TierItem';
import { useDroppable } from '@dnd-kit/core';
import { SortableContext, verticalListSortingStrategy } from '@dnd-kit/sortable';
import React from 'react';
import DraggableItem from './DraggableItem';
import styles from './Tier.module.css';

type TierProps = {
    name: string;
    items: TierItem[];
};

const Tier: React.FC<TierProps> = ({ name, items }) => {
    const { isOver, setNodeRef } = useDroppable({
        id: name,
        data: { tierName: name },
    });

    return (
        <div ref={setNodeRef} className={styles.tier}>
            <h3>{name}</h3>
            <SortableContext items={items.map((item) => item.id)} strategy={verticalListSortingStrategy}>
                {items.map((item) => (
                    <DraggableItem key={item.id} item={item} tierName={name} />
                ))}
            </SortableContext>
        </div>
    );
};

export default Tier;
