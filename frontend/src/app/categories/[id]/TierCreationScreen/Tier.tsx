// components/Tier.tsx

import React from "react";
import {Item} from "@/types/Item";
import {Typography} from "antd";
import DraggableItem from "./DraggableItem";
import DroppableArea from "./DraggableArea";

const {Title} = Typography;

type TierProps = {
    id: string;
    name: string;
    items: Item[];
    onNameChange: (newName: string) => void;
    isOverlay?: boolean;
};

const Tier: React.FC<TierProps> = ({id, name, items, onNameChange, isOverlay = false}) => {
    return (
        <div className={`p-4 border rounded shadow-sm ${isOverlay ? "bg-white" : ""}`}>
            <DroppableArea id={id} items={items}>
                <Title
                    level={4}
                    editable={{
                        onChange: onNameChange,
                    }}
                    className="mb-4 cursor-move"
                >
                    {name}
                </Title>
                <div className="flex gap-4 flex-wrap">
                    {items.map((item) => (
                        <DraggableItem key={item.id} item={item}/>
                    ))}
                </div>
            </DroppableArea>
        </div>
    );
};

export default Tier;
