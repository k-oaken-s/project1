"use client";

import { Item } from "@/types/Item";
import { getAnonymousId } from "@/utils/getAnonymousId";
import { getApiBaseUrl } from "@/utils/getApiBaseUrl";
import {
    DndContext,
    DragEndEvent,
    DragOverlay,
    DragStartEvent,
    PointerSensor,
    useSensor,
    useSensors,
} from "@dnd-kit/core";
import React, { useState } from "react";
import { Button, message, Typography } from 'antd';
import DroppableArea from "./DraggableArea";
import DraggableItem from "./DraggableItem";
import Tier from "./Tier";

const { Text } = Typography;

type TierCreationScreenProps = {
    items: Item[];
    categoryId: string;
};

const TierCreationScreen: React.FC<TierCreationScreenProps> = ({
                                                                   items,
                                                                   categoryId,
                                                               }) => {
    const [tiers, setTiers] = useState<{ [key: string]: Item[] }>({
        Tier1: [],
        Tier2: [],
        Tier3: [],
        Tier4: [],
    });

    const [availableItems, setAvailableItems] = useState<Item[]>(items);
    const [activeId, setActiveId] = useState<string | null>(null);
    const [generatedUrl, setGeneratedUrl] = useState<string | null>(null);
    const [isGenerating, setIsGenerating] = useState(false);

    const sensors = useSensors(
        useSensor(PointerSensor, { activationConstraint: { distance: 5 } })
    );

    const anonymousId = getAnonymousId();

    const findItemById = (id: string): Item | null => {
        const allItems = [...availableItems, ...Object.values(tiers).flat()];
        return allItems.find((item) => item.id === id) || null;
    };

    const handleDragStart = ({ active }: DragStartEvent) => {
        setActiveId(String(active.id));
    };

    const handleDragEnd = ({ active, over }: DragEndEvent) => {
        if (!over) {
            setActiveId(null);
            return;
        }

        const activeItem = findItemById(String(active.id));
        if (!activeItem) {
            setActiveId(null);
            return;
        }

        const sourceTier = Object.keys(tiers).find((key) => tiers[key].some((item) => item.id === active.id)) || "unassigned";
        const destinationTier = over.id;

        if (sourceTier === destinationTier) {
            setActiveId(null);
            return;
        }

        if (sourceTier === "unassigned") {
            setAvailableItems((prev) => prev.filter((item) => item.id !== active.id));
        } else {
            setTiers((prev) => ({
                ...prev,
                [sourceTier]: prev[sourceTier].filter((item) => item.id !== active.id)
            }));
        }

        if (destinationTier === "unassigned") {
            setAvailableItems((prev) => [...prev, activeItem]);
        } else {
            setTiers((prev) => ({
                ...prev,
                [destinationTier]: [...prev[destinationTier], activeItem]
            }));
        }

        setActiveId(null);
    };

    const generateTierUrl = async (isPublic: boolean) => {
        setIsGenerating(true);
        try {
            const levels = Object.keys(tiers).map((tierName, index) => ({
                name: tierName,
                orderIndex: index + 1,
                items: tiers[tierName].map((item, itemIndex) => ({
                    itemId: item.id,
                    orderIndex: itemIndex + 1,
                })),
            }));

            const response = await fetch(`${getApiBaseUrl()}/user-tiers`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    anonymousId,
                    categoryId,
                    name: "My Custom Tier",
                    isPublic,
                    levels,
                }),
            });

            if (!response.ok) {
                throw new Error("Tierの生成に失敗しました");
            }

            const data = await response.json();
            setGeneratedUrl(data.accessUrl);

            navigator.clipboard.writeText(data.accessUrl);
            message.success("URLが生成されクリップボードにコピーされました");
        } catch (error) {
            console.error("エラー:", error);
            message.error("URLの生成に失敗しました");
        } finally {
            setIsGenerating(false);
        }
    };

    return (
        <DndContext sensors={sensors} onDragStart={handleDragStart} onDragEnd={handleDragEnd}>
            <div style={{ display: "flex", flexDirection: "column", gap: "16px" }}>
                {Object.keys(tiers).map((tierName) => (
                    <Tier key={tierName} name={tierName} items={tiers[tierName]} />
                ))}
            </div>

            <DroppableArea id="unassigned" items={availableItems}>
                <h3>未割り当てアイテム</h3>
                <div style={{ display: "flex", gap: "8px", flexWrap: "wrap" }}>
                    {availableItems.map((item) => (
                        <DraggableItem key={item.id} item={item} />
                    ))}
                </div>
            </DroppableArea>

            <div style={{ marginTop: "16px" }}>
                <Button type="default" onClick={() => generateTierUrl(true)} loading={isGenerating} className="mb-4">
                    公開してURL生成
                </Button>
                <Button type="primary" onClick={() => generateTierUrl(false)} loading={isGenerating}>
                    非公開でURL生成
                </Button>
                {generatedUrl && (
                    <div style={{ marginTop: "16px" }}>
                        <Text>生成されたURL: <a href={generatedUrl} target="_blank" rel="noopener noreferrer">{generatedUrl}</a></Text>
                    </div>
                )}
            </div>

            <DragOverlay>
                {activeId ? (() => {
                    const item = findItemById(activeId);
                    return item ? <DraggableItem item={item} isOverlay /> : null;
                })() : null}
            </DragOverlay>
        </DndContext>
    );
};

export default TierCreationScreen;
