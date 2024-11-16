import { Item } from "@/types/Item";
import { useDroppable } from "@dnd-kit/core";
import React from "react";
import DraggableItem from "./DraggableItem";

type TierProps = {
    name: string;
    items: Item[];
};

const Tier: React.FC<TierProps> = ({ name, items }) => {
    const { setNodeRef } = useDroppable({
        id: name,
    });

    return (
        <div
            ref={setNodeRef}
            style={{
                border: "1px solid #ccc",
                borderRadius: "8px",
                padding: "16px",
                backgroundColor: "#f8f9fa",
                minHeight: "120px",
                display: "flex",
                flexWrap: "wrap",
                gap: "8px",
            }}
        >
            <h3 style={{ marginBottom: "8px", width: "100%" }}>{name}</h3>
            {items.map((item) => (
                <DraggableItem key={item.id} item={item} />
            ))}
        </div>
    );
};

export default Tier;
