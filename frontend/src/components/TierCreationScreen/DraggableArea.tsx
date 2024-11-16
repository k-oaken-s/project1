import { Item } from "@/types/Item";
import { useDroppable } from "@dnd-kit/core";
import React from "react";

type DroppableAreaProps = {
    id: string;
    items: Item[];
    children: React.ReactNode;
};

const DroppableArea: React.FC<DroppableAreaProps> = ({ id, items, children }) => {
    const { setNodeRef } = useDroppable({
        id,
        data: { tierName: id },
    });

    return (
        <div
            ref={setNodeRef}
            style={{
                border: "1px dashed #ccc",
                borderRadius: "8px",
                padding: "16px",
                backgroundColor: "#f9f9f9",
                minHeight: "100px",
            }}
        >
            {children}
        </div>
    );
};

export default DroppableArea;
