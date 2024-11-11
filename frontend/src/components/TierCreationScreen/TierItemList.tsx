// ItemList.tsx

import { Item } from '@/types/Item';
import { Card, List } from 'antd';
import React from 'react';
import DraggableItem from './DraggableItem';

type TierItemListProps = {
    items: Item[];
};

const TierItemList: React.FC<TierItemListProps> = ({ items }) => {
    return (
        <Card title="Available Items" style={{ width: '100%', margin: '16px 0' }}>
            <List
                dataSource={items}
                renderItem={(item) => (
                    <List.Item key={item.id}>
                        <DraggableItem item={item} tierName="unassigned" />
                    </List.Item>
                )}
            />
        </Card>
    );
};

export default TierItemList;
