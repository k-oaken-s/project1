import { Item } from '@/types/Item';
import { useDroppable } from '@dnd-kit/core';
import { SortableContext, verticalListSortingStrategy } from '@dnd-kit/sortable';
import { Card, List } from 'antd';
import React from 'react';
import DraggableItem from './DraggableItem';

type TierProps = {
    name: string;
    items: Item[];
};

const Tier: React.FC<TierProps> = ({ name, items }) => {
    const { isOver, setNodeRef } = useDroppable({
        id: name,
        data: { tierName: name },
    });

    return (
        <Card
            ref={setNodeRef}
            title={name}
            style={{ width: 300, margin: '16px', backgroundColor: isOver ? '#e6f7ff' : '#fff' }}
        >
            <SortableContext items={items.map((item) => item.id)} strategy={verticalListSortingStrategy}>
                <List
                    dataSource={items}
                    renderItem={(item) => (
                        <List.Item key={item.id}>
                            <DraggableItem item={item} tierName={name} />
                        </List.Item>
                    )}
                />
            </SortableContext>
        </Card>
    );
};

export default Tier;
