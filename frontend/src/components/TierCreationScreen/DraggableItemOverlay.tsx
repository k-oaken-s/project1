import { Item } from '@/types/Item';
import React from 'react';

type DraggableItemOverlayProps = {
    id: string;
    findItemById: (id: string) => Item | undefined;
};

const DraggableItemOverlay: React.FC<DraggableItemOverlayProps> = ({ id, findItemById }) => {
    const item = findItemById(id);

    if (!item) return null;

    return (
        <div className="draggable-item-overlay">
            {item.name}
        </div>
    );
};

export default DraggableItemOverlay;
