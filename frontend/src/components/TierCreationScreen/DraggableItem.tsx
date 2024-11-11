import { Item } from '@/types/Item';
import { useSortable } from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';
import { Avatar, Typography } from 'antd';
import React from 'react';

type DraggableItemProps = {
    item: Item;
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
        <div ref={setNodeRef} style={style} {...attributes} {...listeners}>
            <Avatar style={{ marginRight: 8 }}>{item.name.charAt(0)}</Avatar>
            <Typography.Text>{item.name}</Typography.Text>
        </div>
    );
};

export default DraggableItem;
