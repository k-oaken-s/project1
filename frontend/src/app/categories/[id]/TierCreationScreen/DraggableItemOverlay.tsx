import { Item } from '@/types/Item';
import { getImageUrl } from '@/utils/getImageUrl';
import { Avatar, Card } from 'antd';
import React from 'react';

type DraggableItemOverlayProps = {
    id: string;
    findItemById: (id: string) => Item | undefined;
};

const DraggableItemOverlay: React.FC<DraggableItemOverlayProps> = ({ id, findItemById }) => {
    const item = findItemById(id);

    if (!item) return null;

    return (
        <Card
            style={{ width: 150, display: 'flex', alignItems: 'center' }}
        >
            <Avatar
                src={getImageUrl(item.image)}
                alt={item.name}
                size={50}
                style={{ marginRight: '16px' }}
            />
            <span>{item.name}</span>
        </Card>
    );
};

export default DraggableItemOverlay;
