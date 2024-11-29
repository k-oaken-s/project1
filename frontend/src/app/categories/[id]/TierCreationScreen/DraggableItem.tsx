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
        // ドラッグ中は元のアイテムを非表示
        visibility: isDragging ? 'hidden' : 'visible',
        // ドラッグ中のtransformを無効化
        transform: isDragging ? undefined : transform ? CSS.Transform.toString(transform) : undefined,
        transition: "transform 0.2s ease",
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
            ref={isOverlay ? undefined : setNodeRef} // オーバーレイの場合はrefを設定しない
            style={style}
            {...listeners}
            {...attributes}
        >
            <ImageWrapper
                src={getImageUrl(item.image || "")}
                alt={item.name}
                width={80}
                height={80}
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
