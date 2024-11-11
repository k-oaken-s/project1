// DraggableItem.tsx
import { TierItem } from '@/types/TierItem';
import { useSortable } from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';
import React from 'react';

type DraggableItemProps = {
    item: TierItem;
    tierName: string;
};

const DraggableItem: React.FC<DraggableItemProps> = ({ item, tierName }) => {
    const {
        attributes,
        listeners,
        setNodeRef,
        transform,
        transition,
        isDragging,
    } = useSortable({
        id: item.id,
        data: { item, tierName },
    });

    const style = {
        transform: CSS.Transform.toString(transform),
        transition,
        opacity: isDragging ? 0 : 1, // ドラッグ中は非表示
    };

    return (
        <div
            ref={setNodeRef}
            style={style}
            {...attributes}
            {...listeners}
            className="draggable-item"
        >
            {item.name}
        </div>
    );
};

export default DraggableItem;
