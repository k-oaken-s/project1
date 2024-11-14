import { Item } from '@/types/Item';
import { Avatar, Card } from 'antd';
import React from 'react';

type TierItemListProps = {
    items: Item[];
};

const TierItemList: React.FC<TierItemListProps> = ({ items }) => (
    <div className="item-list">
        {items.map((item) => (
            <Card
                key={item.id}
                style={{ marginBottom: '10px', display: 'flex', alignItems: 'center' }}
            >
                <Avatar
                    src={item.image}
                    alt={item.name}
                    size={50}
                    style={{ marginRight: '16px' }}
                />
                <span>{item.name}</span>
            </Card>
        ))}
    </div>
);

export default TierItemList;
