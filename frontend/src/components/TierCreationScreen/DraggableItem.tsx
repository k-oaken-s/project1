import { TierItem } from '@/types/TierItem';
import { useSortable } from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';
import React from 'react';

type DraggableItemProps = {
    item: TierItem;
};

const DraggableItem: React.FC<DraggableItemProps> = ({ item }) => {
    const {
        attributes,
        listeners,
        setNodeRef,
        transform,
        transition,
    } = useSortable({ id: item.id });

    const style = {
        transform: CSS.Transform.toString(transform),
        transition,
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
