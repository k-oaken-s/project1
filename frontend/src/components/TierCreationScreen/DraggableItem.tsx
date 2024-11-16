import { Item } from '@/types/Item';
import { getImageUrl } from '@/utils/getImageUrl';
import { useDraggable } from '@dnd-kit/core';
import { CSS } from '@dnd-kit/utilities';
import { Avatar } from 'antd';
import React from 'react';

type DraggableItemProps = {
    item: Item;
    tierName?: string;
    isOverlay?: boolean; // この行を追加
};

const DraggableItem: React.FC<DraggableItemProps> = ({ item, tierName, isOverlay = false }) => {
    const draggable = useDraggable({
        id: item.id,
        data: {
            item,
            tierName,
        },
    });

    const { attributes, listeners, setNodeRef, transform, isDragging } = draggable;

    const style: React.CSSProperties = {
        transform: transform ? CSS.Transform.toString(transform) : undefined,
        transition: isDragging ? 'transform 0.2s ease' : undefined,
        margin: '8px',
        opacity: isDragging ? 0.5 : 1,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        width: '100px',
        height: '100px',
        background: '#f0f0f0',
        borderRadius: '8px',
    };

    const ref = isOverlay ? undefined : setNodeRef; // isOverlay の場合、ref を設定しない

    return (
        <div ref={ref} style={style} {...listeners} {...attributes}>
            <Avatar
                src={getImageUrl(item.image)}
                alt={item.name}
                size={64}
            />
            <span style={{ marginTop: '8px' }}>{item.name}</span>
        </div>
    );
};

export default DraggableItem;
