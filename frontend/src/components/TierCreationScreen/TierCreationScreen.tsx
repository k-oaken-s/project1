import { TierItem } from '@/types/TierItem';
import { DndContext, DragEndEvent } from '@dnd-kit/core';
import React, { useState } from 'react';
import Tier from './Tier';

type TierCreationScreenProps = {
    items: TierItem[];
};

const TierCreationScreen: React.FC<TierCreationScreenProps> = ({ items }) => {
    const [tiers, setTiers] = useState<{ [key: string]: TierItem[] }>({
        Tier1: [],
        Tier2: [],
        Tier3: [],
        Tier4: [],
    });

    // 初期アイテムをTier4に設定（例として）
    React.useEffect(() => {
        setTiers((prev) => ({ ...prev, Tier4: items }));
    }, [items]);

    const handleDragEnd = (event: DragEndEvent) => {
        const { active, over } = event;

        if (active.id === over?.id) return;

        if (!over) return;

        const sourceTier = findTierByItemId(Number(active.id));
        const destinationTier = findTierByItemId(Number(over.id));

        if (sourceTier && destinationTier) {
            const activeItemIndex = tiers[sourceTier].findIndex((item) => item.id === active.id);
            const overItemIndex = tiers[destinationTier].findIndex((item) => item.id === over.id);

            // 同じTier内での並び替え
            if (sourceTier === destinationTier) {
                const updatedItems = [...tiers[sourceTier]];
                const [movedItem] = updatedItems.splice(activeItemIndex, 1);
                updatedItems.splice(overItemIndex, 0, movedItem);

                setTiers({
                    ...tiers,
                    [sourceTier]: updatedItems,
                });
            } else {
                // 異なるTier間での移動
                const sourceItems = [...tiers[sourceTier]];
                const [movedItem] = sourceItems.splice(activeItemIndex, 1);
                const destinationItems = [...tiers[destinationTier]];
                destinationItems.splice(overItemIndex, 0, movedItem);

                setTiers({
                    ...tiers,
                    [sourceTier]: sourceItems,
                    [destinationTier]: destinationItems,
                });
            }
        }
    };

    const findTierByItemId = (id: number) => {
        return Object.keys(tiers).find((tierName) =>
            tiers[tierName].some((item) => item.id === id)
        );
    };

    return (
        <DndContext onDragEnd={handleDragEnd}>
            {Object.keys(tiers).map((tierName) => (
                <Tier key={tierName} name={tierName} items={tiers[tierName]} />
            ))}
        </DndContext>
    );
};

export default TierCreationScreen;
