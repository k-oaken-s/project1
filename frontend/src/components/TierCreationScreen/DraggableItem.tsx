import { useDraggable } from '@dnd-kit/core';
import { CSS } from '@dnd-kit/utilities';
import React from 'react';
import { Item } from '@/types/Item';
import { Avatar } from 'antd';
import { getImageUrl } from '@/utils/getImageUrl';

type DraggableItemProps = {
    item: Item;
    tierName?: string;
};

const DraggableItem: React.FC<DraggableItemProps> = ({ item, tierName }) => {
    const { attributes, listeners, setNodeRef, transform, isDragging } = useDraggable({
        id: item.id,
        data: {
            item,
            tierName, // 元のTier名を設定
        },
    });

    const style: React.CSSProperties = {
        transform: transform ? CSS.Transform.toString(transform) : undefined,
        transition: 'transform 0.2s ease',
        margin: '10px',
        opacity: isDragging ? 0.5 : 1,
        width: '100px',
        height: '100px',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        border: '1px solid #ccc',
        borderRadius: '10px',
        background: '#fff',
    };

    return (
        <div ref={setNodeRef} style={style} {...listeners} {...attributes}>
            <Avatar
                src={getImageUrl(item.image)}
                alt={item.name}
                size={64}
            />
        </div>
    );
};

export default DraggableItem;
