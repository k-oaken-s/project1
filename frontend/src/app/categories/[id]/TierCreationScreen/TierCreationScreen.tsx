"use client";

import {Item} from "@/types/Item";
import {getAnonymousId} from "@/utils/getAnonymousId";
import {
    closestCenter,
    DndContext,
    DragEndEvent,
    DragOverlay,
    DragStartEvent,
    PointerSensor,
    useSensor,
    useSensors,
} from "@dnd-kit/core";
import {arrayMove, SortableContext, verticalListSortingStrategy,} from "@dnd-kit/sortable";
import React, {useState} from "react";
import {Button, Input, message, Typography} from "antd";
import DroppableArea from "./DraggableArea";
import DraggableItem from "./DraggableItem";
import Tier from "./Tier";
import SortableTier from "./SortableTier"; // 新規作成
import ImageWrapper from "@/components/ImageWrapper";
import {getImageUrl} from "@/utils/getImageUrl";

const {Text, Title} = Typography;

type TierCreationScreenProps = {
    items: Item[];
    categoryId: string;
    categoryName: string;
    categoryImageUrl: string;
};

const TierCreationScreen: React.FC<TierCreationScreenProps> = ({
                                                                   items,
                                                                   categoryId,
                                                                   categoryName,
                                                                   categoryImageUrl,
                                                               }) => {
    const initialTiers = {
        Tier1: {name: "Tier 1", items: []},
        Tier2: {name: "Tier 2", items: []},
        Tier3: {name: "Tier 3", items: []},
        Tier4: {name: "Tier 4", items: []},
    };

    const [tiers, setTiers] = useState<{ [key: string]: { name: string; items: Item[] } }>(initialTiers);
    const [tierOrder, setTierOrder] = useState<string[]>(Object.keys(initialTiers));

    const [tierName, setTierName] = useState<string>(""); // Tier全体の名前
    const [availableItems, setAvailableItems] = useState<Item[]>(items);
    const [activeId, setActiveId] = useState<string | null>(null);
    const [activeTierId, setActiveTierId] = useState<string | null>(null);
    const [generatedUrl, setGeneratedUrl] = useState<string | null>(null);
    const [isGenerating, setIsGenerating] = useState(false);

    const sensors = useSensors(
        useSensor(PointerSensor, {activationConstraint: {distance: 5}})
    );

    const anonymousId = getAnonymousId();

    const findItemById = (id: string): Item | null => {
        const allItems = [...availableItems, ...Object.values(tiers).flatMap((tier) => tier.items)];
        return allItems.find((item) => item.id === id) || null;
    };

    const handleDragStart = (event: DragStartEvent) => {
        const {active} = event;
        if (tierOrder.includes(String(active.id))) {
            // Tierをドラッグ開始
            setActiveTierId(String(active.id));
        } else {
            // アイテムをドラッグ開始
            setActiveId(String(active.id));
        }
    };

    const handleDragOver = (event: any) => {
        // 特に処理が必要なければ空でOK
    };

    const handleDragEnd = (event: DragEndEvent) => {
        const {active, over} = event;

        if (activeTierId && over && tierOrder.includes(String(active.id))) {
            // Tierのドラッグ終了処理
            if (active.id !== over.id) {
                const oldIndex = tierOrder.indexOf(String(active.id));
                const newIndex = tierOrder.indexOf(String(over.id));
                setTierOrder((items) => arrayMove(items, oldIndex, newIndex));
            }
            setActiveTierId(null);
        } else if (activeId) {
            // アイテムのドラッグ終了処理
            if (!over) {
                setActiveId(null);
                return;
            }

            const activeItem = findItemById(String(active.id));
            if (!activeItem) {
                setActiveId(null);
                return;
            }

            const sourceTier =
                Object.keys(tiers).find((key) => tiers[key].items.some((item) => item.id === active.id)) || "unassigned";
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
                    [sourceTier]: {
                        ...prev[sourceTier],
                        items: prev[sourceTier].items.filter((item) => item.id !== active.id),
                    },
                }));
            }

            if (destinationTier === "unassigned") {
                setAvailableItems((prev) => [...prev, activeItem]);
            } else {
                setTiers((prev) => ({
                    ...prev,
                    [destinationTier]: {
                        ...prev[destinationTier],
                        items: [...prev[destinationTier].items, activeItem],
                    },
                }));
            }

            setActiveId(null);
        }
    };

    const handleTierNameChange = (tierKey: string, newName: string) => {
        setTiers((prev) => ({
            ...prev,
            [tierKey]: {
                ...prev[tierKey],
                name: newName,
            },
        }));
    };

    const generateTierUrl = async (isPublic: boolean) => {
        if (!tierName.trim()) {
            message.error("Tier全体の名前を入力してください");
            return;
        }

        setIsGenerating(true);
        try {
            const levels = tierOrder.map((tierKey, index) => ({
                name: tiers[tierKey].name,
                orderIndex: index + 1,
                items: tiers[tierKey].items.map((item, itemIndex) => ({
                    itemId: item.id,
                    orderIndex: itemIndex + 1,
                })),
            }));

            const response = await fetch(`/user-tiers`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    anonymousId,
                    categoryId,
                    name: tierName, // 入力されたTier全体の名前
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
        <DndContext
            sensors={sensors}
            onDragStart={handleDragStart}
            onDragOver={handleDragOver}
            onDragEnd={handleDragEnd}
            collisionDetection={closestCenter}
        >
            <div className="text-center mb-6">
                <Title level={2}>{categoryName}</Title>
                <ImageWrapper
                    src={getImageUrl(categoryImageUrl)}
                    alt={categoryName}
                    layout="responsive"
                    width={700}
                    height={475}
                    style={{maxWidth: "100%", height: "auto"}}
                />
                <Input
                    value={tierName}
                    onChange={(e) => setTierName(e.target.value)}
                    placeholder="Tier全体の名前を入力してください"
                    style={{
                        width: "80%",
                        height: "50px",
                        fontSize: "18px",
                        margin: "20px auto",
                        display: "block",
                    }}
                    className="mt-4"
                />
            </div>

            <SortableContext items={tierOrder} strategy={verticalListSortingStrategy}>
                <div className="space-y-8">
                    {tierOrder.map((tierKey) => (
                        <SortableTier
                            key={tierKey}
                            id={tierKey}
                            name={tiers[tierKey].name}
                            items={tiers[tierKey].items}
                            onNameChange={(newName) => handleTierNameChange(tierKey, newName)}
                        />
                    ))}
                </div>
            </SortableContext>

            <div className="mt-8">
                <DroppableArea id="unassigned" items={availableItems}>
                    <h3 className="text-lg font-semibold mb-4">未割り当てアイテム</h3>
                    <div className="flex gap-4 flex-wrap">
                        {availableItems.map((item) => (
                            <DraggableItem key={item.id} item={item}/>
                        ))}
                    </div>
                </DroppableArea>
            </div>

            <div className="mt-8 text-center">
                <Button type="default" onClick={() => generateTierUrl(true)} loading={isGenerating}
                        className="mb-4 mr-4">
                    公開してURL生成
                </Button>
                <Button type="primary" onClick={() => generateTierUrl(false)} loading={isGenerating}>
                    非公開でURL生成
                </Button>
                {generatedUrl && (
                    <div className="mt-4">
                        <Text>
                            生成されたURL:{" "}
                            <a href={generatedUrl} target="_blank" rel="noopener noreferrer">
                                {generatedUrl}
                            </a>
                        </Text>
                    </div>
                )}
            </div>

            <DragOverlay>
                {activeTierId ? (
                    <Tier
                        id={activeTierId}
                        name={tiers[activeTierId].name}
                        items={tiers[activeTierId].items}
                        onNameChange={(newName) => handleTierNameChange(activeTierId, newName)}
                        isOverlay
                    />
                ) : activeId ? (
                    (() => {
                        const item = findItemById(activeId);
                        return item ? <DraggableItem item={item} isOverlay/> : null;
                    })()
                ) : null}
            </DragOverlay>
        </DndContext>
    );
};

export default TierCreationScreen;
