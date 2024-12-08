import {Item} from "@/types/Item";
import {getImageUrl} from "@/utils/getImageUrl";
import {useDraggable} from "@dnd-kit/core";
import {CSS} from "@dnd-kit/utilities";
import React from "react";
import ImageWrapper from "@/components/ImageWrapper";

type DraggableItemProps = {
    item: Item;
    isOverlay?: boolean;
};

const DraggableItem: React.FC<DraggableItemProps> = ({item, isOverlay = false}) => {
    const {attributes, listeners, setNodeRef, transform, isDragging} = useDraggable({
        id: item.id,
    });

    // スタイル設定
    const style: React.CSSProperties = {
        visibility: isDragging ? "hidden" : "visible",
        transform: !isDragging && transform ? CSS.Transform.toString(transform) : undefined,
        transition: "transform 0.2s ease",
        width: "120px",
        height: "120px",
        borderRadius: "8px",
        overflow: "hidden",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#fff",
        boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.1)",
        position: "relative",
    };

    const textStyle: React.CSSProperties = {
        position: "absolute",
        bottom: 0,
        width: "100%",
        backgroundColor: "rgba(0, 0, 0, 0.6)",
        color: "#fff",
        textAlign: "center",
        fontSize: "14px",
        fontWeight: "bold",
        padding: "4px 0",
    };

    return (
        <div
            ref={isOverlay ? undefined : (node) => setNodeRef(node)} // 動的にrefを設定
            style={style}
            {...listeners}
            {...attributes}
        >
            <ImageWrapper
                src={getImageUrl(item.image || "")}
                alt={item.name}
                width={120}
                height={120}
                style={{
                    width: "100%",
                    height: "100%",
                    objectFit: "cover",
                    borderRadius: "8px",
                }}
            />
            <div style={textStyle}>{item.name}</div>
        </div>
    );
};

export default DraggableItem;
