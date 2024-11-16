import { Item } from '@/types/Item';
import { Avatar, Card } from 'antd';
import React from 'react';
import { getImageUrl } from '@/utils/getImageUrl';

type TierItemListProps = {
    items: Item[];
};

const TierItemList: React.FC<TierItemListProps> = ({ items }) => (
    <div className="item-list" style={{ display: 'flex', flexWrap: 'wrap', gap: '10px', justifyContent: 'center' }}>
        {items.map((item) => (
            <Card
                key={item.id}
                style={{
                    width: '150px',
                    textAlign: 'center',
                    padding: '10px',
                }}
            >
                <Avatar
                    src={getImageUrl(item.image)}
                    alt={item.name}
                    size={100}
                    style={{ marginBottom: '10px' }}
                />
                <span>{item.name}</span>
            </Card>
        ))}
    </div>
);

export default TierItemList;
