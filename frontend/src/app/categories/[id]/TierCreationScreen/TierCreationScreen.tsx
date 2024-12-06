"use client";

import {Item} from "@/types/Item";
import {getAnonymousId} from "@/utils/getAnonymousId";
import {
    DndContext,
    DragEndEvent,
    DragOverlay,
    DragStartEvent,
    PointerSensor,
    rectIntersection,
    useSensor,
    useSensors,
} from "@dnd-kit/core";
import {arrayMove, rectSortingStrategy, SortableContext} from "@dnd-kit/sortable";
import {useState} from "react";
import {Input, message, Typography} from "antd";
import SortableTier from "./SortableTier";
import DraggableItem from "./DraggableItem";
import ImageWrapper from "@/components/ImageWrapper";
import {getImageUrl} from "@/utils/getImageUrl";
import Tier from "@/app/categories/[id]/TierCreationScreen/Tier";

const {Text, Title} = Typography;

// Tier の構造を型定義
type Tier = {
    name: string;
    items: Item[];
};

type Tiers = Record<string, Tier>;

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
    const initialTiers: Tiers = {
        Tier1: {name: "Tier 1", items: []},
        Tier2: {name: "Tier 2", items: []},
        Tier3: {name: "Tier 3", items: []},
        Tier4: {name: "Tier 4", items: []},
    };

    const [tiers, setTiers] = useState<Tiers>(initialTiers);
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

    const tierColors: Record<string, string> = {
        Tier1: "#2A1536",
        Tier2: "#3A1A1A",
        Tier3: "#3A2A15",
        Tier4: "#152641",
        Tier5: "#153A29",
        default: "#2D2D2D", // グレー背景 (Tier6以降)
        unassigned: "#8A8A8A",
    };


    const findItemById = (id: string): Item | null => {
        const allItems = [...availableItems, ...Object.values(tiers).flatMap((tier) => tier.items)];
        return allItems.find((item) => item.id === id) || null;
    };

    const handleDragStart = (event: DragStartEvent) => {
        const {active} = event;
        if (tierOrder.includes(active.id as string)) {
            // Tierをドラッグ開始
            setActiveTierId(active.id as string);
        } else {
            // アイテムをドラッグ開始
            setActiveId(active.id as string);
        }
    };

    const handleDragEnd = (event: DragEndEvent) => {
        const {active, over} = event;

        if (activeTierId && over && tierOrder.includes(active.id as string)) {
            // Tierのドラッグ終了処理
            if (active.id !== over.id) {
                const oldIndex = tierOrder.indexOf(active.id as string);
                const newIndex = tierOrder.indexOf(over.id as string);
                setTierOrder((items) => arrayMove(items, oldIndex, newIndex));
            }
            setActiveTierId(null);
        } else if (activeId) {
            // アイテムのドラッグ終了処理
            if (!over) {
                setActiveId(null);
                return;
            }

            const activeItem = findItemById(active.id as string);
            if (!activeItem) {
                setActiveId(null);
                return;
            }

            const sourceTier =
                Object.keys(tiers).find((key) =>
                    tiers[key].items.some((item) => item.id === active.id)
                ) || "unassigned";
            const destinationTier = over.id as string;

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
            onDragEnd={handleDragEnd}
            collisionDetection={rectIntersection}
        >
            <div className="text-center mb-8">
                <div
                    className="flex justify-center items-center p-6 mb-6">
                    <ImageWrapper
                        src={getImageUrl(categoryImageUrl)}
                        alt={categoryName}
                        className="w-24 h-24 object-cover rounded-lg mr-6 shadow-md"
                    />
                    <h2 className="text-gray-300 text-3xl">
                        {categoryName}
                    </h2>
                </div>
                <label htmlFor="tierNameInput" className="block text-lg font-medium text-gray-300 mb-2">
                    Your Tier Name
                </label>
                <Input
                    id="tierNameInput"
                    value={tierName}
                    onChange={(e) => setTierName(e.target.value)}
                    placeholder="例: 最強キャラランキング"
                    style={{
                        width: "100%",
                        height: "45px",
                        fontSize: "16px",
                        backgroundColor: "#444",
                        color: "#fff",
                        borderRadius: "6px",
                        border: "1px solid #666",
                        padding: "0 10px",
                    }}
                    className="focus:outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500 transition"
                />
                {/*</div>*/}
            </div>

            <SortableContext items={tierOrder} strategy={rectSortingStrategy}>
                <div
                    style={{
                        display: "flex",
                        flexDirection: "column",
                        rowGap: "1px",
                    }}
                >
                    {tierOrder.map((tierKey) => (
                        <SortableTier
                            key={tierKey}
                            id={tierKey}
                            name={tiers[tierKey].name}
                            items={tiers[tierKey].items}
                            onNameChange={(newName) => handleTierNameChange(tierKey, newName)}
                            backgroundColor={tierColors[tierKey] || tierColors.default}
                        />
                    ))}
                </div>
            </SortableContext>

            <div
                className="mt-8 p-4 rounded-md shadow-md"
                style={{
                    backgroundColor: tierColors.unassigned,
                    minHeight: "150px",
                }}
            >
                <h3 className="text-lg font-semibold mb-4" style={{color: "#333"}}>
                    未割り当てアイテム
                </h3>
                <div className="flex gap-4 flex-wrap">
                    {availableItems.map((item) => (
                        <DraggableItem key={item.id} item={item}/>
                    ))}
                </div>
            </div>

            <div className="mt-8 text-center">
                <button
                    onClick={() => generateTierUrl(true)}
                    disabled={isGenerating}
                    className={`mb-4 mr-4 px-6 py-3 rounded-lg font-semibold text-base transition duration-200 ${
                        isGenerating
                            ? 'bg-gray-700 text-gray-500 cursor-not-allowed'
                            : 'bg-gray-800 text-white hover:bg-gray-700'
                    }`}
                >
                    公開してURL生成
                </button>
                <button
                    onClick={() => generateTierUrl(false)}
                    disabled={isGenerating}
                    className={`px-6 py-3 rounded-lg font-semibold text-base transition duration-200 ${
                        isGenerating
                            ? 'bg-gray-700 text-gray-500 cursor-not-allowed'
                            : 'bg-gray-800 text-white hover:bg-gray-700'
                    }`}
                >
                    非公開でURL生成
                </button>
                {generatedUrl && (
                    <div className="mt-6">
                        <p className="text-gray-400">
                            生成されたURL:{" "}
                            <a
                                href={generatedUrl}
                                target="_blank"
                                rel="noopener noreferrer"
                                className="text-blue-400 underline hover:text-blue-300"
                            >
                                {generatedUrl}
                            </a>
                        </p>
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
                        backgroundColor={tierColors[activeTierId]}
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
