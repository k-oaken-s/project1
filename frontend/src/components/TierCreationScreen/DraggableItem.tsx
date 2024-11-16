import { Item } from "@/types/Item";
import { getImageUrl } from "@/utils/getImageUrl";
import { useDraggable } from "@dnd-kit/core";
import { CSS } from "@dnd-kit/utilities";
import React from "react";

type DraggableItemProps = {
    item: Item;
    isOverlay?: boolean;
};

const DraggableItem: React.FC<DraggableItemProps> = ({ item, isOverlay = false }) => {
    const { attributes, listeners, setNodeRef, transform, isDragging } = useDraggable({
        id: item.id,
    });

    const style: React.CSSProperties = {
        transform: transform ? CSS.Transform.toString(transform) : undefined,
        transition: "transform 0.2s ease",
        opacity: isDragging ? 0.5 : 1,
        width: "80px",
        height: "80px",
        borderRadius: "8px",
        overflow: "hidden",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#fff",
        boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.1)",
    };

    return (
        <div
            ref={isOverlay ? undefined : setNodeRef}
            style={style}
            {...listeners}
            {...attributes}
        >
            <img
                src={getImageUrl(item.image)}
                alt={item.name}
                style={{
                    width: "100%",
                    height: "100%",
                    objectFit: "cover",
                    borderRadius: "8px",
                }}
            />
        </div>
    );
};

export default DraggableItem;
