"use client";

import {
    DndContext,
    DragEndEvent,
    DragOverlay,
    DragStartEvent,
    PointerSensor,
    useSensor,
    useSensors,
    closestCenter,
} from '@dnd-kit/core';
import { Typography } from 'antd';
import DraggableItem from './DraggableItem';
import Tier from './Tier';
import TierItemList from './TierItemList';
import { Item } from '@/types/Item';
import { arrayMove } from '@dnd-kit/sortable';
import React, { useState } from 'react';

const { Title } = Typography;

type TierCreationScreenProps = {
    items: Item[];
};

const TierCreationScreen: React.FC<TierCreationScreenProps> = ({ items }) => {
    const [tiers, setTiers] = useState<{ [key: string]: Item[] }>({
        Tier1: [],
        Tier2: [],
        Tier3: [],
        Tier4: [],
    });

    const [activeId, setActiveId] = useState<string | null>(null);
    const [availableItems, setAvailableItems] = useState<Item[]>(items);

    const findItemById = (id: string): Item | undefined => {
        const itemInAvailable = availableItems.find((item) => item.id === id);
        if (itemInAvailable) return itemInAvailable;

        for (const tier of Object.values(tiers)) {
            const itemInTier = tier.find((item) => item.id === id);
            if (itemInTier) return itemInTier;
        }

        return undefined;
    };


    const handleDragStart = (event: DragStartEvent) => {
        setActiveId(String(event.active.id));
    };

    const handleDragEnd = (event: DragEndEvent) => {
        const { active, over } = event;

        if (!over) {
            setActiveId(null);
            return;
        }

        const activeId = String(active.id);
        const item = findItemById(activeId);
        if (!item) {
            setActiveId(null);
            return;
        }

        const sourceTierName = active.data.current?.tierName || 'unassigned';
        const destinationTierName = over.data.current?.tierName || 'unassigned';

        if (sourceTierName === destinationTierName) {
            // 同じTier内での移動
            setActiveId(null);
            return;
        }

        // アイテムを移動
        setTiers((prev) => {
            const newTiers = { ...prev };

            // 元のTierからアイテムを削除
            if (sourceTierName !== 'unassigned') {
                newTiers[sourceTierName] = newTiers[sourceTierName].filter((i) => i.id !== activeId);
            } else {
                setAvailableItems((prevItems) => prevItems.filter((i) => i.id !== activeId));
            }

            // 新しいTierにアイテムを追加
            if (destinationTierName !== 'unassigned') {
                newTiers[destinationTierName] = [...newTiers[destinationTierName], item];
            } else {
                setAvailableItems((prevItems) => [...prevItems, item]);
            }

            return newTiers;
        });

        setActiveId(null);
    };


    const sensors = useSensors(
        useSensor(PointerSensor, {
            activationConstraint: {
                distance: 5,
            },
        })
    );

    return (
        <DndContext
            sensors={sensors}
            collisionDetection={closestCenter}
            onDragStart={handleDragStart}
            onDragEnd={handleDragEnd}
        >
            <div style={{ width: '100%' }}>
                {Object.keys(tiers).map((tierName) => (
                    <Tier key={tierName} name={tierName} items={tiers[tierName]} />
                ))}
            </div>

            <div>
                <Title level={4}>未割り当てアイテム</Title>
                <TierItemList items={availableItems} />
            </div>

            <DragOverlay>
                {activeId ? (
                    (() => {
                        const item = findItemById(activeId);
                        return item ? <DraggableItem item={item} isOverlay /> : null;
                    })()
                ) : null}
            </DragOverlay>
        </DndContext>
    );
};

export default TierCreationScreen;
