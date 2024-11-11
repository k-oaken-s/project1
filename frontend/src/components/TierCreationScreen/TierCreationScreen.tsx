"use client";

import { closestCenter, DndContext, DragEndEvent, DragOverlay, DragStartEvent, PointerSensor, useSensor, useSensors } from '@dnd-kit/core';
import { arrayMove } from '@dnd-kit/sortable';
import { Col, Row } from 'antd';
import React, { useState } from 'react';
import DraggableItemOverlay from './DraggableItemOverlay';
import Tier from './Tier';
import TierItemList from './TierItemList';
import { Item } from '@/types/Item';

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
        // availableItemsから検索
        const item = availableItems.find((item) => item.id === id);
        if (item) return item;

        // tiersから検索
        for (const tierItems of Object.values(tiers)) {
            const foundItem = tierItems.find((item) => item.id === id);
            if (foundItem) return foundItem;
        }

        return undefined;
    };

    const handleDragStart = (event: DragStartEvent) => {
        setActiveId(String(event.active.id));
    };

    // TierCreationScreen.tsx
    const handleDragEnd = (event: DragEndEvent) => {
        const { active, over } = event;

        if (!over) return;

        const activeId = String(active.id);
        const overId = String(over.id);

        const item = active.data.current?.item as Item;
        const sourceTierName = active.data.current?.tierName;
        const destinationTierName = over.data.current?.tierName;

        if (!item || !sourceTierName || !destinationTierName) return;
        if (sourceTierName === destinationTierName) {
            // 同じTier内での並び替え
            setTiers((prevTiers) => {
                const items = prevTiers[sourceTierName];
                const oldIndex = items.findIndex((item) => item.id === activeId);
                const newIndex = items.findIndex((item) => item.id === overId);

                if (oldIndex === -1 || newIndex === -1) return prevTiers;

                const newItems = arrayMove(items, oldIndex, newIndex);

                return {
                    ...prevTiers,
                    [sourceTierName]: newItems,
                };
            });
        } else {
            if (sourceTierName === 'unassigned') {
                // ItemListからTierへの移動
                setAvailableItems((prev) => prev.filter((i) => i.id !== item.id));
            } else {
                // 元のTierからアイテムを削除
                setTiers((prev) => ({
                    ...prev,
                    [sourceTierName]: prev[sourceTierName].filter((i) => i.id !== item.id),
                }));
            }

            if (destinationTierName === 'unassigned') {
                // TierからItemListへの移動
                setAvailableItems((prev) => [...prev, item]);
            } else {
                // 目的のTierにアイテムを追加
                setTiers((prev) => ({
                    ...prev,
                    [destinationTierName]: [...prev[destinationTierName], item],
                }));
            }
        }
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
            <Row gutter={[16, 16]}>
                <Col span={24}>
                    <TierItemList items={availableItems} />
                </Col>
                {Object.keys(tiers).map((tierName) => (
                    <Col key={tierName} xs={24} sm={12} md={8} lg={6}>
                        <Tier name={tierName} items={tiers[tierName]} />
                    </Col>
                ))}
            </Row>

            <DragOverlay>
                {activeId ? (
                    <DraggableItemOverlay id={activeId} findItemById={findItemById} />
                ) : null}
            </DragOverlay>
        </DndContext>
    );
};

export default TierCreationScreen;
